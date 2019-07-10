package org.poem.api.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@ApiModel("数据库")
@NoArgsConstructor
public class DbQueryVO {

    @ApiModelProperty("数据库名字")
    private String name;

    @ApiModelProperty("连接信息id")
    private String gatherId;

    public DbQueryVO(String name, String gatherId) {
        this.name = name;
        this.gatherId = gatherId;
    }
}
