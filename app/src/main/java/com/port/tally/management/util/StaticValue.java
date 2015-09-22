package com.port.tally.management.util;
/**
 * Created by 超悟空 on 2015/9/7.
 */

/**
 * 存放应用使用的全局静态常量
 *
 * @author 超悟空
 * @version 1.0 2015/9/7
 * @since 1.0
 */
public interface StaticValue {
    /**
     * 本应用编号
     */
    String APP_CODE = "LHGL";

    /**
     * 数据列表工具标签
     */
    interface CodeListTag {
        /**
         * 货物类别
         */
        String CARGO_TYPE_LIST = "cargo_type_list";

        /**
         * 货主
         */
        String CARGO_OWNER_LIST = "cargo_owner_list";

        /**
         * 航次
         */
        String VOYAGE_LIST = "voyage_list";

        /**
         * 作业过程
         */
        String OPERATION_LIST = "operation_list";

    }

    /**
     * 样例任务地址
     */
    String HTTP_GET_SAMPLE_URL = "http://168.100.1.218/wlkg/Service/";

    /**
     * 获取货主列表请求地址
     */
    String CARGO_OWNER_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetCargoOwner.aspx";

    /**
     * 获取航次列表请求地址
     */
    String VOYAGE_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetVoyage.aspx";

    /**
     * 获取作业过程请求地址
     */
    String OPERATION_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetOperation.aspx";

    /**
     * 获取货物种类列表的请求地址
     */
    String CARGO_TYPE_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetCargo.aspx";

    /**
     * 查询委托列表的请求地址
     */
    String ENTRUST_QUERY_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Consign/GetConsign.aspx";
}
