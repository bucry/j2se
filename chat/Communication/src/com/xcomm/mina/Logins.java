package com.xcomm.mina;


import com.xcomm.Packet.Packet;
import com.xcomm.mina.util.LoUtils;

public class Logins extends Packet {
	public Logins() {
		FieldCount = 3;
		FieldName = new String[FieldCount];
		FieldLen = new int[FieldCount];
		FieldVarLen = new byte[FieldCount];

		Header[0] = 0x65;
		Header[1] = 0x01;

		Type[0] = 0x57;
		Type[1] = 0x00;

		Bit[0] = 0x30;
		Bit[6] = 0x02;

		FieldName[0] = "报文代码";
		FieldName[1] = "交易类型";
		FieldName[2] = "交易内容";

		FieldLen[0] = 3;
		FieldLen[1] = 2;
		//FieldLen[2] = 64;

		FieldVarLen[2] = 1;

		setBasicData();
		SetField(LoUtils.StrToBytes("570001"), "报文代码");
		SetField(LoUtils.StrToBytes("0001"), "交易类型");
	}
}
