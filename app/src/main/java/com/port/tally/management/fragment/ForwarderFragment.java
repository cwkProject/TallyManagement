package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/10/12.
 */

import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.port.tally.management.bean.Forwarder;

import java.util.List;

/**
 * 货代片段
 *
 * @author 超悟空
 * @version 1.0 2015/10/12
 * @since 1.0
 */
public class ForwarderFragment extends BaseCodeListFragment<Forwarder,String> {

    @Override
    protected SimpleAdapter onCreateAdapter(List<Forwarder> dataList) {
        return null;
    }

    @Override
    protected List<Forwarder> onCreateDataList() {
        return null;
    }

    @Override
    protected String itemClick(AdapterView<?> parent, View view, int position, long id) {
        return null;
    }
}
