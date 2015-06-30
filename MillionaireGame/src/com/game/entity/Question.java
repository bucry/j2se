/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.entity;

/**
 *
 * @author perfecking
 */
public class Question {
    private String question = null;
    private String category = null;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;

    public Question(){
    
    }
    public String getQuestion() {
        return question;
    }

    public String getCategory() {
        return category;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setB(String b) {
        this.b = b;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }    
}
