import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cantoress on 23.11.2016.
 */
public class Field {

    private FieldCell[][] cellMap;
    private ArrayList<Ship> shipList;

    public Field() {
        cellMap = new FieldCell [10][10];
        for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                cellMap[j][i] = new FieldCell();
            }
        }
        shipList = new ArrayList <Ship>();
    }

    public FieldCell[][] getCellMap() {
        return cellMap;
    }

    public void setCellMap(FieldCell[][] cellMap) {
        this.cellMap = cellMap;
    }

    public ArrayList<Ship> getShipList() {
        return shipList;
    }

    public void setShipList(ArrayList<Ship> shipList) {
        this.shipList = shipList;
    }

    public void createNewRandomField(){

        boolean success;
        int shipLength;
        int i = 0;
        while(i<10) {
            success = false;
            //Определяем длину корабля, который размещается в данный момент
            if (i == 0) {
                shipLength = 4;
            } else if ((i == 1) || (i == 2)) {
                shipLength = 3;
            } else if ((i == 3) || (i == 4) || (i == 5)) {
                shipLength = 2;
            } else {
                shipLength = 1;
            }

            Random random = new Random();
            int xb = random.nextInt(10);
            int yb = random.nextInt(10);

            while(cellMap[xb][yb].getHasNearShip()||cellMap[xb][yb].isHasShip()){
                xb = random.nextInt(10);
                yb = random.nextInt(10);
            }
            if(shipLength==1){
                //Добавляем корабль единичной длины
                Ship ship = new Ship(1,xb,yb,xb,yb);
                //System.out.println(ship.info());
                if(ship.checkShipAround(cellMap)){
                    cellMap[xb][yb].setHasShip(true);
                    cellMap[xb][yb].setShip(ship);
                    ship.setNearAround(cellMap);
                    shipList.add(ship);
                    success = true;
//                    System.out.println("добавлен единичный корабль");
                }
            } else {
                //Выбираем направление расположения корабля относительно начальной точки. Следим, чтобы корабль находился в пределах поля
                int direction;
                char side;
                int counter = 0;
                int modx,mody;
                do{
                    direction = random.nextInt(4);
                    if(direction == 0){
                        modx = 0;
                        mody = -shipLength+1;
                    }else if (direction==1){
                        modx = shipLength-1;
                        mody = 0;
                    }else if(direction==2){
                        modx = 0;
                        mody = shipLength-1;
                    }else{
                        modx = -shipLength+1;
                        mody = 0;
                    }
                    // System.out.println(modx+" "+mody);
                } while(!((xb+modx<=9)&&(xb+modx>=0)&&(yb+mody<=9)&&(yb+mody>=0)));

                //Проверяем, что клетки, попадающие под предполагаемое расположение корабля, позволяют разместить корабль
                if(modx!=0){
                    side = 'x';
                    for(int j = 0;j<shipLength-1;j++){
                        if (!(cellMap[xb+sign(modx)*j][yb].isHasShip()&&cellMap[xb+sign(modx)*j][yb].getHasNearShip())) counter++;
                    }
                } else{
                    side = 'y';
                    for(int j = 0;j<shipLength-1;j++){
                        if (!(cellMap[xb][yb+sign(mody)*j].isHasShip()&&cellMap[xb][yb+sign(mody)*j].getHasNearShip())) counter++;
                    }
                }
//                System.out.println(counter);
                //Расставляем корабли
                if(counter==shipLength-1){
                    int xe = xb+modx;
                    int ye = yb+mody;
                    xb = Math.min(xb,xe);
                    xe = xb+Math.abs(modx);
                    yb = Math.min(yb,ye);
                    ye = yb+Math.abs(mody);
                    if(shipLength==2) {
                        Ship ship = new Ship(2, xb, yb, xe, ye);
                        //System.out.println(ship.info());
                        if (ship.checkShipAround(cellMap)) {
                            cellMap[xb][yb].setHasShip(true);
                            cellMap[xe][ye].setHasShip(true);

                            cellMap[xb][yb].setShip(ship);
                            cellMap[xe][ye].setShip(ship);

                            ship.setNearAround(cellMap);
                            shipList.add(ship);
                            success = true;
//                            System.out.println("добавлен двойной корабль");
                        }
                    }
                    else if(shipLength==3){
                        Ship ship = new Ship(3,xb,yb,xe,ye);
                        //System.out.println(ship.info());
                        if(ship.checkShipAround(cellMap)){
                            cellMap[xb][yb].setHasShip(true);
                            cellMap[xe][ye].setHasShip(true);

                            cellMap[xb][yb].setShip(ship);
                            cellMap[xe][ye].setShip(ship);

                            if (side=='x'){
                                cellMap[xb+1][yb].setHasShip(true);
                                cellMap[xb+1][yb].setShip(ship);
                            }else{
                                cellMap[xb][yb+1].setHasShip(true);
                                cellMap[xb][yb+1].setShip(ship);
                            }
                            ship.setNearAround(cellMap);
                            shipList.add(ship);
                            success = true;
//                            System.out.println("добавлен тройной корабль");
                        }
                    } else{
                        Ship ship = new Ship(4,xb,yb,xe,ye);
                        //System.out.println(ship.info());
                        if(ship.checkShipAround(cellMap)){
                            cellMap[xb][yb].setHasShip(true);
                            cellMap[xe][ye].setHasShip(true);

                            cellMap[xb][yb].setShip(ship);
                            cellMap[xe][ye].setShip(ship);

                        if (side=='x'){
                            cellMap[xb+1][yb].setHasShip(true);
                            cellMap[xb+2][yb].setHasShip(true);

                            cellMap[xb+1][yb].setShip(ship);
                            cellMap[xb+2][yb].setShip(ship);
                        }else {
                            cellMap[xb][yb+1].setHasShip(true);
                            cellMap[xb][yb+2].setHasShip(true);

                            cellMap[xb][yb+1].setShip(ship);
                            cellMap[xb][yb+2].setShip(ship);
                        }
                            ship.setNearAround(cellMap);
                            shipList.add(ship);
                            success = true;
//                            System.out.println("добавлен четверной корабль");

                        }
                    }
                }

            }
//            System.out.println(success);
            if(success){
//                System.out.println(i);
                i++;
//                for(int k = 0;k<10;k++){
//                    for(int j = 0; j<10;j++){
//                        if(cellMap[j][k].isHasShip()) {System.out.print("0");}
//                        else if(cellMap[j][k].getHasNearShip()) {System.out.print("!");}
//                        else {System.out.print("*");}
//                    }
//                    System.out.println();
//                }
            }
        }

    }

    /**
    * Возвращает знак числа
    * @param x число
    * @return знак числа
    */
    private int sign(int x) {
        if (x > 0)
            return 1;
        else if (x < 0)
            return -1;
        return 0;
    }

}
