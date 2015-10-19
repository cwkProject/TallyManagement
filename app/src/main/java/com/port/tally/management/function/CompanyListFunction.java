package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;

import com.port.tally.management.bean.Company;
import com.port.tally.management.database.CompanyOperator;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullCompanyList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.BroadcastUtil;

import java.util.List;

/**
 * 公司数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/10/14
 * @since 1.0
 */
public class CompanyListFunction extends BaseCodeListFunction<Company, Void> {

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public CompanyListFunction(Context context) {
        super(context);
    }

    @Override
    protected BaseOperator<Company> onCreateOperator(Context context) {
        return new CompanyOperator(context);
    }

    @Override
    protected void onLoadFromNetWork(Void parameter) {
        PullCompanyList pullCompanyList = new PullCompanyList();

        pullCompanyList.setWorkEndListener(new WorkBack<List<Company>>() {
            @Override
            public void doEndWork(boolean state, List<Company> data) {
                netWorkEndSetData(state, data);
            }
        });

        pullCompanyList.execute();
    }

    @Override
    protected void onNotify(Context context) {
        BroadcastUtil.sendBroadcast(context, StaticValue.CodeListTag.COMPANY_LIST);
    }
}
