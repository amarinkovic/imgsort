package com.am.image.tools;

import java.text.DecimalFormat;

public class Tester {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("####");
		df.setMinimumIntegerDigits(4);
		for(int i=0; i<100; i++) {
			System.out.println(df.format(i));
		}

	}

}
