package org.poem.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel("数据库")
public class DbVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("数据库名字")
    private String name;

    @ApiModelProperty("连接信息id")
    private String gatherId;
}
