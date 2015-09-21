package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/9/17.
 */

import android.content.Context;
import android.content.Intent;

import com.port.tally.management.activity.EntrustQueryActivity;
import com.port.tally.management.activity.LiHuoActivity;
import com.port.tally.management.activity.QiYunZuoYe;
import com.port.tally.management.activity.WorkPlanQuery;

/**
 * 主界面功能索引
 *
 * @author 超悟空
 * @version 1.0 2015/9/17
 * @since 1.0
 */
public class FunctionIndex {

    /**
     * 跳转到选中的功能界面
     *
     * @param context  上下文
     * @param position 功能索引位置，从0开始
     */
    public static void toFunction(Context context, int position) {

        // 功能页跳转
        Intent intent = null;

        switch (position) {
            case 0:
                // 作业票
                intent = new Intent(context, LiHuoActivity.class);
                break;
            case 1:
                // 功能页跳转
                break;
            case 2:
                // 委托查询
                intent = new Intent(context, EntrustQueryActivity.class);
                break;
            case 3:

                break;
            case 4:
                // 功能页跳转
                break;
            case 5:
                // 汽运作业
                intent = new Intent(context, QiYunZuoYe.class);
                break;
            case 6:
                // 作业计划
                intent = new Intent(context, WorkPlanQuery.class);
                break;
            case 7:
                // 功能页跳转
                break;
            case 8:
                // 功能页跳转
                break;
        }

        if (intent != null) {
            // 执行跳转
            context.startActivity(intent);
        }
    }
}
