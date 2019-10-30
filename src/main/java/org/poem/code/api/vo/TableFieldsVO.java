package org.poem.code.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel("数据表列")
public class TableFieldsVO {

    @ApiModelProperty("表id")
    private String tableId;

    @ApiModelProperty("列id")
    private String field;

    @ApiModelProperty("列的数据类型")
    private String dataType;

    @ApiModelProperty("表的描述")
    private String description;

    @ApiModelProperty("默认值")
    private String defaultValue;
}
