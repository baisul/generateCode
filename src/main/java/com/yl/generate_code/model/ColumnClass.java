package com.yl.generate_code.model;

import java.io.Serializable;

public class ColumnClass implements Serializable {
    //实体类属性名
    private String propertyName;
    //实体类属性名对应的表的字段名
    private String columnName;
    //字段类型
    private String type;
    //备注
    private String remark;
    //该字段是否为主键
    private Boolean isPrimary;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    @Override
    public String toString() {
        return "ColumnClass{" +
                "propertyName='" + propertyName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
    }
}
