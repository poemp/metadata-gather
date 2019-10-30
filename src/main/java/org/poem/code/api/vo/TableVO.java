package org.poem.code.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel("数据表")
public class TableVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("库id")
    private String dbId;

    @ApiModelProperty("表名字")
    private String table;

    @ApiModelProperty("表的中文注释名字")
    private String name;
}
