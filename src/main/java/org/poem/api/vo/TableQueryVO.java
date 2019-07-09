package org.poem.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@NoArgsConstructor
@Data
@ApiModel("数据表")
public class TableQueryVO {

    @ApiModelProperty("库id")
    private String dbId;

    @ApiModelProperty("表名字")
    private String tableId;

    @ApiModelProperty("表的中文注释名字")
    private String name;

    public TableQueryVO(String dbId, String tableId, String name) {
        this.dbId = dbId;
        this.tableId = tableId;
        this.name = name;
    }
}
