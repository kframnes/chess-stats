package com.framnes.chessstats.model;

public enum GameSite {

    CHESS_COM,
    LICHESS_ORG,
    UNKNOWN;

    public static GameSite fromTagValue(String value) {
        if (value.equals("Chess.com")) {
            return CHESS_COM;
        } else if (value.startsWith("https://lichess.org")) {
            return LICHESS_ORG;
        }
        return UNKNOWN;
    }

}
