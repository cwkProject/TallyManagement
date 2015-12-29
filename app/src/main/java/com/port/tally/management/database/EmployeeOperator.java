package com.port.tally.management.database;
/**
 * Created by 超悟空 on 2015/12/28.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.port.tally.management.bean.Employee;

import org.mobile.library.model.database.BaseOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工列表数据库操作类
 *
 * @author 超悟空
 * @version 1.0 2015/12/28
 * @since 1.0
 */
public class EmployeeOperator extends BaseOperator<Employee> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "EmployeeOperator.";

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public EmployeeOperator(Context context) {
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
        return TableConst.Employee.TABLE_NAME;
    }

    @Override
    protected ContentValues onFillData(Employee data) {
        // 数据库值对象
        ContentValues cv = new ContentValues();
        cv.put(CommonConst.CODE, data.getId());
        cv.put(CommonConst.NAME, data.getName());
        cv.put(CommonConst.COMPANY_CODE, data.getCompany());
        cv.put(CommonConst.SHORT_CODE, data.getShortCode());

        return cv;
    }

    @Override
    protected List<Employee> query(String sql) {
        Log.i(LOG_TAG + "query", "sql is " + sql);
        // 查询数据
        Cursor cursor = sqLiteHelper.getReadableDatabase().rawQuery(sql, null);

        // 列索引
        int shortCode = cursor.getColumnIndex(CommonConst.SHORT_CODE);
        int nameIndex = cursor.getColumnIndex(CommonConst.NAME);
        int codeIndex = cursor.getColumnIndex(CommonConst.CODE);
        int company = cursor.getColumnIndex(CommonConst.COMPANY_CODE);

        // 数据填充
        List<Employee> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            // 一条记录
            Employee data = new Employee();

            data.setId(cursor.getString(codeIndex));
            data.setName(cursor.getString(nameIndex));
            data.setShortCode(cursor.getString(shortCode));
            data.setCompany(cursor.getString(company));

            list.add(data);
        }

        Log.i(LOG_TAG + "query", "row count is " + list.size());

        // 关闭数据库
        cursor.close();
        close(sqLiteHelper);

        return list;
    }

    @Override
    public List<Employee> queryWithCondition(String... parameters) {
        Log.i(LOG_TAG + "queryWithCondition", "queryWithCondition is invoked");

        if (parameters == null || parameters.length < 1) {
            Log.d(LOG_TAG + "queryWithCondition", "parameters is null");
            return null;
        }

        // 查询语句
        String sql = String.format("select * from %s where %s='%s'", TableConst.Employee
                .TABLE_NAME, CommonConst.COMPANY_CODE, parameters[0]);

        return query(sql);
    }

    @Override
    protected String onWhereSql(Employee data) {
        return String.format("%s='%s'", CommonConst.CODE, data.getId());
    }
}
