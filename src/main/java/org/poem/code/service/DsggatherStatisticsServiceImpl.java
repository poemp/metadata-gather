package org.poem.code.service;

import org.apache.commons.collections.CollectionUtils;
import org.poem.code.api.DsggatherStatisticsService;
import org.poem.code.api.vo.statistics.DsggatherStatisticsVO;
import org.poem.code.dao.statistics.DsggatherStatisticsDao;
import org.poem.code.entities.tables.DsgGatherStatistics;
import org.poem.code.entities.tables.records.DsgGatherStatisticsRecord;
import org.poem.utils.SnowFlake;
import org.poem.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 统计信息
 * @author Administrator
 */
@Service
public class DsggatherStatisticsServiceImpl implements DsggatherStatisticsService {

    private static final Logger logger = LoggerFactory.getLogger( DsggatherStatisticsServiceImpl.class );


    @Autowired
    private DsggatherStatisticsDao dsggatherStatisticsDao;


    /**
     *
     */
    private static final ExecutorService EXECUTE_SERVICE;

    /**
     *
     */
    static {
        logger.info( "Begin Init JDBC Thread Pool. " );
        int procsssors = Runtime.getRuntime().availableProcessors();
        final AtomicInteger threadNumber = new AtomicInteger( 0 );
        EXECUTE_SERVICE = new ThreadPoolExecutor( procsssors * 2, Integer.MAX_VALUE, 7, TimeUnit.DAYS,
                new LinkedBlockingDeque<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread( r );
                t.setName( "gather-" + threadNumber.getAndIncrement() );
                t.setUncaughtExceptionHandler( (th, thr) -> {
                    logger.error( th.getName() + " uncaught: " + thr.getClass().getName() );
                    thr.printStackTrace();
                } );
                return t;
            }
        } );
    }

    /**
     * 保存数据
     * 保存
     */
    @Override
    public void saveCurrent(DsggatherStatisticsVO dsggatherStatisticsVO){
        EXECUTE_SERVICE.submit( new Runnable() {
            @Override
            public void run() {
                ThreadUtils.setTaskId( dsggatherStatisticsVO.getGatherId() );
                save( dsggatherStatisticsVO );
            }
        } );
    }

    /**
     * @param dsggatherStatisticsVO
     */
    @Override
    public synchronized void  save(DsggatherStatisticsVO dsggatherStatisticsVO) {
        List<DsgGatherStatisticsRecord> records =
                dsggatherStatisticsDao.findByCondition( DsgGatherStatistics.DSG_GATHER_STATISTICS.GATHER_INFO_ID.eq( dsggatherStatisticsVO.getGatherId() ) );
        DsgGatherStatisticsRecord dsgGatherStatisticsRecord;
        if (!CollectionUtils.isEmpty( records )) {
            dsgGatherStatisticsRecord = records.get( 0 );
        } else {
            dsgGatherStatisticsRecord = new DsgGatherStatisticsRecord();
            dsgGatherStatisticsRecord.setId( SnowFlake.genId() );
            dsgGatherStatisticsRecord.setCreateTime( new Timestamp( System.currentTimeMillis() ) );
            dsgGatherStatisticsRecord.setGatherDbCount( 0 );
            dsgGatherStatisticsRecord.setGatherFieldCount( 0 );
            dsgGatherStatisticsRecord.setGatherTableCount( 0 );
            dsgGatherStatisticsRecord.setGatherInfoId( dsggatherStatisticsVO.getGatherId() );
        }
        dsgGatherStatisticsRecord.setUpdateTime( new Timestamp( System.currentTimeMillis() ) );
        dsgGatherStatisticsRecord.setGatherTableCount( dsgGatherStatisticsRecord.getGatherTableCount() + dsggatherStatisticsVO.getTableCount() );
        dsgGatherStatisticsRecord.setGatherDbCount( dsgGatherStatisticsRecord.getGatherDbCount() + dsggatherStatisticsVO.getDbCount() );
        dsgGatherStatisticsRecord.setGatherFieldCount( dsgGatherStatisticsRecord.getGatherFieldCount() + dsggatherStatisticsVO.getFieldCount() );
        dsgGatherStatisticsRecord.setUpdateTime( new Timestamp( System.currentTimeMillis() ) );
        if (CollectionUtils.isEmpty( records )) {
            this.dsggatherStatisticsDao.insert( dsgGatherStatisticsRecord );
        } else {
            this.dsggatherStatisticsDao.update( dsgGatherStatisticsRecord );
        }
    }

    /**
     * @return
     */
    @Override
    public List<DsggatherStatisticsVO> query() {
        List<DsgGatherStatisticsRecord> records =
                dsggatherStatisticsDao.findAll();
        return records.stream().map(
                o ->{
                    DsggatherStatisticsVO dsggatherStatisticsVO = new DsggatherStatisticsVO();
                    dsggatherStatisticsVO.setDbCount( o.getGatherDbCount());
                    dsggatherStatisticsVO.setTableCount( o.getGatherTableCount() );
                    dsggatherStatisticsVO.setFieldCount( o.getGatherFieldCount() );
                    return dsggatherStatisticsVO;
                }
        ).collect( Collectors.toList());

    }
}
