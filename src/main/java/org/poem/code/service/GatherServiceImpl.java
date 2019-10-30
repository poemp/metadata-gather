package org.poem.code.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.poem.code.api.DsggatherStatisticsService;
import org.poem.code.api.GatherService;
import org.poem.code.api.vo.*;
import org.poem.code.api.vo.column.entity.DataSetVO;
import org.poem.code.api.vo.databases.entity.DateBaseEntity;
import org.poem.code.api.vo.statistics.DsggatherStatisticsVO;
import org.poem.code.api.vo.table.entity.TableEntity;
import org.poem.code.dao.gather.GatherInfoDao;
import org.poem.code.dao.info.DsgGatherDBDao;
import org.poem.code.dao.info.DsgGatherTableDao;
import org.poem.code.entities.tables.records.DsgGatherDbRecord;
import org.poem.code.entities.tables.records.DsgGatherInfoRecord;
import org.poem.code.entities.tables.records.DsgGatherTableRecord;
import org.poem.code.service.connect.DataType;
import org.poem.code.service.connect.GatherBuilder;
import org.poem.code.service.connect.GatherConnection;
import org.poem.code.service.databases.GatherDataBaseInter;
import org.poem.utils.GatherDataSourceUtils;
import org.poem.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class GatherServiceImpl implements GatherService {

    private static final Logger logger = LoggerFactory.getLogger( GatherServiceImpl.class );

    @Autowired
    private GatherInfoDao gatherDao;

    @Autowired
    private DsgGatherDBDao dsgGatherDBDao;

    @Autowired
    private DsgGatherTableDao dsgGatherTableDao;


    @Autowired
    private DsggatherStatisticsService dsggatherStatisticsService;

    /**
     * @param gatherId
     * @return
     */
    @Override
    public boolean testGather(String gatherId) {
        GatherConnection c = getGatherConnection( gatherId );
        String testSql= "select 1 ";
        try {
            return  c.getConnection().createStatement().execute(testSql);
        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取连接
     *
     * @param gatherId
     * @return
     */
    private GatherConnection getGatherConnection(String gatherId) {
        DsgGatherInfoRecord infoRecord = this.gatherDao.findById( gatherId );
        if (infoRecord == null) {
            throw new IllegalArgumentException( "this entity[" + gatherId + "] is not exist." );
        }
        ThreadUtils.setDataTypeThreadLocal( DataType.valueOf( infoRecord.getType() ) );
        return new GatherBuilder()
                .ip( infoRecord.getIp() )
                .password( infoRecord.getPassword() )
                .port( infoRecord.getPort() )
                .serverName( infoRecord.getServiceName() )
                .user( infoRecord.getUser() ).dataType( DataType.valueOf( infoRecord.getType() ) )
                .budder();
    }

    /**
     * @param gatherId
     * @return
     */
    @Override
    public List<DbVO> getSchema(String gatherId) {
        GatherConnection connection = this.getGatherConnection( gatherId );
        GatherDataBaseInter gatherDataBaseInter = GatherDataSourceUtils.getBean();
        List<DateBaseEntity> dateBaseEntities = gatherDataBaseInter.getDataBases( connection.getConnection() );
        try {
            connection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dateBaseEntities.stream().map(
                o -> {
                    dsggatherStatisticsService.saveCurrent( new DsggatherStatisticsVO( gatherId, 1, 0, 0 ) );
                    DbVO dbVO = new DbVO();
                    dbVO.setGatherId( gatherId );
                    dbVO.setName( o.getDatabaseName() );
                    return dbVO;
                }
        ).collect( Collectors.toList() );
    }

    /**
     * 获取数据库的表
     *
     * @param gatherVO
     * @return
     */
    @Override
    public List<TableVO> getTable(GatherVO gatherVO) {
        GatherConnection connection = this.getGatherConnection( gatherVO.getGatherId() );
        DsgGatherDbRecord dsgGatherDbRecord = this.dsgGatherDBDao.findById( gatherVO.getDbId() );
        if (dsgGatherDbRecord == null) {
            throw new NullPointerException( "entity DsgGatherDbRecord[" + gatherVO.getDbId() + "] is null !!!!!!" );
        }
        GatherDataBaseInter gatherDataBaseInter = GatherDataSourceUtils.getBean();
        List<TableEntity> tableEntities = gatherDataBaseInter
                .getAllTableName( dsgGatherDbRecord.getSchema(), connection.getConnection() );
        return tableEntities.stream().map(
                o -> {
                    dsggatherStatisticsService.saveCurrent( new DsggatherStatisticsVO( gatherVO.getGatherId(), 0, 1, 0 ) );
                    TableVO tableVO = new TableVO();
                    tableVO.setDbId( gatherVO.getId() );
                    tableVO.setName( o.getComment() );
                    tableVO.setTable( o.getTableName() );
                    return tableVO;
                }
        ).collect( Collectors.toList() );
    }


    /**
     * @param table
     * @return
     */
    @Override
    public TableVO getTableById(String table) {
        DsgGatherTableRecord record = this.dsgGatherTableDao.findById( table );
        TableVO tableVO = new TableVO();
        tableVO.setDbId( record.getGatherDbId() );
        tableVO.setName( record.getTableName() );
        tableVO.setTable( record.getTable_() );
        tableVO.setId( record.getId() );
        return tableVO;
    }

    /**
     * @param tableVO
     * @return
     */
    @Override
    public List<TableFieldsVO> getTableFieles(TableVO tableVO) {
        DsgGatherDbRecord dbRecord = this.dsgGatherDBDao.findById( tableVO.getDbId() );
        if (dbRecord == null) {
            throw new NullPointerException( "entity DsgGatherDbRecord[" + tableVO.getDbId() + "] is null !!!!!!" );
        }
        DsgGatherTableRecord tableRecord = dsgGatherTableDao.findById( tableVO.getId() );
        GatherConnection connection = this.getGatherConnection( dbRecord.getGatherId() );
        DsgGatherDbRecord dsgGatherDbRecord = this.dsgGatherDBDao.findById( tableVO.getDbId() );
        if (dsgGatherDbRecord == null) {
            throw new NullPointerException( "entity DsgGatherDbRecord[" + tableVO.getDbId() + "] is null !!!!!!" );
        }
        GatherDataBaseInter gatherDataBaseInter = GatherDataSourceUtils.getBean();
        DataSetVO setVO = gatherDataBaseInter
                .getTaleFields( dbRecord.getSchema(), tableRecord.getTable_(), connection.getConnection() );
        try {
            connection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return setVO.getDatas().stream().map(
                list -> {
                    dsggatherStatisticsService.saveCurrent( new DsggatherStatisticsVO( dbRecord.getGatherId(), 0, 0, 1 ) );
                    String columnName = String.valueOf( list.get( 0 ) );
                    String type = String.valueOf( list.get( 1 ) );
                    String name = String.valueOf( list.get( 2 ) );
                    TableFieldsVO fieldsVO = new TableFieldsVO();
                    fieldsVO.setDataType( type );
                    fieldsVO.setDescription( name );
                    fieldsVO.setTableId( tableVO.getId() );
                    fieldsVO.setField( columnName );
                    return fieldsVO;
                }
        ).collect( Collectors.toList() );
    }

    /**
     * @return
     */
    @Override
    public GatherDBTableFieldsVO getAllGatherDBTableFieldsVO(String gatherId, String db, String table) {
        GatherDBTableFieldsVO gatherDBTableFieldsVO = new GatherDBTableFieldsVO();
        GatherConnection n = this.getGatherConnection( gatherId );
        Connection connection = n.getConnection();
        GatherDataBaseInter gatherDataBaseInter = GatherDataSourceUtils.getBean();
        List<DateBaseEntity> dateBaseEntities = gatherDataBaseInter.getDataBases( connection );
        List<GatherDBVO> gatherDBVOS = Lists.newArrayList();
        for (DateBaseEntity dateBaseEntity : dateBaseEntities) {
            DbVO dbVO = new DbVO();
            dbVO.setGatherId( gatherId );
            dbVO.setName( dateBaseEntity.getDatabaseName() );
            if (!StringUtils.isBlank( db ) && !db.equals( dateBaseEntity.getDatabaseName() )) {
                continue;
            }
            dsggatherStatisticsService.saveCurrent( new DsggatherStatisticsVO( gatherId, 1, 0, 0 ) );
            List<TableEntity> tableEntities = gatherDataBaseInter.getAllTableName( dateBaseEntity.getDatabaseName(), connection );
            List<GatherTableVO> gatherTableVOS = Lists.newArrayList();
            for (TableEntity tableEntity : tableEntities) {
                TableVO tableVO = new TableVO();
                tableVO.setDbId( null );
                tableVO.setName( tableEntity.getComment() );
                tableVO.setTable( tableEntity.getTableName() );
                if (!StringUtils.isBlank( table ) && !table.equals( tableEntity.getTableName() )) {
                    continue;
                }
                dsggatherStatisticsService.saveCurrent( new DsggatherStatisticsVO( gatherId, 0, 1, 0 ) );
                DataSetVO dataSetVO = gatherDataBaseInter.getTaleFields( dateBaseEntity.getDatabaseName(), tableEntity.getTableName(), connection );
                List<TableFieldsVO> tableFieldsVOS = dataSetVO.getDatas().stream().map(
                        list -> {
                            String columnName = String.valueOf( list.get( 0 ) );
                            String type = String.valueOf( list.get( 1 ) );
                            String name = String.valueOf( list.get( 2 ) );
                            TableFieldsVO fieldsVO = new TableFieldsVO();
                            fieldsVO.setDataType( type );
                            fieldsVO.setDescription( name );
                            fieldsVO.setTableId( tableVO.getId() );
                            fieldsVO.setField( columnName );
                            dsggatherStatisticsService.saveCurrent( new DsggatherStatisticsVO( gatherId, 0, 0, 1 ) );
                            return fieldsVO;
                        }
                ).collect( Collectors.toList() );
                GatherTableVO gatherTableVO = new GatherTableVO();
                gatherTableVO.setTableVO( tableVO );
                gatherTableVO.setTableFieldsVOS( tableFieldsVOS );
                gatherTableVOS.add( gatherTableVO );
            }
            GatherDBVO gatherDBVO = new GatherDBVO();
            gatherDBVO.setDbVO( dbVO );
            gatherDBVO.setGatherTableVOS( gatherTableVOS );
            gatherDBVOS.add( gatherDBVO );
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        gatherDBTableFieldsVO.setGatherDBVOS( gatherDBVOS );
        gatherDBTableFieldsVO.setGratherid( gatherId );
        return gatherDBTableFieldsVO;
    }

    /**
     * @param sql      执行的sql
     * @param sourceId source id
     * @return
     */
    @Override
    public DataSetVO executeSQL(String sql, String sourceId) {
        GatherConnection n = this.getGatherConnection( sourceId );
        Connection connection = n.getConnection();
        GatherDataBaseInter gatherDataBaseInter = GatherDataSourceUtils.getBean();
        DataSetVO dataSetVO = gatherDataBaseInter.executeSql( sql, connection );
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSetVO;
    }
}
