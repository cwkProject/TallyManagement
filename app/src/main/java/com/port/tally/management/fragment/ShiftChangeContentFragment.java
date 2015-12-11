package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/11.
 */

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;

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
     * 控件集
     */
    private class LocalViewHolder {

        /**
         * 交接班信息正文列表缓存工具
         */
        public CacheTool contentCacheTool = null;

        /**
         * 内容列表
         */
        public RecyclerView contentRecyclerView = null;

        /**
         * 音频播放器
         */
        public MediaPlayer mediaPlayer = null;

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

    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder(View rootView) {

        viewHolder.contentRecyclerView = (RecyclerView) rootView.findViewById(R.id
                .fragment_shift_change_content_recyclerView);

        viewHolder.contentCacheTool = CacheManager.getCacheTool("shift_change_content");

        viewHolder.mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewHolder.mediaPlayer.release();
    }
}
