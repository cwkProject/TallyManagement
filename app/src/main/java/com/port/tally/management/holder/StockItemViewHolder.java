package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 堆存列表的ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/10/13
 * @since 1.0
 */
public class StockItemViewHolder extends RecyclerView.ViewHolder {

    /**
     * 货物文本框
     */
    public TextView cargoTextView = null;

    /**
     * 货主文本框
     */
    public TextView cargoOwnerTextView = null;

    /**
     * 航次文本框
     */
    public TextView voyageTextView = null;

    /**
     * 货代文本框
     */
    public TextView forwarderTextView = null;

    /**
     * 货场文本框
     */
    public TextView storageTextView = null;

    /**
     * 重量文本框
     */
    public TextView weightTextView = null;

    public StockItemViewHolder(View itemView) {
        super(itemView);

        cargoTextView = (TextView) itemView.findViewById(R.id.stock_list_item_cargo_textView);

        cargoOwnerTextView = (TextView) itemView.findViewById(R.id
                .stock_list_item_cargo_owner_textView);

        voyageTextView = (TextView) itemView.findViewById(R.id.stock_list_item_voyage_textView);

        storageTextView = (TextView) itemView.findViewById(R.id.stock_list_item_storage_textView);

        weightTextView = (TextView) itemView.findViewById(R.id.stock_list_item_weight_textView);

        forwarderTextView = (TextView) itemView.findViewById(R.id
                .stock_list_item_forwarder_textView);
    }
}
