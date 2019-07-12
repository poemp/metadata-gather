package org.poem.dao.statistics;


import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.poem.dao.BaseDaoImpl;
import org.poem.entities.tables.DsgGatherStatistics;
import org.poem.entities.tables.records.DsgGatherStatisticsRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public class DsggatherStatisticsDao extends BaseDaoImpl<DsgGatherStatisticsRecord, String> {


    @Autowired
    private DSLContext dslContext;

    public DsggatherStatisticsDao() {
        super( DsgGatherStatistics.DSG_GATHER_STATISTICS );
    }

    @Override
    public Configuration getConfiguration() {
        return dslContext.configuration();
    }
}
