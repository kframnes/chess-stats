package com.framnes.chessstats.model;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum GameType {

    BLITZ("180", "180+2", "300", "300+3", "300+5"),
    RAPID("900+10", "600"),
    CLASSICAL(),
    UNKNOWN();

    private Set<String> values;

    GameType(String ... values) {
        this.values = ImmutableSet.<String>builder().add(values).build();
    }

    public static GameType fromTagValue(String value) {
        for (GameType type : values()) {
            if (type.values.contains(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }



}
