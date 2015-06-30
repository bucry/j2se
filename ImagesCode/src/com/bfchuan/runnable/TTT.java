package com.bfchuan.runnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TTT {
	public static void main(String[] args) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String a = br.readLine();
			System.out.println(a);
			int x = Integer.parseInt(a);
			System.out.println(x);
		} catch (Exception e) {
		}
	}
}