package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.bean.BalanceWeight;
import com.port.tally.management.holder.BalanceWeightItemViewHolder;

import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;


/**
 * 衡重查询结果列表数据适配器
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class BalanceWeightRecyclerViewAdapter extends RecyclerView
        .Adapter<BalanceWeightItemViewHolder> {

    /**
     * 数据源
     */
    private List<BalanceWeight> dataList = null;

    /**
     * item点击事件监听器
     */
    private OnItemClickListenerForRecyclerViewItem<List<BalanceWeight>,
            BalanceWeightItemViewHolder> onItemClickListener = null;

    /**
     * 构造函数
     */
    public BalanceWeightRecyclerViewAdapter() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 构造函数
     *
     * @param dataList 初始数据源
     */
    public BalanceWeightRecyclerViewAdapter(List<BalanceWeight> dataList) {
        this.dataList = dataList;
    }

    /**
     * 添加一组数据
     *
     * @param position 添加位置
     * @param data     数组
     */
    public void addData(int position, List<BalanceWeight> data) {
        this.dataList.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    /**
     * 重置数据
     *
     * @param data 新数据
     */
    public void reset(List<BalanceWeight> data) {
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
    public void setOnItemClickListener(OnItemClickListenerForRecyclerViewItem<List<BalanceWeight
            >, BalanceWeightItemViewHolder> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BalanceWeightItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .balance_weight_list_item, parent, false);

        // 创建Item布局管理器
        return new BalanceWeightItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BalanceWeightItemViewHolder holder, int position) {

        // 数据绑定
        BalanceWeight balanceWeight = this.dataList.get(position);

        holder.cargoTextView.setText(balanceWeight.getCargo());
        holder.entrustTextView.setText(balanceWeight.getEntrust());
        holder.shipTextView.setText(balanceWeight.getShip());
        holder.entrustNumberTextView.setText(balanceWeight.getEntrustNumber());
        holder.planWeightTextView.setText(balanceWeight.getPlanWeight());
        holder.dutyWeightTextView.setText(balanceWeight.getDutyWeight());

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
