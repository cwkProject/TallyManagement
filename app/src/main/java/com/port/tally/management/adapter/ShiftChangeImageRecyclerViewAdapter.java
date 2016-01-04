package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/12/7.
 */

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.holder.ShiftChangeImageViewHolder;

import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 交接班待发送图片列表适配器
 *
 * @author 超悟空
 * @version 1.0 2015/12/7
 * @since 1.0
 */
public class ShiftChangeImageRecyclerViewAdapter extends RecyclerView
        .Adapter<ShiftChangeImageViewHolder> {

    /**
     * 存有图片缩略图的缓存工具
     */
    private CacheTool cacheTool = null;

    /**
     * 显示的图片缩略图缓存key集合
     */
    private List<String> dataList = null;

    /**
     * item点击事件监听器
     */
    private OnItemClickListenerForRecyclerViewItem<List<String>, ShiftChangeImageViewHolder>
            onItemClickListener = null;

    /**
     * 构造函数
     *
     * @param cacheTool 图片缩略图缓存工具
     */
    public ShiftChangeImageRecyclerViewAdapter(CacheTool cacheTool) {
        this.cacheTool = cacheTool;
        dataList = new ArrayList<>();
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener 点击事件监听器
     */
    public void setOnItemClickListener(OnItemClickListenerForRecyclerViewItem<List<String>,
            ShiftChangeImageViewHolder> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 添加一条数据
     *
     * @param position 插入位置
     * @param data     添加的值
     */
    public void addData(int position, String data) {
        this.dataList.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 添加一组数据
     *
     * @param position 插入位置
     * @param data     添加的值集合
     */
    public void addData(int position, List<String> data) {
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
    public ShiftChangeImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .shift_change_image_item, parent, false);

        return new ShiftChangeImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShiftChangeImageViewHolder holder, int position) {

        Bitmap bitmap = cacheTool.getForBitmap(dataList.get(position));

        if (bitmap != null) {
            holder.imageView.setImageBitmap(bitmap);
        }

        // 绑定监听事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(dataList, holder);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
