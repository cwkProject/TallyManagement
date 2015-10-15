package com.port.tally.management.database;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.port.tally.management.bean.Company;

import org.mobile.library.model.database.BaseOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司数据库操作
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class CompanyOperator extends BaseOperator<Company> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "CompanyOperator.";

    public CompanyOperator(Context context) {
        super(context);
    }

    @Override
    protected SQLiteOpenHelper onCreateDatabaseHelper(Context context) {
        return new TallySQLiteHelper(context);
    }

    @Override
    protected String onCreateTableName() {
        return TableConst.Company.TABLE_NAME;
    }

    @Override
    protected ContentValues onFillData(Company data) {
        // 数据库值对象
        ContentValues cv = new ContentValues();
        cv.put(CommonConst.CODE, data.getId());
        cv.put(CommonConst.NAME, data.getName());

        return cv;
    }

    @Override
    protected List<Company> query(String sql) {
        Log.i(LOG_TAG + "query", "sql is " + sql);
        // 查询数据
        Cursor cursor = sqLiteHelper.getReadableDatabase().rawQuery(sql, null);

        // 列索引
        int nameIndex = cursor.getColumnIndex(CommonConst.NAME);
        int codeIndex = cursor.getColumnIndex(CommonConst.CODE);

        // 数据填充
        List<Company> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            // 一条记录
            Company data = new Company();

            data.setId(cursor.getString(codeIndex));
            data.setName(cursor.getString(nameIndex));

            list.add(data);
        }

        Log.i(LOG_TAG + "query", "row count is " + list.size());

        // 关闭数据库
        cursor.close();
        close();

        return list;
    }

    @Override
    protected String onWhereSql(Company data) {
        return String.format("%s='%s'", CommonConst.CODE, data.getId());
    }
}
