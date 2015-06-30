/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.entity;

/**
 *
 * @author perfecking
 */
public class GameInfo {
    private int scores;
    private String username;
    private String category;
    
    public GameInfo(String username,int scores,String category){
        this.username = username;
        this.scores = scores;
        this.category = category;
    }

    public GameInfo(){
        
    }
    
    public int getScores() {
        return scores;
    }

    public String getUsername() {
        return username;
    }

    public String getCategory() {
        return category;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
