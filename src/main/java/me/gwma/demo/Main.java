package me.gwma.demo;

public class Main {

	public static void main(String[] args) {
		for (int i = 11; i <= 30; i++) {
			System.out.println(String.format("0.%d = %5.3f", i, Math.pow(0.01 * i, 0.5)));
		}
		System.out.println("--------------");
		for (int i = 31; i <= 50; i++) {
			System.out.println(String.format("0.%d = %5.3f", i, Math.pow(0.01 * i, 1.0 / 3)));
		}
		System.out.println("--------------");
		for (int i = 51; i <= 70; i++) {
			System.out.println(String.format("0.%d = %5.3f", i, Math.pow(0.01 * i, 0.25)));
		}
	}

}
