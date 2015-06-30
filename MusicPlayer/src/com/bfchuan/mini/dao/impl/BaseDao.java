package com.bfchuan.mini.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import com.bfchuan.mini.util.FileStreamUtils;

/**
 * dao层的公用方法
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class BaseDao {

	/**
	 * 从文件中读取对象
	 * @param filename
	 * @return
	 */
	public Object readObjectFromFile(String filename) {
		Object object = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {

			File file = new File(filename);

			if (!file.exists()) {
				File parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				file.createNewFile();
				return null;
			} else if (file.length() == 0) {
				return null;
			}
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			object = ois.readObject();
		} catch (Exception e) {
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			} finally {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}

		}
		return object;
	}

	/**
	 * 向文件中写入对象
	 * @param filename
	 * @param obj
	 */
	public void writeObjectToFile(String filename, Object obj) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {

			File file = new File(filename);

			if (!file.exists()) {
				return;
			}
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(obj);
		} catch (Exception e) {
		} finally {
			try {
				oos.flush();
				oos.close();
			} catch (Exception e) {
			} finally {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}

		}
	}
	
	/**
	 * 向文件中写入字符串
	 * @param file
	 * @param str
	 */
	public void writeStringToFile(File file, String str) {
		FileOutputStream fos = null;
		try {
			if (!file.exists()) {
				return;
			}
			fos = new FileOutputStream(file);
			fos.write(str.getBytes("GBK"));
			fos.flush();
		} catch (Exception e) {
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 加载属性文件
	 * @param pp 目标属性对象
	 * @param path 真实文件的路径
	 * @param sign 
	 * @return
	 */
	public boolean loadProperties(Properties pp, String path, boolean sign) {
		File file = new File(path);
		if (!sign && !file.exists()) {
			return false;
		}
		try {
			if (sign) {// 读取jar内部属性文件
				pp.load(FileStreamUtils.getInputStream(path));
			} else {// 读取外部属性文件
				pp.load(new FileInputStream(file));// 读取属性文件
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 保存配置文件
	 * @param pp
	 * @param path
	 * @param name
	 * @return
	 */
	public boolean saveProperties(Properties pp, String path, String name) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			pp.store(new FileOutputStream(file), name); // 保存属性到普通文件
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
