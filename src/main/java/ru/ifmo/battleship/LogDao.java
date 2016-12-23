package ru.ifmo.battleship;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by cantoress on 21.12.2016.
 */

//public class LogDao implements InitializingBean {
    public class LogDao {


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

//    public void afterPropertiesSet() throws Exception {
//        if (dataSource == null) {
//            throw new BeanCreationException("Must set dataSource on Log");
//        }
//    }

    public List<LogInfo> findAll() {
        String sql = "select id, game_num, player, shoot, shoot_res from log";
        return jdbcTemplate.query(sql, new LogMapper());
    }

    public List<LogInfo> findGame(int game) {
        String sql = "select id, game_num, player, shoot, shoot_res from log where game_num = ?";

        return jdbcTemplate.query(sql, new Object[] { game}, new LogMapper());
    }

    public void add(LogInfo log){
        jdbcTemplate.update(
                "INSERT INTO log (id, game_num, player, shoot, shoot_res) values(?, ?, ?, ?, ?)",
               log.getId(), log.getGameNum(), log.getPlayer(), log.getShoot(), log.getShootResult());
    }

    public void delete(int game){
        jdbcTemplate.update("DELETE FROM log WHERE GAME_NUM = ?", game);
    }

    public int idcount(){
        return findAll().size();
    }

    public int gamecount(){

        List<LogInfo> all = findAll();
        int num = 0;
        for(LogInfo a:all){
            if(a.getGameNum()>=num){
                num = a.getGameNum();
            }
        }
        return num;
    }


    private static final class LogMapper implements RowMapper<LogInfo> {

        public LogInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

            LogInfo log = new LogInfo();
            log.setId(rs.getInt("id"));
            log.setGameNum(rs.getInt("game_num"));
            log.setPlayer(rs.getString("player"));
            log.setShoot(rs.getString("shoot"));
            log.setShootResult(rs.getString("shoot_res"));
            return log;
        }
    }

}



