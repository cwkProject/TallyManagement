package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 主界面功能列表的ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/10/120
 * @since 1.0
 */
public class MainFunctionItemViewHolder extends RecyclerView.ViewHolder {

    /**
     * 功能图标
     */
    public ImageView iconImageView = null;

    /**
     * 功能标题
     */
    public TextView titleTextView = null;

    public MainFunctionItemViewHolder(View itemView) {
        super(itemView);

        iconImageView = (ImageView) itemView.findViewById(R.id.function_grid_item_image);

        titleTextView = (TextView) itemView.findViewById(R.id.function_grid_item_text);
    }
}
