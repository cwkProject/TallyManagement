package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 委托列表的ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class EntrustItemViewHolder extends RecyclerView.ViewHolder {

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
     * 航次文本框
     */
    public TextView entrustTextView = null;

    /**
     * 各种委托属性文本框
     */
    public TextView attributesTextView = null;

    public EntrustItemViewHolder(View itemView) {
        super(itemView);

        cargoTextView = (TextView) itemView.findViewById(R.id.entrust_list_item_cargo_textView);

        cargoOwnerTextView = (TextView) itemView.findViewById(R.id
                .entrust_list_item_cargo_owner_textView);

        voyageTextView = (TextView) itemView.findViewById(R.id.entrust_list_item_voyage_textView);

        entrustTextView = (TextView) itemView.findViewById(R.id.entrust_list_item_entrust_textView);

        attributesTextView = (TextView) itemView.findViewById(R.id
                .entrust_list_item_work_attributes_textView);
    }
}
