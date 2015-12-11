package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/12/2.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.port.tally.management.R;
import com.port.tally.management.fragment.ShiftChangeAudioListFragment;
import com.port.tally.management.fragment.ShiftChangeImageListFragment;
import com.port.tally.management.fragment.ShiftChangeRecordFragment;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.common.function.InputMethodController;
import org.mobile.library.model.operate.DataChangeObserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     * 线程池线程数
     */
    private static final int POOL_COUNT = Runtime.getRuntime().availableProcessors() * 2 + 2;

    /**
     * 控件集
     */
    private class LocalViewHolder {

        /**
         * 线程池
         */
        public ExecutorService taskExecutor = null;

        /**
         * 待发送数据临时缓存工具
         */
        public CacheTool sendCacheTool = null;

        /**
         * 交接班信息正文列表缓存工具
         */
        public CacheTool contentCacheTool = null;

        /**
         * 底部功能布局
         */
        public LinearLayout functionLayout = null;

        /**
         * 正文列表
         */
        public RecyclerView contentRecyclerView = null;

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
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder() {

        viewHolder.taskExecutor = Executors.newFixedThreadPool(POOL_COUNT);

        viewHolder.contentRecyclerView = (RecyclerView) findViewById(R.id
                .activity_shift_change_content_recyclerView);

        viewHolder.audioImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_audio_imageButton);

        viewHolder.contentEditText = (EditText) findViewById(R.id.activity_shift_change_editText);

        viewHolder.sendImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_send_imageButton);

        viewHolder.contentCacheTool = CacheManager.getCacheTool("shift_change_content");

        viewHolder.sendCacheTool = CacheManager.getCacheTool("shift_change_send");

        viewHolder.bottomFrameLayout = (FrameLayout) findViewById(R.id
                .activity_shift_change_bottom_frameLayout);

        viewHolder.functionLayout = (LinearLayout) findViewById(R.id
                .activity_shift_change_function_layout);

        viewHolder.recordFragment = new ShiftChangeRecordFragment();

        viewHolder.imageListFragment = new ShiftChangeImageListFragment();

        viewHolder.audioListFragment = new ShiftChangeAudioListFragment();
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
                    viewHolder.bottomFrameLayout.setVisibility(View.GONE);
                }
            }
        });
    }
}
