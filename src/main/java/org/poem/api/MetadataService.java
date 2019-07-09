package org.poem.api;

import org.poem.api.vo.*;
import org.poem.entities.tables.records.DsgGatherDbRecord;
import org.poem.entities.tables.records.DsgGatherTableFieldsRecord;
import org.poem.entities.tables.records.DsgGatherTableRecord;

import java.util.List;

/**
 * @author Administrator
 */
public interface MetadataService {
    /**
     * 保存连接信息
     */

    void savgeGather(GatherVO gatherVO);

    /**
     * 获取连接信息
     */

    List<GatherVO> getAllGather();


    /**
     * @param gatherId
     * @return
     */
    GatherVO getGatherVOById(String gatherId);

    /**
     * 保存
     *
     * @param dbVOS
     */
    List<DsgGatherDbRecord> saveDB(List<DbVO> dbVOS, String gatherInfoId);


    /**
     * @param gatherId
     * @return
     */
    List<DbVO> getAllDB(String gatherId);

    /**
     * @param tableVOS
     */
    List<DsgGatherTableRecord> saveTable(List<TableVO> tableVOS, String dbId);

    /**
     * @param tableQueryVO
     * @return
     */
    List<TableVO> getTable(TableQueryVO  tableQueryVO);


    /**
     * 获取表的信息
     * @param tableFieldsQueryVO
     * @return
     */
    public List<TableFieldsVO> getTableFileds(TableFieldsQueryVO tableFieldsQueryVO);
    /**
     * 保存数据
     *
     * @param fieldsVOS
     */
    List<DsgGatherTableFieldsRecord> saveTableFields(List<TableFieldsVO> fieldsVOS, String tableId);

    /**
     * 删除
     * @param gatherId
     */
    void deleteAllDataGatherInfoId(String gatherId);

    /**
     * @param gatherDBTableFieldsVO
     */
    public void saveGather(GatherDBTableFieldsVO gatherDBTableFieldsVO);

    /**
     * 查询保存的数据
     * @param queryGatherDBTableFieldsVO
     * @return
     */
    GatherDBTableFieldsVO getDBTableAndFields(QueryGatherDBTableFieldsVO queryGatherDBTableFieldsVO);
}
