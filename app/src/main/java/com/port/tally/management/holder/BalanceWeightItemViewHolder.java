package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 衡重列表的ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class BalanceWeightItemViewHolder extends RecyclerView.ViewHolder {

    /**
     * 货物文本框
     */
    public TextView cargoTextView = null;

    /**
     * 委托人文本框
     */
    public TextView entrustTextView = null;

    /**
     * 船名文本框
     */
    public TextView shipTextView = null;

    /**
     * 委托号文本框
     */
    public TextView entrustNumberTextView = null;

    /**
     * 计划重量文本框
     */
    public TextView planWeightTextView = null;

    /**
     * 当班重量文本框
     */
    public TextView dutyWeightTextView = null;

    public BalanceWeightItemViewHolder(View itemView) {
        super(itemView);

        cargoTextView = (TextView) itemView.findViewById(R.id
                .balance_weight_list_item_cargo_textView);

        entrustTextView = (TextView) itemView.findViewById(R.id
                .balance_weight_list_item_entrust_textView);

        shipTextView = (TextView) itemView.findViewById(R.id
                .balance_weight_list_item_ship_textView);

        entrustNumberTextView = (TextView) itemView.findViewById(R.id
                .balance_weight_list_item_entrust_number_textView);

        planWeightTextView = (TextView) itemView.findViewById(R.id
                .balance_weight_list_item_plan_weight_textView);

        dutyWeightTextView = (TextView) itemView.findViewById(R.id
                .balance_weight_list_item_duty_weight_textView);
    }
}
