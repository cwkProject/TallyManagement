package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/12/23.
 */

import android.content.Context;
import android.support.v7.widget.GridLayout;

import com.port.tally.management.holder.ShiftChangeContentAudioViewHolder;

import java.util.Stack;

/**
 * 交接班消息列表音频控件回收器
 *
 * @author 超悟空
 * @version 1.0 2015/12/23
 * @since 1.0
 */
public class ShiftChangeContentAudioRecycler {

    /**
     * 存放布局引用的栈
     */
    private Stack<ShiftChangeContentAudioViewHolder> stack = new Stack<>();

    /**
     * 上下文
     */
    private Context context = null;

    /**
     * 控件大小
     */
    private int size = 0;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public ShiftChangeContentAudioRecycler(Context context) {
        this.context = context;

        size = context.getResources().getDisplayMetrics().widthPixels / 6;
    }

    /**
     * 获取一个布局
     *
     * @return 布局管理器
     */
    public ShiftChangeContentAudioViewHolder getViewHolder() {
        if (!stack.empty()) {
            return stack.pop();
        } else {
            return new ShiftChangeContentAudioViewHolder(context);
        }
    }

    /**
     * 存入一个不使用的布局
     *
     * @param viewHolder 布局管理器
     */
    public void putViewHolder(ShiftChangeContentAudioViewHolder viewHolder) {
        if (viewHolder != null) {
            // 清除资源引用
            viewHolder.imageView.setImageDrawable(null);
            viewHolder.textView.setText(null);
            viewHolder.rootItem.setOnClickListener(null);

            // 入栈
            stack.push(viewHolder);
        }
    }

    /**
     * 获取布局属性
     *
     * @return 配置好的布局属性
     */
    public GridLayout.LayoutParams getLayoutParams() {
        // 创建一个GridLayout布局属性
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec
                (GridLayout.UNDEFINED), GridLayout.spec(GridLayout.UNDEFINED));
        layoutParams.height = size;
        layoutParams.width = size;

        return layoutParams;
    }
}
