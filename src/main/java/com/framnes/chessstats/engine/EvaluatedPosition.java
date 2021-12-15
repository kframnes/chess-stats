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

        // We have no ranking, so obviously this wasn't forced.
        if (moveRank == null) return false;

        // If our move rank was the worst move in a position where the engine
        // couldn't find all the moves we asked for, we consider the move forced.
        //
        // Example:
        // Best move gives opponent mate in 4, second and only other option gives
        // mate in 6.  Picking that second option was forced (only other choice
        // was to pick the best move).
        return moveRank < engineMoves.length && engineMoves[moveRank] == null;

    }

}