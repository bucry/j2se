/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.util;

import com.game.entity.GameInfo;
import com.game.entity.Question;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author perfecking
 */
public class DbHelper {
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String DRIVER_URL = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DATABASE_URL = "jdbc:derby:game;create=true";
    
    static {
        try {
             Class.forName(DRIVER_URL).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static int save(GameInfo gameInfo){
        int result = 0;
        Connection connection = getConnection();
        if(connection==null||gameInfo==null){
            return result;
        }
        PreparedStatement ps = null;    
        try {
            String sql  = "select * from records where username=? and category=?"; 
            ps = connection.prepareStatement(sql);
            ps.setString(1, gameInfo.getUsername());
            ps.setString(2, gameInfo.getCategory());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int scores = Integer.parseInt(rs.getString("scores"));
                if(gameInfo.getScores()>scores){
                    sql = "update records set scores=? where username=? and category=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, gameInfo.getScores());
                    ps.setString(2, gameInfo.getUsername());
                    ps.setString(3, gameInfo.getCategory());
                    result = ps.executeUpdate();
                }
            }else{
                sql = "insert into records(username,scores,category) values(?,?,?)";   
                ps = connection.prepareStatement(sql);
                ps.setString(1, gameInfo.getUsername());            
                ps.setInt(2, gameInfo.getScores());
                ps.setString(3, gameInfo.getCategory());
                result = ps.executeUpdate();   
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static List<GameInfo> getRecords(){
        List<GameInfo> list = new ArrayList<>();
        GameInfo gameInfo = null;
        Connection connection = DbHelper.getConnection();
        if(connection==null){
            return list;
        }
        Statement smt = null;
        ResultSet rs = null;
        try {
            smt = connection.createStatement();
            rs = smt.executeQuery("select * from records order by scores desc");
            while(rs.next()){
                gameInfo = new GameInfo();
                gameInfo.setUsername(rs.getString("username"));
                gameInfo.setScores(rs.getInt("scores"));
                gameInfo.setCategory(rs.getString("category"));
                list.add(gameInfo);
            }     
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                rs.close();
                smt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public static List<Question> getQuestions(String category){
        List<Question> list = new ArrayList<>();
        Connection connection = DbHelper.getConnection();
        if(connection==null){
            return list;
        }
        PreparedStatement ps = null;
        Question question = null;
        ResultSet rs = null;
        try {
            String sql = "select * from questions where category=?";
            ps = connection.prepareStatement(sql);            
            ps.setString(1, category);
            rs = ps.executeQuery();
            while(rs.next()){
                question = new Question();
                question.setQuestion(rs.getString("question"));
                question.setCategory(rs.getString("category"));
                question.setA(rs.getString("a"));
                question.setB(rs.getString("b"));
                question.setC(rs.getString("c"));
                question.setD(rs.getString("d"));
                question.setAnswer(rs.getString("answer"));
                list.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
