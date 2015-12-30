package com.port.tally.management.database;
/**
 * Created by 超悟空 on 2015/12/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.port.tally.management.bean.ShiftChange;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.global.GlobalApplication;
import org.mobile.library.model.database.BaseOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 交接班消息数据库操作工具
 *
 * @author 超悟空
 * @version 1.0 2015/12/14
 * @since 1.0
 */
public class ShiftChangeOperator extends BaseOperator<ShiftChange> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeOperator.";

    /**
     * 当前使用的表名(与用户关联)
     */
    private String nowTableName = null;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public ShiftChangeOperator(Context context) {
        super(context);
    }

    @Override
    protected SQLiteOpenHelper onCreateDatabaseHelper(Context context) {
        return new TallySQLiteHelper(context);
    }

    @Override
    protected SQLiteOpenHelper onCreateWriteDatabaseHelper(Context context) {
        return TallySQLiteHelper.getSqLiteOpenHelper(context);
    }

    @Override
    protected String onCreateTableName() {

        nowTableName = TableConst.ShiftChange.TABLE_NAME + "_" + GlobalApplication.getGlobal()
                .getLoginStatus().getUserID();

        return nowTableName;
    }

    @Override
    protected void onCreateTable(SQLiteOpenHelper sqLiteHelper) {
        /**
         * 建表语句
         */
        String createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                        "PRIMARY KEY, %s TEXT UNIQUE, %s TEXT NOT NULL, %s TEXT, %s TEXT " +
                        "NOT NULL, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)", nowTableName,
                CommonConst._ID, CommonConst.CODE, TableConst.ShiftChange.SEND_NAME, TableConst
                        .ShiftChange.RECEIVE_NAME, TableConst.ShiftChange.TIME, TableConst
                        .ShiftChange.CONTENT, TableConst.ShiftChange.IMAGE_URL, TableConst
                        .ShiftChange.AUDIO_URL, TableConst.ShiftChange.MY_SEND);
        Log.i(LOG_TAG + "onCreateTable", "sql is " + createTableSql);
        sqLiteHelper.getWritableDatabase().execSQL(createTableSql);

        close(sqLiteHelper);
    }

    @Override
    protected ContentValues onFillData(ShiftChange data) {
        // 数据库值对象
        ContentValues cv = new ContentValues();
        cv.put(CommonConst.CODE, data.getToken());
        cv.put(TableConst.ShiftChange.SEND_NAME, data.getSend());
        cv.put(TableConst.ShiftChange.RECEIVE_NAME, data.getReceive());
        cv.put(TableConst.ShiftChange.TIME, data.getTime());
        cv.put(TableConst.ShiftChange.CONTENT, data.getContent());
        cv.put(TableConst.ShiftChange.MY_SEND, data.isMySend() ? 1 : 0);

        if (data.getImageUrlList() != null) {
            JSONObject imageUrl = new JSONObject(data.getImageUrlList());
            cv.put(TableConst.ShiftChange.IMAGE_URL, imageUrl.toString());
        }

        if (data.getAudioUrlList() != null) {
            JSONObject audioUrl = new JSONObject(data.getAudioUrlList());
            cv.put(TableConst.ShiftChange.AUDIO_URL, audioUrl.toString());
        }

        return cv;
    }

    @Override
    protected List<ShiftChange> query(String sql) {
        Log.i(LOG_TAG + "query", "sql is " + sql);
        // 查询数据
        Cursor cursor = sqLiteHelper.getReadableDatabase().rawQuery(sql, null);

        // 列索引
        int code = cursor.getColumnIndex(CommonConst.CODE);
        int sendName = cursor.getColumnIndex(TableConst.ShiftChange.SEND_NAME);
        int receiveName = cursor.getColumnIndex(TableConst.ShiftChange.RECEIVE_NAME);
        int time = cursor.getColumnIndex(TableConst.ShiftChange.TIME);
        int content = cursor.getColumnIndex(TableConst.ShiftChange.CONTENT);
        int mySend = cursor.getColumnIndex(TableConst.ShiftChange.MY_SEND);
        int imageUrl = cursor.getColumnIndex(TableConst.ShiftChange.IMAGE_URL);
        int audioUrl = cursor.getColumnIndex(TableConst.ShiftChange.AUDIO_URL);

        // 数据填充
        List<ShiftChange> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            // 一条记录
            ShiftChange data = new ShiftChange();
            data.setToken(cursor.getString(code));
            data.setSend(cursor.getString(sendName));
            data.setReceive(cursor.getString(receiveName));
            data.setTime(cursor.getString(time));
            data.setContent(cursor.getString(content));
            data.setMySend(cursor.getInt(mySend) == 1);

            try {
                String imageString = cursor.getString(imageUrl);

                if (imageString != null && !imageString.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(imageString);

                    Map<String, String> map = new HashMap<>();

                    Iterator<String> iterator = jsonObject.keys();

                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        map.put(key, jsonObject.getString(key));
                    }

                    data.setImageUrlList(map);
                }

                String audioString = cursor.getString(audioUrl);

                if (audioString != null && !audioString.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(audioString);

                    Map<String, String> map = new HashMap<>();

                    Iterator<String> iterator = jsonObject.keys();

                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        map.put(key, jsonObject.getString(key));
                    }

                    data.setAudioUrlList(map);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG + "query", "JSONException is " + e.getMessage());
            }

            list.add(data);
        }

        Log.i(LOG_TAG + "query", "row count is " + list.size());

        // 关闭数据库
        cursor.close();
        close(sqLiteHelper);

        return list;
    }

    @Override
    public List<ShiftChange> queryWithCondition(String... parameters) {
        Log.i(LOG_TAG + "queryWithCondition", "queryWithCondition is invoked");

        // 查询语句
        String sql;

        if (parameters.length == 1) {
            sql = String.format("select * from %s where %s=%s", nowTableName, CommonConst.CODE,
                    parameters[0]);
        } else {
            sql = String.format("select * from %s order by %s desc limit %s,%s", nowTableName,
                    TableConst.ShiftChange.TIME, parameters[0], parameters[1]);
        }
        return query(sql);
    }

    /**
     * 根据行ID查询结果
     *
     * @param id 行ID
     *
     * @return 数据对象，没有返回null
     */
    public ShiftChange queryById(long id) {
        Log.i(LOG_TAG + "queryById", "query id is " + id);

        // 查询语句
        String sql = String.format("select * from %s where %s=%s", nowTableName, CommonConst._ID,
                id);

        List<ShiftChange> list = query(sql);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    protected String onWhereSql(ShiftChange data) {
        return String.format("%s='%s'", CommonConst.CODE, data.getToken());
    }
}
