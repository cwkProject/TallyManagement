package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 今日待办列表的ViewHolder
 *
 * @author 超悟空
 * @version 1.0 2015/10/24
 * @since 1.0
 */
public class ToDoTaskItemViewHolder extends RecyclerView.ViewHolder {

    /**
     * 标题文本框
     */
    public TextView titleTextView = null;

    /**
     * 编号文本框
     */
    public TextView numberTextView = null;

    /**
     * 内容文本框
     */
    public TextView contentTextView = null;

    public ToDoTaskItemViewHolder(View itemView) {
        super(itemView);

        // 标题文本框
        titleTextView = (TextView) itemView.findViewById(R.id.to_do_task_list_item_first_row_textView);
        // 编号文本框
        numberTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_second_row_textView);
        // 内容文本框
        contentTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_third_row_textView);
    }
}
