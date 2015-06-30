package com.xcomm.Packet;


public class Packet {
	//报文头代码
	protected byte[] Header = new byte[2];
	//报文长度
	protected byte[] Len = new byte[2];
	//报文信息类型
	protected byte[] Type = new byte[2];
	//域
	protected byte[] Bit = new byte[8];
	//需要发送的报文
	protected byte[] PacketBuff;
	//域内容
	protected byte[] DataBuff;
	//表示变长
	protected byte[] FieldVarLen;
	//报文长度
	protected int DataLen;
	//各个域名称
	protected String FieldName[];
	//各个域数据长度
	protected int FieldLen[];
	//域的个数
	protected int FieldCount;

	//设置每个域的内容
	public int SetField(byte[] value, String TheField) {
		int i, j;
		int Offset;
		
		for (i = 0, Offset = 0; i < FieldCount; i++) {
			if (TheField.equals(FieldName[i])) {
				if (FieldVarLen[i] > 0) {
					FieldVarLen[i] *= 2;
					if (FieldVarLen[i] == 2) {
						DataBuff[Offset] = (byte) (FieldLen[i] / 10);
						DataBuff[Offset + 1] = (byte) (FieldLen[i] - DataBuff[Offset] * 10);
						DataBuff[Offset] = (byte) (((DataBuff[Offset] << 4) & 0xF0) | (DataBuff[Offset + 1] & 0x0F));
						Offset += 1;
					}else if(FieldVarLen[i] == 4){
						DataBuff[Offset] = (byte) (FieldLen[i] / 1000);
						DataBuff[Offset + 1] = (byte) ((FieldLen[i] - DataBuff[Offset] * 1000) / 100);
						DataBuff[Offset + 2] = (byte) ((FieldLen[i] - DataBuff[Offset + 1] * 100) / 10);
						DataBuff[Offset + 3] = (byte)(FieldLen[i] % 10);
						DataBuff[Offset] = (byte) (((DataBuff[Offset] << 4) & 0xF0) | (DataBuff[Offset + 1] & 0x0F));
						DataBuff[Offset+1] = (byte) (((DataBuff[Offset+2] << 4) & 0xF0) | (DataBuff[Offset + 3] & 0x0F));
						Offset += 2;
					}
				}
				break;
			}
			Offset = Offset + FieldVarLen[i] + FieldLen[i];
		}
		if (i == FieldCount)
			return -1;
		for (j = 0; j < FieldLen[i]; j++) {
			if(j < value.length)
				DataBuff[Offset + j] = value[j];
			else 
				DataBuff[Offset + j] = 0x00;
		}

		return 0;
	}

	//取得指定域的数据
	public byte[] GetField(String TheField) {
		int i, j;
		int Offset;
		byte tmpbuff[];
		for (i = 0, Offset = 0; i < FieldCount; i++) {
			if (TheField.equals(FieldName[i])) {
				if (FieldVarLen[i] > 0){
					Offset += FieldVarLen[i];
				}
				break;
			}
			Offset = Offset + FieldVarLen[i] + FieldLen[i];
		}
		if (i == FieldCount) {
			return null;
		}
		tmpbuff = new byte[FieldLen[i]];
		for (j = 0; j < FieldLen[i]; j++) {
			tmpbuff[j] = DataBuff[Offset + j];
		}
		return tmpbuff;
	}

	//取得报文（未加密）
	public byte[] getPacket() {
		System.arraycopy(DataBuff, 0, PacketBuff, PacketBuff.length - DataLen,
				DataLen);

		return PacketBuff;

	}

	//组装收到回复报文
	public byte[] setPacket(byte[] buff) {
		System.arraycopy(buff, Header.length + Len.length + Type.length
				+ Bit.length, DataBuff, 0, DataLen);

		return DataBuff;
	}

	//初始化报文内容
	public void setBasicData() {
		int temp = 0;
		DataLen = 0;
		for (int i = 0; i < FieldCount; i++) {
			DataLen = DataLen + FieldVarLen[i] + FieldLen[i];
		}
		int length = DataLen + Type.length + Bit.length;
		if (length > 0xFF) {
			Len[0] = (byte) (length - 0xFF);
			Len[1] = (byte) 0xFF;
		} else {
			Len[0] = 0;
			Len[1] = (byte) length;
		}
		PacketBuff = new byte[DataLen + Header.length + Len.length
				+ Type.length + Bit.length];
		System.arraycopy(Header, 0, PacketBuff, 0, Header.length);
		temp += Header.length;
		System.arraycopy(Len, 0, PacketBuff, temp, Len.length);
		temp += Len.length;
		System.arraycopy(Type, 0, PacketBuff, temp, Type.length);
		temp += Type.length;
		System.arraycopy(Bit, 0, PacketBuff, temp, Bit.length);

		DataBuff = new byte[DataLen];
	}

	public byte[] getBit() {
		return Bit;
	}

	public byte[] getHeader() {
		return Header;
	}

	public void setHeader(byte[] header) {
		Header = header;
	}

	public byte[] getLen() {
		return Len;
	}

	public void setLen(byte[] len) {
		Len = len;
	}

	public byte[] getType() {
		return Type;
	}

	public void setType(byte[] type) {
		Type = type;
	}

	public byte[] getPacketBuff() {
		return PacketBuff;
	}

	public void setPacketBuff(byte[] packetBuff) {
		PacketBuff = packetBuff;
	}

	public byte[] getDataBuff() {
		return DataBuff;
	}

	public void setDataBuff(byte[] dataBuff) {
		DataBuff = dataBuff;
	}

	public byte[] getFieldVarLen() {
		return FieldVarLen;
	}

	public void setFieldVarLen(byte[] fieldVarLen) {
		FieldVarLen = fieldVarLen;
	}

	public int getDataLen() {
		return DataLen;
	}

	public void setDataLen(int dataLen) {
		DataLen = dataLen;
	}

	public String[] getFieldName() {
		return FieldName;
	}

	public void setFieldName(String[] fieldName) {
		FieldName = fieldName;
	}

	public int[] getFieldLen() {
		return FieldLen;
	}

	public void setFieldLen(int[] fieldLen) {
		FieldLen = fieldLen;
	}

	public int getFieldCount() {
		return FieldCount;
	}

	public void setFieldCount(int fieldCount) {
		FieldCount = fieldCount;
	}

	public void setBit(byte[] bit) {
		Bit = bit;
	}

}

