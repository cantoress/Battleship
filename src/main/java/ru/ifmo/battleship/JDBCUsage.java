//package ru.ifmo.battleship;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * Created by cantoress on 03.12.2016.
// */
//@Service
//public class JDBCUsage {
//
//    @Autowired
//    private static SessionFactory sessionFactory;
//
//    @Transactional
//    public void addUser(BattleUser user) {
//            sessionFactory.getCurrentSession().save(user);
//        }
//
//    @Transactional
//    public static List<BattleUser> listUsers() {
//
//        return sessionFactory.getCurrentSession().createQuery("from BattleUser")
//                .list();
//        }
//
//    @Transactional
//    public void removeUser(Integer id) {
//        BattleUser user = (BattleUser) sessionFactory.getCurrentSession().load(
//                BattleUser.class, id);
//        if (null != user) {
//            sessionFactory.getCurrentSession().delete(user);
//        }
//
//    }
//}
//
