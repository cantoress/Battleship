package ru.ifmo.battleship;

import java.util.Scanner;

/** Класс для игры в консоли
 * Created by cantoress on 01.12.2016.
 */
public class ConsolePlay {

    private static Field playerField, computerField;
    private static GameProcess game;


    public static void main(String [] args){
        System.out.println("Ваше поле");
        playerField = new Field();
        playerField.createNewRandomField();
        game.showField(playerField);
        System.out.println("Поле противника");
        computerField = new Field();
        computerField.createNewRandomField();
        game.showField(computerField);


        Scanner scanner = new Scanner(System.in);
        while(game.canPlay(playerField)&&game.canPlay(computerField)){
            System.out.println("Ваш ход. Введите координаты выстрела, используя латинские буквы и цифры");
            String shoot = scanner.next();
            String res = game.shoot(1,computerField,shoot);
            System.out.println(res);
            System.out.println("Поле противника");
            game.showField(computerField);
            System.out.println("Осталось кораблей: "+computerField.getShipList().size());
            while(!res.contains("промазал")){
                System.out.println("Ваш ход. Введите координаты выстрела, используя латинские буквы и цифры");
                shoot = scanner.next();
                res = game.shoot(1,computerField,shoot);
                System.out.println(res);
                System.out.println("Поле противника");
                game.showField(computerField);
                System.out.println("Осталось кораблей: "+computerField.getShipList().size());
            }
            if(!(game.canPlay(playerField)||game.canPlay(computerField))){break;}
            System.out.println("Ход противника!");
            res = game.shoot(2,playerField,null);
            System.out.println(res);
            System.out.println("Ваше поле");
            game.showField(playerField);
            System.out.println("Осталось кораблей: "+playerField.getShipList().size());
            while(!res.contains("промазал")){
                res = game.shoot(2,playerField,null);
                System.out.println(res);
                System.out.println("Ваше поле");
                game.showField(playerField);
                System.out.println("Осталось кораблей: "+playerField.getShipList().size());
            }
        }
        if(game.canPlay(playerField)){
            System.out.println("Вы выиграли!");
        } else{
            System.out.println("Вы проиграли!");
        }

    }

}
