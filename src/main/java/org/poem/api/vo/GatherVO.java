package org.poem.api.vo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class GatherVO {

    /**
     *
     */
    private String gatherId;
    /**
     * id
     */
    private String id;

    /**
     *
     */
    private String dbId;

    /**
     *
     */
    private String name;
    /**
     * 地址
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据类型
     */
    private String type;

}
