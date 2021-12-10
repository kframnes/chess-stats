package com.framnes.chessstats.model;

public class Game {

    private int id;
    private GameSite gameSite;

    // The game key is a hash of the PGN (sans tags and whitespace)
    private String gameKey;

    private GameType gameType;
    private String whitePlayer;
    private String blackPlayer;
    private Integer whiteElo;
    private Integer blackElo;
    private GameResult gameResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameSite getGameSite() {
        return gameSite;
    }

    public void setGameSite(GameSite gameSite) {
        this.gameSite = gameSite;
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Integer getWhiteElo() {
        return whiteElo;
    }

    public void setWhiteElo(Integer whiteElo) {
        this.whiteElo = whiteElo;
    }

    public Integer getBlackElo() {
        return blackElo;
    }

    public void setBlackElo(Integer blackElo) {
        this.blackElo = blackElo;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

}
