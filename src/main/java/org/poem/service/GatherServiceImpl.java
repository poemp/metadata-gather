package org.poem.service;

import com.google.common.collect.Lists;
import org.poem.api.GatherService;
import org.poem.api.vo.*;
import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.databases.entity.DateBaseEntity;
import org.poem.api.vo.table.entity.TableEntity;
import org.poem.dao.gather.GatherInfoDao;
import org.poem.dao.info.DsgGatherDBDao;
import org.poem.dao.info.DsgGatherTableDao;
import org.poem.entities.tables.records.DsgGatherDbRecord;
import org.poem.entities.tables.records.DsgGatherInfoRecord;
import org.poem.entities.tables.records.DsgGatherTableRecord;
import org.poem.service.connect.DataType;
import org.poem.service.connect.GatherBuilder;
import org.poem.service.connect.GatherConnection;
import org.poem.service.databases.GatherDataBaseInter;
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

    /**
     * @param gatherId
     * @return
     */
    @Override
    public boolean testGather(String gatherId) {
        GatherConnection c = getGatherConnection( gatherId );
        try {
            return c.getConnection().createStatement() != null;
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
        return dateBaseEntities.stream().map(
                o -> {
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
        tableVO.setName( record.getName() );
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
        return setVO.getDatas().stream().map(
                list -> {
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
    public GatherDBTableFieldsVO getAllGatherDBTableFieldsVO(String gatherId) {
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
            List<TableEntity> tableEntities = gatherDataBaseInter.getAllTableName( dateBaseEntity.getDatabaseName(), connection );
            List<GatherTableVO> gatherTableVOS = Lists.newArrayList();
            for (TableEntity tableEntity : tableEntities) {
                TableVO tableVO = new TableVO();
                tableVO.setDbId( null );
                tableVO.setName( tableEntity.getComment() );
                tableVO.setTable( tableEntity.getTableName() );
                DataSetVO dataSetVO = gatherDataBaseInter .getTaleFields( dateBaseEntity.getDatabaseName(), tableEntity.getTableName(), connection );
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
        gatherDBTableFieldsVO.setGatherDBVOS( gatherDBVOS );
        gatherDBTableFieldsVO.setGratherid( gatherId );
        return gatherDBTableFieldsVO;
    }
}
