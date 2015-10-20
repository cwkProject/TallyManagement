package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/9/7.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.MainFunctionRecyclerViewAdapter;
import com.port.tally.management.adapter.PushMessageAdapter;
import com.port.tally.management.holder.MainFunctionItemViewHolder;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.common.function.CheckUpdate;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.LoginStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.port.tally.management.adapter.FunctionIndex.toFunction;

/**
 * 主界面
 *
 * @author 超悟空
 * @version 2.0 2015/10/20
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private TextView moreTv;
    private XListView listView;
    private PushMessageAdapter pushMessageAdapter;
    private List<Map<String, Object>> dataList = null;
    int flag = 1;
    private Toast mToast;
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "MainActivity.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 加载界面
        initView();

        initListView();
        moreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PushMessage.class);
                startActivity(intent);

            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.app_name);

        // 初始化功能布局
        initGridView();
        moreTv = (TextView) findViewById(R.id.tv_more);
        // 执行检查更新
        checkUpdate();
    }

    /**
     * 检查新版本
     */
    private void checkUpdate() {
        // 新建版本检查工具
        CheckUpdate checkUpdate = new CheckUpdate(this, StaticValue.APP_CODE);
        // 执行检查
        checkUpdate.checkInBackground();
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        // 得到Toolbar标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 得到标题文本
        //toolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);

        // 关联ActionBar
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        // 取消原actionBar标题
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化表格布局
     */
    private void initGridView() {

        // RecyclerView列表对象
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);

        // 创建布局管理器
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this, 2);

        // 设置布局管理器
        recyclerView.setLayoutManager(gridlayoutManager);

        // 新建数据适配器
        MainFunctionRecyclerViewAdapter adapter = new MainFunctionRecyclerViewAdapter(this);

        // 设置点击事件
        adapter.setOnItemClickListener(new OnItemClickListenerForRecyclerViewItem<String[],
                MainFunctionItemViewHolder>() {
            @Override
            public void onClick(String[] dataSource, MainFunctionItemViewHolder holder) {
                onGridItemClick(holder.getAdapterPosition());
            }
        });

        // 绑定数据适配器
        recyclerView.setAdapter(adapter);
    }

    /**
     * 表格项点击事件触发操作，
     * 默认触发功能跳转，
     * 并检测登录状态
     *
     * @param position 点击的位置索引
     */
    private void onGridItemClick(int position) {

        if (!LoginStatus.getLoginStatus().isLogin()) {
            // 未登录
            // 新建意图,跳转到登录页面
            Intent intent = new Intent(this, LoginActivity.class);
            // 执行跳转
            startActivity(intent);
            finish();
            return;
        }

        // 跳转到功能
        toFunction(this, position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                // 退出操作
                doLogout();
                break;
        }
        return true;
    }

    /**
     * 退出操作
     */
    private void doLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //显示数据
    private void showData(String key, String type, String company, String cargo, String trustno) {
        key = "3";
        type = "1";
        company = "14";
        loadValue(key, type, company, cargo, trustno);
    }

    @Override
    public void onLoadMore() {
        String count = "5";
        String stratcount = String.valueOf(flag);
        String company = "14";
        String cargo = null;
        String trustno = null;
        loadValue(count, stratcount, company, cargo, trustno);
    }

    private void onLoad() {

        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");
    }

    public void onRefresh() {
        //        showData();
    }

    //给个控件赋值
    private void loadValue(String key, final String type, String company, String cargo, String
            trustno) {

        //实例化，传入参数
        ToallyManageWork toallyManageWork = new ToallyManageWork();

        toallyManageWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {

                if ("1".equals(type)) {
                    dataList.clear();

                    flag = 1;
                }

                if (b && data != null) {

                    flag += data.size();
                    listView.setPullLoadEnable(true);
                    dataList.addAll(data);
                    pushMessageAdapter.notifyDataSetChanged();
                } else {
                    //清空操作
                    //                    listView.setPullLoadEnable(false);
                    //                    for(int i =0;i<1;i++){
                    showToast("无相关信息");
                    //                        tallyManageAdapter.notifyDataSetChanged();
                    //                    }


                }

                pushMessageAdapter.notifyDataSetChanged();
                onLoad();
            }
        });
        toallyManageWork.beginExecute(key, company, type, cargo, trustno);
    }

    private void initListView() {

        listView = (XListView) findViewById(R.id.xListView);
        dataList = new ArrayList<>();
        showData(null, null, null, null, null);
        listView.setPullLoadEnable(true);
        pushMessageAdapter = new PushMessageAdapter(MainActivity.this, dataList);

        pushMessageAdapter.notifyDataSetChanged();
        listView.setAdapter(pushMessageAdapter);

        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                //                String cgno = "14";
                //                Bundle b = new Bundle();
                //                Intent intent = new Intent();
                //                //                b.putStringArray("Cgno", cgno);
                //                b.putString("Cgno", cgno);
                //                intent = new Intent(TallyActivity.this, TallyDetail.class);
                //                intent.putExtras(b);
                //                startActivity(intent);
                HashMap map = (HashMap) arg0.getItemAtPosition(arg2);
                //                派工编码
                //                委托编码
                //                票货编码
                String[] strings = new String[]{map.get("pmno").toString() , map.get
                        ("tv_Entrust").toString() , map.get("gbno").toString()};
                Bundle b = new Bundle();
                Intent intent = new Intent();
                b.putStringArray("detailString", strings);

                Log.i("valuedetailString的值是", strings[0] + "" + strings[1] + "" + strings[2] + "");
                intent = new Intent(MainActivity.this, TallyDetail.class);
                intent.putExtras(b);
                startActivity(intent);


            }

        });
    }

    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

        } else {
            mToast.setText(msg);

            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
