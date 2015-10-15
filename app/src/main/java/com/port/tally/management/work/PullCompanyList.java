package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import com.port.tally.management.bean.Company;
import com.port.tally.management.data.CompanyListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取公司列表任务
 *
 * @author 超悟空
 * @version 1.0 2015/10/14
 * @since 1.0
 */
public class PullCompanyList extends DefaultWorkModel<String, List<Company>, CompanyListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return true;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.COMPANY_LIST_URL;
    }

    @Override
    protected List<Company> onRequestSuccessSetResult(CompanyListData data) {
        return data.getCompanyList();
    }

    @Override
    protected List<Company> onRequestFailedSetResult(CompanyListData data) {
        return null;
    }

    @Override
    protected CompanyListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        return new CompanyListData();
    }
}
