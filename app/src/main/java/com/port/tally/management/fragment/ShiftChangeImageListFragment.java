package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/12/11.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.port.tally.management.R;
import com.port.tally.management.activity.ImageShowActivity;
import com.port.tally.management.adapter.ShiftChangeImageRecyclerViewAdapter;
import com.port.tally.management.holder.ShiftChangeImageViewHolder;
import com.port.tally.management.util.CacheKeyUtil;
import com.port.tally.management.util.ImageUtil;

import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.model.operate.DataGetHandle;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 交接班中的待发送图片列表片段
 *
 * @author 超悟空
 * @version 1.0 2015/12/11
 * @since 1.0
 */
public class ShiftChangeImageListFragment extends Fragment implements DataGetHandle<File[]> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeImageListFragment.";

    /**
     * 照相接收返回码
     */
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    /**
     * 相册接收返回码
     */
    private static final int CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE = 200;

    /**
     * 暂存图片缓存key列表的取值标签
     */
    private static final String SAVE_IMAGE_CACHE_KEY_LIST = "save_image_cache_key_list";

    /**
     * 控件集
     */
    private class LocalViewHolder {
        /**
         * 待发送数据临时缓存工具
         */
        public CacheTool sendCacheTool = null;

        /**
         * 发送图片列表
         */
        public RecyclerView imageRecyclerView = null;

        /**
         * 发送图片列表的适配器
         */
        public ShiftChangeImageRecyclerViewAdapter imageRecyclerViewAdapter = null;

        /**
         * 照相按钮
         */
        public ImageButton photoImageButton = null;

        /**
         * 相册按钮
         */
        public ImageButton galleryImageButton = null;

        /**
         * 缩略图高度
         */
        public int thumbnailHeight = 0;

        /**
         * 缩略图宽度
         */
        public int thumbnailWidth = 0;

        /**
         * 存放待发送图片缓存key列表
         */
        public ArrayList<String> sendImageCacheKeyList = null;
    }

    /**
     * 控件集对象
     */
    private LocalViewHolder viewHolder = new LocalViewHolder();

    /**
     * 设置存放待发送图片的缓存工具
     *
     * @param cacheTool 缓存工具
     */
    public void setCacheTool(CacheTool cacheTool) {
        viewHolder.sendCacheTool = cacheTool;
    }

    /**
     * 设置照相按钮
     *
     * @param photoImageButton 照相按钮
     */
    public void setPhotoImageButton(ImageButton photoImageButton) {
        viewHolder.photoImageButton = photoImageButton;
    }

    /**
     * 设置相册按钮
     *
     * @param galleryImageButton 相册按钮
     */
    public void setGalleryImageButton(ImageButton galleryImageButton) {
        viewHolder.galleryImageButton = galleryImageButton;
    }

    /**
     * 清空列表
     */
    public void clearList() {
        viewHolder.sendImageCacheKeyList.clear();
        viewHolder.imageRecyclerViewAdapter.clear();
        viewHolder.imageRecyclerView.setVisibility(View.GONE);
    }

    /**
     * 获取待发送的图片文件
     *
     * @return 文件数组
     */
    @Override
    public File[] getData() {

        if (viewHolder.sendImageCacheKeyList.isEmpty()) {
            return null;
        }

        File[] files = new File[viewHolder.sendImageCacheKeyList.size()];

        for (int i = 0; i < viewHolder.sendImageCacheKeyList.size(); i++) {
            files[i] = viewHolder.sendCacheTool.getForFile(ImageUtil.SOURCE_IMAGE_CACHE_PRE +
                    viewHolder.sendImageCacheKeyList.get(i));
        }

        return files;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // 根布局
        View rootView = inflater.inflate(R.layout.fragment_shift_change_image_list, container,
                false);

        // 初始化布局
        initView(rootView);

        // 初始化数据
        initData(savedInstanceState);

        return rootView;
    }

    /**
     * 初始化布局
     *
     * @param rootView 根布局
     */
    private void initView(View rootView) {
        // 初始化控件引用
        initViewHolder(rootView);

        // 初始化待发送图片列表
        initImageList();

        // 初始化照相按钮
        initPhotoButton();

        // 初始化相册按钮
        initGalleryButton();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder(View rootView) {

        viewHolder.imageRecyclerView = (RecyclerView) rootView.findViewById(R.id
                .fragment_shift_change_image_recyclerView);

        viewHolder.sendImageCacheKeyList = new ArrayList<>();

        viewHolder.thumbnailHeight = getResources().getDimensionPixelSize(R.dimen
                .image_list_item_height);
        viewHolder.thumbnailWidth = getResources().getDimensionPixelSize(R.dimen
                .image_list_item_width);
    }

    /**
     * 初始化列表数据
     *
     * @param savedInstanceState 暂存数据
     */
    private void initData(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            ArrayList<String> list = savedInstanceState.getStringArrayList
                    (SAVE_IMAGE_CACHE_KEY_LIST);


            if (list != null && !list.isEmpty()) {
                viewHolder.sendImageCacheKeyList.addAll(list);
                // 使图片列表可见
                viewHolder.imageRecyclerView.setVisibility(View.VISIBLE);
                viewHolder.imageRecyclerViewAdapter.addData(0, viewHolder.sendImageCacheKeyList);
            }
        }
    }

    /**
     * 初始化待发送图片列表
     */
    private void initImageList() {

        RecyclerView recyclerView = viewHolder.imageRecyclerView;

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
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
                showImage(viewHolder.sendCacheTool.getForFile(ImageUtil.SOURCE_IMAGE_CACHE_PRE +
                        viewHolder.sendImageCacheKeyList.get(holder.getAdapterPosition())),
                        holder.imageView, dataSource.get(holder.getAdapterPosition()));
            }
        });

        recyclerView.setAdapter(viewHolder.imageRecyclerViewAdapter);

        // 滑动拖拽监听器
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 上滑删除
                ShiftChangeImageListFragment.this.viewHolder.sendImageCacheKeyList.remove
                        (viewHolder.getAdapterPosition());
                ShiftChangeImageListFragment.this.viewHolder.imageRecyclerViewAdapter.remove
                        (viewHolder.getAdapterPosition());
                if (ShiftChangeImageListFragment.this.viewHolder.imageRecyclerViewAdapter
                        .getItemCount() == 0) {
                    // 列表清空
                    ShiftChangeImageListFragment.this.viewHolder.imageRecyclerView.setVisibility
                            (View.GONE);
                }
            }
        });

        // 绑定事件
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

        Intent intent = new Intent(getActivity(), ImageShowActivity.class);
        intent.putExtra(ImageShowActivity.IMAGE_FILE, file);
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    /**
     * 初始化照相按钮
     */
    private void initPhotoButton() {
        viewHolder.photoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 屏蔽多次连击
                viewHolder.photoImageButton.setEnabled(false);

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

                // 屏蔽多次连击
                viewHolder.galleryImageButton.setEnabled(false);

                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore
                        .Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE);
            }
        });
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
        FileOutputStream fileOutputStream = viewHolder.sendCacheTool.putAndBack(ImageUtil
                .SOURCE_IMAGE_CACHE_PRE + key);

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG + "getOutputMediaFileUri", "IOException is " + e.getMessage());
        }

        File file = viewHolder.sendCacheTool.getForFile(ImageUtil.SOURCE_IMAGE_CACHE_PRE + key);

        return Uri.fromFile(file);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                // 拍照结果
                // 重新开启按钮
                viewHolder.photoImageButton.setEnabled(true);
                Log.i(LOG_TAG + "onActivityResult", "image result is " + resultCode);
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // 拍照成功
                        photoSuccess();
                        break;
                    default:
                        // 取消或失败
                        photoFailed();
                        break;
                }
                break;
            case CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE:
                // 相册结果
                // 重新开启按钮
                viewHolder.galleryImageButton.setEnabled(true);

                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // 选取成功
                        Uri selectedImage = data.getData();
                        Log.i(LOG_TAG + "onActivityResult", "gallery result is " + selectedImage);
                        gallerySuccess(selectedImage);
                        break;
                }
        }
    }

    /**
     * 拍照失败
     */
    private void photoFailed() {
        // 移除缓存位
        viewHolder.sendCacheTool.remove(ImageUtil.SOURCE_IMAGE_CACHE_PRE + viewHolder
                .sendImageCacheKeyList.get(0));
        // 移除预置缓存key
        viewHolder.sendImageCacheKeyList.remove(0);
    }

    /**
     * 相册选择图片成功
     *
     * @param selectedImage 图片信息Uri
     */
    private void gallerySuccess(Uri selectedImage) {
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null,
                null, null);
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        while (c.moveToNext()) {
            final String picturePath = c.getString(columnIndex);
            Log.i(LOG_TAG + "gallerySuccess", "picture path is " + picturePath);

            // 生成一个缓存key
            final String cacheKey = CacheKeyUtil.getRandomKey();

            // 存入缓存key列表
            viewHolder.sendImageCacheKeyList.add(0, cacheKey);

            // 使图片列表可见
            viewHolder.imageRecyclerView.setVisibility(View.VISIBLE);

            // 处理图片
            ImageUtil.createThumbnail(new File(picturePath), viewHolder.sendCacheTool, cacheKey,
                    viewHolder.thumbnailWidth, viewHolder.thumbnailHeight, new ImageUtil
                            .ProcessFinishListener() {
                        @Override
                        public void finish(CacheTool cacheTool, String key) {
                            // 通知列表刷新
                            notifyImageAdapter(key);

                            // 存入缓存
                            viewHolder.sendCacheTool.put(ImageUtil.SOURCE_IMAGE_CACHE_PRE +
                                    cacheKey, new File(picturePath));
                        }
                    });

        }
        c.close();
    }

    /**
     * 拍照成功
     */
    private void photoSuccess() {
        // 图片文件缓存key
        String key = viewHolder.sendImageCacheKeyList.get(0);

        // 提取图片文件
        File file = viewHolder.sendCacheTool.getForFile(ImageUtil.SOURCE_IMAGE_CACHE_PRE + key);
        // 使图片列表可见
        viewHolder.imageRecyclerView.setVisibility(View.VISIBLE);

        // 处理图片
        ImageUtil.createThumbnail(file, viewHolder.sendCacheTool, key, viewHolder.thumbnailWidth,
                viewHolder.thumbnailHeight, new ImageUtil.ProcessFinishListener() {
                    @Override
                    public void finish(CacheTool cacheTool, String key) {
                        // 通知列表刷新
                        notifyImageAdapter(key);
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVE_IMAGE_CACHE_KEY_LIST, viewHolder.sendImageCacheKeyList);
    }

    /**
     * 通知待发送图片列表刷新
     *
     * @param key 缩略图key
     */
    private void notifyImageAdapter(final String key) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 滚动到头部准备插图
                viewHolder.imageRecyclerView.scrollToPosition(0);
                viewHolder.imageRecyclerViewAdapter.addData(0, key);
            }
        });
    }
}
