package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.bean.CargoOwner;
import com.port.tally.management.function.CodeListManager;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.operate.DataChangeObserver;

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

    @Override
    protected SimpleAdapter onCreateAdapter(List<CargoOwner> dataList) {
        List<Map<String, String>> mapList = new ArrayList<>();

        if (dataList == null) {
            // 数据加载失败或未完成
            dataList = new ArrayList<>();
        }

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
    protected void onCustom(View rootView, ListView listView, SimpleAdapter adapter,
                            List<CargoOwner> dataList) {

        // 获取activity布局中的关联控件
        editText = (EditText) getActivity().findViewById(R.id.cargo_owner_edit_editText);
    }

    @Override
    protected void onSetClickBind(final DataChangeObserver<CargoOwner> clickListener) {
        if (clickListener != null) {
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CargoOwner cargoOwner = new CargoOwner();
                    cargoOwner.setName(editText.getText().toString());
                    clickListener.notifyDataChange(cargoOwner);
                }
            });
        }
    }

    @Override
    protected void onSetFilter(ListView listView, SimpleAdapter adapter, List<CargoOwner>
            dataList) {

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
        return CodeListManager.get(StaticValue.CodeListTag.CARGO_OWNER_LIST).getDataList();
    }

    @Override
    protected void itemClick(CargoOwner data) {
        if (editText != null) {
            editText.setText(data.getName());
        }
    }
}
