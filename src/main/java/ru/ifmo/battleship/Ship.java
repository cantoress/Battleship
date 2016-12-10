package ru.ifmo.battleship;

/**
 * Created by cantoress on 21.11.2016.
 */
public class Ship {

    private int length;
    private int parts;
    private int xb,yb, xe, ye;

    public Ship(int length, int xb, int yb, int xe, int ye) {
        this.length = length;
        parts = length;
        this.xb = xb;
        this.yb = yb;
        this.xe = xe;
        this.ye = ye;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getParts() {
        return parts;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }

    public void setPartDamaged(){
        parts--;
    }

    public int getXb() {
        return xb;
    }

    public void setXb(int xb) {
        this.xb = xb;
    }

    public int getYb() {
        return yb;
    }

    public void setYb(int yb) {
        this.yb = yb;
    }

    public int getXe() {
        return xe;
    }

    public void setXe(int xe) {
        this.xe = xe;
    }

    public int getYe() {
        return ye;
    }

    public void setYe(int ye) {
        this.ye = ye;
    }

    /**
     * @return информацию о корабле: длина, координаты начала и конца
     */
    public String info(){
        return ("Корабль длины "+this.length+" координаты начала " +this.xb+" "+this.yb+" координаты конца "+this.xe+" "+this.ye);
    }
    /**
     * Метод отмечает клетку проверенной, если такой клетки не существует, то игнорирует ее
     * @param map поле игрока
     * @param x координата точки
     * @param y координата точки
     */
    public void setCellChecked(FieldCell [][] map, int x, int y){
        try{
            map[x][y].setHasChecked(true);
        } catch( IndexOutOfBoundsException e) {

        }
    }
    /**
     * Метод окружает убитый корабль проверенными ячейками(т.к. вокруг корабля нет ячеек с другими кораблями)
     * @param map поле игрока
     */
    public void setCheckedAround(FieldCell [][] map){
        for(int i = xb-1;i<=xe+1;i++){
            for(int j = yb-1;j<=ye+1;j++){
                setCellChecked(map,i,j);
            }
        }

    }

    /**
     * Метод отмечает клетку отмеченной о наличии рядом корабля, если такой клетки не существует, то игнорирует ее
     * @param map поле игрока
     * @param x координата точки
     * @param y координата точки
     */

    public void setNearShip(FieldCell [][] map, int x, int y){
        try{
            map[x][y].setHasNearShip(true);
        } catch( IndexOutOfBoundsException e) {

        }
    }
    /**
     * Метод окружает  корабль ячейками с информацией о том, что рядом есть корабль(т.к. вокруг корабля нет ячеек с другими кораблями)
     * @param map поле игрока
     */
    public void setNearAround(FieldCell [][] map){
        for(int i = xb-1;i<=xe+1;i++){
            for(int j = yb-1;j<=ye+1;j++){
                setNearShip(map,i,j);
            }
        }
    }

    /**
     * Метод проверяет клетку на наличиие в ней корабля, если такой клетки не существует, то игнорирует ее
     * @param map поле игрока
     * @param x координата точки
     * @param y координата точки
     * @return есть или нет корабль в этой точке
     */
    public static boolean cellHasShip(FieldCell [][]map,int x,int y){
        try{
            return (map[x][y].isHasShip());
        } catch( IndexOutOfBoundsException e) {
            return false;
        }

    }

    /**
     * Метод проверяет, если вокруг предполагаемого места для корабля есть клетки с кораблями
     * @param map поле игрока
     * @return есть или нет такие клетки
     */
    public boolean checkShipAround(FieldCell [][]map){
        for(int i = xb-1;i<=xe+1;i++){
            for(int j = yb-1;j<=ye+1;j++){
                if(cellHasShip(map,i,j)){return false;}
            }
        }
        return true;
    }
}


