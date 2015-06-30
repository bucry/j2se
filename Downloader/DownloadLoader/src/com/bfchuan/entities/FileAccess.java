package com.bfchuan.entities;



import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * 文件下载实体类
 * @author Administrator
 *
 */
public class FileAccess implements Serializable{
	

	private static final long serialVersionUID = 1L;

	private RandomAccessFile raf;

	private long position;

	private String name;

	/**
	 * 根据文件名构造随机存储文件
	 * @param name
	 */
	public FileAccess(String name) {
		this.name = name;
		try {
			raf = new RandomAccessFile(this.name, "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将缓冲字节写入文件
	 * @param b
	 * @param start
	 * @param length
	 */
	public void write(byte[] b, int start, int length) {
		try {
			raf.write(b, start, length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭文件
	 */
	public void close() {
		try {
			this.raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件名
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取当前位置
	 * @return
	 */
	public long getPosition() {
		return position;
	}

	/**
	 * 设置当前位置
	 * @param position
	 */
	public void setPosition(long position) {
		try {
			this.position = position;
			raf.seek(position);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
