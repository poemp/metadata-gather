package org.poem.code.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("数据表和列")
public class GatherTableVO {

    @ApiModelProperty("数据表")
    private TableVO tableVO;

    @ApiModelProperty("数据表列")
    private List<TableFieldsVO> tableFieldsVOS;
}
