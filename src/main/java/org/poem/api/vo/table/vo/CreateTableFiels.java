package org.poem.api.vo.table.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateTableFiels {

    private String field;

    private String type;

    private String content;

}
