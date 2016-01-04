package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/29.
 */

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.bean.Employee;
import com.port.tally.management.util.StaticValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工列表
 *
 * @author 超悟空
 * @version 1.0 2015/12/29
 * @since 1.0
 */
public class EmployeeFragment extends BaseCodeListFragment<Employee, Employee> {

    /**
     * 用户编码取值标签
     */
    private static final String CODE_TAG = "code_tag";

    /**
     * 公司编码取值标签
     */
    private static final String COMPANY_TAG = "company_tag";

    @Override
    protected SimpleAdapter onCreateAdapter(List<Map<String, String>> mapList) {
        return new SimpleAdapter(getContext(), mapList, R.layout.employee_list_item, new
                String[]{NAME_TAG , SHORT_CODE_TAG}, new int[]{R.id.employee_list_name_textView ,
                                                               R.id.employee_list_short_code_textView});
    }

    @Override
    protected void onFillDataList(@Nullable List<Employee> dataList, List<Map<String, String>>
            mapList) {
        if (dataList == null) {
            // 数据加载失败或未完成
            return;
        }

        for (Employee data : dataList) {
            Map<String, String> map = new HashMap<>();

            map.put(NAME_TAG, data.getName());
            map.put(SHORT_CODE_TAG, data.getShortCode());
            map.put(CODE_TAG, data.getId());
            map.put(COMPANY_TAG, data.getCompany());

            mapList.add(map);
        }
    }

    @Override
    protected Employee itemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, String> map = (Map<String, String>) parent.getItemAtPosition(position);

        Employee employee = new Employee();

        employee.setName(map.get(NAME_TAG));
        employee.setId(map.get(CODE_TAG));
        employee.setCompany(map.get(COMPANY_TAG));
        employee.setShortCode(map.get(SHORT_CODE_TAG));

        return employee;
    }

    @Override
    protected String onActionTag() {
        return StaticValue.CodeListTag.EMPLOYEE_LIST;
    }

    @Override
    protected EditText onFilterEditText(View rootView) {
        return (EditText) getActivity().findViewById(R.id.activity_employee_list_editText);
    }
}
