package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/12/12.
 */

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.bean.ShiftChangeContent;
import com.port.tally.management.holder.ImageWithTextViewHolder;
import com.port.tally.management.holder.ShiftChangeContentViewHolder;
import com.port.tally.management.util.ImageUtil;

import org.mobile.library.cache.util.CacheTool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 交接班内容列表适配器
 *
 * @author 超悟空
 * @version 1.0 2015/12/12
 * @since 1.0
 */
public class ShiftChangeContentRecyclerViewAdapter extends RecyclerView
        .Adapter<ShiftChangeContentViewHolder> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeContentRecyclerViewAdapter.";

    /**
     * 图片类型内容
     */
    public static final int TYPE_IMAGE_CONTENT = 1;

    /**
     * 音频类型内容
     */
    public static final int TYPE_AUDIO_CONTENT = 2;

    /**
     * 记录当前布局管理器数量
     */
    private int holderCount = 0;

    /**
     * 存有内容文件的缓存工具
     */
    private CacheTool cacheTool = null;

    /**
     * 音频播放器
     */
    private MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * 内容数据源列表
     */
    private List<ShiftChangeContent> dataList = null;

    /**
     * 绑定内容中的表格布局时的监听器
     */
    public interface BindGridItemViewHolderListener {

        /**
         * 控件绑定之后执行
         *
         * @param holder      要绑定的控件管理器
         * @param key         数据缓存key
         * @param contentType 内容类型
         * @param done        是否为完成状态
         */
        void onBind(ImageWithTextViewHolder holder, String key, int contentType, boolean done);
    }

    /**
     * 绑定内容中的表格布局资源缓存读取失败时的监听器
     */
    public interface BindGridItemFailedListener {

        /**
         * 缓存读取失败，同时绑定控件点击事件
         *
         * @param holder       要绑定的控件管理器
         * @param ItemPosition 列表位置
         * @param key          缓存key
         * @param contentType  内容类型
         */
        void onFailed(ImageWithTextViewHolder holder, int ItemPosition, String key, int
                contentType);
    }

    /**
     * 绑定内容中的表格布局时的监听器
     */
    private BindGridItemViewHolderListener bindGridItemViewHolderListener = null;

    /**
     * 绑定内容中的表格布局资源缓存读取失败时的监听器
     */
    private BindGridItemFailedListener bindGridItemFailedListener = null;

    /**
     * 构造函数
     *
     * @param cacheTool 内容缓存工具
     * @param dataList  数据源列表
     */
    public ShiftChangeContentRecyclerViewAdapter(CacheTool cacheTool, List<ShiftChangeContent>
            dataList) {
        this.cacheTool = cacheTool;
        this.dataList = dataList;
    }

    /**
     * 设置绑定内容中的表格布局时的监听器
     *
     * @param bindGridItemViewHolderListener 监听器实例
     */
    public void setBindGridItemViewHolderListener(BindGridItemViewHolderListener
                                                          bindGridItemViewHolderListener) {
        this.bindGridItemViewHolderListener = bindGridItemViewHolderListener;
    }

    /**
     * 绑定内容中的表格布局资源缓存读取失败时的监听器
     *
     * @param bindGridItemFailedListener 监听器实例
     */
    public void setBindGridItemFailedListener(BindGridItemFailedListener
                                                      bindGridItemFailedListener) {
        this.bindGridItemFailedListener = bindGridItemFailedListener;
    }

    /**
     * 添加一条数据
     *
     * @param position 插入位置
     * @param data     添加的值
     */
    public void addData(int position, ShiftChangeContent data) {
        this.dataList.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 添加一组数据
     *
     * @param position 插入位置
     * @param data     添加的值集合
     */
    public void addData(int position, List<ShiftChangeContent> data) {
        this.dataList.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    @Override
    public ShiftChangeContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .shift_change_content_item, parent, false);

        ShiftChangeContentViewHolder holder = new ShiftChangeContentViewHolder(itemView);
        holder.holderIndex = holderCount++;
        Log.i(LOG_TAG + "onCreateViewHolder", "holder index is " + holder.holderIndex);

        return holder;
    }

    @Override
    public void onBindViewHolder(ShiftChangeContentViewHolder holder, int position) {

        Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " position:" +
                position);

        Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " position:" +
                position + " image grid " + "row:" + holder.imageGridLayout.getRowCount());

        Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " position:" +
                position + " audio grid " + "row:" + holder.audioGridLayout.getRowCount());

        // 内容
        ShiftChangeContent content = dataList.get(position);

        holder.nameTextView.setText(content.getName());
        holder.timeTextView.setText(content.getTime());
        holder.messageTextView.setText(content.getMessage());
        holder.imageGridLayout.removeAllViews();
        holder.audioGridLayout.removeAllViews();

        Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " " +
                "position:" + position + " send state:" + content.isSend());

        if (content.getImageList() != null) {
            // 当前为上传状态

            Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " " +
                    "position:" + position + " ImageList " + "count:" + content.getImageList()
                    .size());

            for (Map.Entry<String, Integer> entry : content.getImageList().entrySet()) {

                Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " " +
                        "position:" + position + " ImageList " + " key:" + entry.getKey() + " " +
                        "value:" + entry.getValue());

                // 一个图片布局控件
                ImageWithTextViewHolder imageWithTextViewHolder = new ImageWithTextViewHolder
                        (holder.itemView.getContext());

                if (entry.getValue() != 100) {
                    imageWithTextViewHolder.textView.setText(entry.getValue() + "%");
                }

                imageWithTextViewHolder.rootItem.setTag(entry.getKey());
                holder.imageGridLayout.addView(imageWithTextViewHolder.rootItem);

                if (content.isSend()) {
                    // 当前为上传状态
                    Bitmap bitmap = cacheTool.getForBitmap(ImageUtil.THUMBNAIL_CACHE_PRE + entry
                            .getKey());
                    if (bitmap != null) {
                        imageWithTextViewHolder.imageView.setImageBitmap(bitmap);
                    } else {
                        Log.d(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex
                                + " position:" + position + " ImageList " + " key:" + entry
                                .getKey() + " value:" + entry.getValue() + " no bitmap");
                    }
                } else {
                    if (entry.getValue() == 100) {

                        // 图片源文件缓存
                        File file = cacheTool.getForFile(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE +
                                entry.getKey());

                        if (file == null || !file.exists()) {
                            // 图片源文件缓存已丢失，需要重新下载
                            Log.d(LOG_TAG + "onBindViewHolder", "holder index:" + holder
                                    .holderIndex + " position:" + position + " ImageList " +
                                    "key:" + entry.getKey() + " value:" + entry.getValue() + " no" +
                                    " bitmap");
                            // 此处触发失败事件回调
                            if (bindGridItemFailedListener != null) {
                                bindGridItemFailedListener.onFailed(imageWithTextViewHolder,
                                        holder.getAdapterPosition(), entry.getKey(),
                                        TYPE_IMAGE_CONTENT);
                            }
                            break;
                        }

                        Bitmap bitmap = cacheTool.getForBitmap(ImageUtil.THUMBNAIL_CACHE_PRE +
                                entry.getKey());
                        if (bitmap != null) {
                            imageWithTextViewHolder.imageView.setImageBitmap(bitmap);
                        } else {
                            // 缩略图缓存丢失，重新加载缩略图
                            Log.d(LOG_TAG + "onBindViewHolder", "holder index:" + holder
                                    .holderIndex + " position:" + position + " ImageList " +
                                    "key:" + entry.getKey() + " value:" + entry.getValue() + " no" +
                                    " bitmap");
                            // 此处触发失败事件回调
                            if (bindGridItemFailedListener != null) {
                                bindGridItemFailedListener.onFailed(imageWithTextViewHolder,
                                        holder.getAdapterPosition(), entry.getKey(),
                                        TYPE_IMAGE_CONTENT);
                            }
                        }
                    }
                }

                // 此处绑定事件
                if (bindGridItemViewHolderListener != null) {
                    bindGridItemViewHolderListener.onBind(imageWithTextViewHolder, entry.getKey()
                            , TYPE_IMAGE_CONTENT, entry.getValue() == 100);
                }
            }
        }

        if (content.getAudioList() != null) {
            // 开始绑定音频列表

            Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " " +
                    "AudioList " +
                    "count:" + content.getAudioList().size());

            for (Map.Entry<String, Integer> entry : content.getAudioList().entrySet()) {

                Log.i(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex + " " +
                        "position:" + position + " AudioList " + " key:" + entry.getKey() + " " +
                        "value:" + entry.getValue());

                // 一个图片布局控件
                ImageWithTextViewHolder imageWithTextViewHolder = new ImageWithTextViewHolder
                        (holder.itemView.getContext());

                imageWithTextViewHolder.imageView.setImageResource(R.drawable.audio_image_list);

                imageWithTextViewHolder.rootItem.setTag(entry.getKey());
                holder.audioGridLayout.addView(imageWithTextViewHolder.rootItem);

                if (entry.getValue() != 100) {
                    imageWithTextViewHolder.textView.setText(entry.getValue() + "%");
                } else {

                    File file = cacheTool.getForFile(entry.getKey());
                    if (file != null) {

                        String lengthString = getAudioLength(file.getPath());

                        if (lengthString != null) {
                            imageWithTextViewHolder.textView.setText(lengthString);
                        } else {
                            Log.d(LOG_TAG + "onBindViewHolder", "holder index:" + holder
                                    .holderIndex + " position:" + position + " AudioList " + " " +
                                    "key:" + entry.getKey() + " value:" + entry.getValue() + " " +
                                    "audio file error.");
                        }
                    } else {
                        Log.d(LOG_TAG + "onBindViewHolder", "holder index:" + holder.holderIndex
                                + " position:" + position + " AudioList " + " " +
                                "key:" + entry.getKey() + " value:" + entry.getValue() + " " +
                                "no audio file.");

                        // 此处触发下载事件回调
                        if (bindGridItemFailedListener != null) {
                            bindGridItemFailedListener.onFailed(imageWithTextViewHolder, holder
                                    .getAdapterPosition(), entry.getKey(), TYPE_AUDIO_CONTENT);
                        }

                        break;
                    }
                }

                // 此处绑定事件
                if (bindGridItemViewHolderListener != null) {
                    bindGridItemViewHolderListener.onBind(imageWithTextViewHolder, entry.getKey()
                            , TYPE_AUDIO_CONTENT, entry.getValue() == 100);
                }
            }
        }

    }

    /**
     * 获取音频长度文本
     *
     * @param path 音频路径
     *
     * @return 格式化后的文本
     */
    private String getAudioLength(String path) {
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            // 音频长度
            int length = mediaPlayer.getDuration();
            mediaPlayer.reset();

            String lengthString = "";

            if (length / 60000 > 0) {
                lengthString += length / 60000 + "'";
            }
            lengthString += (length / 1000) % 60 + "\"";

            lengthString += length % 1000;

            return lengthString;
        } catch (IOException e) {
            Log.e(LOG_TAG + "getAudioLength", "IOException is " + e.getMessage());
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 释放资源
     */
    public void release() {
        mediaPlayer.release();
    }
}
