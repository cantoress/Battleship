package ru.ifmo.battleship;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by cantoress on 06.12.2016.
 */
public class ConvertionToJSON {

    @JsonProperty("coord")
    private int coord;

    /**
     * 1 - проверенный корабль
     * 2 - проверенная клетка
     * 3 - есть корабль
     * 4 - пустая клетка
     */
    @JsonProperty("action")
    private int action;

    /**
     * 0 - начало игры/переход хода
     * 1 - повторный ход игрока
     * -1 - повторный ход компьютера
     * 2 - игрок выиграл
     * -2 - компьютер выиград
     */
    @JsonProperty("stat")
    private int stat;

    public ConvertionToJSON() {
    }

    public ConvertionToJSON(int coord, int action, int stat) {
        this.stat = stat;
        this.action = action;
        this.coord = coord;
    }

    public int getCoord() {
        return coord;
    }

    public void setCoord(int coord) {
        this.coord = coord;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }
}
