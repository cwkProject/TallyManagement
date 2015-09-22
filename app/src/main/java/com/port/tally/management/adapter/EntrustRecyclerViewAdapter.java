package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.bean.Entrust;
import com.port.tally.management.holder.EntrustItemViewHolder;

import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 委托查询结果列表数据适配器
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class EntrustRecyclerViewAdapter extends RecyclerView.Adapter<EntrustItemViewHolder> {

    /**
     * 数据源
     */
    private List<Entrust> dataList = null;

    /**
     * item点击事件监听器
     */
    private OnItemClickListenerForRecyclerViewItem<List<Entrust>, EntrustItemViewHolder>
            onItemClickListener = null;

    /**
     * 构造函数
     */
    public EntrustRecyclerViewAdapter() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 构造函数
     *
     * @param dataList 初始数据源
     */
    public EntrustRecyclerViewAdapter(List<Entrust> dataList) {
        this.dataList = dataList;
    }

    /**
     * 添加一组数据
     *
     * @param position 添加位置
     * @param data     数组
     */
    public void addData(int position, List<Entrust> data) {
        this.dataList.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    /**
     * 重置数据
     *
     * @param data 新数据
     */
    public void reset(List<Entrust> data) {
        this.dataList.clear();
        if (data != null) {
            this.dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置Item点击事件监听器
     *
     * @param onItemClickListener 监听器
     */
    public void setOnItemClickListener(OnItemClickListenerForRecyclerViewItem<List<Entrust>,
            EntrustItemViewHolder> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public EntrustItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .entrust_list_item, parent, false);

        // 创建Item布局管理器
        return new EntrustItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EntrustItemViewHolder holder, int position) {

        // 数据绑定
        Entrust entrust = this.dataList.get(position);

        holder.cargoTextView.setText(entrust.getCargo());
        holder.cargoOwnerTextView.setText(entrust.getCargoOwner());
        holder.voyageTextView.setText(entrust.getVoyage());
        holder.entrustTextView.setText(entrust.getEntrustNumber());

        // 各类属性字符串
        String[] attributes = new String[2];

        attributes[0] = entrust.getWork().length() == 0 ? null : entrust.getWork();
        attributes[1] = entrust.getPlanAmount().trim().length() == 0 && entrust.getPlanWeight()
                .trim().length() == 0 ? null : holder.itemView.getContext().getString(R.string
                .splice_plan_amount) + entrust.getPlanAmount() + "/" + entrust.getPlanWeight();

        holder.attributesTextView.setText(TextUtils.join(" , ", attributes));

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
