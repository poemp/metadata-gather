package org.poem.code.api.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@ApiModel("数据表列")
@NoArgsConstructor
public class TableFieldsQueryVO {

    @ApiModelProperty("表id")
    private String tableId;

    @ApiModelProperty("列id")
    private String field;

    @ApiModelProperty("表的描述")
    private String description;

    public TableFieldsQueryVO(String tableId, String field, String description) {
        this.tableId = tableId;
        this.field = field;
        this.description = description;
    }
}
