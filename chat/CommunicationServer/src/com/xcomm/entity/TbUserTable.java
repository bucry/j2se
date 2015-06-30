package com.xcomm.entity;

import java.io.Serializable;

import com.xcomm.test.LoUtils;

public class TbUserTable implements Serializable{
	
	private static final long serialVersionUID = 653523829821625003L;
	
	private int id;
	//tt号码
	private String ttId;
	//昵称
	private String nickname;
	//密码
	private String passWd;
	//生日
	private String birthday;
	//兴趣爱好
	private String likeThing;
	//key
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTtId() {
		return ttId;
	}
	public void setTtId(String ttId) {
		this.ttId = ttId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassWd() {
		return passWd;
	}
	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getLikeThing() {
		return likeThing;
	}
	public void setLikeThing(String likeThing) {
		this.likeThing = likeThing;
	}
	
	public byte[] toBytes(){
		String byteStr = LoUtils.addLength(ttId) + LoUtils.addLength(passWd);
		return byteStr.getBytes();
	}
}
