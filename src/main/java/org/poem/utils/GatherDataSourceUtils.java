package org.poem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Administrator
 */
public class GatherDataSourceUtils {

    private static final Logger logger = LoggerFactory.getLogger( GatherDataSourceUtils.class );
    /**
     * 关闭
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
}
