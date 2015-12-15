package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/11.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.port.tally.management.bean.ShiftChangeContent;
import com.port.tally.management.holder.ImageWithTextViewHolder;
import com.port.tally.management.util.ImageUtil;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;

import java.io.File;
import java.io.IOException;
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
         * 当前正在播放音频的对应图标控件
         */
        public ImageView audioImageView = null;

        /**
         * 缩略图宽度
         */
        public int thumbnailWidth = 0;
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
            public void onBind(final ImageWithTextViewHolder holder, final String key, int
                    contentType, boolean done) {
                // 此处绑定不同的操作
                if (done) {
                    // 下载完成，文件加载也完成
                    switch (contentType) {
                        case ShiftChangeContentRecyclerViewAdapter.TYPE_IMAGE_CONTENT:
                            // 图片展示
                            holder.rootItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(viewHolder.contentCacheTool.getForFile(ImageUtil
                                            .COMPRESSION_IMAGE_CACHE_PRE + key), holder
                                            .imageView, ImageUtil.THUMBNAIL_CACHE_PRE + key);
                                }
                            });
                            break;
                        case ShiftChangeContentRecyclerViewAdapter.TYPE_AUDIO_CONTENT:
                            // 音频播放
                            holder.rootItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    playAudio(key, holder.imageView);
                                }
                            });
                            break;
                    }
                } else {
                    holder.rootItem.setOnClickListener(null);
                }
            }
        });

        // 设置资源读取失败监听器
        viewHolder.adapter.setBindGridItemFailedListener(new ShiftChangeContentRecyclerViewAdapter.BindGridItemFailedListener() {
            @Override
            public void onFailed(final ImageWithTextViewHolder holder, int ItemPosition, final
            String key, int contentType) {

                switch (contentType) {
                    case ShiftChangeContentRecyclerViewAdapter.TYPE_IMAGE_CONTENT:
                        // 图片加载失败

                        // 尝试重新加载
                        Bitmap bitmap = reloadImage(key);

                        if (bitmap != null) {
                            // 加载成功

                            holder.imageView.setImageBitmap(bitmap);

                            holder.rootItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(viewHolder.contentCacheTool.getForFile(ImageUtil
                                            .COMPRESSION_IMAGE_CACHE_PRE + key), holder
                                            .imageView, ImageUtil.THUMBNAIL_CACHE_PRE + key);
                                }
                            });
                        } else {
                            // 加载失败
                            holder.rootItem.setOnClickListener(null);
                            // 重新下载
                            downloadImage(ItemPosition, key);
                        }
                        break;
                    case ShiftChangeContentRecyclerViewAdapter.TYPE_AUDIO_CONTENT:
                        // 音频加载失败
                        holder.rootItem.setOnClickListener(null);
                        downloadAudio(ItemPosition, key);
                }

            }
        });

        recyclerView.setAdapter(viewHolder.adapter);
    }

    /**
     * 展示大图
     *
     * @param file 大图路径
     * @param view 缩略图控件
     */
    private void showImage(File file, View view, String key) {

        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view,
                viewHolder.contentCacheTool.getForBitmap(key), view.getWidth() / 2, view
                        .getHeight() / 2);

        Intent intent = new Intent(getActivity(), ImageShowActivity.class);
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
        File file = viewHolder.contentCacheTool.getForFile(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE
                + key);

        if (file == null || !file.exists()) {
            Log.d(LOG_TAG + "reloadImage", "no source image");
            return null;
        }

        return viewHolder.contentCacheTool.getForBitmap(ImageUtil.createThumbnail(file,
                viewHolder.contentCacheTool, key, viewHolder.thumbnailWidth, 0));
    }

    /**
     * 更新资源网络加载进度
     *
     * @param token 消息标识
     * @param key   缓存key
     */
    private void updateProgress(String token, String key) {
        Log.i(LOG_TAG + "updateProgress", "this token:" + token + " key:" + key);

        int position=0;

        // 找出要更新进度的资源在数据源列表中的位置
        while (position<viewHolder.dataList.size()){
            if (viewHolder.dataList.get(position).getToken().equals(token)){
                break;
            }
            else {
                position++;
            }
        }

        // 找出要更新的资源键值对和资源类型
        boolean hit=false;

    }

    /**
     * 图片缓存丢失重新下载
     *
     * @param position 图片对应的消息位置索引
     * @param key      缓存key(不含前缀)
     */
    private void downloadImage(int position, String key) {
        Log.i(LOG_TAG + "downloadImage", "content index:" + position + " key:" + key);

    }

    /**
     * 音频缓存丢失重新下载
     *
     * @param position 音频对应的消息位置索引
     * @param key      缓存key(不含前缀)
     */
    private void downloadAudio(int position, String key) {
        Log.i(LOG_TAG + "downloadAudio", "content index:" + position + " key:" + key);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewHolder.mediaPlayer.release();
        viewHolder.adapter.release();
    }
}
