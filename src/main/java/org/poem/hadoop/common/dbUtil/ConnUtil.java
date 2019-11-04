package org.poem.hadoop.common.dbUtil;

import java.sql.*;

/**
 *
 * @author sai.luo
 * @date 2017-5-18
 */
public class ConnUtil {


    /**
     * 得到连接
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConn(String driverName, String url, String user, String password) throws SQLException,ClassNotFoundException{
        Class.forName(driverName);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭连接
     * @param conn
     * @throws SQLException
     */
    static void close(Connection conn) throws SQLException{
        if(conn != null){
            conn.close();
        }
    }

    /**
     * 关闭Statement
     * @param stmt
     * @throws SQLException
     */
    static void close(Statement stmt) throws SQLException{
        if(stmt != null){
            stmt.close();
        }
    }
    /**
     * 关闭结果集
     * @param rs
     * @throws
     */
    static void close(ResultSet rs) throws SQLException{
        if(rs != null){
            rs.close();
        }
    }

    /**
     *
     * @param rs
     * @param statement
     * @param connection
     */
   public static void close(ResultSet rs,Statement statement,Connection connection){
       if(rs != null){
           try {
               rs.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       if(statement != null){
           try {
               statement.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       if(connection != null){
           try {
               connection.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

   }
}
