package com.framnes.chessstats.model;

import com.framnes.chessstats.util.GameKeyGenerator;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.MoveConversionException;

public class ChessGame {

    private int id;
    private GameSite gameSite;

    // The game key is a hash of the PGN (sans tags and whitespace)
    private String gameKey;

    private GameType gameType;
    private int whitePlayerId;
    private int blackPlayerId;
    private GameResult gameResult;

    public ChessGame() {}

    public ChessGame(Game game, int whitePlayerId, int blackPlayerId) throws MoveConversionException {

        this.gameKey = GameKeyGenerator.generateKey(game);
        this.gameSite = GameSite.fromTagValue(game.getRound().getEvent().getSite());
        this.gameType = GameType.fromTagValue(game.getRound().getEvent().getTimeControl().toPGNString());
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;
        this.gameResult = GameResult.fromTagValue(game.getResult().getDescription());

    }

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

    public int getWhitePlayerId() {
        return whitePlayerId;
    }

    public void setWhitePlayerId(int whitePlayer) {
        this.whitePlayerId = whitePlayerId;
    }

    public int getBlackPlayerId() {
        return blackPlayerId;
    }

    public void setBlackPlayerId(int blackPlayer) {
        this.blackPlayerId = blackPlayerId;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

}
