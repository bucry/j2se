package com.xcomm.entity;

import java.io.Serializable;

public class TbUserInfo implements Serializable{
	
	private static final long serialVersionUID = 2842052275908179204L;

	private int userId;
	
	private String userName;

    private String userSex;

    private int userAge;

	public String getUserName() {
		return userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	
	@Override

    public String toString() {

       return this.getUserId() + "   " + this.getUserName() + "   "

              + this.getUserSex() + "    " + this.getUserAge();

    }
    
}
