package com.framnes.chessstats.engine;

public class EngineMove {

    private EngineMove checkmate = new EngineMove();

    private String move;
    private int moveEvaluation;
    private boolean isMating;
    private Integer mateIn;

    /**
     * Construct an {@code EngineMove} with an evaluation, but no mate.
     *
     * @param move - engine move
     * @param eval - evaluation in centipawns
     * @return an initialized {@code EngineMove}
     */
    public static EngineMove withEval(String move, int eval) {

        EngineMove eMove = new EngineMove();
        eMove.move = move;
        eMove.isMating = false;
        eMove.mateIn = null;
        eMove.moveEvaluation = eval;

        return eMove;

    }

    /**
     * Construct an {@code EngineMove} with a mating line.
     *
     * @param move - engine move
     * @param mateIn - number of moves to mate with best play
     * @return an initialized {@code EngineMove}
     */
    public static EngineMove withMate(String move, int mateIn) {

        EngineMove eMove = new EngineMove();
        eMove.move = move;
        eMove.isMating = true;
        eMove.mateIn = mateIn;
        eMove.moveEvaluation = (32767 - 1000 * Math.abs(mateIn)) * (mateIn < 0 ? -1 : 1);

        return eMove;

    }

    /**
     * @return the checkmate representation of {@code EngineMove}
     */
    public static EngineMove positionCheckmate() {

        EngineMove eMove = new EngineMove();
        eMove.move = "#";
        eMove.isMating = true;
        eMove.mateIn = 0;
        eMove.moveEvaluation = Integer.MAX_VALUE;

        return eMove;

    }

    public EngineMove getCheckmate() {
        return checkmate;
    }

    public String getMove() {
        return move;
    }

    public int getMoveEvaluation() {
        return moveEvaluation;
    }

    public boolean isMating() {
        return isMating;
    }

    public Integer getMateIn() {
        return mateIn;
    }

}
