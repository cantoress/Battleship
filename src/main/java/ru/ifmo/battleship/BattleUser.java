package ru.ifmo.battleship;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Class for user's identity
 * Created by cantoress on 20.11.2016.
 */

@Entity
@Table(name = "BATTLEUSER")
public class BattleUser implements Serializable{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "GAMES_PLAYED")
    private int games_played;

    @Column(name = "GAMES_WON")
    private int games_won;



    public int getGames_played() {
        return games_played;
    }

    public void setGames_played(int games_played) {
        this.games_played = games_played;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getGames_won() {
        return games_won;
    }

    public void setGames_won(int games_won) {
        this.games_won = games_won;
    }

}
