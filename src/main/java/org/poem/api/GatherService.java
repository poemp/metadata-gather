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
     * @param gatherId
     * @return
     */
    public GatherDBTableFieldsVO getAllGatherDBTableFieldsVO(String gatherId);
}
