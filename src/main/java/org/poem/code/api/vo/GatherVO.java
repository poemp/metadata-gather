package org.poem.code.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel("数据源连接信息")
public class GatherVO {

    /**
     *
     */
    @ApiModelProperty("为空")
    private String gatherId;
    /**
     * id
     */
    @ApiModelProperty("id 新添加任务为空")
    private String id;

    /**
     *
     */
    @ApiModelProperty("为空")
    private String dbId;

    /**
     *
     */
    @ApiModelProperty("连接名字")
    private String name;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String ip;

    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private String port;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 数据类型
     */
    @ApiModelProperty("数据类型 MYSQL ORACLE")
    private String type;

    /**
     * oracle 服务名字
     */
    @ApiModelProperty("oracle 服务名字")
    private String serverName;
}
