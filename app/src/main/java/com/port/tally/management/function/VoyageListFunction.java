package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;

import com.port.tally.management.bean.Voyage;
import com.port.tally.management.database.VoyageOperator;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullVoyageList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.BroadcastUtil;

import java.util.List;

/**
 * 航次数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class VoyageListFunction extends BaseCodeListFunction<Voyage, Void> {

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public VoyageListFunction(Context context) {
        super(context);
    }

    @Override
    protected BaseOperator<Voyage> onCreateOperator(Context context) {
        return new VoyageOperator(context);
    }

    @Override
    protected void onLoadFromNetWork(Void parameter) {
        PullVoyageList pullVoyageList = new PullVoyageList();

        pullVoyageList.setWorkEndListener(new WorkBack<List<Voyage>>() {
            @Override
            public void doEndWork(boolean state, List<Voyage> data) {
                netWorkEndSetData(state, data);
            }
        }, false);

        pullVoyageList.execute();
    }

    @Override
    protected void onNotify(Context context) {
        BroadcastUtil.sendBroadcast(context, StaticValue.CodeListTag.VOYAGE_LIST);
    }
}
