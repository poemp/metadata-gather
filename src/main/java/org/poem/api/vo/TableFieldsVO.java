package org.poem.api.vo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class TableFieldsVO {

    private String tableId;

    private String field;

    private String dataType;

    private String description;

    private String defaultValue;
}
