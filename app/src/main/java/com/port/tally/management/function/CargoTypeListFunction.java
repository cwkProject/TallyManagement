package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;

import com.port.tally.management.bean.CargoType;
import com.port.tally.management.database.CargoTypeOperator;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullCargoTypeList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.BroadcastUtil;

import java.util.List;

/**
 * 货物类别数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class CargoTypeListFunction extends BaseCodeListFunction<CargoType, Void> {

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public CargoTypeListFunction(Context context) {
        super(context);
    }

    @Override
    protected BaseOperator<CargoType> onCreateOperator(Context context) {
        return new CargoTypeOperator(context);
    }

    @Override
    protected void onLoadFromNetWork(Void parameter) {
        PullCargoTypeList pullCargoTypeList = new PullCargoTypeList();

        pullCargoTypeList.setWorkEndListener(new WorkBack<List<CargoType>>() {
            @Override
            public void doEndWork(boolean state, List<CargoType> data) {
                netWorkEndSetData(state, data);
            }
        });

        pullCargoTypeList.execute();
    }

    @Override
    protected void onNotify(Context context) {
        BroadcastUtil.sendBroadcast(context, StaticValue.CodeListTag.CARGO_TYPE_LIST);
    }
}
