package org.poem.hadoop.common.dbUtil;

/**
 *
 * @author sai.luo
 * @date 2017-5-19
 */
public class DataSourceType {
    public static final int MYSQL_TYPE = 1;
    public static final int SQLSERVER_TYPE = 2;
    public static final int ORACLE_TYPE = 3;

    public static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String SQLSERVER_DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String ORACLE_TYPE_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";

    public static String getDriverName(int dataSourceType) {
        switch (dataSourceType) {
            case MYSQL_TYPE:
                return MYSQL_DRIVER_NAME;
            case SQLSERVER_TYPE:
                return SQLSERVER_DRIVER_NAME;
            case ORACLE_TYPE:
                return ORACLE_TYPE_DRIVER_NAME;
            default:return null;
        }
    }
}
