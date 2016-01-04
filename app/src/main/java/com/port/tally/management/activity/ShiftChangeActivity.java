package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/12/2.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.bean.Employee;
import com.port.tally.management.fragment.ShiftChangeAudioListFragment;
import com.port.tally.management.fragment.ShiftChangeContentFragment;
import com.port.tally.management.fragment.ShiftChangeImageListFragment;
import com.port.tally.management.fragment.ShiftChangeRecordFragment;
import com.port.tally.management.function.AudioFileLengthFunction;
import com.port.tally.management.util.StaticValue;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.common.function.InputMethodController;
import org.mobile.library.global.GlobalApplication;
import org.mobile.library.model.operate.DataChangeObserver;

import java.io.File;

/**
 * 交接班界面
 *
 * @author 超悟空
 * @version 1.0 2015/12/2
 * @since 1.0
 */
public class ShiftChangeActivity extends AppCompatActivity {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeActivity.";

    /**
     * 保存待发送相关数据的取值标签
     */
    private static final String SAVE_CONTENT = "save_content";

    /**
     * 保存待发送文本的取值标签
     */
    private static final String SAVE_MESSAGE = "save_massage";

    /**
     * 保存收件人id的取值标签
     */
    private static final String EMPLOYEE_ID = "Employee_id";

    /**
     * 保存收件人姓名的取值标签
     */
    private static final String EMPLOYEE_NAME = "Employee_name";

    /**
     * 保存收件人公司的取值标签
     */
    private static final String EMPLOYEE_COMPANY = "Employee_company";

    /**
     * 保存收件人编码的取值标签
     */
    private static final String EMPLOYEE_SHORT_CODE = "Employee_short_code";

    /**
     * 选择联系人响应码
     */
    private static final int SELECT_RECEIVE = 100;

    /**
     * 控件集
     */
    private class LocalViewHolder {

        /**
         * 待发送数据临时缓存工具
         */
        public CacheTool sendCacheTool = null;

        /**
         * 底部功能布局
         */
        public LinearLayout functionLayout = null;

        /**
         * 音频按钮
         */
        public ImageButton audioImageButton = null;

        /**
         * 音频布局的标签
         */
        public String AUDIO_FRAGMENT_TAG = "audio_fragment_tag";

        /**
         * 底部扩展布局
         */
        public FrameLayout bottomFrameLayout = null;

        /**
         * 发送内容文本
         */
        public EditText contentEditText = null;

        /**
         * 发送按钮
         */
        public ImageButton sendImageButton = null;

        /**
         * 收件人名称文本
         */
        public TextView receiveTextView = null;

        /**
         * 当前选择的收件人
         */
        public Employee receiveEmployee = null;

        /**
         * 录音机片段
         */
        public ShiftChangeRecordFragment recordFragment = null;

        /**
         * 待发送图片列表片段
         */
        public ShiftChangeImageListFragment imageListFragment = null;

        /**
         * 待发送音频列表片段
         */
        public ShiftChangeAudioListFragment audioListFragment = null;

        /**
         * 内容列表片段
         */
        public ShiftChangeContentFragment contentFragment = null;

        /**
         * 共享片段标签
         */
        public String[] shareFragmentTag = null;
    }

    /**
     * 控件集对象
     */
    private LocalViewHolder viewHolder = new LocalViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_change);

        // 初始化控件引用
        initViewHolder();
        // 加载界面
        initView();

        // 初始化数据
        initData();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder() {

        viewHolder.audioImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_audio_imageButton);

        viewHolder.contentEditText = (EditText) findViewById(R.id.activity_shift_change_editText);

        viewHolder.sendImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_send_imageButton);

        viewHolder.receiveTextView = (TextView) findViewById(R.id
                .activity_shift_change_receive_textView);

        viewHolder.sendCacheTool = CacheManager.getCacheTool("shift_change_send");

        viewHolder.bottomFrameLayout = (FrameLayout) findViewById(R.id
                .activity_shift_change_bottom_frameLayout);

        viewHolder.functionLayout = (LinearLayout) findViewById(R.id
                .activity_shift_change_function_layout);

        viewHolder.recordFragment = new ShiftChangeRecordFragment();

        viewHolder.imageListFragment = new ShiftChangeImageListFragment();

        viewHolder.audioListFragment = new ShiftChangeAudioListFragment();

        viewHolder.contentFragment = (ShiftChangeContentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_shift_change_content_fragment);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.shift_change);

        // 初始化fragment
        initFragment();

        // 初始化音频按钮
        initAudioButton();
        // 初始化编辑框
        initEditText();
        // 初始化发送按钮
        initSendButton();
        // 初始化收件人文本框
        initReceiveText();
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

        // 显示后退
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 与返回键相同
                onBackPressed();
            }
        });

        // 取消原actionBar标题
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化内容片段
     */
    private void initFragment() {

        // 初始化待发送图片列表片段
        initImageListFragment();

        // 初始化待发送音频列表片段
        initAudioListFragment();

        // 初始化录音机片段
        initRecordFragment();

        // 注册要共享片段
        initShareFragment();
    }

    /**
     * 初始化待发送音频列表片段
     */
    private void initAudioListFragment() {
        viewHolder.audioListFragment.setCacheTool(viewHolder.sendCacheTool);

        getSupportFragmentManager().beginTransaction().replace(R.id
                .activity_shift_change_audio_frameLayout, viewHolder.audioListFragment).commit();
    }

    /**
     * 初始化待发送图片列表片段
     */
    private void initImageListFragment() {
        viewHolder.imageListFragment.setCacheTool(viewHolder.sendCacheTool);

        viewHolder.imageListFragment.setPhotoImageButton((ImageButton) findViewById(R.id
                .activity_shift_change_photo_imageButton));

        viewHolder.imageListFragment.setGalleryImageButton((ImageButton) findViewById(R.id
                .activity_shift_change_gallery_imageButton));

        getSupportFragmentManager().beginTransaction().replace(R.id
                .activity_shift_change_image_frameLayout, viewHolder.imageListFragment).commit();
    }

    /**
     * 初始化录音机片段
     */
    private void initRecordFragment() {

        // 缓存工具
        viewHolder.recordFragment.setRecordCacheTool(viewHolder.sendCacheTool);

        // 接收
        viewHolder.recordFragment.setRecordEndListener(new DataChangeObserver<String>() {
            @Override
            public void notifyDataChange(String data) {
                viewHolder.audioListFragment.addAudio(data);
            }
        });
    }

    /**
     * 注册要共享片段
     */
    private void initShareFragment() {
        // 加入布局管理器
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 录音机片段
        transaction.add(R.id.activity_shift_change_bottom_frameLayout, viewHolder.recordFragment,
                viewHolder.AUDIO_FRAGMENT_TAG).hide(viewHolder.recordFragment);

        transaction.commit();

        // 注册要共享片段标签
        viewHolder.shareFragmentTag = new String[]{viewHolder.AUDIO_FRAGMENT_TAG};
    }

    /**
     * 显示指定可替换片段
     *
     * @param fragmentTag 要显示的片段
     */
    private void showFragment(String fragmentTag) {

        // 获取片段管理器
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 要显示的片段
        Fragment showFragment = fragmentManager.findFragmentByTag(fragmentTag);

        // 当前正在显示
        if (showFragment.isVisible()) {
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (String tag : viewHolder.shareFragmentTag) {
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment != null && fragment.isVisible()) {
                // 隐藏上一个显示片段
                transaction.hide(fragment);
                break;
            }
        }

        // 显示目标片段
        transaction.show(showFragment).commit();
    }

    /**
     * 初始化音频按钮
     */
    private void initAudioButton() {

        // 加载动画
        //final Animation animationIn = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        //final Animation animationOut = AnimationUtils.loadAnimation(this, R.anim
        // .slide_bottom_out);

        viewHolder.audioImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(LOG_TAG + "initAudioButton", "bottom frame visibility is " + viewHolder
                        .bottomFrameLayout.getVisibility());
                viewHolder.contentEditText.clearFocus();
                InputMethodController.CloseInputMethod(ShiftChangeActivity.this);
                if (viewHolder.bottomFrameLayout.getVisibility() == View.GONE) {
                    showFragment(viewHolder.AUDIO_FRAGMENT_TAG);
                    viewHolder.bottomFrameLayout.setVisibility(View.VISIBLE);
                    //viewHolder.functionLayout.startAnimation(animationIn);
                } else {
                    viewHolder.bottomFrameLayout.setVisibility(View.GONE);
                    //viewHolder.functionLayout.startAnimation(animationOut);
                }

                Log.i(LOG_TAG + "initAudioButton", "content edit focus is " + viewHolder
                        .contentEditText.isFocused());
            }
        });
    }

    /**
     * 初始化编辑框
     */
    private void initEditText() {
        viewHolder.contentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.i(LOG_TAG + "initEditText", "bottom frame visibility is " + viewHolder
                            .bottomFrameLayout.getVisibility());
                    viewHolder.bottomFrameLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 初始化发送按钮
     */
    private void initSendButton() {
        viewHolder.sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.sendImageButton.setEnabled(false);
                String text = viewHolder.contentEditText.getText().toString();
                File[] images = viewHolder.imageListFragment.getData();
                File[] audios = viewHolder.audioListFragment.getData();

                if (text.isEmpty() && images == null && audios == null) {
                    // 无内容
                    viewHolder.sendImageButton.setEnabled(true);
                    return;
                }

                if (viewHolder.receiveEmployee == null || viewHolder.receiveEmployee.getId() ==
                        null) {
                    // 无收件人
                    viewHolder.sendImageButton.setEnabled(true);
                    // 消息发送失败
                    Toast.makeText(ShiftChangeActivity.this, R.string.alert_select_receive, Toast
                            .LENGTH_SHORT).show();
                    return;
                }

                viewHolder.contentFragment.sendNewMessage(viewHolder.receiveEmployee.getId(),
                        viewHolder.receiveEmployee.getCompany(), text, images, audios, new
                                DataChangeObserver<Boolean>() {

                            @Override
                            public void notifyDataChange(final Boolean data) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        viewHolder.sendImageButton.setEnabled(true);
                                        if (data) {
                                            // 消息发送成功
                                            // 清除缓存
                                            viewHolder.contentEditText.setText(null);
                                            viewHolder.imageListFragment.clearList();
                                            viewHolder.audioListFragment.clearList();
                                            viewHolder.sendCacheTool.clear();
                                        } else {
                                            // 消息发送失败
                                            Toast.makeText(ShiftChangeActivity.this, R.string
                                                    .send_failed_check_network, Toast
                                                    .LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
            }
        });
    }

    /**
     * 初始化收件人文本框
     */
    private void initReceiveText() {
        viewHolder.receiveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShiftChangeActivity.this, EmployeeListActivity.class);

                startActivityForResult(intent, SELECT_RECEIVE);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        String user_id = viewHolder.sendCacheTool.getForText(StaticValue.IntentTag.USER_ID_TAG);
        if (user_id != null && !user_id.equals(GlobalApplication.getGlobal().getLoginStatus()
                .getUserID())) {
            // 更换了用户
            return;
        }

        String jsonString = viewHolder.sendCacheTool.getForText(SAVE_CONTENT);

        if (jsonString != null && !jsonString.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                String message = jsonObject.optString(SAVE_MESSAGE);

                viewHolder.contentEditText.setText(message);

                if (!jsonObject.isNull(EMPLOYEE_ID)) {
                    viewHolder.receiveEmployee = new Employee();

                    viewHolder.receiveEmployee.setId(jsonObject.getString(EMPLOYEE_ID));
                    viewHolder.receiveEmployee.setName(jsonObject.getString(EMPLOYEE_NAME));
                    viewHolder.receiveEmployee.setCompany(jsonObject.getString(EMPLOYEE_COMPANY));
                    viewHolder.receiveEmployee.setShortCode(jsonObject.getString
                            (EMPLOYEE_SHORT_CODE));

                    viewHolder.receiveTextView.setText(viewHolder.receiveEmployee.getName());
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG + "initData", "JSONException is " + e.getMessage());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_RECEIVE:
                // 选择收件人
                switch (resultCode) {
                    case RESULT_OK:
                        // 取得员工信息
                        Employee employee = data.getParcelableExtra(StaticValue.IntentTag
                                .EMPLOYEE_TAG);

                        viewHolder.receiveEmployee = employee;

                        if (employee != null) {
                            viewHolder.receiveTextView.setText(employee.getName());
                        }
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioFileLengthFunction.release();
        viewHolder.sendCacheTool.put(StaticValue.IntentTag.USER_ID_TAG, GlobalApplication
                .getGlobal().getLoginStatus().getUserID());

        try {
            // 保存数据
            JSONObject jsonObject = new JSONObject();

            if (viewHolder.contentEditText.getText().length() > 0) {
                jsonObject.put(SAVE_MESSAGE, viewHolder.contentEditText.getText().toString());
            }

            if (viewHolder.receiveEmployee != null) {
                jsonObject.put(EMPLOYEE_ID, viewHolder.receiveEmployee.getId());
                jsonObject.put(EMPLOYEE_NAME, viewHolder.receiveEmployee.getName());
                jsonObject.put(EMPLOYEE_COMPANY, viewHolder.receiveEmployee.getCompany());
                jsonObject.put(EMPLOYEE_SHORT_CODE, viewHolder.receiveEmployee.getShortCode());
            }

            viewHolder.sendCacheTool.put(SAVE_CONTENT, jsonObject.toString());

        } catch (JSONException e) {
            Log.e(LOG_TAG + "onDestroy", "JSONException is " + e.getMessage());
        }
    }
}
