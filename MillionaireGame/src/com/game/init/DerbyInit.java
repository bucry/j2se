/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.init;

import com.game.entity.GameInfo;
import com.game.util.DbHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author perfecking
 */
public class DerbyInit {
    public static void main(String[] args){
        showTableData();
    }
    public static void createTables(){
        System.err.println("正在创建tables");
        String sql = "create table records\n" +
"(\n" +
"	username varchar(20) not null,\n" +
"	scores int not null,\n" +
"	category varchar(20) not null\n" +
")";
        Connection connection = DbHelper.getConnection();
        if(connection==null){
            return;
        }
        Statement smt = null;
        try {
//          创建records表
            smt = connection.createStatement();
            smt.execute(sql);
            
//          创建questions表
            sql = "create table questions\n" +
"(\n" +
"	question varchar(100) not null,\n" +
"	category varchar(10) not null,\n" +
"	a varchar(50) not null,\n" +
"	b varchar(50) not null,\n" +
"	c varchar(50) not null,\n" +
"	d varchar(50) not null,\n" +
"	answer varchar(1) not null\n" +
")";
        smt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void insertSingleRecord(GameInfo gameInfo){
        System.out.println("正在插入记录。");
        String sql = "insert into records(username,scores,category) values(?,?,?)";
        Connection connection = DbHelper.getConnection();
        if(connection==null){
            return;
        }
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "test");
            ps.setInt(2, 20);
            ps.setString(3, "sports");
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
             try {
                ps.close();
                connection.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public static void showTableData(){
        String sql = "select * from records order by scores desc";
        Statement smt = null;
        Connection connection = DbHelper.getConnection();
        if(connection==null){
            return;
        }
        int count = 0;
        String temp = null;
        ResultSet rs = null;
        try {
            smt = connection.createStatement();
            rs = smt.executeQuery(sql);
            while(rs.next()){
                count++;
                temp = "Name = "+rs.getString("username")+" "+"Scores = "+rs.getInt("scores")+" "+"Category = "+rs.getString("category");
                System.out.println(temp);
            }
            System.out.println("\n\n");

//          打印所有问题
            sql = "select * from questions";
            rs = smt.executeQuery(sql);
            while(rs.next()){
                count++;
                temp = "Questions: "+rs.getString("question")+" "+"a: "+rs.getString("a")+" "+"b: "+rs.getString("b")+" "+"c: "+rs.getString("c")+" "+"d: "+rs.getString("d")+" "+"answer: "+rs.getString("answer");
                System.out.println(temp);
            }
            System.out.println("total "+count+" records.");
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
    }
    public static void insertAllData(){
        Connection connection = DbHelper.getConnection();
        if(connection==null){
            return;
        }
        Statement smt = null;
        try {
            smt = connection.createStatement();
//            插入Science题库
            String sql = "insert into questions(question,category,a,b,c,d,answer) values\n" +
"	('How many planets are there in the solar system?','science','Three','Six','Nine','Twelve','c'),\n" +
"	('Planets orbit','science','the Sun','the Stars','the Moon','the Earth','a'),\n" +
"	('The largest planet in the solar system is','science','Earth','Mars','Pluto','Jupiter','d')";
            smt.executeUpdate(sql);
//          插入Art题库           
            sql = "insert into questions(question,category,a,b,c,d,answer) values\n" +
"	('Frailty, thy name is woman. But what is the name of the specific woman Hamlet has in mind ?','art','Eminem','Gertrude','Al Capone','Cindarella','b'),\n" +
"	('Who developed the three laws of robotics, as well as a zeroith law that says that a robot may not injure humanity or, through inaction, allow humanity to come to harm ?','art','Brad Pitt','Gerard Butler','Isaac Asimov','Joe Pesci','c'),\n" +
"	('In the Herman Melville novel, he`s an officer on the Bellipotent. In the Benjamin Britten opera based on the novel, he is an officer on the HMS Indomitable. Who is he ?','art','Billy Budd','Moby Dick','Captain Planet','Wolfman','a')";
            smt.executeUpdate(sql);

//          插入Sports题库
            sql = "insert into questions(question,category,a,b,c,d,answer) values\n" +
"	('What is the scoring system in soccer?','sports','Holes','Runs','Goals','Baskets','c'),\n" +
"	('What does the goalie usually wear?','sports','Gloves','A bracelet','A lucky pin','A hat','b'),\n" +
"	('What is the highest award given in soccer?','sports','The Soccer Trophy','The FIFA World Cup','The Soccer Honor Award','The UEFA Cup','b')";
            smt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                smt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void alterTable(){
        Connection connection = DbHelper.getConnection();
        Statement smt = null;
        try {
            smt = connection.createStatement();
            String sql = "create table questions\n" +
"(\n" +
"	question varchar(200) not null,\n" +
"	category varchar(10) not null,\n" +
"	a varchar(50) not null,\n" +
"	b varchar(50) not null,\n" +
"	c varchar(50) not null,\n" +
"	d varchar(50) not null,\n" +
"	answer varchar(1) not null\n" +
")";
            smt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
