package org.poem.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
/**
 * @author Administrator
 */
@Data
@ApiModel("数据库表信息")
public class GatherDBVO {

    @ApiModelProperty("数据库")
    private  DbVO dbVO;

    @ApiModelProperty("表")
    List<GatherTableVO> gatherTableVOS;
}
