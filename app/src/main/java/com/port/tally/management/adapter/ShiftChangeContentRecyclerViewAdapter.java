package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/12/12.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.bean.ShiftChangeContent;
import com.port.tally.management.function.AudioFileLengthFunction;
import com.port.tally.management.function.ShiftChangeContentAudioRecycler;
import com.port.tally.management.function.ShiftChangeContentImageRecycler;
import com.port.tally.management.holder.ShiftChangeContentAudioViewHolder;
import com.port.tally.management.holder.ShiftChangeContentImageViewHolder;
import com.port.tally.management.holder.ShiftChangeContentViewHolder;
import com.port.tally.management.util.ImageUtil;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.cache.util.CacheTool;

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
     * 存有内容文件的缓存工具
     */
    private CacheTool cacheTool = null;

    /**
     * 内容数据源列表
     */
    private List<ShiftChangeContent> dataList = null;

    /**
     * 图片布局项回收器
     */
    private ShiftChangeContentImageRecycler imageRecycler = null;

    /**
     * 音频布局项回收器
     */
    private ShiftChangeContentAudioRecycler audioRecycler = null;

    /**
     * 绑定内容中的表格布局时的监听器
     */
    public interface BindGridItemViewHolderListener {

        /**
         * 控件绑定之后执行
         *
         * @param position    在数据源中的索引
         * @param holder      要绑定的控件管理器
         * @param key         数据缓存key
         * @param contentType 内容类型
         * @param progress    当前文件加载进度
         */
        void onBind(int position, Object holder, String key, int contentType, int progress);
    }

    /**
     * 绑定内容中的表格布局时的监听器
     */
    private BindGridItemViewHolderListener bindGridItemViewHolderListener = null;

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param cacheTool 内容缓存工具
     * @param dataList  数据源列表
     */
    public ShiftChangeContentRecyclerViewAdapter(Context context, CacheTool cacheTool,
                                                 List<ShiftChangeContent> dataList) {
        this.cacheTool = cacheTool;
        this.dataList = dataList;
        this.imageRecycler = new ShiftChangeContentImageRecycler(context);
        this.audioRecycler = new ShiftChangeContentAudioRecycler(context);
    }

    /**
     * 设置绑定内容中的表格布局时的监听器，用于绑定表格项事件
     *
     * @param bindGridItemViewHolderListener 监听器实例
     */
    public void setBindGridItemViewHolderListener(BindGridItemViewHolderListener
                                                          bindGridItemViewHolderListener) {
        this.bindGridItemViewHolderListener = bindGridItemViewHolderListener;
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

    /**
     * 移除一组数据
     *
     * @param start 起始位置
     * @param count 删除行数
     */
    public void remove(int start, int count) {
        for (int i = 0; i < count; i++) {
            this.dataList.remove(start);
        }
        notifyItemRangeRemoved(start, count);
    }

    /**
     * 移除一条数据
     *
     * @param position 起始位置
     */
    public void remove(int position) {
        this.dataList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 清空
     */
    public void clear() {
        int count = this.dataList.size();
        this.dataList.clear();
        notifyItemRangeRemoved(0, count);
    }

    @Override
    public ShiftChangeContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .shift_change_content_item, parent, false);
        return new ShiftChangeContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShiftChangeContentViewHolder holder, int position) {
        Log.i(LOG_TAG + "onBindViewHolder", "position:" + position);

        // 内容
        ShiftChangeContent content = dataList.get(position);

        holder.nameTextView.setText(content.getName());
        holder.timeTextView.setText(content.getTime());
        holder.messageTextView.setText(content.getMessage());

        if (content.getImageList() != null) {

            Log.i(LOG_TAG + "onBindViewHolder", "position:" + position + " ImageList " + "count:"
                    + content.getImageList().size());

            for (Map.Entry<String, Integer> entry : content.getImageList().entrySet()) {

                // 一个图片布局控件
                ShiftChangeContentImageViewHolder viewHolder = imageRecycler.getViewHolder();

                viewHolder.rootItem.setTag(entry.getKey());

                if (content.isSend()) {
                    // 发送状态优先加载图片
                    Bitmap bitmap = cacheTool.getForBitmap(ImageUtil.THUMBNAIL_CACHE_PRE + entry
                            .getKey());
                    if (bitmap != null) {
                        viewHolder.imageView.setImageBitmap(bitmap);
                    } else {
                        Log.d(LOG_TAG + "onBindViewHolder", "position:" + position + " ImageList " +
                                " key:" + entry.getKey() + " value:" + entry.getValue() + " no " +
                                "bitmap");
                        viewHolder.imageView.setImageDrawable(null);
                    }
                }

                if (entry.getValue() > -1 && entry.getValue() < 100) {
                    viewHolder.textView.setText(entry.getValue() + "%");
                } else {
                    if (entry.getValue() == 100) {
                        if (!content.isSend()) {
                            // 非发送状态此时加载图片
                            viewHolder.textView.setText(null);
                            Bitmap bitmap = cacheTool.getForBitmap(ImageUtil.THUMBNAIL_CACHE_PRE
                                    + entry.getKey());

                            if (bitmap != null) {
                                viewHolder.imageView.setImageBitmap(bitmap);
                            } else {
                                Log.d(LOG_TAG + "onBindViewHolder", "position:" + position + " " +
                                        "ImageList key:" + entry.getKey() + " value:" + entry
                                        .getValue() + " no bitmap");
                                viewHolder.imageView.setImageDrawable(null);
                            }
                        }
                    } else {
                        viewHolder.textView.setText(R.string.load_failed);
                    }
                }

                holder.imageGridLayout.addView(viewHolder.rootItem, imageRecycler.getLayoutParams
                        ());

                // 此处绑定事件
                if (bindGridItemViewHolderListener != null) {
                    bindGridItemViewHolderListener.onBind(position, viewHolder, entry.getKey(),
                            StaticValue.TypeTag.TYPE_IMAGE_CONTENT, entry.getValue());
                }
            }
        }

        if (content.getAudioList() != null) {
            // 开始绑定音频列表
            Log.i(LOG_TAG + "onBindViewHolder", "position:" + position + " AudioList " + "count:"
                    + content.getAudioList().size());

            for (Map.Entry<String, Integer> entry : content.getAudioList().entrySet()) {

                // 一个音频布局控件
                ShiftChangeContentAudioViewHolder viewHolder = audioRecycler.getViewHolder();
                viewHolder.rootItem.setTag(entry.getKey());
                viewHolder.imageView.setImageResource(R.drawable.audio_image_list);

                if (entry.getValue() > -1 && entry.getValue() < 100) {
                    viewHolder.textView.setText(entry.getValue() + "%");
                }

                if (entry.getValue() == 100) {
                    viewHolder.textView.setText(AudioFileLengthFunction.getFunction()
                            .getAudioLength(cacheTool, entry.getKey()));
                }

                holder.audioGridLayout.addView(viewHolder.rootItem, audioRecycler.getLayoutParams
                        ());

                // 此处绑定事件
                if (bindGridItemViewHolderListener != null) {
                    bindGridItemViewHolderListener.onBind(position, viewHolder, entry.getKey(),
                            StaticValue.TypeTag.TYPE_AUDIO_CONTENT, entry.getValue());
                }
            }
        }

    }

    @Override
    public void onViewRecycled(ShiftChangeContentViewHolder holder) {
        super.onViewRecycled(holder);

        Log.i(LOG_TAG + "onViewRecycled", "holder adapter position:" + holder.getAdapterPosition
                () + " image grid count:" + holder.imageGridLayout.getChildCount() + " audio grid" +
                " count:" + holder.audioGridLayout.getChildCount());

        // 回收图片表格项
        for (int i = 0; i < holder.imageGridLayout.getChildCount(); i++) {
            Object tag = holder.imageGridLayout.getChildAt(i).getTag(R.id.view_holder_tag);

            if (tag != null && tag instanceof ShiftChangeContentImageViewHolder) {
                imageRecycler.putViewHolder((ShiftChangeContentImageViewHolder) tag);
            }
        }

        holder.imageGridLayout.removeAllViews();

        // 回收音频表格项
        for (int i = 0; i < holder.audioGridLayout.getChildCount(); i++) {
            Object tag = holder.audioGridLayout.getChildAt(i).getTag(R.id.view_holder_tag);

            if (tag != null && tag instanceof ShiftChangeContentAudioViewHolder) {
                audioRecycler.putViewHolder((ShiftChangeContentAudioViewHolder) tag);
            }
        }

        holder.audioGridLayout.removeAllViews();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
