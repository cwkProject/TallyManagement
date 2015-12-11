package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/8.
 */

import android.app.Service;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.port.tally.management.R;
import com.port.tally.management.util.CacheKeyUtil;

import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.model.operate.DataChangeObserver;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 录音布局
 *
 * @author 超悟空
 * @version 1.0 2015/12/8
 * @since 1.0
 */
public class ShiftChangeRecordFragment extends Fragment {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "RecordFragment.";

    /**
     * 音频缓存前缀
     */
    private static final String AUDIO_CACHE_PRE = "audio_";

    /**
     * 控件集
     */
    private class LocalViewHolder {
        /**
         * 当前是否长按状态
         */
        public boolean longClicked = false;

        /**
         * 震动器
         */
        public Vibrator vibrator = null;

        /**
         * 录音键
         */
        public Button button = null;

        /**
         * 录音机
         */
        public MediaRecorder recorder = null;

        /**
         * 存放录音文件的缓存工具
         */
        public CacheTool recordCacheTool = null;

        /**
         * 录音结束通知
         */
        public DataChangeObserver<String> recordEndListener = null;

        /**
         * 记录当前录音缓存key
         */
        public String cacheKey = null;
    }

    /**
     * 控件集对象
     */
    private LocalViewHolder viewHolder = new LocalViewHolder();

    /**
     * 设置录音缓存工具
     *
     * @param cacheTool 用于存储录音缓存文件
     */
    public void setRecordCacheTool(CacheTool cacheTool) {
        viewHolder.recordCacheTool = cacheTool;
    }

    /**
     * 设置录音完成监听器
     *
     * @param recordEndListener 监听器对象，通知值为录音文件缓存key
     */
    public void setRecordEndListener(DataChangeObserver<String> recordEndListener) {
        viewHolder.recordEndListener = recordEndListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media_recorder, container, false);

        // 初始化布局
        initView(rootView);

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

        // 初始化录音按键
        initButton();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder(View rootView) {

        viewHolder.button = (Button) rootView.findViewById(R.id.fragment_media_recorder_button);

        viewHolder.vibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);

        viewHolder.recorder = new MediaRecorder();
    }

    /**
     * 初始化录音机
     */
    private void initRecorder() {
        viewHolder.recorder.reset();
        viewHolder.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        viewHolder.recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
        viewHolder.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
    }

    /**
     * 初始化录音按键
     */
    private void initButton() {
        viewHolder.button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewHolder.longClicked = true;

                // 准备录音资源
                createAudioFile();
                // 开始提示
                playAlert();
                // 开始录音
                viewHolder.recorder.start();

                return false;
            }

        });

        viewHolder.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 长按松开
                        if (viewHolder.longClicked) {
                            viewHolder.longClicked = false;
                            viewHolder.recorder.stop();
                            if (viewHolder.recordEndListener != null) {
                                viewHolder.recordEndListener.notifyDataChange(AUDIO_CACHE_PRE +
                                        viewHolder.cacheKey);
                            }
                            return false;
                        }
                    default:
                        return false;
                }
            }
        });
    }

    /**
     * 准备录音资源
     */
    private void createAudioFile() {
        // 生成一个缓存key
        viewHolder.cacheKey = CacheKeyUtil.getRandomKey();

        FileOutputStream fileOutputStream = viewHolder.recordCacheTool.putAndBack(AUDIO_CACHE_PRE
                + viewHolder.cacheKey);

        try {
            fileOutputStream.close();

            // 初始化录音机
            initRecorder();

            viewHolder.recorder.setOutputFile(viewHolder.recordCacheTool.getForFile
                    (AUDIO_CACHE_PRE + viewHolder.cacheKey).getPath());
            viewHolder.recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG + "createAudioFile", "IOException is " + e.getMessage());
        }
    }

    /**
     * 播放震动
     */
    private void playAlert() {

        // 短促震动
        viewHolder.vibrator.vibrate(200);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewHolder.recorder.release();
    }
}
