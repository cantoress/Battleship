package ru.ifmo.battleship;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by cantoress on 20.12.2016.
 */
public class LogInfo implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("gameNum")
    private int gameNum;
    @JsonProperty("player")
    private String player;
    @JsonProperty("shoot")
    private String shoot;
    @JsonProperty("shootResult")
    private String shootResult;

    public LogInfo() {
    }

    public LogInfo(int id, int gameNum, String player, String shoot, String shootResult) {
        this.id = id;
        this.shootResult = shootResult;
        this.shoot = shoot;
        this.player = player;
        this.gameNum = gameNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameNum() {
        return gameNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getShoot() {
        return shoot;
    }

    public void setShoot(String shoot) {
        this.shoot = shoot;
    }

    public String getShootResult() {
        return shootResult;
    }

    public void setShootResult(String shootResult) {
        this.shootResult = shootResult;
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "id=" + id +
                ", gameNum=" + gameNum +
                ", player='" + player + '\'' +
                ", shoot='" + shoot + '\'' +
                ", shootResult='" + shootResult + '\'' +
                '}';
    }
}
