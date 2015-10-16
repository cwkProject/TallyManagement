package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.bean.Company;
import com.port.tally.management.function.CodeListManager;
import com.port.tally.management.util.StaticValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公司过滤器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class CompanyFragment extends BaseCodeListFragment<Company, String> {

    /**
     * 关联的输入框
     */
    private EditText editText = null;

    /**
     * 公司编码取值标签
     */
    private static final String COMPANY_CODE_TAG = "company_code_tag";

    @Override
    protected SimpleAdapter onCreateAdapter(List<Company> dataList) {
        List<Map<String, String>> mapList = new ArrayList<>();

        if (dataList == null) {
            // 数据加载失败或未完成
            dataList = new ArrayList<>();
        }

        for (Company data : dataList) {
            Map<String, String> map = new HashMap<>();

            map.put(NAME_TAG, data.getName());
            map.put(COMPANY_CODE_TAG, data.getId());

            mapList.add(map);
        }

        return new SimpleAdapter(getContext(), mapList, R.layout.only_text_item, new
                String[]{NAME_TAG , COMPANY_CODE_TAG}, new int[]{R.id.only_text_item_textView , R
                .id.only_text_item_hide_textView});
    }

    @Override
    protected void onCustom(View rootView, ListView listView, SimpleAdapter adapter,
                            List<Company> dataList) {

        // 获取activity布局中的关联控件
        editText = (EditText) getActivity().findViewById(R.id.company_edit_editText);
    }

    @Override
    protected void onSetFilter(ListView listView, SimpleAdapter adapter, List<Company> dataList) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @Override
    protected List<Company> onCreateDataList() {
        return CodeListManager.get(StaticValue.CodeListTag.COMPANY_LIST).getDataList();
    }

    @Override
    protected String itemClick(AdapterView parent, View view, int position, long id) {

        Map<String, String> map = (Map<String, String>) parent.getItemAtPosition(position);

        // 文本框赋值
        editText.setText(map.get(NAME_TAG));

        return map.get(COMPANY_CODE_TAG);
    }
}
