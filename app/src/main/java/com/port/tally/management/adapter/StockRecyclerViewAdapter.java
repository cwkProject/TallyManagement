package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.bean.Stock;
import com.port.tally.management.holder.StockItemViewHolder;

import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;


/**
 * 堆存查询结果列表数据适配器
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class StockRecyclerViewAdapter extends RecyclerView.Adapter<StockItemViewHolder> {

    /**
     * 数据源
     */
    private List<Stock> dataList = null;

    /**
     * item点击事件监听器
     */
    private OnItemClickListenerForRecyclerViewItem<List<Stock>, StockItemViewHolder>
            onItemClickListener = null;

    /**
     * 构造函数
     */
    public StockRecyclerViewAdapter() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 构造函数
     *
     * @param dataList 初始数据源
     */
    public StockRecyclerViewAdapter(List<Stock> dataList) {
        this.dataList = dataList;
    }

    /**
     * 添加一组数据
     *
     * @param position 添加位置
     * @param data     数组
     */
    public void addData(int position, List<Stock> data) {
        this.dataList.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    /**
     * 重置数据
     *
     * @param data 新数据
     */
    public void reset(List<Stock> data) {
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
        int count = dataList.size();
        this.dataList.clear();
        notifyItemRangeRemoved(0, count);
    }

    /**
     * 设置Item点击事件监听器
     *
     * @param onItemClickListener 监听器
     */
    public void setOnItemClickListener(OnItemClickListenerForRecyclerViewItem<List<Stock>,
            StockItemViewHolder> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public StockItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .stock_list_item, parent, false);

        // 创建Item布局管理器
        return new StockItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StockItemViewHolder holder, int position) {

        // 数据绑定
        Stock stock = this.dataList.get(position);

        holder.cargoTextView.setText(stock.getCargo());
        holder.cargoOwnerTextView.setText(stock.getCargoOwner());
        holder.voyageTextView.setText(stock.getVoyage());
        holder.forwarderTextView.setText(stock.getForwarder());
        holder.weightTextView.setText(stock.getWeight());
        holder.storageTextView.setText(stock.getStorage());

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
