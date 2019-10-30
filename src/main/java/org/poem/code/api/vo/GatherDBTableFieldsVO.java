package org.poem.code.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("连接信息")
public class GatherDBTableFieldsVO {

    @ApiModelProperty("连接信息Id")
    private String gratherid;

    @ApiModelProperty("数据库信息")
    private List<GatherDBVO> gatherDBVOS;
}
