package org.poem.service.connect;


/**
 * @author Administrator
 */

public enum DataType {
    /**
     * mysql
     */
    MYSQL( "MYSQL" ),
    /**
     * orcale
     */
    ORCALE( "ORCALE" );

    private String name;

    private DataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean eq(DataType dataType) {
        if (dataType == null) {
            return false;
        }
        String name = dataType.getName();
        return name.equals( this.name );
    }
}
