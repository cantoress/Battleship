import java.util.Random;

/**
 * Created by cantoress on 23.11.2016.
 */
public class GameProcess {


    public static Field playerField, computerField;

    public static void main(String [] args){

        playerField = new Field();
        playerField.createNewRandomField();
        showField(playerField);

        computerField = new Field();
        computerField.createNewRandomField();
        showField(computerField);


    }

    /**
     * Функция реализует выстрел. Возвращаемая строка содержит информацию о результате выстрела
     * @param player код игрока (1 - живой игрок, иначе - компьютер)
     * @param field поле игрока, по которому стреляют
     * @param data информация о ходе (для живого игрока), для компьютера - null
     * @return информация о результате выстрела
     */
    public static String shoot(int player, Field field, String data){

        int coord;
        if(player == 1){
            coord = getCoordsFromData(data);
        } else{
            coord = getRandomCoord();
        }

        if(ableToShoot(coord, field)){
            FieldCell cell = field.getCellMap()[coord/10][coord%10];
            if(cell.isHasShip()){
                Ship ship = cell.getShip();
                ship.setPartDamaged();
                cell.setHasChecked(true);

                if(ship.getParts()==0){
                    ship.setCheckedAround(field.getCellMap());
                    field.getShipList().remove(ship);
                    return "Вы убили "+ship.info()+"! Продолжайте ходить!";
                } else {
                    return "Вы попали в корабль! Продолжайте ходить!";
                }
            } else{
                return "Вы промазали! Ход переходит к противнику!";
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

        return field.getCellMap()[coord/10][coord%10].isHasChecked();

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
     *  Обрабатывает ход, полученный от игрока в формате "буква-цифра" в ход, понятный программе
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
            y = Character.getNumericValue(chars[1]);
        }

        return x*10+y;
    }

    /**
     * Вспомогательная функция, которая выводит поле в консоль
     * @param field поле игрока/компьютера
     */

    private static void showField(Field field){

        for(int i = 0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(field.getCellMap()[j][i].isHasShip()) {System.out.print("0");}
                else if(field.getCellMap()[j][i].getHasNearShip()) {System.out.print("!");}
                else {System.out.print("*");}
            }
            System.out.println();
        }

    }

}
