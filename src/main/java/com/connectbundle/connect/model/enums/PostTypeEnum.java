package com.connectbundle.connect.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PostTypeEnum {
    EVENT, PUBLICATION, HACKATHON, PROJECT , ALL;

    @JsonCreator
    public static PostTypeEnum fromString(String value) {
        try {
            return PostTypeEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
