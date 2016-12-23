package ru.ifmo.battleship;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * Created by cantoress on 04.12.2016.
 */
@Controller
public class GameController {

    final String [] first =  {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

    private static Field playerField, computerField;
    private static GameProcess game;
    private static int gamenum;

    @Autowired
    private LogDao dao;



    @RequestMapping(value = "/game/play", method = RequestMethod.GET)
    @ResponseBody
    public List<ConvertionToJSON> createFields() {
        List<ConvertionToJSON> records = new ArrayList<ConvertionToJSON>();

//        ServletContext servletContext = getServletContext();
//        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//
//
//         dao = ctx.getBean("logBean", LogDao.class);

//        game = new GameProcess();
        playerField = new Field();
        computerField = new Field();

        playerField.createNewRandomField();
        computerField.createNewRandomField();


        records.addAll(addAllToJSON(playerField,0));
        records.addAll(addAllToJSON(computerField,1));


        return records;
    }

    @RequestMapping("/game")
    public void home() {

    }

    @RequestMapping("/info")
    public void home2() {

    }

    @RequestMapping(value = "/game/del", method = RequestMethod.GET)
    public void deletegame(@RequestParam(value = "game") int game){
        dao.delete(game);
    }

    @RequestMapping(value = "/info/log", method=RequestMethod.GET)
    @ResponseBody
    public List<LogInfo> playerShoot(@RequestParam(value = "game") int game){
        return dao.findGame(game);
    }

    @RequestMapping(value = "/gamenum", method = RequestMethod.GET)
    @ResponseBody
    public int getGamenum() {
        gamenum = dao.gamecount()+1;
        return gamenum;
    }

    @RequestMapping("/")
    public String home1() {
        return "redirect:/game";
    }


    @RequestMapping(value = "/game/shoot", method=RequestMethod.GET)
    @ResponseBody
    public List<ConvertionToJSON> playerShoot(@RequestParam(value = "turn") String turn){
        List<ConvertionToJSON> records = new ArrayList<ConvertionToJSON>();


        records.addAll(shoot(1,computerField,turn));
        if(records.get(0).getCoord()!=-1) {
            dao.add(fromShootToLog(records.get(0), 1, gamenum, records.size()));
        }
        System.out.println(dao.findAll());

        return records;
    }

    @RequestMapping(value = "/game/compshoot", method=RequestMethod.GET)
    @ResponseBody
    public List<ConvertionToJSON> compShoot(){
        List<ConvertionToJSON> records = new ArrayList<ConvertionToJSON>();

        Random random = new Random();
        String turn = String.valueOf(random.nextInt(99));

        records.addAll(shoot(0,playerField,turn));
        if(records.get(0).getCoord()!=-1) {
            dao.add(fromShootToLog(records.get(0), 0, gamenum, records.size()));
        }
        System.out.println(dao.findAll());

        return records;
    }

    private static List <ConvertionToJSON> shoot(int num,Field field, String turn){

        List<ConvertionToJSON> records = new ArrayList<ConvertionToJSON>();
        int turnInt;
        if(num==1){
            turnInt = game.getCoordsFromData(turn);
        } else{
            turnInt = Integer.valueOf(turn);
        }
        int x = turnInt/10;
        int y = turnInt%10;
        String resultOfTurn = game.shoot(num,field, turn);

        if(resultOfTurn.contains("промазал")){

            records.add(addToJSON(field,x,y,num));

        } else if(resultOfTurn.contains("уже")){

            ConvertionToJSON toAdd;
            if(num==1) {
                toAdd = new ConvertionToJSON(-1, -1, 1);
            } else{
                toAdd = new ConvertionToJSON(-1, -1, -1);
            }
            records.add(toAdd);

        } else if(resultOfTurn.contains("попал")){

            ConvertionToJSON toAdd = addToJSON(field,x,y,num);
            if(num==1){
                toAdd.setStat(1);
            } else{
                toAdd.setStat(-1);
            }
            records.add(toAdd);

        } else{
            ConvertionToJSON toAdd;
            Ship ship = field.getCellMap()[x][y].getShip();
            for(int i = ship.getXb()-1;i<=ship.getXe()+1;i++){
                for(int j = ship.getYb()-1;j<=ship.getYe()+1;j++){
                    if((i>=0)&&(i<=9)&&(j>=0)&&(j<=9)){
                        toAdd = addToJSON(field,i,j,num);
                        if(!game.canPlay(field)){
                            if(num==1){
                                toAdd.setStat(2);
                            } else{
                                toAdd.setStat(-2);
                            }
                        } else {
                            if(num==1){
                                toAdd.setStat(1);
                            } else{
                                toAdd.setStat(-1);
                            }
                        }
                        records.add(toAdd);
                    }
                }
            }
        }

        return records;
    }


    private static Set<ConvertionToJSON> addAllToJSON(Field field, int num){

        Set<ConvertionToJSON> answer = new HashSet<ConvertionToJSON>();
        for(int i = 0;i<10;i++) {
            for (int j = 0; j < 10; j++) {
                if (field.getCellMap()[j][i].isHasChecked() && field.getCellMap()[j][i].isHasShip()) {
                    answer.add(new ConvertionToJSON(num*100+j * 10 + i, 1, 0));
                } else if (field.getCellMap()[j][i].isHasChecked()) {
                    answer.add(new ConvertionToJSON(num*100+j * 10 + i, 2, 0));
                } else if (field.getCellMap()[j][i].isHasShip()) {
                    answer.add(new ConvertionToJSON(num*100+j * 10 + i, 3, 0));
                } else {
                    answer.add(new ConvertionToJSON(num*100+j * 10 + i, 4, 0));
                }
            }
        }

        return answer;

    }

    private static ConvertionToJSON addToJSON(Field field, int j, int i, int num){

        ConvertionToJSON answer;

        if (field.getCellMap()[j][i].isHasChecked() && field.getCellMap()[j][i].isHasShip()) {
            answer = new ConvertionToJSON(num*100+j * 10 + i, 1, 0);
        } else if (field.getCellMap()[j][i].isHasChecked()) {
            answer= new ConvertionToJSON(num*100+j * 10 + i, 2, 0);
        } else if (field.getCellMap()[j][i].isHasShip()) {
            answer = new ConvertionToJSON(num*100+j * 10 + i, 3, 0);
        } else {
            answer = new ConvertionToJSON(num*100+j * 10 + i, 4, 0);
        }

        return answer;
    }

    private LogInfo fromShootToLog(ConvertionToJSON json, int player, int game, int size){

        LogInfo log = new LogInfo();

        log.setId(dao.idcount()+1);
        log.setGameNum(game);
        if(player==1){
            log.setPlayer("player");
        } else{
            log.setPlayer("computer");
        }
        //Change to literal shoot
        int coord = json.getCoord()%100;
        String res = first[coord/10]+Integer.toString((coord%10)+1);
        log.setShoot(res);

        int act = json.getAction();
        String shootresult = "";
        if(size>1){
            shootresult = "killed";
        }
        else if(act==1){
            shootresult = "got";
        } else{
            shootresult = "empty";
        }
        log.setShootResult(shootresult);

        return log;
    }

}
