package com.framnes.chessstats.model;

public enum GameResult {
    WHITE_WIN,
    BLACK_WIN,
    DRAW,
    UNKNOWN;

    public static GameResult fromTagValue(String value) {
        if (value.equals("1-0")) {
            return WHITE_WIN;
        } else if (value.equals("0-1")) {
            return BLACK_WIN;
        } else if (value.equals("1/2-1/2")) {
            return DRAW;
        }
        return UNKNOWN;
    }

}
