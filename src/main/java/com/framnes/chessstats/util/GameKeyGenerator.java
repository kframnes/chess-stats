package com.framnes.chessstats.util;

import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.MoveConversionException;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class GameKeyGenerator {

    public static String generateKey(Game game) throws MoveConversionException {

        String rawPgn = game.toPgn(false, false);
        rawPgn = rawPgn
            .replaceAll("\\[.*","") // Strip away all PGN tags
            .replaceAll("\\n", ""); // Strip away all newlines

        String rawKey = String.format("%s - %s | %s",
                game.getWhitePlayer().getName(), game.getBlackPlayer().getName(), rawPgn);

        return Hashing.sha512().hashString(rawKey, StandardCharsets.UTF_8).toString();

    }

}
