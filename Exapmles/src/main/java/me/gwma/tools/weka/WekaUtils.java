package me.gwma.tools.weka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WekaUtils {

	/**
	 * 
	 * @param data
	 * @param dataRelationName
	 * @param resultFileName
	 */
	public static void toWekaFile(double[][] data, String dataRelationName, String resultFileName) {
		StringBuilder sb = new StringBuilder();
		sb.append("@RELATION " + dataRelationName + "\n\n");
		if (data == null || data.length <= 0) {
			throw new RuntimeException("The dataset is empty!");
		}
		for (int i = 0; i < data[0].length; ++i) {
			sb.append("@ATTRIBUTE feature" + i + " REAL\n");
		}
		sb.append("\n");
		sb.append("@DATA\n");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(resultFileName)));
			bw.write(sb.toString());
			bw.flush();
			for (int i = 0; i < data.length; ++i) {
				int j = 0;
				for (; j < data[0].length - 1; ++j) {
					bw.write(String.format("%.6f,", data[i][j]));// 实数保留6位小数
				}
				bw.write(String.format("%.6f\n", data[i][j]));
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * random a data set
	 * 
	 * @param rowNum
	 * @param colNum
	 * @return
	 */
	public static double[][] randomData(int rowNum, int colNum) {
		double[][] data = new double[rowNum][colNum];
		for (int i = 0; i < rowNum; ++i) {
			for (int j = 0; j < colNum; ++j) {
				data[i][j] = Math.random();
			}
		}
		return data;
	}

	public static void main(String[] args) {
		String resultFileName = "ZhJinHong.arff";
		String dataRelationName = "ZhJinHong";
		double[][] data = randomData(3, 4);
		toWekaFile(data, dataRelationName, resultFileName);
	}

}
