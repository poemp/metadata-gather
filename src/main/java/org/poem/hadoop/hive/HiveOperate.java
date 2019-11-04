package org.poem.hadoop.hive;


import org.poem.hadoop.common.constant.Constant;
import org.poem.hadoop.model.ColumnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sai.luo on 2017-5-18.
 */
public class HiveOperate {
    private static final HiveJdbcClient hiveJdbcClient = new HiveJdbcClient();
    private static   RemoteOpera remoteOpera = new RemoteOpera();
    private static final Logger log = LoggerFactory.getLogger(HiveOperate.class);


    public static List<ColumnInfo> getTableColumnInfo(String tableName) throws Exception {
        List<ColumnInfo> resultList = new ArrayList<ColumnInfo>();
        List<Map<String,String>> resultMapList = hiveJdbcClient.describeTable(tableName);
        for(int i=0;i<resultMapList.size();i++){
            if (resultMapList.get(i).get("col_name").equals(Constant.ROW_ID))continue;
            resultList.add(new ColumnInfo(resultMapList.get(i).get("col_name"),resultMapList.get(i).get("data_type")));
        }
        return resultList;
    }



    /**
     * 获取生成hive表的hiveql
     * @param tableName
     * @param columnInfoList
     * @return
     */
    public static String genCreateTablesqlByColumnInfo(String tableName,List<ColumnInfo> columnInfoList,String split){
        String sql = "create table "+tableName+"(";

        for (ColumnInfo columnInfo:columnInfoList){
            sql +=  "`"+columnInfo.getColumnName() +"`"+"  " +columnInfo.getColumnType() + ",";
        }
        sql = sql.substring(0,sql.length()-1);
        sql += ") row format delimited fields terminated by '"+split+"'";
        return sql;
    }



    /**
     * 文件上传并且导入到hive中
     * @param tableName
     * @param tmpFile
     * @param columnMapList
     */
    public static String  executeHiveOperate(String tableName,String tmpFile,List<ColumnInfo> columnMapList,String split,String localFilePath)throws Exception{
        String result = tableName ;
        //上传服务器
        remoteOpera.transferFile(hiveJdbcClient.getProperty("remote.ip"),hiveJdbcClient.getProperty("remote.username"),hiveJdbcClient.getProperty("remote.password"),localFilePath+tmpFile,hiveJdbcClient.getProperty("remote.filepath"));

        //根据表名 表头 拼接sql
        String createTableSql = HiveOperate.genCreateTablesqlByColumnInfo(tableName,columnMapList,split);
        log.info(createTableSql);
        try {
            //建表 hive
            String info =  hiveJdbcClient.excuteHiveql(createTableSql);
            log.info(info);
            String loadStr = "load data local inpath '/root/" + tmpFile + "' into table " + tableName;
            hiveJdbcClient.excuteHiveql(loadStr);
            //shell 删除临时文件
            String cmd1 = "rm -rf "+hiveJdbcClient.getProperty("remote.filepath") + "/" + tmpFile;
            remoteOpera.excuteCmd(hiveJdbcClient.getProperty("remote.ip"), hiveJdbcClient.getProperty("remote.username"), hiveJdbcClient.getProperty("remote.password"), cmd1);

        } catch (Exception e) {
            log.error("error: ",e);
            throw  new RuntimeException(e.getCause().getLocalizedMessage());
        }
        return  result ;
    }


}
