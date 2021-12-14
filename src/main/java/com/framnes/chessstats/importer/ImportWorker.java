package com.framnes.chessstats.importer;

import com.framnes.chessstats.console.ImportConsole;
import com.framnes.chessstats.dao.ChessGamesDao;
import com.framnes.chessstats.model.ChessGame;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.MoveConversionException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import java.sql.SQLIntegrityConstraintViolationException;

public class ImportWorker implements Runnable {

    private final ChessGamesDao chessGamesDao;
    private final ImportConsole importConsole;
    private final Game game;

    public ImportWorker(ChessGamesDao chessGamesDao, ImportConsole importConsole, Game game) {
        this.chessGamesDao = chessGamesDao;
        this.importConsole = importConsole;
        this.game = game;
    }

    @Override
    public void run() {
        try {

            ChessGame chessGame = new ChessGame(game);
            chessGamesDao.insertGame(chessGame);

        } catch (UnableToExecuteStatementException insertFailed) {
            if (insertFailed.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw insertFailed;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

}
