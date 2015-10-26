package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/10/24.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.adapter.ToDoTaskPagerAdapter;
import com.port.tally.management.work.ToallyManageWork;

import org.mobile.library.model.work.WorkBack;

import java.util.List;
import java.util.Map;

/**
 * 今日待办布局片段
 *
 * @author 超悟空
 * @version 1.0 2015/10/24
 * @since 1.0
 */
public class ToDoTaskFragment extends Fragment {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ToDoTaskFragment.";

    /**
     * 列表数据适配器
     */
    //private ToDoTaskRecyclerViewAdapter adapter = new ToDoTaskRecyclerViewAdapter();

    /**
     * 列表数据适配器
     */
    private ToDoTaskPagerAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // 根布局
        View rootView = inflater.inflate(R.layout.fragment_to_do_task, container, false);

        // 初始化控件
        initView(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 加载数据
        loadData();
    }

    /**
     * 初始化控件
     *
     * @param rootView 根布局
     */
    private void initView(View rootView) {

        //        // RecyclerView列表对象
        //        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id
        //                .fragment_to_do_task_recyclerView);
        //
        //        // 设置item动画
        //        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //
        //        // 创建布局管理器
        //        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
        //                LinearLayoutManager.HORIZONTAL, false);
        //
        //        // 设置布局管理器
        //        recyclerView.setLayoutManager(layoutManager);
        //
        //        // 列表布局参数
        //        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        //
        //        // 给列表固定高度
        //        layoutParams.height = getItemHeight(recyclerView);
        //
        //        Log.i(LOG_TAG + "initView", "layoutParams height is " + layoutParams.height);
        //
        //        recyclerView.setLayoutParams(layoutParams);
        //
        //        recyclerView.setHasFixedSize(true);
        //
        //        // 设置数据适配器
        //        recyclerView.setAdapter(adapter);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.fragment_to_do_task_viewPager);
        // 列表布局参数
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        // 给列表固定高度
        layoutParams.height = getItemHeight(viewPager);
        viewPager.setLayoutParams(layoutParams);

        adapter = new ToDoTaskPagerAdapter(getActivity());

        viewPager.setAdapter(adapter);
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //实例化，传入参数
        ToallyManageWork toallyManageWork = new ToallyManageWork();

        toallyManageWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean state, List<Map<String, Object>> data) {

                if (state && data != null) {
                    adapter.reset(data);
                }
            }
        });

        toallyManageWork.beginExecute("5", "14", "1", null, null);
    }

    /**
     * 计算布局真实宽高
     *
     * @param child 要计算的布局
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT);
        }

        // View的宽度信息
        int childMeasureWidth = ViewGroup.getChildMeasureSpec(View.MeasureSpec.UNSPECIFIED, 0, lp
                .width);

        // View的高度信息
        int childMeasureHeight = 0;

        if (lp.height > 0) {
            childMeasureHeight = View.MeasureSpec.makeMeasureSpec(lp.height, View.MeasureSpec
                    .EXACTLY);
            //最后一个参数表示：适合、匹配
        } else {
            childMeasureHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec
                    .UNSPECIFIED);//未指定
        }

        //将宽和高设置给child
        child.measure(childMeasureWidth, childMeasureHeight);
    }

    /**
     * 获取Item高度，包含父布局padding和item布局margin
     *
     * @param container 父布局
     *
     * @return Item高度值
     */
    private int getItemHeight(ViewGroup container) {
        // Item布局
        View item = getActivity().getLayoutInflater().inflate(R.layout.to_do_task_list_item,
                container, false);

        // 计算Item布局宽高
        measureView(item);

        int height = 0;

        if (item.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) item
                    .getLayoutParams();

            height += mlp.topMargin + mlp.bottomMargin;
        }

        height += container.getPaddingTop() + container.getPaddingBottom();

        Log.i(LOG_TAG + "initView", "item height is " + item.getHeight());
        Log.i(LOG_TAG + "initView", "item measured height is " + item.getMeasuredHeight());

        return item.getMeasuredHeight() + height;
    }
}
