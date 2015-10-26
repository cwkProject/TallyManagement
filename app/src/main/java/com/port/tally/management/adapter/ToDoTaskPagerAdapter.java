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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List<Map<String, Object>> dataList = null;

    /**
     * 上下文
     */
    private Context context = null;

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
    public ToDoTaskPagerAdapter(Context context, List<Map<String, Object>> dataList) {
        this.dataList = dataList;
        this.context = context;
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
    private View onCreateView(int position) {
        // Item根布局
        View itemView = View.inflate(context, R.layout.to_do_task_list_item, null);

        // 标题文本框
        TextView titleTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_title_textView);
        // 编号文本框
        TextView numberTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_number_textView);
        // 内容文本框
        TextView contentTextView = (TextView) itemView.findViewById(R.id
                .to_do_task_list_item_content_textView);

        // 数据绑定
        Map<String, Object> data = this.dataList.get(position);

        if (data != null) {

            titleTextView.setText("作业票");
            numberTextView.setText(data.get("taskno").toString());
            contentTextView.setText(String.format("%s/%s/%s/%s", data.get("tv_cargo").toString(),
                    data.get("tv_planwork").toString(), data.get("tv_sourcevector").toString(),
                    data.get("tv_destinationvector").toString()));
        }

        return itemView;
    }

    /**
     * 重置数据
     *
     * @param data 新数据
     */
    public void reset(List<Map<String, Object>> data) {
        this.dataList.clear();
        if (data != null) {
            this.dataList.addAll(data);
        }
        notifyDataSetChanged();
    }
}
