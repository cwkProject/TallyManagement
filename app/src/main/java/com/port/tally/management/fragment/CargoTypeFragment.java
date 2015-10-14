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
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.bean.CargoType;
import com.port.tally.management.function.CodeListManager;
import com.port.tally.management.util.StaticValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货物类别过滤器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class CargoTypeFragment extends BaseCodeListFragment<CargoType, String> {

    /**
     * 关联的输入框
     */
    private EditText editText = null;

    @Override
    protected void onCustom(View rootView, ListView listView, SimpleAdapter adapter,
                            List<CargoType> dataList) {

        // 获取activity布局中的关联控件
        editText = (EditText) getActivity().findViewById(R.id.cargo_edit_editText);
    }

    @Override
    protected SimpleAdapter onCreateAdapter(List<CargoType> dataList) {
        List<Map<String, String>> mapList = new ArrayList<>();

        if (dataList == null) {
            // 数据加载失败或未完成
            dataList = new ArrayList<>();
        }

        for (CargoType data : dataList) {
            Map<String, String> map = new HashMap<>();

            map.put(NAME_TAG, data.getName());
            map.put(SHORT_CODE_TAG, data.getShortCode());

            mapList.add(map);
        }

        return new SimpleAdapter(getContext(), mapList, R.layout.two_row_text_item, new
                String[]{NAME_TAG , SHORT_CODE_TAG}, new int[]{R.id.two_row_text_item_top_textView , R.id.two_row_text_item_bottom_textView});
    }

    @Override
    protected void onSetFilter(ListView listView, SimpleAdapter adapter, List<CargoType> dataList) {

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
    protected List<CargoType> onCreateDataList() {
        return CodeListManager.get(StaticValue.CodeListTag.CARGO_TYPE_LIST).getDataList();
    }

    @Override
    protected String itemClick(AdapterView parent, View view, int position, long id) {

        // 名称文本框
        TextView name = (TextView) view.findViewById(R.id.two_row_text_item_top_textView);

        return name.getText().toString();
    }
}