package com.framnes.chessstats.engine;

import com.github.bhlangonijr.chesslib.move.Move;

public class EvaluatedPosition {

    private final EngineMove[] engineMoves;
    private final int positionEvaluation;
    private final Integer positionMateIn;

    public EvaluatedPosition(EngineMove[] engineMoves) {
        this.engineMoves = engineMoves;
        this.positionEvaluation = engineMoves[0] != null ? engineMoves[0].getMoveEvaluation() : 0;
        this.positionMateIn = engineMoves[0] != null ? engineMoves[0].getMateIn() : null;
    }

    public int getPositionEvaluation() {
        return positionEvaluation;
    }

    public Integer getPositionMateIn() {
        return positionMateIn;
    }

    public Integer getEngineRanking(Move move) {
        for (int i=0; i<engineMoves.length; i++) {
            if (engineMoves[i].getMove().equals(move.toString())) {
                return i+1;
            }
        }
        return null;
    }

    public boolean isForced(Integer moveRank) {
        if (moveRank == null) return false;
        return false; // TODO <--- this is not right
    }

}
