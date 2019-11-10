package com.example.demo.rover.utils;

public class Utils {
	public static boolean isNumericPositive(String strNum) {
	    return strNum.matches("[1-9][0-9]{0,8}");
	}
}
