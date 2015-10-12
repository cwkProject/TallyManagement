package com.port.tally.management.database;
/**
 * Created by 超悟空 on 2015/9/21.
 */

/**
 * 通用数据库表常量
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public interface CommonConst {

    /**
     * ID列
     */
    String _ID = "_id";

    /**
     * 数据库名
     */
    String DB_NAME = "tally.db";

    /**
     * 数据库版本
     */
    int DB_VERSION = 1;

    /**
     * 编码列，一般作为功能主键
     */
    String CODE = "code";

    /**
     * 名称列
     */
    String NAME = "name";

    /**
     * 速记码列
     */
    String SHORT_CODE = "short_code";

    /**
     * 公司编码列
     */
    String COMPANY_CODE = "company_code";
}
