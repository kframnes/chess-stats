package com.framnes.chessstats.model;

public class ChessMove {

    private int id;
    private int gameId;
    private Color moveColor;
    private int moveNumber;
    private String move;

    // Engine evaluation of the position before this move was made.
    private int positionEvalBefore;

    // If there was a forced mating line in the position before this
    // move was made (with best play), how many moves away was it?
    private Integer mateInBefore;

    // Engine evaluation of the position after this move is made.
    private int positionEvalAfter;

    // If there is a forced mating line in the position after this
    // move is made (with best play), how many moves away is it?
    private Integer mateInAfter;

    // Of the ranked moves suggested by the engine, which rank is
    // this move.  If the move was not ranked, this will be null.
    private Integer moveEngineRank;

    // Is the move considered forced?  We consider a move forced
    // if it appears as the last engine option with no other legal
    // moves available.  For example, in a position with one legal
    // move it would be pointless to include it as part of T1.
    // Likewise, a position with two legal moves would have no real
    // information on T2.
    private boolean forced;

    // Is this move checkmate?
    private boolean mate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Color getMoveColor() {
        return moveColor;
    }

    public void setMoveColor(Color moveColor) {
        this.moveColor = moveColor;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public int getPositionEvalBefore() {
        return positionEvalBefore;
    }

    public void setPositionEvalBefore(int positionEvalBefore) {
        this.positionEvalBefore = positionEvalBefore;
    }

    public Integer getMateInBefore() {
        return mateInBefore;
    }

    public void setMateInBefore(Integer mateInBefore) {
        this.mateInBefore = mateInBefore;
    }

    public int getPositionEvalAfter() {
        return positionEvalAfter;
    }

    public void setPositionEvalAfter(int positionEvalAfter) {
        this.positionEvalAfter = positionEvalAfter;
    }

    public Integer getMateInAfter() {
        return mateInAfter;
    }

    public void setMateInAfter(Integer mateInAfter) {
        this.mateInAfter = mateInAfter;
    }

    public Integer getMoveEngineRank() {
        return moveEngineRank;
    }

    public void setMoveEngineRank(Integer moveEngineRank) {
        this.moveEngineRank = moveEngineRank;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public boolean isMate() {
        return mate;
    }

    public void setMate(boolean mate) {
        this.mate = mate;
    }

}
