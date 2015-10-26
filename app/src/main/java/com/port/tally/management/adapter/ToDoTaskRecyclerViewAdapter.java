package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.holder.ToDoTaskItemViewHolder;

import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 今日待办列表数据适配器
 *
 * @author 超悟空
 * @version 1.0 2015/10/24
 * @since 1.0
 */
public class ToDoTaskRecyclerViewAdapter extends RecyclerView.Adapter<ToDoTaskItemViewHolder> {

    /**
     * 数据源
     */
    private List<Map<String, Object>> dataList = null;

    /**
     * item点击事件监听器
     */
    private OnItemClickListenerForRecyclerViewItem<List<Map<String, Object>>,
            ToDoTaskItemViewHolder> onItemClickListener = null;

    /**
     * 构造函数
     */
    public ToDoTaskRecyclerViewAdapter() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 构造函数
     *
     * @param dataList 初始数据源
     */
    public ToDoTaskRecyclerViewAdapter(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    /**
     * 添加一组数据
     *
     * @param position 添加位置
     * @param data     数组
     */
    public void addData(int position, List<Map<String, Object>> data) {
        this.dataList.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    /**
     * 重置数据
     *
     * @param data 新数据
     */
    public void reset(List<Map<String, Object>> data) {
        this.dataList.clear();
        if (data != null) {
            this.dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clear() {
        if (dataList.size() > 0) {
            int count = dataList.size();
            dataList.clear();
            notifyItemRangeRemoved(0, count);
        }
    }

    /**
     * 设置Item点击事件监听器
     *
     * @param onItemClickListener 监听器
     */
    public void setOnItemClickListener(OnItemClickListenerForRecyclerViewItem<List<Map<String,
            Object>>, ToDoTaskItemViewHolder> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ToDoTaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .to_do_task_list_item, parent, false);

        // 创建Item布局管理器
        return new ToDoTaskItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ToDoTaskItemViewHolder holder, int position) {

        // 数据绑定
        Map<String, Object> data = this.dataList.get(position);

        if (data != null) {

            holder.titleTextView.setText("作业票");
            holder.numberTextView.setText(data.get("taskno").toString());
            holder.contentTextView.setText(String.format("%s/%s/%s/%s", data.get("tv_cargo")
                    .toString(), data.get("tv_planwork").toString(), data.get("tv_sourcevector")
                    .toString(), data.get("tv_destinationvector").toString()));
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
        return this.dataList.size();
    }
}
