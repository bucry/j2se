package com.xcomm.entity;

import java.io.Serializable;
import java.net.SocketAddress;

import com.xcomm.mina.util.LoUtils;
import com.xomm.comm.FindChatFrameExchangeDialog;



/**
 * 用户信息POJO类
 * 作者：Leonidas
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：
 */
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
	private String key = "tbUserKey";
	
	//该用户的图标
	private String icon;
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SocketAddress getAddress() {
		return address;
	}

	public void setAddress(SocketAddress address) {
		this.address = address;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public FindChatFrameExchangeDialog getChatFrame() {
		return chatFrame;
	}

	public void setChatFrame(FindChatFrameExchangeDialog chatFrame) {
		this.chatFrame = chatFrame;
	}
	//该用户的名字
	private String name;
	//该用户的MulitcastSocket所在的IP和端口
	private SocketAddress address;
	//该用户失去联系的次数
	private int lost;
	//该用户对应的交谈窗口
	private FindChatFrameExchangeDialog chatFrame;
	
	
	public TbUserTable(){
		
	}
	
	public TbUserTable(String icon , String name , SocketAddress address , int lost)
	{
		this.icon = icon;
		this.name = name;
		this.address = address;
		this.lost = lost;
	}
	
	
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
