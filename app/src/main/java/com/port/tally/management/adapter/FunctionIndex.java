package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/9/17.
 */

import android.content.Context;
import android.content.Intent;

import com.port.tally.management.activity.DianZiMap;
import com.port.tally.management.activity.EntrustQueryActivity;
import com.port.tally.management.activity.StockQueryActivity;
import com.port.tally.management.activity.TallyActivity;
import com.port.tally.management.activity.PushMessage;
import com.port.tally.management.activity.TrunkActivity;
import com.port.tally.management.activity.TrunkQuery;
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
                intent = new Intent(context, TallyActivity.class);
                break;
            case 1:
                // 衡重
                intent = new Intent(context, TrunkActivity.class);
                break;
            case 2:
                // 委托查询
                intent = new Intent(context, EntrustQueryActivity.class);
                break;
            case 3:
                // 堆存
                intent = new Intent(context, StockQueryActivity.class);
                break;
            case 4:
                // 汽运查询
                intent = new Intent(context, TrunkQuery.class);
                break;
            case 5:
                // 汽运作业
                intent = new Intent(context, TrunkActivity.class);
                break;
            case 6:
                // 作业计划
                intent = new Intent(context, WorkPlanQuery.class);
                break;
            case 7:
                // 交接班

                break;

            case 8:
                // 电子场图
                intent = new Intent(context, DianZiMap.class);
                break;
            case 9:
                // 消息推送
                intent = new Intent(context, PushMessage.class);
                break;

        }

        if (intent != null) {
            // 执行跳转
            context.startActivity(intent);
        }
    }
}
