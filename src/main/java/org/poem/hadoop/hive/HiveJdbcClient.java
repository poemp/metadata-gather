package org.poem.hadoop.hive;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * hivejdbc连接类
 * @author suntree.xu
 *
 */
@Repository
public class HiveJdbcClient {


  public static void main(String[] args) throws Exception {
      HiveJdbcClient hiveJdbcClient = new HiveJdbcClient();


      List<String> goods2 = hiveJdbcClient.getTableInfo("goods2");
      for (String s : goods2) {
          System.out.println(s);
      }
      List<Map<String, String>> dataFromTable = hiveJdbcClient.getDataFromTable("goods2", Arrays.asList("id", "name"), null, null);
      for (Map<String, String> map : dataFromTable) {
          map.forEach((key,value)->{
              System.out.println(key+":"+value);
          });
      }
  }

  /**
   * 获取jdbc连接
   * @return Connection
   */
  public static Connection getConn(){
	  Connection con = null;
	  try {
	      Class.forName( getProperties("hive.driverName"));
	      con = DriverManager.getConnection(getProperties("hive.connection.url"),getProperties("remote.username"),getProperties("remote.password"));
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	  return con;
  }

 /**
  * 获取指定表名的表字段信息
  * @param tableName
  * @return List<String>
  * @throws Exception
  */
 private List<String> getTableInfo(String tableName) throws Exception {
      List<String> resultList = new ArrayList<String>();
      List<Map<String,String>> resultMapList = describeTable(tableName);
     for (Map<String, String> stringStringMap : resultMapList) {
         resultList.add(stringStringMap.get("col_name"));
     }
      return resultList;
  }


    static List<Map<String,String>> describeTable(String tableName){
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        Connection con = getConn();
        ResultSet res = null;
        String hiveql = "describe "+tableName;
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeQuery(hiveql);
            ResultSetMetaData rsmd = res.getMetaData();
            while(res.next()) {
                Map<String,String> map = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i), res.getString(rsmd.getColumnName(i)));
                }
                resultList.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 根据表名称、查询字段、条件、限制条数返回数据,若参数为空,请填入"";
     * @param tableName
     * @param columnList
     * @param condition
     * @param limitInfo
     * @return
     */
    private List<Map<String,String>> getDataFromTable(String tableName, List<String> columnList, String condition, String limitInfo){
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        Connection con = getConn();
        ResultSet res = null;
        StringBuilder hiveql = new StringBuilder("select ");
        if(columnList !=null&&columnList.size() > 0){
            for (int i = 0;i < columnList.size();i++){
                if(i != columnList.size()-1) {
                    hiveql.append(columnList.get(i)).append(",");
                } else {
                    hiveql.append(columnList.get(i));
                }
            }
        }else{
            hiveql.append("* ");
        }
       hiveql.append(" from ").append(tableName);
       if(condition!=null&&!"".equals(condition)){
           hiveql.append(" where ").append(condition);
       }
        if(limitInfo!=null&&!"".equals(limitInfo)){
            hiveql.append(" ").append(limitInfo);
        }
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeQuery(hiveql.toString());
            ResultSetMetaData rsmd = res.getMetaData();
            while(res.next()) {
                Map<String,String> map = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i), res.getString(rsmd.getColumnName(i)));
                }
                resultList.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    public String excuteLoadData(String loadStr){
        String result = "";
        Connection con = getConn();
        //ResultSet res = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(loadStr);
        } catch (Exception e){
                result = "执行失败："+loadStr;
                e.printStackTrace();
            }
        return result;
    }

    /**
     * 仅执行hiveql，不返回数据，只返回成功失败，比如执行创建表，加载数据等
     * @param hiveql
     * @return
     * @throws Exception
     */
    public String excuteHiveql(String hiveql) throws Exception{
        String result = "";
        Connection con = getConn();
        //ResultSet res = null;
       try {
           Statement stmt = con.createStatement();
           int bool = stmt.executeUpdate(hiveql);
           result = "执行成功："+hiveql;
       }catch (Exception e){
           result = "执行失败："+hiveql;
           e.printStackTrace();
       }
        return result;
    }

    public int queryCount(String tableName){
        int sum = 0;
        Connection con = getConn();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select count(*) from "+tableName);
            while(res.next()){
                sum = res.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sum;
    }

    /**
     * 获取生成hive表的hiveql
     * hive表默认全部都是string类型，若有其他类型的需要另外封装方法
     * @param tableName
     * @param columnList
     * @return
     */
    public String genCreateTablesql(String tableName,List<String> columnList){
        String sql = "create table "+tableName+"(";
        for(int i = 0;i<columnList.size();i++){
            if(columnList.get(i).equals("")){
               continue;
            }
            if(i != columnList.size()-1){
                sql += columnList.get(i) + "  string,";
            }else {
                sql += columnList.get(i) + "  string";
            }
        }
        sql += ") row format delimited fields terminated by ','";
        return sql;
    }

    /**
     * 获取生成hive表的hiveql
     * @param tableName
     * @param columnMapList
     * @return
     */
    public String genCreateTablesqlByColumnAndType(String tableName,List<Map<String,String >> columnMapList){
        String sql = "create table "+tableName+"(";

        for (Map<String,String> map:columnMapList){
            Set<String> keys = map.keySet();
            for (String key :keys){
                sql +=  "`"+key +"`"+" " +map.get(key) + ",";
            }
        }
        sql = sql.substring(0,sql.length()-1);
        sql += ") row format delimited fields terminated by ','";
        return sql;
    }

    public String genQueryDatasql(String tableName,List<String> columnList){
        String sql = "select ";
        if(columnList.size() == 0) {
            sql += "*";
        }else {
            for (int i = 0; i < columnList.size(); i++) {
                if (i != columnList.size() - 1) {
                    sql += columnList.get(i) + ",";
                } else {
                    sql += columnList.get(i);
                }
            }
        }
        sql += " from "+tableName;
        return sql;
    }

    public boolean isTableDataExist(String tableName) {
        Connection con = getConn();
        ResultSet res =null;
        String str = "";
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("select * from "+tableName+" limit 1");
            while(res.next()){
              str = res.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return !str.equals("");
        //return false;
    }

    /**
     * 获取相关配置
     * @param key
     * @return
     */
    public static String getProperties(String key){
        String value = "";
        Properties prop = new Properties();
        InputStream in = HiveJdbcClient.class.getResourceAsStream("/hiveConfiguration.properties");
        try {
            prop.load(in);
            value = prop.getProperty(key).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取相关配置(公用)
     * @param key
     * @return
     */
    public  String getProperty(String key){
        String value = "";
        Properties prop = new Properties();
        InputStream in = HiveJdbcClient.class.getResourceAsStream("/hiveConfiguration.properties");
        try {
            prop.load(in);
            value = prop.getProperty(key).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return value;
    }
    class testThread extends Thread{
        public void run()
        {
            System.out.println("testThread run");
            try{
                sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
