package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/12/2.
 */

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.port.tally.management.R;
import com.port.tally.management.adapter.ShiftChangeAudioRecyclerViewAdapter;
import com.port.tally.management.adapter.ShiftChangeImageRecyclerViewAdapter;
import com.port.tally.management.fragment.RecordFragment;
import com.port.tally.management.holder.ShiftChangeAudioViewHolder;
import com.port.tally.management.holder.ShiftChangeImageViewHolder;
import com.port.tally.management.util.CacheKeyUtil;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.common.function.ImageCompression;
import org.mobile.library.common.function.InputMethodController;
import org.mobile.library.model.operate.DataChangeObserver;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
     * 照相接收返回码
     */
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    /**
     * 相册接收返回码
     */
    private static final int CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE = 200;

    /**
     * 原图缓存key前缀
     */
    private static final String SOURCE_IMAGE_CACHE_PRE = "source_";

    /**
     * 压缩后图片缓存key前缀
     */
    private static final String COMPRESSION_IMAGE_CACHE_PRE = "compression_";

    /**
     * 缩略图缓存key前缀
     */
    private static final String THUMBNAIL_CACHE_PRE = "thumbnail_";

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
         * 发送图片列表
         */
        public RecyclerView imageRecyclerView = null;

        /**
         * 发送图片列表的适配器
         */
        public ShiftChangeImageRecyclerViewAdapter imageRecyclerViewAdapter = null;

        /**
         * 发送音频列表
         */
        public RecyclerView audioRecyclerView = null;

        /**
         * 发送音频列表的适配器
         */
        public ShiftChangeAudioRecyclerViewAdapter audioRecyclerViewAdapter = null;

        /**
         * 音频按钮
         */
        public ImageButton audioImageButton = null;

        /**
         * 音频布局的标签
         */
        public String AUDIO_FRAGMENT_TAG = "audio_fragment_tag";

        /**
         * 照相按钮
         */
        public ImageButton photoImageButton = null;

        /**
         * 相册按钮
         */
        public ImageButton galleryImageButton = null;

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
         * 存放待发送图片缓存key列表
         */
        public List<String> sendImageCacheKeyList = null;

        /**
         * 缩略图高度
         */
        public int thumbnailHeight = 0;

        /**
         * 缩略图宽度
         */
        public int thumbnailWidth = 0;

        /**
         * 音频播放器
         */
        public MediaPlayer mediaPlayer = null;

        /**
         * 记录当前正在播放的音频文件图标
         */
        public ImageView audioImageView = null;
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

        viewHolder.imageRecyclerView = (RecyclerView) findViewById(R.id
                .activity_shift_change_image_recyclerView);

        viewHolder.audioRecyclerView = (RecyclerView) findViewById(R.id
                .activity_shift_change_audio_recyclerView);

        viewHolder.audioImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_audio_imageButton);

        viewHolder.photoImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_photo_imageButton);

        viewHolder.galleryImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_gallery_imageButton);

        viewHolder.contentEditText = (EditText) findViewById(R.id.activity_shift_change_editText);

        viewHolder.sendImageButton = (ImageButton) findViewById(R.id
                .activity_shift_change_send_imageButton);

        viewHolder.contentCacheTool = CacheManager.getCacheTool("shift_change_content");

        viewHolder.sendCacheTool = CacheManager.getCacheTool("shift_change_send");

        viewHolder.sendImageCacheKeyList = new ArrayList<>();

        viewHolder.thumbnailHeight = getResources().getDimensionPixelSize(R.dimen
                .image_list_item_height);
        viewHolder.thumbnailWidth = getResources().getDimensionPixelSize(R.dimen
                .image_list_item_width);

        viewHolder.bottomFrameLayout = (FrameLayout) findViewById(R.id
                .activity_shift_change_bottom_frameLayout);
        viewHolder.functionLayout = (LinearLayout) findViewById(R.id
                .activity_shift_change_function_layout);

        viewHolder.mediaPlayer = new MediaPlayer();
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

        // 初始化待发送图片列表
        initImageList();
        // 初始化照相按钮
        initPhotoButton();
        // 初始化相册按钮
        initGalleryButton();
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
        // 初始化录音机片段
        initRecordFragment();
        // 初始化待发送音频列表
        initAudioList();
    }

    /**
     * 初始化录音机片段
     */
    private void initRecordFragment() {
        // 录音机片段
        RecordFragment fragment = new RecordFragment();

        // 缓存工具
        fragment.setRecordCacheTool(viewHolder.sendCacheTool);

        // 接收
        fragment.setRecordEndListener(new DataChangeObserver<String>() {
            @Override
            public void notifyDataChange(String data) {
                viewHolder.audioRecyclerView.setVisibility(View.VISIBLE);
                viewHolder.audioRecyclerViewAdapter.addData(0, data);
            }
        });

        // 加入布局管理器
        getSupportFragmentManager().beginTransaction().add(R.id
                .activity_shift_change_bottom_frameLayout, fragment, viewHolder
                .AUDIO_FRAGMENT_TAG).hide(fragment).commit();
    }

    /**
     * 初始化待发送音频列表
     */
    private void initAudioList() {
        RecyclerView recyclerView = viewHolder.audioRecyclerView;

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 初始化适配器
        viewHolder.audioRecyclerViewAdapter = new ShiftChangeAudioRecyclerViewAdapter(viewHolder
                .sendCacheTool);

        // 设置监听器
        viewHolder.audioRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListenerForRecyclerViewItem<List<String>, ShiftChangeAudioViewHolder>() {
            @Override
            public void onClick(List<String> dataSource, final ShiftChangeAudioViewHolder holder) {

                // 播放音频
                playAudio(dataSource.get(holder.getAdapterPosition()), holder.imageView);
            }
        });

        recyclerView.setAdapter(viewHolder.audioRecyclerViewAdapter);
    }

    /**
     * 播放音频
     *
     * @param key       音频缓存key
     * @param imageView 同步操作的音频图标
     */
    private void playAudio(String key, ImageView imageView) {

        if (viewHolder.mediaPlayer.isPlaying()) {
            viewHolder.mediaPlayer.stop();
            if (viewHolder.audioImageView != null) {
                viewHolder.audioImageView.getDrawable().setLevel(0);
            }
        }

        // 记录正在播放的文件
        viewHolder.audioImageView = imageView;

        viewHolder.mediaPlayer.reset();

        try {
            viewHolder.mediaPlayer.setDataSource(viewHolder.sendCacheTool.getForFile(key).getPath
                    ());
            viewHolder.mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG + "initAudioList", "IOException is " + e.getMessage());
        }

        viewHolder.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                viewHolder.audioImageView.getDrawable().setLevel(0);
                viewHolder.audioImageView = null;
            }
        });

        imageView.getDrawable().setLevel(1);
        viewHolder.mediaPlayer.start();
    }

    /**
     * 初始化待发送图片列表
     */
    private void initImageList() {

        RecyclerView recyclerView = viewHolder.imageRecyclerView;

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 初始化适配器
        viewHolder.imageRecyclerViewAdapter = new ShiftChangeImageRecyclerViewAdapter(viewHolder
                .sendCacheTool);

        // 设置监听器
        viewHolder.imageRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListenerForRecyclerViewItem<List<String>, ShiftChangeImageViewHolder>() {
            @Override
            public void onClick(List<String> dataSource, ShiftChangeImageViewHolder holder) {
                // 显示大图
                showImage(viewHolder.sendCacheTool.getForFile(SOURCE_IMAGE_CACHE_PRE + viewHolder
                        .sendImageCacheKeyList.get(holder.getAdapterPosition())), holder
                        .imageView, dataSource.get(holder.getAdapterPosition()));
            }
        });

        recyclerView.setAdapter(viewHolder.imageRecyclerViewAdapter);
    }

    /**
     * 展示大图
     *
     * @param file 大图路径
     * @param view 缩略图控件
     */
    private void showImage(File file, View view, String key) {

        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view,
                viewHolder.sendCacheTool.getForBitmap(key), view.getWidth() / 2, view.getHeight()
                        / 2);

        Intent intent = new Intent(ShiftChangeActivity.this, ImageShowActivity.class);
        intent.putExtra(ImageShowActivity.IMAGE_FILE, file);
        ActivityCompat.startActivity(ShiftChangeActivity.this, intent, options.toBundle());
    }

    /**
     * 初始化照相按钮
     */
    private void initPhotoButton() {
        viewHolder.photoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 利用系统自带的相机应用:拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // 创建图片存放路径
                Uri fileUri = getOutputMediaFileUri();

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    /**
     * 初始化相册按钮
     */
    private void initGalleryButton() {
        viewHolder.galleryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore
                        .Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE);
            }
        });
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

    /**
     * 显示指定标签的片段布局
     *
     * @param tag 指定的标签
     */
    private void showFragment(String tag) {
        // 获取片段管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 尝试获取该片段
        Fragment tagFragment = fragmentManager.findFragmentByTag(tag);
        // 尝试获取当前显示的片段对象
        Fragment currentFragment = getVisibleFragment();

        if (currentFragment != null) {
            // 有显示的片段则先隐藏
            Log.i(LOG_TAG + "showFragment", currentFragment.getTag() + " fragment is gone");
            fragmentManager.beginTransaction().hide(currentFragment).commit();
        }

        // 显示
        Log.i(LOG_TAG + "showFragment", tagFragment.getTag() + " fragment is show");
        fragmentManager.beginTransaction().show(tagFragment).commit();
    }

    /**
     * 获取当前显示的片段对象
     *
     * @return 当前显示的片段实例，没有则返回null
     */
    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.size() == 0) {
            Log.i(LOG_TAG + "getVisibleFragment", "no fragment");
            return null;
        } else {
            Log.i(LOG_TAG + "getVisibleFragment", "fragment count is " + fragments.size());
        }
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                Log.i(LOG_TAG + "getVisibleFragment", "fragment tag is " + fragment.getTag());
                return fragment;
            }
        }
        return null;
    }

    /**
     * 创建图片存放路径
     *
     * @return 文件uri
     */
    private Uri getOutputMediaFileUri() {

        // 创建缓存key
        String key = CacheKeyUtil.getRandomKey();

        // 存入缓存key列表
        viewHolder.sendImageCacheKeyList.add(0, key);

        // 获取一个缓存位置
        FileOutputStream fileOutputStream = viewHolder.sendCacheTool.putAndBack
                (SOURCE_IMAGE_CACHE_PRE + key);

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG + "getOutputMediaFileUri", "IOException is " + e.getMessage());
        }

        File file = viewHolder.sendCacheTool.getForFile(SOURCE_IMAGE_CACHE_PRE + key);

        return Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                // 拍照结果
                Log.i(LOG_TAG + "onActivityResult", "image result is " + resultCode);
                switch (resultCode) {
                    case RESULT_OK:
                        // 拍照成功
                        // 图片文件缓存key
                        String key = viewHolder.sendImageCacheKeyList.get(0);

                        // 提取图片文件
                        File file = viewHolder.sendCacheTool.getForFile(SOURCE_IMAGE_CACHE_PRE +
                                key);
                        // 处理图片
                        createThumbnail(file, key);
                        // 使图片列表可见
                        viewHolder.imageRecyclerView.setVisibility(View.VISIBLE);
                        // 通知列表刷新
                        notifyImageAdapter(THUMBNAIL_CACHE_PRE + key);
                        break;
                    default:
                        // 取消或失败
                        // 移除缓存位
                        viewHolder.sendCacheTool.remove(SOURCE_IMAGE_CACHE_PRE + viewHolder
                                .sendImageCacheKeyList.get(0));
                        // 移除预置缓存key
                        viewHolder.sendImageCacheKeyList.remove(0);
                        break;
                }
                break;
            case CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE:
                // 相册结果
                switch (resultCode) {
                    case RESULT_OK:
                        // 选取成功
                        Uri selectedImage = data.getData();
                        Log.i(LOG_TAG + "onActivityResult", "gallery result is " + selectedImage);
                        String[] filePathColumns = {MediaStore.Images.Media.DATA};
                        Cursor c = this.getContentResolver().query(selectedImage,
                                filePathColumns, null, null, null);
                        int columnIndex = c.getColumnIndex(filePathColumns[0]);
                        while (c.moveToNext()) {
                            String picturePath = c.getString(columnIndex);
                            Log.i(LOG_TAG + "onActivityResult", "picture path is " + picturePath);

                            // 生成一个缓存key
                            String key = CacheKeyUtil.getRandomKey();

                            // 存入缓存key列表
                            viewHolder.sendImageCacheKeyList.add(0, key);

                            // 处理图片
                            createThumbnail(new File(picturePath), key);
                            // 使图片列表可见
                            viewHolder.imageRecyclerView.setVisibility(View.VISIBLE);
                            // 通知列表刷新
                            notifyImageAdapter(THUMBNAIL_CACHE_PRE + key);

                            // 存入缓存
                            viewHolder.sendCacheTool.put(SOURCE_IMAGE_CACHE_PRE + key, new File
                                    (picturePath));
                        }
                        c.close();

                        break;
                }
        }
    }

    /**
     * 创建缩略图
     *
     * @param file 原图文件
     * @param key  要存放的缓存key（不含前缀）
     */
    private void createThumbnail(File file, String key) {
        Log.i(LOG_TAG + "createThumbnail", "image path:" + file.getPath() + " target cache key" +
                key);

        // 创建缩略图
        Log.i(LOG_TAG + "createThumbnail", "thumbnail begin");

        viewHolder.sendCacheTool.put(THUMBNAIL_CACHE_PRE + key, resolutionBitmap(file, viewHolder
                .thumbnailWidth, viewHolder.thumbnailHeight));

        Log.i(LOG_TAG + "createThumbnail", "thumbnail end");
    }

    /**
     * 处理图片
     *
     * @param file 要处理的原图文件
     * @param key  要存放的缓存key（不含前缀）
     */
    private void processPicture(final File file, final String key) {

        Log.i(LOG_TAG + "processPicture", "image path:" + file.getPath() + " target cache key" +
                key);

        viewHolder.taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // 像素压缩，尺寸缩小为720P
                Bitmap bitmap = resolutionBitmap(file, 720, 1280);

                // 质量压缩50K
                qualityBitmap(key, bitmap, 50);
            }
        });
    }

    /**
     * 通知待发送图片列表刷新
     *
     * @param key 缩略图key
     */
    private synchronized void notifyImageAdapter(String key) {
        viewHolder.imageRecyclerViewAdapter.addData(0, key);
    }

    /**
     * 质量压缩
     *
     * @param key    要存放的缓存key（不含前缀）
     * @param bitmap 要压缩的图片
     * @param size   目标容量，单位KB
     */
    private void qualityBitmap(String key, Bitmap bitmap, int size) {
        Log.i(LOG_TAG + "processPicture", "quality compression begin");
        // 进行质量压缩
        ByteArrayOutputStream byteArrayOutputStream = ImageCompression.compressImage(bitmap, size);

        // 获取一个缓存位置
        FileOutputStream fileOutputStream = viewHolder.sendCacheTool.putAndBack
                (COMPRESSION_IMAGE_CACHE_PRE + key);

        try {
            byteArrayOutputStream.writeTo(fileOutputStream);

            byteArrayOutputStream.flush();

            byteArrayOutputStream.close();

            fileOutputStream.flush();

            fileOutputStream.close();

        } catch (IOException e) {
            Log.e(LOG_TAG + "processPicture", "IOException is " + e.getMessage());
        }

        Log.i(LOG_TAG + "processPicture", "quality compression end");
    }

    /**
     * 像素压缩
     *
     * @param file   图片路径
     * @param width  目标宽
     * @param height 目标高
     *
     * @return 压缩图
     */
    private Bitmap resolutionBitmap(File file, int width, int height) {
        Log.i(LOG_TAG + "processPicture", "resolution compression begin");
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(file.getPath(), options);

        options.inSampleSize = ImageCompression.calculateHighSampleSize(options, width, height);

        options.inJustDecodeBounds = false;
        options.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
        Log.i(LOG_TAG + "processPicture", "resolution compression end");
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewHolder.mediaPlayer.release();
        viewHolder.audioRecyclerViewAdapter.release();
    }
}
