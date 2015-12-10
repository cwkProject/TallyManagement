package com.port.tally.management.util;
/**
 * Created by 超悟空 on 2015/12/10.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.common.function.ImageCompression;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片处理工具
 *
 * @author 超悟空
 * @version 1.0 2015/12/10
 * @since 1.0
 */
public class ImageUtil {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ImageUtil.";

    /**
     * 原图缓存key前缀
     */
    public static final String SOURCE_IMAGE_CACHE_PRE = "source_";

    /**
     * 压缩后图片缓存key前缀
     */
    public static final String COMPRESSION_IMAGE_CACHE_PRE = "compression_";

    /**
     * 缩略图缓存key前缀
     */
    public static final String THUMBNAIL_CACHE_PRE = "thumbnail_";

    /**
     * 线程池线程数
     */
    private static final int POOL_COUNT = Runtime.getRuntime().availableProcessors() * 2 + 2;

    /**
     * 线程池
     */
    private static ExecutorService taskExecutor = Executors.newFixedThreadPool(POOL_COUNT);

    /**
     * 图片处理完成监听器
     */
    public interface ProcessFinishListener {

        /**
         * 图片处理完成
         *
         * @param cacheTool 存放处理后图片的缓存工具
         * @param key       处理后图片的缓存key(已包含前缀)
         */
        void finish(CacheTool cacheTool, String key);
    }

    /**
     * 创建缩略图，异步方法
     *
     * @param file      原图文件
     * @param cacheTool 存放缩略图的缓存工具
     * @param key       要存放的缓存key（不含前缀）
     * @param width     缩略图宽
     * @param height    缩略图高
     * @param listener  处理完成监听器
     */
    public static void createThumbnail(@NotNull final File file, @NotNull final CacheTool
            cacheTool, @NotNull final String key, final int width, final int height, @Nullable
    final ProcessFinishListener listener) {
        Log.i(LOG_TAG + "createThumbnail", "image path:" + file.getPath() + " target cache key" +
                key);

        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // 创建缩略图
                Log.i(LOG_TAG + "createThumbnail", "thumbnail begin");

                cacheTool.put(THUMBNAIL_CACHE_PRE + key, resolutionBitmap(file, width, height));

                Log.i(LOG_TAG + "createThumbnail", "thumbnail end");

                if (listener != null) {
                    listener.finish(cacheTool, THUMBNAIL_CACHE_PRE + key);
                }
            }
        });
    }

    /**
     * 创建缩略图，同步方法
     *
     * @param file      原图文件
     * @param cacheTool 存放缩略图的缓存工具
     * @param key       要存放的缓存key（不含前缀）
     * @param width     缩略图宽
     * @param height    缩略图高
     *
     * @return 处理后图片的缓存key(已包含前缀)
     */
    public static String createThumbnail(@NotNull File file, @NotNull CacheTool cacheTool,
                                         @NotNull String key, int width, int height) {
        Log.i(LOG_TAG + "createThumbnail", "image path:" + file.getPath() + " target cache key" +
                key);

        // 创建缩略图
        Log.i(LOG_TAG + "createThumbnail", "thumbnail begin");

        cacheTool.put(THUMBNAIL_CACHE_PRE + key, resolutionBitmap(file, width, height));

        Log.i(LOG_TAG + "createThumbnail", "thumbnail end");

        return THUMBNAIL_CACHE_PRE + key;
    }

    /**
     * 处理图片
     *
     * @param file      要处理的原图文件
     * @param cacheTool 存放缩略图的缓存工具
     * @param key       要存放的缓存key（不含前缀）
     * @param listener  处理完成监听器
     */
    public static void processPicture(@NotNull final File file, @NotNull final CacheTool
            cacheTool, final String key, @Nullable final ProcessFinishListener listener) {

        Log.i(LOG_TAG + "processPicture", "image path:" + file.getPath() + " target cache key" +
                key);

        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {

                // 像素压缩，尺寸缩小为720P
                Bitmap bitmap = resolutionBitmap(file, 720, 1280);

                // 质量压缩50K
                qualityBitmap(cacheTool, key, bitmap, 50);

                if (listener != null) {
                    listener.finish(cacheTool, COMPRESSION_IMAGE_CACHE_PRE + key);
                }
            }
        });
    }

    /**
     * 质量压缩，同步方法
     *
     * @param cacheTool 存放压缩图的缓存工具
     * @param key       要存放的缓存key（不含前缀）
     * @param bitmap    要压缩的图片
     * @param size      目标容量，单位KB
     */
    public static void qualityBitmap(@NotNull CacheTool cacheTool, String key, Bitmap bitmap, int
            size) {
        Log.i(LOG_TAG + "processPicture", "quality compression begin");
        // 进行质量压缩
        ByteArrayOutputStream byteArrayOutputStream = ImageCompression.compressImage(bitmap, size);

        // 获取一个缓存位置
        FileOutputStream fileOutputStream = cacheTool.putAndBack(COMPRESSION_IMAGE_CACHE_PRE + key);

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
     * 质量压缩，异步方法
     *
     * @param cacheTool 存放压缩图的缓存工具
     * @param key       要存放的缓存key（不含前缀）
     * @param bitmap    要压缩的图片
     * @param size      目标容量，单位KB
     * @param listener  处理完成监听器
     */
    public static void qualityBitmap(@NotNull final CacheTool cacheTool, final String key, final
    Bitmap bitmap, final int size, @Nullable final ProcessFinishListener listener) {

        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Log.i(LOG_TAG + "processPicture", "quality compression begin");
                // 进行质量压缩
                ByteArrayOutputStream byteArrayOutputStream = ImageCompression.compressImage
                        (bitmap, size);

                // 获取一个缓存位置
                FileOutputStream fileOutputStream = cacheTool.putAndBack
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

                if (listener != null) {
                    listener.finish(cacheTool, COMPRESSION_IMAGE_CACHE_PRE + key);
                }
            }
        });
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
    public static Bitmap resolutionBitmap(File file, int width, int height) {
        Log.i(LOG_TAG + "processPicture", "resolution compression begin");
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(file.getPath(), options);

        options.inSampleSize = ImageCompression.calculateLowSampleSize(options, width, height);

        options.inJustDecodeBounds = false;
        options.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
        Log.i(LOG_TAG + "processPicture", "resolution compression end");
        return bitmap;
    }
}
