package ru.ifmo.battleship;

import java.util.Random;

/**
 * Created by cantoress on 23.11.2016.
 */
public class GameProcess {


    /**
     * Функция реализует выстрел. Возвращаемая строка содержит информацию о результате выстрела
     * @param player код игрока (1 - живой игрок, 0 - ход в виде цифр, иначе - компьютер)
     * @param field поле игрока, по которому стреляют
     * @param data информация о ходе (для живого игрока), для компьютера - null
     * @return информация о результате выстрела
     */
    public static String shoot(int player, Field field, String data){

        int coord;
        if(player == 1){
            coord = getCoordsFromData(data);
        }else if(player==0){
            coord = Integer.valueOf(data);
        } else{
            coord = getRandomCoord();
        }

        if(ableToShoot(coord, field)){
            FieldCell cell = field.getCellMap()[coord/10][coord%10];
            cell.setHasChecked(true);
            if(cell.isHasShip()){
                Ship ship = cell.getShip();
                ship.setPartDamaged();

                if(ship.getParts()==0){
                    ship.setCheckedAround(field.getCellMap());
                    field.getShipList().remove(ship);
                    if(player==1){ return "Вы убили "+ship.info()+"! Продолжайте ходить!";}
                   else{ return "Компьютер убил "+ship.info()+"! Ход компьютера!";}
                } else {
                    if(player==1){ return "Вы попали в корабль! Продолжайте ходить!";}
                    else{ return "Компьютер попал в ваш корабль! Ход компьютера!";}

                }
            } else{
                if(player==1){ return "Вы промазали! Ход переходит к противнику!";}
                else{ return "Компьютер промазал! Ход переходит к вам!";}

            }

        } else{
            return "В эту клетку уже стреляли! Сделайте новый ход!";
        }

    }

    /**
     * Функция проверяет, стреляли ли уже в эту клетку
     * @param coord координаты клетки в виде 10х+у
     * @param field поле игрока, по которому стреляют
     * @return стреляли/не стреляли
     */
    public static boolean ableToShoot(int coord, Field field){

        return (!field.getCellMap()[coord/10][coord%10].isHasChecked());

    }

    /**
     *  Имитирует случайный ход компьютера
     * @return Ход компьютера в клетку
     */
    public static int getRandomCoord(){

        Random random = new Random();
        int x = random.nextInt(9);
        int y = random.nextInt(9);

        return x*10+y;
    }

    /**
     * Обрабатывает ход, полученный от игрока в формате "буква-цифра" в ход, понятный программе
     * @param data ход в виде строки
     * @return ход в виде числа
     */

    public static int getCoordsFromData(String data){

        int x = 0;
        int y = 0;

        byte [] bytes = data.getBytes();
        char [] chars = data.toCharArray();

        x = bytes[0]-97;
        if(chars.length ==3){
            y = 9;
        } else{
            y = Character.getNumericValue(chars[1])-1;
        }

        return x*10+y;
    }

    /**
     * Проверяет поле на наличие кораблей
     * @param field поле игрока, по которому стреляют
     * @return правда - если по полю еще можно стрелять (есть корабли)
     */
    public static boolean canPlay(Field field) {

        return(!field.getShipList().isEmpty());

    }

    /**
     * Вспомогательная функция, которая выводит поле в консоль
     * @param field поле игрока/компьютера
     */

    public static void showField(Field field){

        for(int i = 0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(field.getCellMap()[j][i].isHasChecked()&&field.getCellMap()[j][i].isHasShip()){System.out.print("E");}
                else if(field.getCellMap()[j][i].isHasChecked()){System.out.print("A");}
                else if(field.getCellMap()[j][i].isHasShip()) {System.out.print("0");}
                else if(field.getCellMap()[j][i].getHasNearShip()) {System.out.print("!");}
                else {System.out.print("*");}
            }
            System.out.println();
        }

    }

    /**
     * Вспомогательная функция, которая выводит поле в консоль, выводит только проверенные клетки
     * @param field поле игрока/компьютера
     */

    public static void showCheckedField(Field field){

        for(int i = 0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(field.getCellMap()[j][i].isHasChecked()&&field.getCellMap()[j][i].isHasShip()){System.out.print("E");}
                else if(field.getCellMap()[j][i].isHasChecked()){System.out.print("A");}
                else {System.out.print("*");}
            }
            System.out.println();
        }

    }

}
