package ru.ifmo.battleship;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by cantoress on 21.12.2016.
 */
public class TestJDBC {

    public static void main(String [] args){

        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        //Сюда подставить файл с контекстом из папки main/resources
//        ctx.load();
        ctx.refresh();

//        ContactDao contactDao = ctx.getBean("contactDao", ContactDao.class);

        LogDao dao = ctx.getBean("logBean", LogDao.class);

        System.out.println(dao.findAll());

        LogInfo log = new LogInfo(5,1,"player", "a8", "empty");
        dao.add(log);
        System.out.println(dao.findAll());
        System.out.println(dao.idcount());
        System.out.println(dao.gamecount());

    }

}
