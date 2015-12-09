package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/12/9.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 交接班待发送音频列表ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/12/9
 * @since 1.0
 */
public class ShiftChangeAudioViewHolder extends RecyclerView.ViewHolder {

    /**
     * 图片控件
     */
    public ImageView imageView = null;

    /**
     * 文本控件
     */
    public TextView textView = null;

    public ShiftChangeAudioViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.shift_change_audio_item_imageView);

        textView = (TextView) itemView.findViewById(R.id.shift_change_audio_item_textView);
    }
}
