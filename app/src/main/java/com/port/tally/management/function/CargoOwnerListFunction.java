package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;

import com.port.tally.management.bean.CargoOwner;
import com.port.tally.management.database.CargoOwnerOperator;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullCargoOwnerList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.BroadcastUtil;

import java.util.List;

/**
 * 货主数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class CargoOwnerListFunction extends BaseCodeListFunction<CargoOwner, Void> {

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public CargoOwnerListFunction(Context context) {
        super(context);
    }

    @Override
    protected BaseOperator<CargoOwner> onCreateOperator(Context context) {
        return new CargoOwnerOperator(context);
    }

    @Override
    protected void onLoadFromNetWork(Void parameter) {
        PullCargoOwnerList pullCargoOwnerList = new PullCargoOwnerList();

        pullCargoOwnerList.setWorkEndListener(new WorkBack<List<CargoOwner>>() {
            @Override
            public void doEndWork(boolean state, List<CargoOwner> data) {
                netWorkEndSetData(state, data);
            }
        }, false);

        pullCargoOwnerList.execute();
    }

    @Override
    protected void onNotify(Context context) {
        BroadcastUtil.sendBroadcast(context, StaticValue.CodeListTag.CARGO_OWNER_LIST);
    }
}
