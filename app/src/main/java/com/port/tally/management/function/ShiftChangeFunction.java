package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/12/15.
 */

import android.content.Context;
import android.util.Log;

import com.port.tally.management.bean.ShiftChange;
import com.port.tally.management.database.ShiftChangeOperator;
import com.port.tally.management.work.PullShiftChangeContent;

import org.mobile.library.global.GlobalApplication;
import org.mobile.library.model.work.WorkBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
     * 构造函数
     *
     * @param context 上下文
     */
    public ShiftChangeFunction(Context context) {
        operator = new ShiftChangeOperator(context);
    }

    /**
     * 获取下一组消息数据，每次获取数量为{@link #INCREMENT}
     *
     * @return 消息列表
     */
    public List<ShiftChange> next() {

        if (isEnd) {
            Log.i(LOG_TAG + "next", "now end");
            return new ArrayList<>();
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
        currentRow = position;
        isEnd = false;
    }

    /**
     * 获取最新消息记录
     *
     * @param listener 请求完成的监听器，返回最新的消息记录
     */
    public void getLatest(final ShiftChangeRequestListener<List<ShiftChange>> listener) {

        // 最近的记录时间
        String time = null;

        // 得到近的一条记录数据
        List<ShiftChange> shiftChangeList = operator.queryWithCondition(String.valueOf(0), String
                .valueOf(1));

        if (shiftChangeList.size() > 0) {
            time = shiftChangeList.get(0).getTime();
            Log.i(LOG_TAG + "getLatest", "found latest time is " + time);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -3);
            time = format.format(calendar.getTime());
            Log.i(LOG_TAG + "getLatest", "no content, use three days ago time is " + time);
        }

        PullShiftChangeContent pullShiftChangeContent = new PullShiftChangeContent();

        pullShiftChangeContent.setWorkEndListener(new WorkBack<List<ShiftChange>>() {
            @Override
            public void doEndWork(boolean state, List<ShiftChange> shiftChanges) {
                if (state && shiftChanges != null) {
                    currentRow += shiftChanges.size();
                    operator.insert(shiftChanges);
                }

                if (listener != null) {
                    listener.onRequestEnd(state, shiftChanges);
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
     * @param shiftChange 要填充的交接班对象
     * @param listener    请求完成的监听器
     */
    public void fillFromNetwork(ShiftChange shiftChange, ShiftChangeRequestListener<ShiftChange>
            listener) {

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

        operator.insert(shiftChange);
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
