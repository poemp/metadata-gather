package org.poem.utils;

import org.poem.service.connect.DataType;
import org.poem.service.databases.GatherDataBaseInter;
import org.poem.service.databases.MysqlGatherData;
import org.poem.service.databases.OracleGatherData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Administrator
 */
public class GatherDataSourceUtils {

    private static final Logger logger = LoggerFactory.getLogger( GatherDataSourceUtils.class );

    /**
     * 关闭
     *
     * @param pstmt
     * @param rs
     */
    public static void release(Statement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                logger.debug( "关闭存储查询结果的ResultSet对象" );
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                logger.debug( "关闭负责执行SQL命令的Statement对象" );
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取数据的bean类型
     *
     * @return
     */
    public static GatherDataBaseInter getBean() {
        DataType dataType = ThreadUtils.getDataTypeThreadLocal().get();
        if (DataType.MYSQL.eq( dataType )) {
            return SpringUtils.getBean( MysqlGatherData.BEAN, MysqlGatherData.class );
        } else if (DataType.ORACLE.eq( dataType )) {
            return SpringUtils.getBean( OracleGatherData.BEAN, OracleGatherData.class );
        } else {
            throw new IllegalArgumentException( "not unrecognizable type[" + dataType + "] !!!!!!!!!" );
        }
    }
}
