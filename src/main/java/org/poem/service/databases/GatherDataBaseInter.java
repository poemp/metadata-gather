package org.poem.service.databases;

import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.databases.entity.DateBaseEntity;
import org.poem.api.vo.page.Page;
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



    /**
     * 获取数据表的数据
     *
     * @param tableEntity 　数据表
     * @return
     */
    DataSetVO getTableColumnsDatas(TableEntity tableEntity, Connection connection);

    /**
     * 所有数据的个数
     *
     * @param tableEntity
     * @return
     */
    long countTableDatas(TableEntity tableEntity, Connection connection);


    /**
     * 分页
     *
     * @param tableEntity
     * @param page
     * @return
     */
    DataSetVO getTableColumnsDatas(TableEntity tableEntity, Page page, Connection connection);


    /**
     * 查询信息
     * @param sql 需要执行的sql
     * @param connection 连接信息
     * @return
     */
    DataSetVO executeSql(String sql, Connection connection);
}
