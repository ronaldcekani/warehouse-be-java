package com.example.warehousemanagement.core.graphql;

import com.example.warehousemanagement.core.enums.SortDirectionEnum;

public class SortExp {
    private String field;
    private SortDirectionEnum direction;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortDirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(SortDirectionEnum direction) {
        this.direction = direction;
    }
}
