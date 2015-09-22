package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.bean.CargoOwner;
import com.port.tally.management.function.CodeListManager;
import com.port.tally.management.util.StaticValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货主过滤器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class CargoOwnerFragment extends BaseCodeListFragment<CargoOwner> {

    /**
     * 关联的输入框
     */
    private EditText editText = null;

    /**
     * 设置关联编辑框
     *
     * @param editText 关联的输入框
     */
    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    protected SimpleAdapter onCreateAdapter(List<CargoOwner> dataList) {
        List<Map<String, String>> mapList = new ArrayList<>();

        for (CargoOwner data : dataList) {
            Map<String, String> map = new HashMap<>();

            map.put(NAME_TAG, data.getName());
            map.put(SHORT_CODE_TAG, data.getShortCode());

            mapList.add(map);
        }

        return new SimpleAdapter(getContext(), mapList, R.layout.two_column_text_item, new
                String[]{NAME_TAG , SHORT_CODE_TAG}, new int[]{R.id.two_column_text_item_left_textView , R.id.two_column_text_item_right_textView});
    }

    @Override
    protected void onSetFilter(ListView listView, SimpleAdapter adapter, List<CargoOwner>
            dataList) {
        listView.setTextFilterEnabled(true);

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
    protected List<CargoOwner> onCreateDataList() {
        return (List<CargoOwner>) CodeListManager.get(StaticValue.CodeListTag.CARGO_OWNER_LIST);
    }

    @Override
    protected void itemClick(CargoOwner data) {
        if (editText != null) {
            editText.setText(data.getName());
        }
    }
}
