package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/12/12.
 */

import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 交接班正文内容列表ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/12/12
 * @since 1.0
 */
public class ShiftChangeContentViewHolder extends RecyclerView.ViewHolder {

    /**
     * 发送人名称文本框
     */
    public TextView nameTextView = null;

    /**
     * 发送时间文本框
     */
    public TextView timeTextView = null;

    /**
     * 消息内容文本框
     */
    public TextView messageTextView = null;

    /**
     * 存放图片的布局
     */
    public GridLayout imageGridLayout = null;

    /**
     * 存放音频的布局
     */
    public GridLayout audioGridLayout = null;

    /**
     * 存放本holder序号
     */
    public int holderIndex = 0;

    public ShiftChangeContentViewHolder(View itemView) {
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id
                .shift_change_content_send_name_textView);

        timeTextView = (TextView) itemView.findViewById(R.id
                .shift_change_content_send_time_textView);

        messageTextView = (TextView) itemView.findViewById(R.id
                .shift_change_content_message_textView);

        imageGridLayout = (GridLayout) itemView.findViewById(R.id
                .shift_change_content_Image_gridLayout);

        audioGridLayout = (GridLayout) itemView.findViewById(R.id
                .shift_change_content_audio_gridLayout);
    }
}
