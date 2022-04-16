package com.framnes.chessstats.model;

/**
 * Represents a player on a specific chess site.
 */
public class ChessPlayer {

    private int id;

    private GameSite site;
    private String username;
    private Integer elo;
    private boolean dirty;
    private boolean accused;
    private boolean flagged;

    public ChessPlayer() {}

    public ChessPlayer(GameSite site, String username, Integer elo) {
        this.site = site;
        this.username = username;
        this.elo = elo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameSite getSite() {
        return site;
    }

    public void setSite(GameSite site) {
        this.site = site;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getElo() {
        return elo;
    }

    public void setElo(Integer elo) {
        this.elo = elo;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isAccused() {
        return accused;
    }

    public void setAccused(boolean accused) {
        this.accused = accused;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }
}
