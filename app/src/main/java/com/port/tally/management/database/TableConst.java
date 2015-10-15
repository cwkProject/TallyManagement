package com.port.tally.management.database;
/**
 * Created by 超悟空 on 2015/9/21.
 */

/**
 * 各表数据库常量
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public interface TableConst {

    /**
     * 所有需要数据库初始化时创建的数据表的建表语句集合
     */
    String[] CREATE_TABLE_SQL_ARRAY = {CargoType.CREATE_TABLE_SQL , CargoOwner.CREATE_TABLE_SQL ,
                                       Voyage.CREATE_TABLE_SQL , Operation.CREATE_TABLE_SQL ,
                                       Forwarder.CREATE_TABLE_SQL , Storage.CREATE_TABLE_SQL ,
                                       Company.CREATE_TABLE_SQL};

    /**
     * 货物类别
     */
    interface CargoType {
        /**
         * 表名
         */
        String TABLE_NAME = "tb_cargo_type";

        /**
         * 建表语句
         */
        String CREATE_TABLE_SQL = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                "PRIMARY" + " KEY, %s TEXT NOT NULL,  %s TEXT, %s TEXT)", TABLE_NAME, CommonConst
                ._ID, CommonConst.CODE, CommonConst.NAME, CommonConst.SHORT_CODE);
    }

    /**
     * 货主
     */
    interface CargoOwner {
        /**
         * 表名
         */
        String TABLE_NAME = "tb_cargo_owner";

        /**
         * 建表语句
         */
        String CREATE_TABLE_SQL = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                "PRIMARY" + " KEY, %s TEXT NOT NULL,  %s TEXT, %s TEXT)", TABLE_NAME, CommonConst
                ._ID, CommonConst.CODE, CommonConst.NAME, CommonConst.SHORT_CODE);
    }

    /**
     * 货代
     */
    interface Forwarder {
        /**
         * 表名
         */
        String TABLE_NAME = "tb_forwarder";

        /**
         * 建表语句
         */
        String CREATE_TABLE_SQL = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                "PRIMARY" + " KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT, %s TEXT)" +
                "", TABLE_NAME, CommonConst._ID, CommonConst.CODE, CommonConst.NAME, CommonConst
                .COMPANY_CODE, CommonConst.SHORT_CODE);
    }

    /**
     * 航次
     */
    interface Voyage {
        /**
         * 表名
         */
        String TABLE_NAME = "tb_voyage";

        /**
         * 建表语句
         */
        String CREATE_TABLE_SQL = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                "PRIMARY" + " KEY, %s TEXT NOT NULL,  %s TEXT, %s TEXT)", TABLE_NAME, CommonConst
                ._ID, CommonConst.CODE, CommonConst.NAME, CommonConst.SHORT_CODE);
    }

    /**
     * 作业过程
     */
    interface Operation {
        /**
         * 表名
         */
        String TABLE_NAME = "tb_operation";

        /**
         * 建表语句
         */
        String CREATE_TABLE_SQL = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                "PRIMARY" + " KEY, %s TEXT NOT NULL,  %s TEXT, %s TEXT)", TABLE_NAME, CommonConst
                ._ID, CommonConst.CODE, CommonConst.NAME, CommonConst.SHORT_CODE);
    }

    interface Storage {
        /**
         * 表名
         */
        String TABLE_NAME = "tb_storage";

        /**
         * 建表语句
         */
        String CREATE_TABLE_SQL = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                "PRIMARY" + " KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT, %s TEXT)" +
                "", TABLE_NAME, CommonConst._ID, CommonConst.CODE, CommonConst.NAME, CommonConst
                .COMPANY_CODE, CommonConst.SHORT_CODE);
    }

    /**
     * 公司
     */
    interface Company {
        /**
         * 表名
         */
        String TABLE_NAME = "tb_company";

        /**
         * 建表语句
         */
        String CREATE_TABLE_SQL = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER " +
                "PRIMARY" + " KEY, %s TEXT NOT NULL,  %s TEXT)", TABLE_NAME, CommonConst._ID,
                CommonConst.CODE, CommonConst.NAME);
    }
}
