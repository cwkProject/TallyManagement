package com.port.tally.management.util;
/**
 * Created by 超悟空 on 2015/12/9.
 */

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 缓存key相关工具
 *
 * @author 超悟空
 * @version 1.0 2015/12/9
 * @since 1.0
 */
public class CacheKeyUtil {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "CacheKeyUtil.";

    /**
     * 日期格式工具
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");

    /**
     * 生成一个缓存随机key
     *
     * @return key
     */
    public static String getRandomKey() {
        // 创建缓存key
        String key = simpleDateFormat.format(new Date());
        Log.i(LOG_TAG + "getRandomKey", "cache key is " + key);

        return key;
    }
}
