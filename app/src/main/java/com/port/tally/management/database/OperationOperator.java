package com.port.tally.management.database;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.port.tally.management.bean.Operation;

import org.mobile.library.model.database.BaseOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业过程数据库操作
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class OperationOperator extends BaseOperator<Operation> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "OperationOperator.";

    public OperationOperator(Context context) {
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
        return TableConst.Operation.TABLE_NAME;
    }

    @Override
    protected ContentValues onFillData(Operation data) {
        // 数据库值对象
        ContentValues cv = new ContentValues();
        cv.put(CommonConst.CODE, data.getId());
        cv.put(CommonConst.NAME, data.getName());
        cv.put(CommonConst.SHORT_CODE, data.getShortCode());

        return cv;
    }

    @Override
    protected List<Operation> query(String sql) {
        Log.i(LOG_TAG + "query", "sql is " + sql);
        // 查询数据
        Cursor cursor = sqLiteHelper.getReadableDatabase().rawQuery(sql, null);

        // 列索引
        int shortCode = cursor.getColumnIndex(CommonConst.SHORT_CODE);
        int nameIndex = cursor.getColumnIndex(CommonConst.NAME);
        int codeIndex = cursor.getColumnIndex(CommonConst.CODE);

        // 数据填充
        List<Operation> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            // 一条记录
            Operation data = new Operation();

            data.setId(cursor.getString(codeIndex));
            data.setName(cursor.getString(nameIndex));
            data.setShortCode(cursor.getString(shortCode));

            list.add(data);
        }

        Log.i(LOG_TAG + "query", "row count is " + list.size());

        // 关闭数据库
        cursor.close();
        close(sqLiteHelper);

        return list;
    }

    @Override
    protected String onWhereSql(Operation data) {
        return String.format("%s='%s'", CommonConst.CODE, data.getId());
    }
}
