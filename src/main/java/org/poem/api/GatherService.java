package org.poem.api;

import org.poem.api.vo.*;

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
}
