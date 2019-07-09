package org.poem.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("连接信息")
public class QueryGatherDBTableFieldsVO {

    @ApiModelProperty("连接信息Id")
    private String gratherid;

    @ApiModelProperty("数据库")
    private String db;

    @ApiModelProperty("数据库id")
    private String dbId;

    @ApiModelProperty("表")
    private String table;

    @ApiModelProperty("表id")
    private String tableId;


    @ApiModelProperty("字端信息")
    private String fields;

    @ApiModelProperty("字端表述")
    private String fieldsDes;



}
