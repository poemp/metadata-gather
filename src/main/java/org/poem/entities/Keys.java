/*
 * This file is generated by jOOQ.
 */
package org.poem.entities;


import org.jooq.UniqueKey;
import org.jooq.impl.Internal;
import org.poem.entities.tables.DsgGatherDb;
import org.poem.entities.tables.DsgGatherInfo;
import org.poem.entities.tables.DsgGatherTable;
import org.poem.entities.tables.DsgGatherTableFields;
import org.poem.entities.tables.records.DsgGatherDbRecord;
import org.poem.entities.tables.records.DsgGatherInfoRecord;
import org.poem.entities.tables.records.DsgGatherTableFieldsRecord;
import org.poem.entities.tables.records.DsgGatherTableRecord;

import javax.annotation.Generated;


/**
 * A class modelling foreign key relationships and constraints of tables of
 * the <code>kylo</code> schema.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.11.9"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DsgGatherDbRecord> KEY_DSG_GATHER_DB_PRIMARY = UniqueKeys0.KEY_DSG_GATHER_DB_PRIMARY;
    public static final UniqueKey<DsgGatherInfoRecord> KEY_DSG_GATHER_INFO_PRIMARY = UniqueKeys0.KEY_DSG_GATHER_INFO_PRIMARY;
    public static final UniqueKey<DsgGatherTableRecord> KEY_DSG_GATHER_TABLE_PRIMARY = UniqueKeys0.KEY_DSG_GATHER_TABLE_PRIMARY;
    public static final UniqueKey<DsgGatherTableFieldsRecord> KEY_DSG_GATHER_TABLE_FIELDS_PRIMARY = UniqueKeys0.KEY_DSG_GATHER_TABLE_FIELDS_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 {
        public static final UniqueKey<DsgGatherDbRecord> KEY_DSG_GATHER_DB_PRIMARY = Internal.createUniqueKey( DsgGatherDb.DSG_GATHER_DB, "KEY_dsg_gather_db_PRIMARY", DsgGatherDb.DSG_GATHER_DB.ID );
        public static final UniqueKey<DsgGatherInfoRecord> KEY_DSG_GATHER_INFO_PRIMARY = Internal.createUniqueKey( DsgGatherInfo.DSG_GATHER_INFO, "KEY_dsg_gather_info_PRIMARY", DsgGatherInfo.DSG_GATHER_INFO.ID );
        public static final UniqueKey<DsgGatherTableRecord> KEY_DSG_GATHER_TABLE_PRIMARY = Internal.createUniqueKey( DsgGatherTable.DSG_GATHER_TABLE, "KEY_dsg_gather_table_PRIMARY", DsgGatherTable.DSG_GATHER_TABLE.ID );
        public static final UniqueKey<DsgGatherTableFieldsRecord> KEY_DSG_GATHER_TABLE_FIELDS_PRIMARY = Internal.createUniqueKey( DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS, "KEY_dsg_gather_table_fields_PRIMARY", DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS.ID );
    }
}
