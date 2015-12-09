package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/12/7.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.port.tally.management.R;

/**
 * 交接班待发送图片列表ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/12/7
 * @since 1.0
 */
public class ShiftChangeImageViewHolder extends RecyclerView.ViewHolder {

    /**
     * 图片控件
     */
    public ImageView imageView = null;

    public ShiftChangeImageViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.shift_change_image_item_imageView);
    }
}
