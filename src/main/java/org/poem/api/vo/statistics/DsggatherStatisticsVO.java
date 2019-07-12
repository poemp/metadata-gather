package org.poem.api.vo.statistics;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class DsggatherStatisticsVO {

    String gatherId;

    private  Integer dbCount;

    private Integer tableCount;

    private Integer fieldCount ;


    public DsggatherStatisticsVO(){
        this.dbCount = 0;
        this.tableCount = 0;
        this.fieldCount = 0;
    }

    public DsggatherStatisticsVO(String gatherId, Integer dbCount, Integer tableCount, Integer fieldCount) {
        this.gatherId = gatherId;
        this.dbCount = dbCount;
        this.tableCount = tableCount;
        this.fieldCount = fieldCount;
    }
}
