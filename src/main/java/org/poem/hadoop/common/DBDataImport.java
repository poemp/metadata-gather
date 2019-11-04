package org.poem.hadoop.common;

import org.poem.hadoop.common.dbUtil.ConnUtil;
import org.poem.hadoop.common.dbUtil.DBUtil;
import org.poem.hadoop.common.dbUtil.DataSourceType;
import org.poem.hadoop.hive.HiveOperate;
import org.poem.hadoop.model.ColumnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sai.luo on 2017-5-19.
 */
public class DBDataImport extends  Thread {

    private static final Logger log = LoggerFactory.getLogger(DBDataImport.class) ;
    private FileWrite fileWrite = new FileWrite();
    private static final String split = "\t";

    private String username ;
    private String password;
    private String url ;
    private int dataSourceType ;
    private String sourceTableName;
    private String localTempPath ;
    /**
     *
     * @param username 用户名
     * @param password 密码
     * @param url 数据库路径
     * @param dataSourceType 数据库类型
     * @param tableName 数据库表名
     */
    public DBDataImport(String username, String password, String url, int dataSourceType, String tableName,String localTempPath) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.dataSourceType = dataSourceType;
        this.sourceTableName = tableName;
        this.localTempPath = localTempPath ;
    }

    @Override
    public void run() {

        try {
            //1、获取数据库连接
            Connection conn = ConnUtil.getConn(DataSourceType.getDriverName(dataSourceType),url,username,password);
            //2、获取表的信息
            List<ColumnInfo> columnMapList = DBUtil.getTableColumnInfo(sourceTableName, conn);
            //3、转换为hive表的对应类型
            DBUtil.tableColumnInfoToHiveColnmnInfo(columnMapList,dataSourceType);
            columnMapList.forEach(columnInfo -> log.info(columnInfo.toString()));
            //4、获取表的所有数据
            List<List<Object>> resultList = DBUtil.getTableDatas(sourceTableName, conn);
            int dataCount = resultList.size();
            //5、写入文件 并导入到hive
            if (dataCount > 0) {
                String tmpFile = sourceTableName;
                fileWrite.writeMapList2File(resultList,  localTempPath+ sourceTableName,split);
                HiveOperate.executeHiveOperate(sourceTableName,tmpFile,columnMapList,split,localTempPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }
}
