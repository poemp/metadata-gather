package org.poem.dao.gather;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.poem.dao.BaseDaoImpl;
import org.poem.entities.tables.DsgGatherInfo;
import org.poem.entities.tables.records.DsgGatherInfoRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public class GatherInfoDao extends BaseDaoImpl<DsgGatherInfoRecord, String> {

    @Autowired
    private DSLContext dslContext;

    public GatherInfoDao() {
        super( DsgGatherInfo.DSG_GATHER_INFO );
    }

    @Override
    public Configuration getConfiguration() {
        return dslContext.configuration();
    }
}
