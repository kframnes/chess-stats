package com.framnes.chessstats.engine;

public class EvaluatedMove {

    private final EngineMove[] engineMoves;
    private final int positionEvaluation;

    public EvaluatedMove(EngineMove[] engineMoves) {
        this.engineMoves = engineMoves;
        this.positionEvaluation = engineMoves[0] != null ? engineMoves[0].getMoveEvaluation() : 0;
    }

}
