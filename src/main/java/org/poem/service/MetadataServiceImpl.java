package org.poem.service;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import org.jooq.SortField;
import org.poem.api.MetadataService;
import org.poem.api.vo.*;
import org.poem.api.vo.query.DbQueryVO;
import org.poem.api.vo.query.TableFieldsQueryVO;
import org.poem.api.vo.query.TableQueryVO;
import org.poem.dao.gather.GatherInfoDao;
import org.poem.dao.info.DsgGatherDBDao;
import org.poem.dao.info.DsgGatherTableDao;
import org.poem.dao.info.DsgGatherTableFieldsDao;
import org.poem.entities.tables.DsgGatherDb;
import org.poem.entities.tables.DsgGatherInfo;
import org.poem.entities.tables.DsgGatherTable;
import org.poem.entities.tables.DsgGatherTableFields;
import org.poem.entities.tables.records.DsgGatherDbRecord;
import org.poem.entities.tables.records.DsgGatherInfoRecord;
import org.poem.entities.tables.records.DsgGatherTableFieldsRecord;
import org.poem.entities.tables.records.DsgGatherTableRecord;
import org.poem.loghelper.LoggerHelper;
import org.poem.utils.SnowFlake;
import org.poem.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class MetadataServiceImpl implements MetadataService {

    private static final Logger logger = LoggerFactory.getLogger( MetadataServiceImpl.class );

    @Autowired
    private GatherInfoDao gatherInfoDao;

    @Autowired
    private DsgGatherDBDao dsgGatherDBDao;

    @Autowired
    private DsgGatherTableDao dsgGatherTableDao;

    @Autowired
    private DsgGatherTableFieldsDao dsgGatherTableFieldsDao;

    @Autowired
    private LoggerHelper loggerHelper;

    /**
     * @param gatherVO
     */
    @Override
    public void savgeGather(GatherVO gatherVO) {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add( DsgGatherInfo.DSG_GATHER_INFO.IP.eq( gatherVO.getIp() ) );
        conditions.add( DsgGatherInfo.DSG_GATHER_INFO.PORT.eq( gatherVO.getPort() ) );
        conditions.add( DsgGatherInfo.DSG_GATHER_INFO.DELETE.eq( (byte) 0 ) );
        List<SortField<?>> sortFields = Lists.newArrayList();
        sortFields.add( DsgGatherInfo.DSG_GATHER_INFO.CREATE_TIME.desc() );
        List<DsgGatherInfoRecord> gatherInfos = this.gatherInfoDao.findByConditions( conditions, sortFields );
        if (!CollectionUtils.isEmpty( gatherInfos )) {
            throw new IllegalArgumentException( "this record DsgGatherInfoRecord(" + gatherVO.getIp() + " / " + gatherVO.getPort() + ") is  exist . !!!!!!" );
        }
        DsgGatherInfoRecord infoRecord = new DsgGatherInfoRecord();
        infoRecord.setId( SnowFlake.genId() );
        infoRecord.setName( gatherVO.getName() );
        infoRecord.setIp( gatherVO.getIp() );
        infoRecord.setPort( gatherVO.getPort() );
        infoRecord.setDelete( (byte) 0 );
        infoRecord.setType( gatherVO.getType() );
        infoRecord.setServiceName( gatherVO.getServerName() );
        infoRecord.setPassword( gatherVO.getPassword() );
        infoRecord.setUser( gatherVO.getUserName() );
        infoRecord.setCreateTime( new Timestamp( System.currentTimeMillis() ) );
        infoRecord.setUpdateTime( new Timestamp( System.currentTimeMillis() ) );
        this.gatherInfoDao.insert( infoRecord );
    }

    /**
     * @return
     */
    @Override
    public List<GatherVO> getAllGather() {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add( DsgGatherInfo.DSG_GATHER_INFO.DELETE.eq( (byte) 0 ) );
        List<SortField<?>> sortFields = Lists.newArrayList();
        sortFields.add( DsgGatherInfo.DSG_GATHER_INFO.CREATE_TIME.desc() );
        List<DsgGatherInfoRecord> gatherInfos = this.gatherInfoDao.findByConditions( conditions, sortFields );
        return gatherInfos.stream().map(
                o -> {
                    GatherVO gatherVO = new GatherVO();
                    gatherVO.setId( o.getId() );
                    gatherVO.setGatherId( o.getId() );
                    gatherVO.setIp( o.getIp() );
                    gatherVO.setServerName( o.getServiceName() );
                    gatherVO.setPort( o.getPort() );
                    gatherVO.setType( o.getType() );
                    gatherVO.setPassword( o.getPassword() );
                    gatherVO.setUserName( o.getUser() );
                    return gatherVO;
                }
        ).collect( Collectors.toList() );
    }

    /**
     * @param gatherId
     * @return
     */
    @Override
    public GatherVO getGatherVOById(String gatherId) {
        DsgGatherInfoRecord o = this.gatherInfoDao.findById( gatherId );
        GatherVO gatherVO = new GatherVO();
        gatherVO.setId( o.getId() );
        gatherVO.setGatherId( gatherId );
        gatherVO.setIp( o.getIp() );
        gatherVO.setPort( o.getPort() );
        gatherVO.setType( o.getType() );
        gatherVO.setServerName( o.getServiceName() );
        gatherVO.setPassword( o.getPassword() );
        gatherVO.setUserName( o.getUser() );
        return gatherVO;
    }

    /**
     * @param dbVOS
     * @param gatherInfoId
     */
    @Override
    public List<DsgGatherDbRecord> saveDB(List<DbVO> dbVOS, String gatherInfoId) {
        DsgGatherInfoRecord i = this.gatherInfoDao.findById( gatherInfoId );
        List<DsgGatherDbRecord> gatherDbRecords = dbVOS.stream().map(
                o -> {
                    DsgGatherDbRecord dsgGatherDbRecord = new DsgGatherDbRecord();
                    dsgGatherDbRecord.setId( SnowFlake.genId() );
                    dsgGatherDbRecord.setCreateTime( new Timestamp( System.currentTimeMillis() ) );
                    dsgGatherDbRecord.setSchema( o.getName() );
                    dsgGatherDbRecord.setGatherId( gatherInfoId );
                    dsgGatherDbRecord.setDescription( o.getName() );
                    dsgGatherDbRecord.setUpdateTime( new Timestamp( System.currentTimeMillis() ) );
                    return dsgGatherDbRecord;
                }
        ).collect( Collectors.toList() );
        this.dsgGatherDBDao.insert( gatherDbRecords );
        return gatherDbRecords;
    }

    /**
     * @param dbQueryVO
     * @return
     */
    @Override
    public List<DbVO> getAllDB(DbQueryVO dbQueryVO) {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add( DsgGatherDb.DSG_GATHER_DB.GATHER_ID.eq( dbQueryVO.getGatherId() ) );
        if (!StringUtils.isEmpty( dbQueryVO.getName() )) {
            conditions.add( DsgGatherDb.DSG_GATHER_DB.DESCRIPTION.like( "%" + dbQueryVO.getName() + "%" ) );
        }
        List<DsgGatherDbRecord> dbRecords = dsgGatherDBDao.findByConditions( conditions );
        return dbRecords.stream()
                .map(
                        o -> {
                            DbVO dbVO = new DbVO();
                            dbVO.setName( o.getSchema() );
                            dbVO.setGatherId( dbQueryVO.getGatherId() );
                            dbVO.setId( o.getId() );
                            return dbVO;
                        }
                ).collect( Collectors.toList() );
    }

    /**
     * @param dbId
     */
    private void deleteTable(String dbId) {
        List<DsgGatherTableRecord> records = this.dsgGatherTableDao.findByCondition( DsgGatherTable.DSG_GATHER_TABLE.GATHER_DB_ID.eq( dbId ) );
        for (DsgGatherTableRecord record : records) {
            deleteTableField( record.getId() );
            this.dsgGatherTableDao.delete( record );
        }
    }

    /**
     * @param tableId
     */
    private void deleteTableField(String tableId) {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add( DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS.GATHER_TABLE_ID.eq( tableId ) );
        this.dsgGatherTableFieldsDao.deleteByConditions( conditions );
    }

    /**
     * @param tableVOS
     * @param dbId
     */
    @Override
    public List<DsgGatherTableRecord> saveTable(List<TableVO> tableVOS, String dbId) {
        List<DsgGatherTableRecord> dsgGatherTableRecords = tableVOS.stream().map(
                o -> {
                    DsgGatherTableRecord tableRecord = new DsgGatherTableRecord();
                    tableRecord.setId( SnowFlake.genId() );
                    tableRecord.setCreateTime( new Timestamp( System.currentTimeMillis() ) );
                    tableRecord.setUpdateTime( new Timestamp( System.currentTimeMillis() ) );
                    tableRecord.setGatherDbId( dbId );
                    tableRecord.setTableName( o.getName() );
                    tableRecord.setComment( o.getName() + "" + o.getTable() );
                    tableRecord.setTable_( o.getTable() );
                    return tableRecord;
                }
        ).collect( Collectors.toList() );
        this.dsgGatherTableDao.insert(
                dsgGatherTableRecords
        );
        return dsgGatherTableRecords;
    }

    /**
     * @param tableQueryVO
     * @return
     */
    @Override
    public List<TableVO> getTable(TableQueryVO tableQueryVO) {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add( DsgGatherTable.DSG_GATHER_TABLE.GATHER_DB_ID.eq( tableQueryVO.getDbId() ) );
        if (!StringUtils.isEmpty( tableQueryVO.getName() )) {
            conditions.add( DsgGatherTable.DSG_GATHER_TABLE.TABLE_NAME.like( "%" + tableQueryVO.getName() + "%" ) );
        }
        if (!StringUtils.isEmpty( tableQueryVO.getTableId() )) {
            conditions.add( DsgGatherTable.DSG_GATHER_TABLE.ID.eq( tableQueryVO.getTableId() ) );
        }
        List<DsgGatherTableRecord> records = this.dsgGatherTableDao.findByConditions( conditions );
        return records.stream().map(
                o -> {
                    TableVO tableVO = new TableVO();
                    tableVO.setTable( o.getTable_() );
                    tableVO.setName( o.getTableName() );
                    tableVO.setDbId( tableQueryVO.getDbId() );
                    tableVO.setId( o.getId() );
                    return tableVO;
                }
        ).collect( Collectors.toList() );
    }


    /**
     * @param tableFieldsQueryVO
     * @return
     */
    @Override
    public List<TableFieldsVO> getTableFileds(TableFieldsQueryVO tableFieldsQueryVO) {
        String tableId = tableFieldsQueryVO.getTableId();

        List<Condition> conditions = Lists.newArrayList();
        conditions.add( DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS.GATHER_TABLE_ID.eq( tableId ) );
        if (!StringUtils.isEmpty( tableFieldsQueryVO.getField() )) {
            conditions.add( DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS.ID.eq( tableFieldsQueryVO.getField() ) );
        }
        if (!StringUtils.isEmpty( tableFieldsQueryVO.getDescription() )) {
            conditions.add( DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS.FIELD_NAME.like( "%" + tableFieldsQueryVO.getDescription() + "%" ) );
        }

        List<DsgGatherTableFieldsRecord> records = this.dsgGatherTableFieldsDao.findByConditions( conditions );
        return records.stream().map(
                o -> {
                    TableFieldsVO vo = new TableFieldsVO();
                    vo.setField( o.getId() );
                    vo.setTableId( tableId );
                    vo.setDescription( o.getColumnName() );
                    vo.setDataType( vo.getDataType() );
                    vo.setDefaultValue( vo.getDefaultValue() );
                    return vo;
                }
        ).collect( Collectors.toList() );
    }

    /**
     * @param fieldsVOS
     * @param tableId
     */
    @Override
    public List<DsgGatherTableFieldsRecord> saveTableFields(List<TableFieldsVO> fieldsVOS, String tableId) {
        List<DsgGatherTableFieldsRecord> dsgGatherTableFieldsRecords = fieldsVOS.stream().map(
                o -> {
                    DsgGatherTableFieldsRecord fieldsRecord = new DsgGatherTableFieldsRecord();
                    fieldsRecord.setId( SnowFlake.genId() );
                    fieldsRecord.setGatherTableId( tableId );
                    fieldsRecord.setDataType( o.getDataType() );
                    fieldsRecord.setFieldName( o.getField() );
                    fieldsRecord.setDefaultValue( o.getDefaultValue() );
                    fieldsRecord.setColumnName( o.getField() );
                    fieldsRecord.setCreateTime( new Timestamp( System.currentTimeMillis() ) );
                    fieldsRecord.setUpdateTime( new Timestamp( System.currentTimeMillis() ) );
                    return fieldsRecord;
                }
        ).collect( Collectors.toList() );
        this.dsgGatherTableFieldsDao.insert(
                dsgGatherTableFieldsRecords
        );
        return dsgGatherTableFieldsRecords;
    }


    /**
     * @param gatherId
     */
    @Override
    public void deleteAllDataGatherInfoId(String gatherId) {
        List<DsgGatherDbRecord> dbRecords = dsgGatherDBDao.findByCondition( DsgGatherDb.DSG_GATHER_DB.GATHER_ID.eq( gatherId ) );
        for (DsgGatherDbRecord dbRecord : dbRecords) {
            deleteTable( dbRecord.getId() );
            this.dsgGatherDBDao.delete( dbRecord );
        }
    }

    /**
     * 保存
     *
     * @param gatherDBTableFieldsVO
     */
    @Override
    public void saveGather(GatherDBTableFieldsVO gatherDBTableFieldsVO) {
        String gatherId = gatherDBTableFieldsVO.getGratherid();
        deleteAllDataGatherInfoId( gatherId );
        for (GatherDBVO dbvo : gatherDBTableFieldsVO.getGatherDBVOS()) {
            logger.info( "" + dbvo.getDbVO().getName() );
            loggerHelper.info(dbvo.getDbVO().getName());
            List<DsgGatherDbRecord> dsgGatherDbRecords = this.saveDB( Collections.singletonList( dbvo.getDbVO() ), gatherId );
            for (GatherTableVO gatherTableVO : dbvo.getGatherTableVOS()) {
                logger.info( "\t\t\t" + gatherTableVO.getTableVO().getTable() + " " + gatherTableVO.getTableVO().getName() );
                loggerHelper.info( "\t\t\t" + gatherTableVO.getTableVO().getTable() + " " + gatherTableVO.getTableVO().getName());
                List<DsgGatherTableRecord> tableRecords =
                        this.saveTable( Collections.singletonList( gatherTableVO.getTableVO() ), dsgGatherDbRecords.get( 0 ).getId() );
                this.saveTableFields( gatherTableVO.getTableFieldsVOS(), tableRecords.get( 0 ).getId() );
            }
        }

    }

    /**
     * @param queryGatherDBTableFieldsVO
     * @return
     */
    @Override
    public GatherDBTableFieldsVO getDBTableAndFields(QueryGatherDBTableFieldsVO queryGatherDBTableFieldsVO) {
        ThreadUtils.setTaskId( queryGatherDBTableFieldsVO.getGratherid() );
        GatherDBTableFieldsVO gatherDBTableFieldsVO = new GatherDBTableFieldsVO();
        List<GatherDBVO> gatherDBVOS = Lists.newArrayList();
        List<DbVO> dbVOS = this.getAllDB( new DbQueryVO( queryGatherDBTableFieldsVO.getDb(), queryGatherDBTableFieldsVO.getGratherid() ) );
        for (DbVO dbVO : dbVOS) {
            dbVO.setGatherId( queryGatherDBTableFieldsVO.getGratherid() );
            TableQueryVO queryVO = new TableQueryVO( dbVO.getId(), queryGatherDBTableFieldsVO.getTableId(), queryGatherDBTableFieldsVO.getTable() );
            List<TableVO> tableVOS = this.getTable( queryVO );
            List<GatherTableVO> gatherTableVOS = Lists.newArrayList();
            for (TableVO tableVO : tableVOS) {
                GatherTableVO gatherTableVO = new GatherTableVO();
                List<TableFieldsVO> tableFieldsVOS =
                        this.getTableFileds( new TableFieldsQueryVO( tableVO.getId(), queryGatherDBTableFieldsVO.getFields(), queryGatherDBTableFieldsVO.getFieldsDes() ) );
                gatherTableVO.setTableVO( tableVO );
                gatherTableVO.setTableFieldsVOS( tableFieldsVOS );
                gatherTableVOS.add( gatherTableVO );
            }
            GatherDBVO gatherDBVO = new GatherDBVO();
            gatherDBVO.setDbVO( dbVO );
            gatherDBVO.setGatherTableVOS( gatherTableVOS );
            gatherDBVOS.add( gatherDBVO );
        }
        gatherDBTableFieldsVO.setGatherDBVOS( gatherDBVOS );
        gatherDBTableFieldsVO.setGratherid( queryGatherDBTableFieldsVO.getGratherid() );
        return gatherDBTableFieldsVO;
    }
}
