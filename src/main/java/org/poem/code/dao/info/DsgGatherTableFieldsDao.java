package org.poem.code.dao.info;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.poem.code.dao.BaseDaoImpl;
import org.poem.code.entities.tables.DsgGatherTableFields;
import org.poem.code.entities.tables.records.DsgGatherTableFieldsRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DsgGatherTableFieldsDao extends BaseDaoImpl<DsgGatherTableFieldsRecord, String> {


    @Autowired
    private DSLContext dslContext;

    public DsgGatherTableFieldsDao() {
        super( DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS);
    }

    @Override
    public Configuration getConfiguration() {
        return dslContext.configuration();
    }
}
