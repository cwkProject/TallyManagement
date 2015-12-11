package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/11.
 */

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.port.tally.management.adapter.ShiftChangeAudioRecyclerViewAdapter;
import com.port.tally.management.holder.ShiftChangeAudioViewHolder;

import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.io.IOException;
import java.util.List;

/**
 * 交接班待发送音频列表片段
 *
 * @author 超悟空
 * @version 1.0 2015/12/11
 * @since 1.0
 */
public class ShiftChangeAudioListFragment extends Fragment {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeAudioListFragment.";

    /**
     * 控件集
     */
    private class LocalViewHolder {
        /**
         * 待发送数据临时缓存工具
         */
        public CacheTool sendCacheTool = null;

        /**
         * 发送音频列表
         */
        public RecyclerView audioRecyclerView = null;

        /**
         * 发送音频列表的适配器
         */
        public ShiftChangeAudioRecyclerViewAdapter audioRecyclerViewAdapter = null;

        /**
         * 音频播放器
         */
        public MediaPlayer mediaPlayer = null;

        /**
         * 记录当前正在播放的音频文件图标
         */
        public ImageView audioImageView = null;
    }

    /**
     * 控件集对象
     */
    private LocalViewHolder viewHolder = new LocalViewHolder();

    /**
     * 设置存放待发送音频文件的缓存工具
     *
     * @param cacheTool 缓存工具
     */
    public void setCacheTool(CacheTool cacheTool) {
        viewHolder.sendCacheTool = cacheTool;
    }

    /**
     * 向列表添加一个音频文件
     *
     * @param cacheKey 音频文件对应的缓存key(包含前缀)
     */
    public void addAudio(String cacheKey) {
        viewHolder.audioRecyclerView.setVisibility(View.VISIBLE);
        viewHolder.audioRecyclerViewAdapter.addData(0, cacheKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shift_change_audio_list, container,
                false);

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

        // 初始化待发送音频列表
        initAudioList();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder(View rootView) {

        viewHolder.audioRecyclerView = (RecyclerView) rootView.findViewById(R.id
                .fragment_shift_change_audio_recyclerView);

        viewHolder.mediaPlayer = new MediaPlayer();
    }

    /**
     * 初始化待发送音频列表
     */
    private void initAudioList() {
        RecyclerView recyclerView = viewHolder.audioRecyclerView;

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 初始化适配器
        viewHolder.audioRecyclerViewAdapter = new ShiftChangeAudioRecyclerViewAdapter(viewHolder
                .sendCacheTool);

        // 设置监听器
        viewHolder.audioRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListenerForRecyclerViewItem<List<String>, ShiftChangeAudioViewHolder>() {
            @Override
            public void onClick(List<String> dataSource, final ShiftChangeAudioViewHolder holder) {

                // 播放音频
                playAudio(dataSource.get(holder.getAdapterPosition()), holder.imageView);
            }
        });

        recyclerView.setAdapter(viewHolder.audioRecyclerViewAdapter);
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
            viewHolder.mediaPlayer.setDataSource(viewHolder.sendCacheTool.getForFile(key).getPath
                    ());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewHolder.mediaPlayer.release();
        viewHolder.audioRecyclerViewAdapter.release();
    }
}
