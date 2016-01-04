package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/12/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.port.tally.management.bean.ShiftChange;
import com.port.tally.management.database.ShiftChangeOperator;
import com.port.tally.management.work.PullShiftChangeContent;
import com.port.tally.management.work.ShiftChangeWork;

import org.mobile.library.global.GlobalApplication;
import org.mobile.library.model.work.WorkBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 交接班消息管理器
 *
 * @author 超悟空
 * @version 1.0 2015/12/15
 * @since 1.0
 */
public class ShiftChangeFunction {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeFunction.";

    /**
     * 用于从配置文件获取最近消息时间
     */
    private static final String LATEST_TIME_TAG = "latest_time_tag";

    /**
     * 每次读取的记录数
     */
    private static final int INCREMENT = 5;

    /**
     * 当前读取的记录位置
     */
    private int currentRow = 0;

    /**
     * 标识是否已经到了最后
     */
    private boolean isEnd = false;

    /**
     * 数据库工具
     */
    private ShiftChangeOperator operator = null;

    /**
     * 配置工具
     */
    private SharedPreferences sharedPreferences = null;

    /**
     * 记录正在执行网络请求的任务
     */
    private Map<String, ShiftChangeTask> networkTask = null;

    /**
     * 网络任务
     */
    private class ShiftChangeTask {

        /**
         * 标识网络请求结果
         */
        public volatile boolean result = false;

        /**
         * 标识网络请求是否完成
         */
        public volatile boolean finish = false;

        /**
         * 请求结果数据集
         */
        public volatile ShiftChange shiftChange = null;

        /**
         * 通知列表
         */
        public Stack<ShiftChangeRequestListener<ShiftChange>> callback = new Stack<>();
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public ShiftChangeFunction(Context context) {
        operator = new ShiftChangeOperator(context);
        networkTask = new ConcurrentHashMap<>();

        sharedPreferences = context.getSharedPreferences("shift_change", Context.MODE_PRIVATE);
    }

    /**
     * 获取下一组消息数据，每次获取数量为{@link #INCREMENT}
     *
     * @return 消息列表
     */
    public List<ShiftChange> next() {

        if (isEnd) {
            Log.i(LOG_TAG + "next", "now end");
            return new ArrayList<>(1);
        }

        List<ShiftChange> shiftChangeList = operator.queryWithCondition(String.valueOf
                (currentRow), String.valueOf(currentRow + INCREMENT));

        currentRow += shiftChangeList.size();

        if (shiftChangeList.size() < INCREMENT) {
            isEnd = true;
        }

        Log.i(LOG_TAG + "next", "plus:" + shiftChangeList.size() + " now index:" + currentRow);
        return shiftChangeList;
    }

    /**
     * 获取下一条消息数据
     *
     * @return 消息对象，如果已到末尾则返回null
     */
    public ShiftChange nextOne() {
        if (isEnd) {
            Log.i(LOG_TAG + "nextOne", "now end");
            return null;
        }

        List<ShiftChange> shiftChangeList = operator.queryWithCondition(String.valueOf
                (currentRow), String.valueOf(currentRow + 1));

        currentRow += shiftChangeList.size();

        Log.i(LOG_TAG + "nextOne", "now index:" + currentRow);
        if (shiftChangeList.size() == 0) {
            isEnd = true;
            return null;
        } else {
            return shiftChangeList.get(0);
        }
    }

    /**
     * 表示是否还有数据
     *
     * @return true表示还有未读取的值
     */
    public boolean hasNext() {
        return !isEnd;
    }

    /**
     * 根据消息标识获取消息
     *
     * @return 消息对象，未找到则返回null
     */
    public ShiftChange findByToken(String token) {
        Log.i(LOG_TAG + "findByToken", "target token is " + token);
        List<ShiftChange> shiftChangeList = operator.queryWithCondition(token);

        if (shiftChangeList.size() == 0) {
            Log.i(LOG_TAG + "findByToken", "no token:" + token);
            return null;
        } else {
            Log.i(LOG_TAG + "findByToken", "found token:" + token);
            return shiftChangeList.get(0);
        }
    }

    /**
     * 将消息读取索引移动到第一条位置
     */
    public void moveToFirst() {
        move(0);
    }

    /**
     * 移动消息读取索引到指定位置
     *
     * @param position 目标位置
     */
    public void move(int position) {
        Log.i(LOG_TAG + "move", "move to " + position);

        if (position < currentRow) {
            isEnd = false;
        }

        currentRow = position;
    }

    /**
     * 获取最新消息记录
     *
     * @param listener 请求完成的监听器，返回最新的消息记录
     */
    public void getLatest(final ShiftChangeRequestListener<List<ShiftChange>> listener) {

        // 最近的记录时间
        String time = sharedPreferences.getString(LATEST_TIME_TAG + "_" + GlobalApplication
                .getGlobal().getLoginStatus().getUserID(), null);

        if (time == null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -3);
            time = format.format(calendar.getTime());
            sharedPreferences.edit().putString(LATEST_TIME_TAG + "_" + GlobalApplication
                    .getGlobal().getLoginStatus().getUserID(), time).apply();
            Log.i(LOG_TAG + "getLatest", "no content, use three days ago time is " + time);
        }

        PullShiftChangeContent pullShiftChangeContent = new PullShiftChangeContent();

        pullShiftChangeContent.setWorkEndListener(new WorkBack<List<ShiftChange>>() {
            @Override
            public void doEndWork(boolean state, List<ShiftChange> shiftChanges) {
                if (state && shiftChanges != null && !shiftChanges.isEmpty()) {
                    Log.i(LOG_TAG + "getLatest", "shiftChanges count is " + shiftChanges.size());
                    // 记录新时间
                    ShiftChange sc = shiftChanges.get(0);
                    sharedPreferences.edit().putString(LATEST_TIME_TAG + "_" + GlobalApplication
                            .getGlobal().getLoginStatus().getUserID(), sc.getTime()).apply();

                    List<Long> idList = operator.insert(shiftChanges);
                    Log.i(LOG_TAG + "getLatest", "id list count is " + idList.size());
                    List<ShiftChange> list;
                    if (idList.size() < shiftChanges.size()) {
                        list = new ArrayList<>();
                        for (long id : idList) {
                            ShiftChange shiftChange = operator.queryById(id);
                            if (shiftChange != null) {
                                list.add(shiftChange);
                            }
                        }
                    } else {
                        list = shiftChanges;
                    }

                    Log.i(LOG_TAG + "getLatest", "final ShiftChange list count is " + list.size());
                    currentRow += list.size();

                    if (listener != null) {
                        if (list.size() == 0) {
                            listener.onRequestEnd(false, null);
                        } else {
                            listener.onRequestEnd(true, list);
                        }
                    }
                } else {
                    if (listener != null) {
                        listener.onRequestEnd(false, null);
                    }
                }
            }
        }, false);

        pullShiftChangeContent.beginExecute(GlobalApplication.getGlobal().getLoginStatus()
                .getUserID(), time);
    }

    /**
     * 向服务器请求填充一个消息对象，
     * 此消息对象可能是本机发送过的一条消息，
     * 用于填充已丢失的图片缓存网络地址
     *
     * @param token    要填充的交接班标识
     * @param listener 请求完成的监听器
     */
    public void fillFromNetwork(final String token, final ShiftChangeRequestListener<ShiftChange>
            listener) {
        if (token == null || token.isEmpty()) {
            if (listener != null) {
                listener.onRequestEnd(false, null);
            }
            return;
        }

        Log.i(LOG_TAG + "fillFromNetwork", "request shiftChange token is " + token);

        if (!networkTask.containsKey(token)) {

            // 从未下载过，开启下载
            networkTask.put(token, new ShiftChangeTask());

            ShiftChangeWork shiftChangeWork = new ShiftChangeWork();

            shiftChangeWork.setWorkEndListener(new WorkBack<ShiftChange>() {
                @Override
                public void doEndWork(boolean state, ShiftChange data) {
                    if (state && data != null) {
                        // 填充数据
                        data.setMySend(false);

                        // 保存到数据库
                        operator.update(data);
                    }

                    ShiftChangeTask task = networkTask.get(token);
                    task.finish = true;
                    task.result = state;
                    task.shiftChange = data;

                    if (listener != null) {
                        listener.onRequestEnd(state, data);
                    }

                    // 通知其他事件
                    while (!task.callback.empty()) {
                        task.callback.pop().onRequestEnd(state, data);
                    }
                }
            }, false);

            shiftChangeWork.beginExecute(token);
        } else {
            ShiftChangeTask task = networkTask.get(token);
            if (task.finish) {
                // 已经请求完成
                if (listener != null) {
                    listener.onRequestEnd(task.result, task.shiftChange);
                }
            } else {
                // 正在请求，挂载通知
                if (listener != null) {
                    task.callback.push(listener);
                }
            }
        }
    }

    /**
     * 保存一条消息，用于发送消息后将新消息数据保存到本地
     *
     * @param shiftChange 新消息
     */
    public void save(ShiftChange shiftChange) {
        if (shiftChange == null || shiftChange.getToken() == null) {
            return;
        }

        sharedPreferences.edit().putString(LATEST_TIME_TAG + "_" + GlobalApplication.getGlobal()
                .getLoginStatus().getUserID(), shiftChange.getTime()).apply();
        operator.insert(shiftChange);
    }

    /**
     * 移除一条消息数据
     *
     * @param token 消息标识
     */
    public void remove(String token) {
        Log.i(LOG_TAG + "remove", "remove shiftChange token is " + token);

        ShiftChange shiftChange = new ShiftChange();
        shiftChange.setToken(token);
        operator.delete(shiftChange);
        currentRow--;
    }

    /**
     * 请求结束监听
     *
     * @param <DATA> 结果数据类型
     */
    public interface ShiftChangeRequestListener<DATA> {

        /**
         * 请求完成
         *
         * @param result 请求结果
         * @param data   返回数据
         */
        void onRequestEnd(boolean result, DATA data);
    }
}
