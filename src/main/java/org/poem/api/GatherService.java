package org.poem.api;

import org.poem.api.vo.*;
import org.poem.api.vo.column.entity.DataSetVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface GatherService {

    /**
     * 测试连接成功
     */
    boolean testGather(String gatherId);

    /**
     * 获取数据的schema
     *
     * @param gatherId
     * @return
     */
    List<DbVO> getSchema(String gatherId);


    /**
     * 获取数据表字端信息
     */

    List<TableVO> getTable(GatherVO gatherVO);

    /**
     * @param table
     * @return
     */
    TableVO getTableById(String table);

    /**
     * 获取表的字端信息
     * @param tableVO
     * @return
     */
    List<TableFieldsVO> getTableFieles(TableVO tableVO);


    /**
     *  获取 某一个库 某一个表 的某一个列
     * @param gatherId
     * @param db
     * @return
     */
    public GatherDBTableFieldsVO getAllGatherDBTableFieldsVO(String gatherId, String  db, String table);


    /**
     * 获取查询
     * @param sql 执行的sql
     * @param sourceId source id
     * @return
     */
    public DataSetVO executeSQL(String sql, String sourceId);
}
