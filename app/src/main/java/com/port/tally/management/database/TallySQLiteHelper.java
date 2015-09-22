package com.port.tally.management.database;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库工具
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class TallySQLiteHelper extends SQLiteOpenHelper {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallySQLiteHelper.";

    public TallySQLiteHelper(Context context) {
        super(context, CommonConst.DB_NAME, null, CommonConst.DB_VERSION);
        Log.i(LOG_TAG + "TallySQLiteHelper", "TallySQLiteHelper is invoked");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG + "onCreate", "onCreate is invoked");

        // 建表
        for (String sql : TableConst.CREATE_TABLE_SQL_ARRAY) {
            db.execSQL(sql);
            Log.i(LOG_TAG + "onCreate", "create table sql is " + sql);
        }

        Log.i(LOG_TAG + "onCreate", "onCreate end");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
