package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.bean.Operation;
import com.port.tally.management.util.StaticValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作业过程过滤器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class OperationFragment extends BaseCodeListFragment<Operation, String> {

    @Override
    protected SimpleAdapter onCreateAdapter(List<Map<String, String>> mapList) {
        return new SimpleAdapter(getContext(), mapList, R.layout.two_row_text_item, new
                String[]{NAME_TAG , SHORT_CODE_TAG}, new int[]{R.id.two_row_text_item_top_textView , R.id.two_row_text_item_bottom_textView});
    }

    @Override
    protected void onFillDataList(@Nullable List<Operation> dataList, List<Map<String, String>>
            mapList) {
        if (dataList == null) {
            // 数据加载失败或未完成
            return;
        }

        for (Operation data : dataList) {
            Map<String, String> map = new HashMap<>();

            map.put(NAME_TAG, data.getName());
            map.put(SHORT_CODE_TAG, data.getShortCode());

            mapList.add(map);
        }
    }

    @Override
    protected String onActionTag() {
        return StaticValue.CodeListTag.OPERATION_LIST;
    }

    @Override
    protected EditText onFilterEditText(View rootView) {
        return (EditText) getActivity().findViewById(R.id.operation_edit_editText);
    }

    @Override
    protected String itemClick(AdapterView parent, View view, int position, long id) {

        Map<String, String> map = (Map<String, String>) parent.getItemAtPosition(position);

        return map.get(NAME_TAG);
    }
}
