package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/11.
 */

import android.graphics.Bitmap;
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

import com.port.tally.management.R;
import com.port.tally.management.adapter.ShiftChangeContentRecyclerViewAdapter;
import com.port.tally.management.bean.ShiftChangeContent;
import com.port.tally.management.holder.ImageWithTextViewHolder;
import com.port.tally.management.util.ImageUtil;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
         * 缩略图宽度
         */
        public int thumbnailWidth = 0;

        /**
         * 图片缓存完整时查看大图的点击事件
         */
        public View.OnClickListener showImageListener = null;

        /**
         * 下载图片失败重新下载点击事件
         */
        public View.OnClickListener reDownloadImageListener = null;

        /**
         * 下载音频失败重新下载点击事件
         */
        public View.OnClickListener reDownloadAudioListener = null;

        /**
         * 音频缓存完整时播放的点击事件
         */
        public View.OnClickListener playAudioListener = null;

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
        viewHolder.adapter = new ShiftChangeContentRecyclerViewAdapter(viewHolder
                .contentCacheTool, viewHolder.dataList);

        // 设置绑定监听器
        viewHolder.adapter.setBindGridItemViewHolderListener(new ShiftChangeContentRecyclerViewAdapter.BindGridItemViewHolderListener() {
            @Override
            public void onBind(ImageWithTextViewHolder holder, String key, int contentType,
                               boolean done) {
                // 此处绑定不同的操作
            }
        });

        // 设置资源读取失败监听器
        viewHolder.adapter.setBindGridItemFailedListener(new ShiftChangeContentRecyclerViewAdapter.BindGridItemFailedListener() {
            @Override
            public void onFailed(int ItemPosition, String key, int contentType) {
                // 此处处理失败情况
            }
        });

        recyclerView.setAdapter(viewHolder.adapter);
    }

    /**
     * 重新读取图片缩略图
     *
     * @param key 缓存key(不含前缀)
     *
     * @return 缩略图，如果原图不存在则返回null
     */
    private Bitmap reloadImage(String key) {
        File file = viewHolder.contentCacheTool.getForFile(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE
                + key);

        if (file == null || !file.exists()) {
            Log.d(LOG_TAG + "reloadImage", "no source image");
            return null;
        }

        return viewHolder.contentCacheTool.getForBitmap(ImageUtil.createThumbnail(file,
                viewHolder.contentCacheTool, key, viewHolder.thumbnailWidth, 0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewHolder.mediaPlayer.release();
        viewHolder.adapter.release();
    }
}
