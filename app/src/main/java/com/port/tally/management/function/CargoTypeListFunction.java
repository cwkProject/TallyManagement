package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;

import com.port.tally.management.bean.CargoType;
import com.port.tally.management.database.CargoTypeOperator;
import com.port.tally.management.work.PullCargoTypeList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkModel;

import java.util.List;

/**
 * 货物类别数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class CargoTypeListFunction extends BaseCodeListFunction<CargoType> {

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
    protected WorkModel<?, List<CargoType>> onCreateWork(BaseOperator<CargoType> operator,
                                                         Context context) {
        return new PullCargoTypeList();
    }
}
