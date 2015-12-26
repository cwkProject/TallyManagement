package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/11.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.port.tally.management.R;
import com.port.tally.management.activity.ImageShowActivity;
import com.port.tally.management.adapter.ShiftChangeContentRecyclerViewAdapter;
import com.port.tally.management.bean.ShiftChange;
import com.port.tally.management.bean.ShiftChangeContent;
import com.port.tally.management.function.AudioFileLengthFunction;
import com.port.tally.management.function.ShiftChangeFunction;
import com.port.tally.management.holder.ShiftChangeContentAudioViewHolder;
import com.port.tally.management.holder.ShiftChangeContentImageViewHolder;
import com.port.tally.management.holder.ShiftChangeContentViewHolder;
import com.port.tally.management.service.ShiftChangeService;
import com.port.tally.management.util.CacheKeyUtil;
import com.port.tally.management.util.ImageUtil;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.SendShiftChangeWork;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.global.GlobalApplication;
import org.mobile.library.model.operate.DataChangeObserver;
import org.mobile.library.model.work.WorkBack;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 交接班正文内容列表片段
 *
 * @author 超悟空
 * @version 1.0 2015/12/11
 * @since 1.0
 */
public class ShiftChangeContentFragment extends Fragment {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeContentFragment.";

    /**
     * 线程池线程数
     */
    private static final int POOL_COUNT = Runtime.getRuntime().availableProcessors() * 3 + 2;

    /**
     * 控件集
     */
    private class LocalViewHolder {

        /**
         * 交接班信息正文列表缓存工具
         */
        public CacheTool contentCacheTool = null;

        /**
         * 交接班消息管理功能
         */
        public ShiftChangeFunction shiftChangeFunction = null;

        /**
         * 内容列表
         */
        public RecyclerView contentRecyclerView = null;

        /**
         * 内容列表数据适配器
         */
        public ShiftChangeContentRecyclerViewAdapter adapter = null;

        /**
         * 内容数据源列表(与数据适配器进行双向绑定)
         */
        public List<ShiftChangeContent> dataList = null;

        /**
         * 音频播放器
         */
        public MediaPlayer mediaPlayer = null;

        /**
         * 当前正在播放音频的对应图标控件
         */
        public ImageView audioImageView = null;

        /**
         * 缩略图宽度
         */
        public int thumbnailWidth = 0;

        /**
         * 用于执行服务端功能的接口
         */
        public ShiftChangeService.ShiftChangeServiceBinder binder = null;

        /**
         * 用于接收服务绑定回调
         */
        public ServiceConnection connection = null;

        /**
         * 线程池
         */
        public ExecutorService taskExecutor = null;

        /**
         * 表示是否正在加载旧数据或请求新数据
         */
        public volatile boolean loading = false;
    }

    /**
     * 控件集对象
     */
    private LocalViewHolder viewHolder = new LocalViewHolder();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // 根布局
        View rootView = inflater.inflate(R.layout.fragment_shift_change_content_list, container,
                false);

        // 初始化布局
        initView(rootView);

        // 初始化数据
        initData();

        return rootView;
    }

    /**
     * 初始化布局
     *
     * @param rootView 根布局
     */
    private void initView(View rootView) {
        // 初始化控件引用
        initViewHolder(rootView);

        // 初始化内容列表
        initContentList();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder(View rootView) {

        viewHolder.contentRecyclerView = (RecyclerView) rootView.findViewById(R.id
                .fragment_shift_change_content_recyclerView);

        viewHolder.contentCacheTool = CacheManager.getCacheTool("shift_change_content");

        viewHolder.mediaPlayer = new MediaPlayer();

        viewHolder.dataList = new ArrayList<>();

        viewHolder.taskExecutor = Executors.newFixedThreadPool(POOL_COUNT);

        viewHolder.shiftChangeFunction = new ShiftChangeFunction(getContext());

        viewHolder.thumbnailWidth = getResources().getDisplayMetrics().widthPixels / 3;
    }

    /**
     * 初始化内容列表
     */
    private void initContentList() {
        RecyclerView recyclerView = viewHolder.contentRecyclerView;

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 初始化适配器
        viewHolder.adapter = new ShiftChangeContentRecyclerViewAdapter(getActivity(), viewHolder
                .contentCacheTool, viewHolder.dataList);

        // 设置绑定监听器
        viewHolder.adapter.setBindGridItemViewHolderListener(new ShiftChangeContentRecyclerViewAdapter.BindGridItemViewHolderListener() {
            @Override
            public void onBind(final int position, final Object holder, final String key, int
                    contentType, int progress) {

                // 消息标识
                final ShiftChangeContent shiftChangeContent = viewHolder.dataList.get(position);

                // 此处绑定不同的操作
                switch (contentType) {
                    case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                        // 图片布局管理器
                        final ShiftChangeContentImageViewHolder imageViewHolder =
                                (ShiftChangeContentImageViewHolder) holder;

                        if (progress == 100) {
                            // 文件加载完成
                            // 图片展示
                            imageViewHolder.rootItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(imageViewHolder.imageView, key);
                                }
                            });
                        } else if (progress == -1) {
                            // 上传或下载失败
                            imageViewHolder.textView.setText(R.string.load_failed);
                            imageViewHolder.rootItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 重新传输
                                    shiftChangeContent.getImageList().put(key, 0);
                                    imageViewHolder.textView.setText("0%");

                                    if (shiftChangeContent.isSend()) {
                                        uploadImage(shiftChangeContent.getToken(), key);
                                    } else {
                                        downloadImage(shiftChangeContent.getToken(), key);
                                    }
                                }
                            });
                        } else {
                            // 上传或下载中
                            imageViewHolder.rootItem.setOnClickListener(null);
                        }

                        break;
                    case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                        // 音频布局管理器
                        final ShiftChangeContentAudioViewHolder audioViewHolder =
                                (ShiftChangeContentAudioViewHolder) holder;

                        if (progress == 100) {
                            // 文件加载完成
                            // 音频播放
                            audioViewHolder.rootItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    playAudio(key, audioViewHolder.imageView);
                                }
                            });
                        } else if (progress == -1) {
                            // 上传或下载失败
                            audioViewHolder.textView.setText(R.string.load_failed);
                            audioViewHolder.rootItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 重新传输
                                    shiftChangeContent.getAudioList().put(key, 0);
                                    audioViewHolder.textView.setText("0%");
                                    if (shiftChangeContent.isSend()) {
                                        uploadAudio(shiftChangeContent.getToken(), key);
                                    } else {
                                        downloadAudio(shiftChangeContent.getToken(), key);
                                    }
                                }
                            });
                        } else {
                            // 上传或下载中
                            audioViewHolder.rootItem.setOnClickListener(null);
                        }
                        break;
                }
            }
        });

        recyclerView.setAdapter(viewHolder.adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 表示向上还是向下滚动，true表示向上滚动
            private boolean upScroll = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                upScroll = dy < 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_SETTLING && upScroll && !viewHolder
                        .loading && viewHolder.shiftChangeFunction.hasNext() && recyclerView
                        .getChildAdapterPosition(recyclerView.getChildAt(0)) <= 0) {
                    // 停止向上滚动，并且当前不在加载数据状态，并且有更多数据，并且当前在列表第一个位置时
                    // 加载更多数据
                    loadOldData();
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        // 启动并绑定服务
        initService();
    }

    /**
     * 启动并绑定服务
     */
    private void initService() {

        // 用于接收服务端通信的消息管理器
        final Messenger messenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Bundle bundle = msg.getData();

                final int what = msg.what;
                final String token = bundle.getString(ShiftChangeService.TOKEN_TAG);
                final String key = bundle.getString(ShiftChangeService.KEY_TAG);
                final int type = bundle.getInt(ShiftChangeService.FILE_TYPE_TAG);
                final int progress = bundle.getInt(ShiftChangeService.PROGRESS_TAG);

                viewHolder.taskExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        switch (what) {
                            case ShiftChangeService.UPDATE_PROGRESS:
                                // 进度更新
                                updateProgress(token, key, type, progress);
                                break;
                            case ShiftChangeService.LOAD_SUCCESS:
                                // 上传或下载成功
                                loadFinish(token, key, type, true);
                                break;
                            case ShiftChangeService.LOAD_FAILED:
                                // 上传或下载失败
                                loadFinish(token, key, type, false);
                                break;
                        }
                    }
                });
            }
        });

        // 用于接收服务绑定回调
        viewHolder.connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(LOG_TAG + "initService", "connected service");

                viewHolder.binder = (ShiftChangeService.ShiftChangeServiceBinder) service;
                viewHolder.binder.bindMessenger(messenger);

                // 尝试获取新消息
                loadNewData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(LOG_TAG + "initService", "disconnected service");
                viewHolder.binder = null;
            }
        };

        // 启动并绑定服务
        Intent intent = new Intent(getActivity(), ShiftChangeService.class);

        Log.i(LOG_TAG + "initService", "start service");
        getActivity().startService(intent);
        Log.i(LOG_TAG + "initService", "bind service");
        getActivity().bindService(intent, viewHolder.connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 尝试获取新消息
     */
    private void loadNewData() {
        Log.i(LOG_TAG + "loadNewData", "load new data");
        viewHolder.loading = true;
        viewHolder.shiftChangeFunction.getLatest(new ShiftChangeFunction
                .ShiftChangeRequestListener<List<ShiftChange>>() {
            @Override
            public void onRequestEnd(boolean result, List<ShiftChange> shiftChanges) {
                if (result) {
                    if (shiftChanges != null) {
                        // 填充到界面
                        notifyAdapter(fillData(shiftChanges, 0), false);
                        viewHolder.loading = false;
                        // 下载数据
                        for (ShiftChange shiftChange : shiftChanges) {
                            if (shiftChange.getImageUrlList() != null) {
                                for (Map.Entry<String, String> entry : shiftChange
                                        .getImageUrlList().entrySet()) {
                                    downloadImage(shiftChange.getToken(), entry.getKey(), entry
                                            .getValue());
                                }
                            }

                            if (shiftChange.getAudioUrlList() != null) {
                                for (Map.Entry<String, String> entry : shiftChange
                                        .getAudioUrlList().entrySet()) {
                                    downloadAudio(shiftChange.getToken(), entry.getKey(), entry
                                            .getValue());
                                }
                            }
                        }
                    }
                } else {
                    loadOldData();
                }
            }
        });
    }

    /**
     * 加载旧消息
     */
    private void loadOldData() {
        viewHolder.loading = true;
        viewHolder.taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // 获取新消息
                List<ShiftChange> shiftChangeList = viewHolder.shiftChangeFunction.next();

                if (!shiftChangeList.isEmpty()) {
                    List<ShiftChangeContent> shiftChangeContentList = new ArrayList<>
                            (shiftChangeList.size());
                    // 数据转换校验任务
                    List<Callable<ShiftChangeContent>> callableList = new ArrayList<>
                            (shiftChangeList.size());

                    // 下载任务栈
                    final Stack<Runnable> runnableList = new Stack<>();

                    // 装配检测校准消息
                    for (final ShiftChange shiftChange : shiftChangeList) {
                        callableList.add(0, new Callable<ShiftChangeContent>() {
                            @Override
                            public ShiftChangeContent call() throws Exception {
                                return checkOldData(shiftChange, runnableList);
                            }
                        });
                    }

                    try {
                        List<Future<ShiftChangeContent>> futureList = viewHolder.taskExecutor
                                .invokeAll(callableList);

                        // 提取数据
                        for (Future<ShiftChangeContent> future : futureList) {
                            shiftChangeContentList.add(future.get());
                        }

                        // 通知界面改变
                        notifyAdapter(shiftChangeContentList, true);
                        viewHolder.loading = false;

                        // 执行下载任务
                        while (!runnableList.empty()) {
                            viewHolder.taskExecutor.submit(runnableList.pop());
                        }

                    } catch (InterruptedException e) {
                        Log.e(LOG_TAG + "loadOldData", "InterruptedException is " + e.getMessage());
                    } catch (ExecutionException e) {
                        Log.e(LOG_TAG + "loadOldData", "ExecutionException is " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 检测消息资源状态，正在传输会自动更新进度值，需要下载则会开启下载
     *
     * @param shiftChange  一条消息
     * @param runnableList 待执行下载任务
     *
     * @return 处理后的显示内容
     */
    private ShiftChangeContent checkOldData(ShiftChange shiftChange, Stack<Runnable> runnableList) {

        // 填充一条待显示数据
        ShiftChangeContent shiftChangeContent = fillData(shiftChange, 100);

        // 修正进度
        viewHolder.binder.correctProgress(shiftChangeContent);

        // 检测图片
        if (shiftChange.getImageUrlList() != null) {
            for (Map.Entry<String, String> entry : shiftChange.getImageUrlList().entrySet()) {

                // 仅对无任务的数据进行检测
                if (shiftChangeContent.getImageList().get(entry.getKey()) == 100) {

                    // 先从源图获取
                    File file = viewHolder.contentCacheTool.getForFile(ImageUtil
                            .SOURCE_IMAGE_CACHE_PRE + entry.getKey());

                    if (file == null || !file.exists()) {
                        // 再从压缩图获取
                        Log.i(LOG_TAG + "checkOldData", "key:" + entry.getKey() + " no source " +
                                "image");
                        file = viewHolder.contentCacheTool.getForFile(ImageUtil
                                .COMPRESSION_IMAGE_CACHE_PRE + entry.getKey());
                    }

                    if (file == null || !file.exists()) {
                        Log.i(LOG_TAG + "checkOldData", "key:" + entry.getKey() + "no compression" +
                                " image");
                        // 修正进度
                        shiftChangeContent.getImageList().put(entry.getKey(), 0);

                        // 添加下载任务
                        addDownloadTask(runnableList, shiftChange.getToken(), entry.getKey(),
                                entry.getValue(), StaticValue.TypeTag.TYPE_IMAGE_CONTENT);

                        continue;
                    }

                    // 尝试获取缩略图
                    Bitmap bitmap = viewHolder.contentCacheTool.getForBitmap(ImageUtil
                            .THUMBNAIL_CACHE_PRE + entry.getKey());

                    if (bitmap == null) {
                        // 尝试重新创建缩略图
                        bitmap = reloadImage(entry.getKey());
                    }

                    if (bitmap == null) {
                        // 原图已损坏
                        Log.i(LOG_TAG + "checkOldData", "key:" + entry.getKey() + "image damage");
                        // 修正进度
                        shiftChangeContent.getImageList().put(entry.getKey(), 0);

                        // 添加下载任务
                        addDownloadTask(runnableList, shiftChange.getToken(), entry.getKey(),
                                entry.getValue(), StaticValue.TypeTag.TYPE_IMAGE_CONTENT);
                    }
                }
            }
        }

        // 检测音频
        if (shiftChange.getAudioUrlList() != null) {
            for (Map.Entry<String, String> entry : shiftChange.getAudioUrlList().entrySet()) {

                // 仅对无任务的数据进行检测
                if (shiftChangeContent.getAudioList().get(entry.getKey()) == 100) {
                    // 尝试获取音频长度
                    String length = AudioFileLengthFunction.getFunction().getAudioLength
                            (viewHolder.contentCacheTool, entry.getKey());

                    if (length == null) {
                        // 音频文件不存在或损坏
                        Log.i(LOG_TAG + "checkOldData", "key:" + entry.getKey() + "no audio");
                        // 修正进度
                        shiftChangeContent.getAudioList().put(entry.getKey(), 0);

                        // 添加下载任务
                        addDownloadTask(runnableList, shiftChange.getToken(), entry.getKey(),
                                entry.getValue(), StaticValue.TypeTag.TYPE_AUDIO_CONTENT);

                    }
                }
            }
        }

        return shiftChangeContent;
    }

    /**
     * 添加一条下载任务
     *
     * @param runnableList 任务栈
     * @param token        消息标识
     * @param key          资源缓存key(不含前缀)
     * @param url          下载地址
     * @param type         文件类型
     */
    private void addDownloadTask(Stack<Runnable> runnableList, final String token, final String
            key, final String url, final int type) {
        Log.i(LOG_TAG + "addDownloadTask", "token:" + token + " key:" + key + " url:" + url + " " +
                "type:" + type);

        if (url == null || url.isEmpty()) {
            // url为null

            // 加入下载队列
            runnableList.push(new Runnable() {
                @Override
                public void run() {
                    viewHolder.shiftChangeFunction.fillFromNetwork(token, new ShiftChangeFunction
                            .ShiftChangeRequestListener<ShiftChange>() {
                        @Override
                        public void onRequestEnd(boolean result, ShiftChange shiftChange) {

                            if (result && shiftChange != null) {
                                switch (type) {
                                    case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                                        downloadImage(token, key, shiftChange.getImageUrlList()
                                                .get(key));
                                        break;
                                    case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                                        downloadAudio(token, key, shiftChange.getAudioUrlList()
                                                .get(key));
                                }
                            } else {
                                // 发送失败
                                loadFinish(token, key, type, false);
                            }
                        }
                    });
                }
            });
        } else {
            // 加入下载队列
            runnableList.push(new Runnable() {
                @Override
                public void run() {

                    switch (type) {
                        case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                            downloadImage(token, key, url);
                            break;
                        case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                            downloadAudio(token, key, url);
                    }
                }
            });
        }
    }

    /**
     * 发送新消息
     *
     * @param receive        接收者编码
     * @param receiveCompany 接收公司编码
     * @param content        正文
     * @param images         图片文件集合
     * @param audios         音频文件集合
     * @param listener       结果监听器
     */
    public void sendNewMessage(final String receive, final String receiveCompany, final String
            content, final File[] images, final File[] audios, final DataChangeObserver<Boolean>
            listener) {
        Log.i(LOG_TAG + "sendNewMessage", "receive:" + receive + " receiveCompany:" +
                receiveCompany + " content:" + content);

        SendShiftChangeWork sendShiftChangeWork = new SendShiftChangeWork();

        sendShiftChangeWork.setWorkEndListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean state, String s) {
                if (state && s != null && !s.isEmpty()) {
                    // 发送成功，保存数据
                    ShiftChange shiftChange = new ShiftChange();

                    shiftChange.setToken(s);
                    shiftChange.setSend(GlobalApplication.getGlobal().getLoginStatus()
                            .getNickname());
                    shiftChange.setReceive(receive);
                    shiftChange.setContent(content);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    shiftChange.setTime(format.format(new Date()));
                    shiftChange.setMySend(true);

                    // 缓存图片
                    if (images != null) {

                        Log.i(LOG_TAG + "sendNewMessage", "receive:" + receive + " " +
                                "receiveCompany:" + receiveCompany + " content:" + content + " " +
                                "images count:" + images.length);

                        Map<String, String> map = onMoveFile(images, ImageUtil
                                .SOURCE_IMAGE_CACHE_PRE);

                        shiftChange.setImageUrlList(map);

                        // 创建缩略图
                        for (String key : map.keySet()) {
                            ImageUtil.createThumbnail(viewHolder.contentCacheTool.getForFile
                                    (ImageUtil.SOURCE_IMAGE_CACHE_PRE + key), viewHolder
                                    .contentCacheTool, key, viewHolder.thumbnailWidth, 0);
                        }
                    }

                    // 缓存音频
                    if (audios != null) {

                        Log.i(LOG_TAG + "sendNewMessage", "receive:" + receive + " " +
                                "receiveCompany:" + receiveCompany + " content:" + content + " " +
                                "audios count:" + audios.length);

                        Map<String, String> map = onMoveFile(audios, null);

                        shiftChange.setAudioUrlList(map);
                    }

                    // 更新到界面
                    notifyAdapter(fillData(shiftChange, 0), false);

                    // 保存数据
                    viewHolder.shiftChangeFunction.save(shiftChange);

                    if (shiftChange.getImageUrlList() != null) {
                        // 上传图片
                        for (String key : shiftChange.getImageUrlList().keySet()) {
                            uploadImage(shiftChange.getToken(), key);
                        }
                    }

                    if (shiftChange.getAudioUrlList() != null) {
                        // 上传音频
                        for (String key : shiftChange.getAudioUrlList().keySet()) {
                            uploadAudio(shiftChange.getToken(), key);
                        }
                    }
                }

                // 执行回调
                if (listener != null) {
                    listener.notifyDataChange(state);
                }
            }
        }, false);

        sendShiftChangeWork.beginExecute(GlobalApplication.getGlobal().getLoginStatus().getUserID
                (), receive, GlobalApplication.getGlobal().getLoginStatus().getCodeCompany(),
                receiveCompany, content, images == null ? "0" : String.valueOf(images.length),
                audios == null ? "0" : String.valueOf(audios.length));
    }

    /**
     * 移动资源文件，得到新的缓存key集合
     *
     * @param files  源文件
     * @param keyPre key前缀
     *
     * @return key集合，value为null
     */
    @NonNull
    private Map<String, String> onMoveFile(File[] files, String keyPre) {
        Log.i(LOG_TAG + "onMoveFile", "source file list count:" + files.length + " key pre:" +
                keyPre);
        Map<String, String> map = new HashMap<>();
        for (File file : files) {
            String key = CacheKeyUtil.getRandomKey();
            String finalKey = keyPre == null ? key : keyPre + key;
            Log.i(LOG_TAG + "onMoveFile", "source file path:" + file.getPath() + " final key:" +
                    finalKey);
            try {
                viewHolder.contentCacheTool.putAndBack(finalKey).close();
            } catch (IOException e) {
                Log.e(LOG_TAG + "onMoveFile", "IOException is " + e.getMessage());
            }
            File newFile = viewHolder.contentCacheTool.getForFile(finalKey);
            newFile.delete();

            // 移动文件
            file.renameTo(newFile);

            map.put(key, null);
        }

        Log.i(LOG_TAG + "onMoveFile", "new file list count is " + map.size());

        return map;
    }

    /**
     * 数据转换，将数据库数据转换为显示数据
     *
     * @param data     数据源
     * @param progress 初始化进度
     *
     * @return 转换后的数据
     */
    private List<ShiftChangeContent> fillData(List<ShiftChange> data, int progress) {
        if (data == null) {
            Log.d(LOG_TAG + "fillData", "data is null");
            return new ArrayList<>(1);
        }

        List<ShiftChangeContent> shiftChangeContentList = new ArrayList<>();

        // 倒序填充
        for (ShiftChange shiftChange : data) {
            ShiftChangeContent shiftChangeContent = fillData(shiftChange, progress);

            shiftChangeContentList.add(0, shiftChangeContent);
        }

        Log.i(LOG_TAG + "fillData", "data count " + shiftChangeContentList.size());

        return shiftChangeContentList;
    }

    /**
     * 数据转换，将数据库数据转换为显示数据
     *
     * @param shiftChange 数据源
     * @param progress    初始化进度
     *
     * @return 转换后的数据
     */
    @NonNull
    private ShiftChangeContent fillData(ShiftChange shiftChange, int progress) {
        ShiftChangeContent shiftChangeContent = new ShiftChangeContent();

        shiftChangeContent.setToken(shiftChange.getToken());
        shiftChangeContent.setName(shiftChange.getSend());
        shiftChangeContent.setTime(shiftChange.getTime());
        shiftChangeContent.setMessage(shiftChange.getContent());
        shiftChangeContent.setSend(shiftChange.isMySend());

        if (shiftChange.getImageUrlList() != null) {
            // 填充图片缓存
            Map<String, Integer> map = new HashMap<>();

            for (String key : shiftChange.getImageUrlList().keySet()) {
                map.put(key, progress);
            }

            shiftChangeContent.setImageList(map);
            Log.i(LOG_TAG + "fillData", "image list count is " + map.size());
        }

        if (shiftChange.getAudioUrlList() != null) {
            // 填充音频缓存
            Map<String, Integer> map = new HashMap<>();

            for (String key : shiftChange.getAudioUrlList().keySet()) {
                map.put(key, progress);
            }

            shiftChangeContent.setAudioList(map);
            Log.i(LOG_TAG + "fillData", "audio list count is " + map.size());
        }
        return shiftChangeContent;
    }

    /**
     * 展示大图
     *
     * @param view 缩略图控件
     * @param key  图片缓存key
     */
    private void showImage(View view, String key) {

        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view,
                viewHolder.contentCacheTool.getForBitmap(ImageUtil.THUMBNAIL_CACHE_PRE + key),
                view.getWidth() / 2, view.getHeight() / 2);

        Intent intent = new Intent(getActivity(), ImageShowActivity.class);

        // 先从源图获取
        File file = viewHolder.contentCacheTool.getForFile(ImageUtil.SOURCE_IMAGE_CACHE_PRE + key);

        if (file == null || !file.exists()) {
            // 再从压缩图获取
            Log.i(LOG_TAG + "showImage", "key:" + key + "no source image");
            file = viewHolder.contentCacheTool.getForFile(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE +
                    key);
        }

        intent.putExtra(ImageShowActivity.IMAGE_FILE, file);
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    /**
     * 播放音频
     *
     * @param key       音频缓存key
     * @param imageView 同步操作的音频图标
     */
    private void playAudio(String key, ImageView imageView) {

        if (viewHolder.mediaPlayer.isPlaying()) {
            viewHolder.mediaPlayer.stop();
            if (viewHolder.audioImageView != null) {
                viewHolder.audioImageView.getDrawable().setLevel(0);
            }
        }

        // 记录正在播放的文件
        viewHolder.audioImageView = imageView;

        viewHolder.mediaPlayer.reset();

        try {
            viewHolder.mediaPlayer.setDataSource(viewHolder.contentCacheTool.getForFile(key)
                    .getPath());
            viewHolder.mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG + "initAudioList", "IOException is " + e.getMessage());
        }

        viewHolder.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                viewHolder.audioImageView.getDrawable().setLevel(0);
                viewHolder.audioImageView = null;
            }
        });

        imageView.getDrawable().setLevel(1);
        viewHolder.mediaPlayer.start();
    }

    /**
     * 重新读取图片缩略图
     *
     * @param key 缓存key(不含前缀)
     *
     * @return 缩略图，如果原图不存在则返回null
     */
    private Bitmap reloadImage(String key) {
        // 先从源图获取
        File file = viewHolder.contentCacheTool.getForFile(ImageUtil.SOURCE_IMAGE_CACHE_PRE + key);

        if (file == null || !file.exists()) {
            // 再从压缩图获取
            Log.i(LOG_TAG + "reloadImage", "key:" + key + " no source image");
            file = viewHolder.contentCacheTool.getForFile(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE +
                    key);
        }

        if (file == null || !file.exists()) {
            Log.d(LOG_TAG + "reloadImage", "key:" + key + " no compression image");
            return null;
        }

        return viewHolder.contentCacheTool.getForBitmap(ImageUtil.createThumbnail(file,
                viewHolder.contentCacheTool, key, viewHolder.thumbnailWidth, 0));
    }

    /**
     * 更新资源网络加载进度
     *
     * @param token    消息标识
     * @param key      缓存key
     * @param type     文件类型
     * @param progress 新的进度
     */
    private void updateProgress(String token, String key, int type, final int progress) {
        Log.i(LOG_TAG + "updateProgress", "this token:" + token + " key:" + key + " type:" + type
                + " progress:" + progress);

        int position = 0;

        // 找出要更新进度的资源在数据源列表中的位置
        while (position < viewHolder.dataList.size()) {

            ShiftChangeContent shiftChangeContent = viewHolder.dataList.get(position);

            if (shiftChangeContent.getToken().equals(token)) {

                Log.i(LOG_TAG + "updateProgress", "this token:" + token + " key:" + key + " " +
                        "source " +
                        "position:" + position);

                // 尝试找出当前显示的资源项布局管理
                Object holder = findGridLayoutItemViewHolder(position, key, type);

                switch (type) {
                    case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                        // 图片类型
                        Map<String, Integer> imageMap = shiftChangeContent.getImageList();
                        if (imageMap != null && imageMap.containsKey(key)) {

                            int oldProgress = imageMap.get(key);

                            if (oldProgress >= 0 && oldProgress < 100) {
                                // 此状态为正在加载，其余可能是迟到线程
                                imageMap.put(key, progress);

                                // 同步更新界面
                                if (holder != null) {
                                    final ShiftChangeContentImageViewHolder imageViewHolder =
                                            (ShiftChangeContentImageViewHolder) holder;

                                    imageViewHolder.textView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageViewHolder.textView.setText(progress + "%");
                                        }
                                    });
                                }
                            }
                        }

                        break;
                    case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                        // 音频类型
                        Map<String, Integer> audioMap = shiftChangeContent.getAudioList();
                        if (audioMap != null && audioMap.containsKey(key)) {

                            int oldProgress = audioMap.get(key);

                            if (oldProgress >= 0 && oldProgress < 100) {
                                // 此状态为正在加载，其余可能是迟到线程
                                audioMap.put(key, progress);
                                // 同步更新界面
                                if (holder != null) {
                                    final ShiftChangeContentAudioViewHolder audioViewHolder =
                                            (ShiftChangeContentAudioViewHolder) holder;

                                    audioViewHolder.textView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            audioViewHolder.textView.setText(progress + "%");
                                        }
                                    });
                                }
                            }
                        }
                        break;
                }

                break;
            } else {
                position++;
            }
        }
    }

    /**
     * 找出当前正在显示的列表项中的表格项
     *
     * @param position 在数据源中的索引位置
     * @param key      要查找的资源key
     * @param type     资源类型
     *
     * @return 图片或音频的布局控件管理器，没有或未显示则返回null
     */
    private Object findGridLayoutItemViewHolder(int position, String key, int type) {
        Log.i(LOG_TAG + "findGridLayoutItemViewHolder", "source position:" + position + " key:" +
                key + " type:" + type);
        // 尝试找到当前显示的列表项
        RecyclerView.ViewHolder holder = viewHolder.contentRecyclerView
                .findViewHolderForAdapterPosition(position);

        if (holder == null) {
            Log.d(LOG_TAG + "findGridLayoutItemViewHolder", "source position:" + position + " " +
                    "key:" + key + " type:" + type + " no display");
            return null;
        }

        ShiftChangeContentViewHolder contentViewHolder = (ShiftChangeContentViewHolder) holder;

        View view = null;

        switch (type) {
            case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                // 图片类型
                view = contentViewHolder.imageGridLayout.findViewWithTag(key);
                break;
            case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                // 音频类型
                view = contentViewHolder.audioGridLayout.findViewWithTag(key);
                break;
        }

        if (view == null) {
            Log.d(LOG_TAG + "findGridLayoutItemViewHolder", "source position:" + position + " " +
                    "key:" + key + " type:" + type + " no view");
            return null;
        } else {
            return view.getTag(R.id.view_holder_tag);
        }
    }

    /**
     * 上传或下载完成
     *
     * @param token 消息标识
     * @param key   缓存key
     * @param type  资源类型
     * @param state 标识成功或失败
     */
    private void loadFinish(final String token, final String key, int type, final boolean state) {
        Log.i(LOG_TAG + "loadFinish", "this token:" + token + " key:" + key + " type:" + type + "" +
                " isSuccess:" + state);
        if (StaticValue.TypeTag.TYPE_IMAGE_CONTENT == type && viewHolder.contentCacheTool
                .getForBitmap(ImageUtil.THUMBNAIL_CACHE_PRE + key) == null) {
            // 生成缩略图
            reloadImage(key);
        }

        int position = 0;

        // 找出要更新进度的资源在数据源列表中的位置
        while (position < viewHolder.dataList.size()) {
            ShiftChangeContent shiftChangeContent = viewHolder.dataList.get(position);
            final boolean isSend = shiftChangeContent.isSend();
            if (shiftChangeContent.getToken().equals(token)) {

                Log.i(LOG_TAG + "loadFinish", "this token:" + token + " key:" + key + " type:" +
                        type + " isSuccess:" + state + " source position:" + position);

                // 尝试找出当前显示的资源项布局管理
                Object holder = findGridLayoutItemViewHolder(position, key, type);

                switch (type) {
                    case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                        // 图片类型
                        final Map<String, Integer> imageMap = shiftChangeContent.getImageList();
                        if (imageMap != null && imageMap.containsKey(key)) {
                            imageMap.put(key, state ? 100 : -1);
                        }

                        // 同步更新界面
                        if (holder != null) {
                            final ShiftChangeContentImageViewHolder imageViewHolder =
                                    (ShiftChangeContentImageViewHolder) holder;

                            if (state) {
                                // 成功
                                imageViewHolder.rootItem.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 修改文字显示
                                        imageViewHolder.textView.setText(null);

                                        // 修改图片
                                        imageViewHolder.imageView.setImageBitmap(viewHolder
                                                .contentCacheTool.getForBitmap(ImageUtil
                                                        .THUMBNAIL_CACHE_PRE + key));

                                        // 添加点击事件
                                        imageViewHolder.rootItem.setOnClickListener(new View
                                                .OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showImage(imageViewHolder.imageView, key);
                                            }
                                        });
                                    }
                                });
                            } else {
                                // 失败
                                imageViewHolder.rootItem.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 修改文字显示
                                        imageViewHolder.textView.setText(R.string.load_failed);

                                        // 添加点击事件
                                        imageViewHolder.rootItem.setOnClickListener(new View
                                                .OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                assert imageMap != null;
                                                imageMap.put(key, 0);
                                                imageViewHolder.textView.setText("0%");

                                                if (isSend) {
                                                    uploadImage(token, key);
                                                } else {
                                                    downloadImage(token, key);
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }

                        break;
                    case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                        // 音频类型
                        final Map<String, Integer> audioMap = shiftChangeContent.getAudioList();
                        if (audioMap != null && audioMap.containsKey(key)) {
                            audioMap.put(key, state ? 100 : -1);
                        }

                        // 同步更新界面
                        if (holder != null) {
                            final ShiftChangeContentAudioViewHolder audioViewHolder =
                                    (ShiftChangeContentAudioViewHolder) holder;

                            if (state) {
                                // 成功
                                audioViewHolder.rootItem.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 修改文字显示
                                        audioViewHolder.textView.setText(AudioFileLengthFunction
                                                .getFunction().getAudioLength(viewHolder
                                                        .contentCacheTool, key));

                                        // 添加点击事件
                                        audioViewHolder.rootItem.setOnClickListener(new View
                                                .OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                playAudio(key, audioViewHolder.imageView);
                                            }
                                        });
                                    }
                                });
                            } else {
                                // 失败
                                audioViewHolder.rootItem.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 修改文字显示
                                        audioViewHolder.textView.setText(R.string.load_failed);

                                        // 添加点击事件
                                        audioViewHolder.rootItem.setOnClickListener(new View
                                                .OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                assert audioMap != null;
                                                audioMap.put(key, 0);
                                                audioViewHolder.textView.setText("0%");
                                                if (isSend) {
                                                    uploadAudio(token, key);
                                                } else
                                                    downloadAudio(token, key);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                        break;
                }
                break;
            } else {
                position++;
            }
        }
    }

    /**
     * 上传图片
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     */
    private void uploadImage(String token, String key) {
        Log.i(LOG_TAG + "uploadImage", "content token:" + token + " key:" + key);
        viewHolder.binder.uploadFile(token, key, StaticValue.TypeTag.TYPE_IMAGE_CONTENT);
    }

    /**
     * 上传音频
     *
     * @param token 消息标识
     * @param key   缓存key(不含前缀)
     */
    private void uploadAudio(String token, String key) {
        Log.i(LOG_TAG + "uploadAudio", "content token:" + token + " key:" + key);
        viewHolder.binder.uploadFile(token, key, StaticValue.TypeTag.TYPE_AUDIO_CONTENT);
    }

    /**
     * 图片缓存丢失重新下载
     *
     * @param token 消息标识
     * @param key   缓存key(不含前缀)
     */
    private void downloadImage(String token, String key) {
        Log.i(LOG_TAG + "downloadImage", "content token:" + token + " key:" + key);
        ShiftChange shiftChange = viewHolder.shiftChangeFunction.findByToken(token);

        if (shiftChange.getImageUrlList() != null && shiftChange.getImageUrlList().containsKey
                (key)) {
            String url = shiftChange.getImageUrlList().get(key);

            if (url != null) {
                downloadImage(token, key, url);
            }
        }
    }

    /**
     * 图片缓存丢失重新下载
     *
     * @param token 消息标识
     * @param key   缓存key(不含前缀)
     * @param url   图片下载地址
     */
    private void downloadImage(String token, String key, String url) {
        Log.i(LOG_TAG + "downloadImage", "content token:" + token + " key:" + key + " url:" + url);
        viewHolder.binder.downloadFile(token, key, url, StaticValue.TypeTag.TYPE_IMAGE_CONTENT);
    }

    /**
     * 音频缓存丢失重新下载
     *
     * @param token 消息标识
     * @param key   缓存key
     */
    private void downloadAudio(String token, String key) {
        Log.i(LOG_TAG + "downloadAudio", "content token:" + token + " key:" + key);
        ShiftChange shiftChange = viewHolder.shiftChangeFunction.findByToken(token);

        if (shiftChange.getAudioUrlList() != null && shiftChange.getAudioUrlList().containsKey
                (key)) {
            String url = shiftChange.getAudioUrlList().get(key);

            if (url != null) {
                downloadAudio(token, key, url);
            }
        }
    }

    /**
     * 音频缓存丢失重新下载
     *
     * @param token 消息标识
     * @param key   缓存key
     * @param url   音频下载地址
     */
    private void downloadAudio(String token, String key, String url) {
        Log.i(LOG_TAG + "downloadAudio", "content token:" + token + " key:" + key + " url:" + url);
        viewHolder.binder.downloadFile(token, key, url, StaticValue.TypeTag.TYPE_IMAGE_CONTENT);
    }

    /**
     * 通知列表增加
     *
     * @param shiftChangeContentList 新添加的数据集
     */
    private void notifyAdapter(final List<ShiftChangeContent> shiftChangeContentList, final
    boolean top) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (top) {
                    // 滚动到头部准备添加
                    viewHolder.contentRecyclerView.scrollToPosition(0);
                    viewHolder.adapter.addData(0, shiftChangeContentList);
                } else {
                    // 滚动到尾部准备添加
                    viewHolder.contentRecyclerView.scrollToPosition(viewHolder.adapter
                            .getItemCount());
                    viewHolder.adapter.addData(viewHolder.adapter.getItemCount(),
                            shiftChangeContentList);
                }
            }
        });
    }

    /**
     * 通知列表增加
     *
     * @param shiftChangeContent 新添加的数据
     * @param top                标识是在头部添加，true表示在0位置添加，false表示在末尾添加
     */
    private void notifyAdapter(final ShiftChangeContent shiftChangeContent, final boolean top) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (top) {
                    // 滚动到头部准备添加
                    viewHolder.contentRecyclerView.scrollToPosition(0);
                    viewHolder.adapter.addData(0, shiftChangeContent);
                } else {
                    // 滚动到尾部准备添加
                    viewHolder.contentRecyclerView.scrollToPosition(viewHolder.adapter
                            .getItemCount());
                    viewHolder.adapter.addData(viewHolder.adapter.getItemCount(),
                            shiftChangeContent);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        viewHolder.mediaPlayer.release();
        getActivity().unbindService(viewHolder.connection);
        viewHolder.taskExecutor.shutdown();
        super.onDestroy();
    }
}
