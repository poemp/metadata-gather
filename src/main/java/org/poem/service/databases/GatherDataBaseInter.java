package org.poem.service.databases;

import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.databases.entity.DateBaseEntity;
import org.poem.api.vo.table.entity.TableEntity;

import java.sql.Connection;
import java.util.List;

/**
 * 数据结构
 * 获取数据库
 * 并且获取数据库表
 * @author Administrator
 */
public interface GatherDataBaseInter {


    /**
     *
     * 获取所有数据库
     * @param connection
     * @return
     */
    public List<DateBaseEntity> getDataBases(Connection connection);


    /**
     * 获取所有的表列表
     * @return
     */
    List<TableEntity> getAllTableName(String db, Connection connection );

    /**
     * 获取表的列信息
     * @param databases
     * @param table
     * @return
     */
    DataSetVO getTaleFields(String databases, String table, Connection connection);

}
