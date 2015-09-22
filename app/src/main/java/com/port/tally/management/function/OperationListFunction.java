package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;

import com.port.tally.management.bean.Operation;
import com.port.tally.management.database.OperationOperator;
import com.port.tally.management.work.PullOperationList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkModel;

import java.util.List;

/**
 * 作业过程数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class OperationListFunction extends BaseCodeListFunction<Operation> {

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public OperationListFunction(Context context) {
        super(context);
    }

    @Override
    protected BaseOperator<Operation> onCreateOperator(Context context) {
        return new OperationOperator(context);
    }

    @Override
    protected WorkModel<?, List<Operation>> onCreateWork(BaseOperator<Operation> operator,
                                                         Context context) {
        return new PullOperationList();
    }
}
