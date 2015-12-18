package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/10/26.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.bean.WorkPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * 今日待办列表数据适配器
 *
 * @author 超悟空
 * @version 1.0 2015/10/26
 * @since 1.0
 */
public class ToDoTaskPagerAdapter extends PagerAdapter {

    /**
     * 数据源
     */
    private List<WorkPlan> dataList = null;

    /**
     * 上下文
     */
    private Context context = null;

    /**
     * 页点击事件
     */
    public interface OnPagerClickListener {

        /**
         * 点击事件
         *
         * @param dataList 数据源
         * @param position 点击页索引
         */
        void onClick(List<WorkPlan> dataList, int position);
    }

    /**
     * 页点击事件
     */
    private OnPagerClickListener onPagerClickListener = null;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public ToDoTaskPagerAdapter(Context context) {
        this.dataList = new ArrayList<>();
        this.context = context;
    }

    /**
     * 构造函数
     *
     * @param context  上下文
     * @param dataList 初始数据源
     */
    public ToDoTaskPagerAdapter(Context context, List<WorkPlan> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    /**
     * 设置页点击事件
     *
     * @param onPagerClickListener 事件监听器
     */
    public void setOnPagerClickListener(OnPagerClickListener onPagerClickListener) {
        this.onPagerClickListener = onPagerClickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = onCreateView(position);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return this.dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 创建指定索引位置的布局
     *
     * @param position 指定索引
     *
     * @return 布局实例
     */
    private View onCreateView(final int position) {
        // Item根布局
        View itemView = View.inflate(context, R.layout.to_do_task_list_item, null);

        // 第一行文本框
        TextView firstTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_first_row_textView);
        // 第二行文本框
        TextView secondTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_second_row_textView);
        // 第三行文本框
        TextView thirdTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_third_row_textView);

        // 数据绑定
        WorkPlan data = this.dataList.get(position);

        if (data != null) {

            firstTextView.setText(String.format("%s\t\t%s\t", data.getTaskNumber(), data
                    .getEntrustName()));
            secondTextView.setText(String.format("%s\t%s\t|\t%s\t-\t%s", data.getAmount(),
                    data.getOperation(), data.getSourceCarrier(), data.getTargetCarrier()));
            thirdTextView.setText(String.format("%s\t\t-\t%s", data.getBeginTime(), data
                    .getEndTime()));
        }

        if (onPagerClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPagerClickListener.onClick(dataList, position);
                }
            });
        }

        return itemView;
    }

    /**
     * 重置数据
     *
     * @param data 新数据
     */
    public void reset(List<WorkPlan> data) {
        this.dataList.clear();
        if (data != null) {
            this.dataList.addAll(data);
        }
        notifyDataSetChanged();
    }
}
