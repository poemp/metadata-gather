package org.poem.hadoop.common.dbUtil;


import org.poem.hadoop.hive.HiveDataType;
import org.poem.hadoop.model.ColumnInfo;
import org.poem.hadoop.rdbs.MysqlDataType;
import org.poem.hadoop.rdbs.OracleDataType;
import org.poem.hadoop.rdbs.SqlServerDataType;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sai.luo
 * @date 2017-5-18
 */
public class DBUtil {

    /**
     * 获取数据库表字段 类型
     * @param tableName
     * @param con
     * @return
     */
    public static List<ColumnInfo> getTableColumnInfo(String tableName, Connection con){
        List<ColumnInfo> resultList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "select * from "+tableName;
            ResultSet res = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = res.getMetaData();

            for(int i=1;i<=rsmd.getColumnCount();i++) {
                resultList.add(new ColumnInfo(rsmd.getColumnName(i), rsmd.getColumnTypeName(i)));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList ;
    }

    /**
     * 获取全表表数据
     * @param tableName
     * @param con
     * @return
     */
    public static List<List<Object>> getTableDatas(String tableName,Connection con){
        List<List<Object>> resultList = new ArrayList<>();
        ResultSet res = null ;
        Statement stmt = null ;
        try {
            stmt = con.createStatement();
            String sql = "select * from "+tableName;
            res = stmt.executeQuery(sql);
            ResultSetMetaData metaData = res.getMetaData();
            int var =0 ;
            while (res.next()){
                List<Object> row = new ArrayList<>();
                for (int i=1;i<=metaData.getColumnCount();i++){
                    row.add(JdbcUtils.getResultSetValue(res,i));
                }
                resultList.add(row);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                ConnUtil.close(res);
                ConnUtil.close(stmt);
                ConnUtil.close(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList ;
    }

    /**
     *
     * @param list
     * @param dataSourceType
     * @return
     */
    public static List<ColumnInfo> tableColumnInfoToHiveColnmnInfo(List<ColumnInfo> list,int dataSourceType){
         list.forEach(columnInfo -> columnInfo.setColumnType(dataBaseDataTypeToHiveDataType(columnInfo.getColumnType(),dataSourceType)));
         return list ;

    }

    /**
     * 数据库数据类型转hive数据类型
     * @param dataBaseDataType
     * @param dataSourceType
     * @return
     */
    public static String dataBaseDataTypeToHiveDataType(String dataBaseDataType,int dataSourceType){
        switch (dataSourceType){
            case DataSourceType.MYSQL_TYPE:
                return MysqlDataType.mysqlDataTypetoHiveDataType(dataBaseDataType);
            case DataSourceType.SQLSERVER_TYPE:
                return SqlServerDataType.sqlServerDataTypetoHiveDataType(dataBaseDataType);
            case DataSourceType.ORACLE_TYPE:
                return OracleDataType.OracleDataTypetoHiveDataType(dataBaseDataType);
            default:return HiveDataType.STRING;
        }

    }

    /**
     * 获取所有的表
     * @param conn 链接
     * @param database 数据库
     * @return
     * @throws SQLException
     */
    public static List getAlltables(Connection conn, String database) throws SQLException {
        List   tables   =   new   ArrayList();
        DatabaseMetaData   dbMetaData   =   conn.getMetaData();
        //可为:"TABLE",   "VIEW",   "SYSTEM   TABLE",
        //"GLOBAL   TEMPORARY",   "LOCAL   TEMPORARY",   "ALIAS",   "SYNONYM"
        String[]   types   =   {"TABLE"};
        ResultSet   tabs   =   dbMetaData.getTables(database,   null,   "%",types/*只要表就好了*/);
  /*记录集的结构如下:
      TABLE_CAT       String   =>   table   catalog   (may   be   null)
      TABLE_SCHEM   String   =>   table   schema   (may   be   null)
      TABLE_NAME     String   =>   table   name
      TABLE_TYPE     String   =>   table   type.
      REMARKS           String   =>   explanatory   comment   on   the   table
      TYPE_CAT         String   =>   the   types   catalog   (may   be   null)
      TYPE_SCHEM     String   =>   the   types   schema   (may   be   null)
      TYPE_NAME       String   =>   type   name   (may   be   null)
      SELF_REFERENCING_COL_NAME   String   =>   name   of   the   designated   "identifier"   column   of   a   typed   table   (may   be   null)
      REF_GENERATION   String   =>   specifies   how   values   in   SELF_REFERENCING_COL_NAME   are   created.   Values   are   "SYSTEM",   "USER",   "DERIVED".   (may   be   null)
    */
        while(tabs.next()){
            //只要表名这一列
            tables.add(tabs.getObject("TABLE_NAME"));

        }
        return   tables;
    }

    /**
     * 数据库导入
     * @param host
     * @param port
     * @param databaseName
     * @param dataSourceType
     * @return
     */
    public static String genDatabaseUrl(String host,int port,String databaseName,int dataSourceType){
        switch (dataSourceType){
            case DataSourceType.MYSQL_TYPE:
                return "jdbc:mysql://"+host+":"+port+"/"+databaseName+"?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            case DataSourceType.SQLSERVER_TYPE:
                return "jdbc:sqlserver://"+host+":"+port+";DatabaseName="+databaseName;
            case DataSourceType.ORACLE_TYPE:
                return "jdbc:oracle:thin:@//"+host+":"+port+":"+databaseName;
            default:
                return null;

        }
    }

}
