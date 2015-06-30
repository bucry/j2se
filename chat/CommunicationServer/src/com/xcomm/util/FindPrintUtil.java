package com.xcomm.util;

public class FindPrintUtil {
	
	public static void printf(Object obj){
		System.out.println(obj);
		if(obj instanceof String){
			System.out.println(obj);
		}
		if(obj instanceof Integer){
			System.out.println(obj);
		}
	}

}
