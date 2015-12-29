package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/12/28.
 */

import android.content.Context;
import android.util.Log;

import com.port.tally.management.bean.Employee;
import com.port.tally.management.database.EmployeeOperator;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullEmployeeList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.util.BroadcastUtil;

import java.util.List;

/**
 * 员工列表数据加载工具
 *
 * @author 超悟空
 * @version 1.0 2015/12/28
 * @since 1.0
 */
public class EmployeeListFunction extends BaseCodeListFunction<Employee, String> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "EmployeeListFunction.";

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param parameter 查询条件参数
     */
    public EmployeeListFunction(Context context, String parameter) {
        super(context, parameter);
    }

    @Override
    protected BaseOperator<Employee> onCreateOperator(Context context) {
        return new EmployeeOperator(context);
    }

    @Override
    protected void onLoadFromNetWork(String parameter) {
        PullEmployeeList pullEmployeeList = new PullEmployeeList();

        boolean state = pullEmployeeList.execute(parameter);

        netWorkEndSetData(state, pullEmployeeList.getResult());
    }

    @Override
    protected List<Employee> onLoadFromDataBase(BaseOperator<Employee> operator, String parameter) {
        if (operator == null || operator.isEmpty() || parameter == null) {
            Log.i(LOG_TAG + "onLoadFromDataBase", "database null or parameter null");
            return null;
        }

        return operator.queryWithCondition(parameter);
    }

    @Override
    protected void onNotify(Context context) {
        BroadcastUtil.sendBroadcast(context, StaticValue.CodeListTag.EMPLOYEE_LIST);
    }
}
