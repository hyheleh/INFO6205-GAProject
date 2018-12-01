package neu.edu.team.ga.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataPreparation {

	//read properties file to load predifined parameters
	private static Properties prop = new Properties();
	static {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream("config.properties"));
			prop.load(in);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static final int citiesNum = Integer.valueOf(prop.getProperty("citiesNum"));
	private static Double[][] cities = new Double[citiesNum][2];
	private static Double[][] distances = new Double[citiesNum][citiesNum];
	/**
	 * generate initial cities info. The latitude and altitude range from 20 to 50
	 * calculate distances matrix of the cities
	 */
	public static void initDatas(){
		for(int i = 0; i < citiesNum; i++) {
			cities[i] = new Double[]{Math.random()*30 + 20.0, Math.random()*30 + 20.0};
		}
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File("city.csv"), true))){
			int i = 0;
			for(; i < citiesNum-1; i++) bw.write("{"+cities[i][0]+","+cities[i][1]+"},\n");
			bw.write("{"+cities[i][0]+","+cities[i][1]+"}");
		}catch (Exception e) {
			e.printStackTrace();
		} 
		for(int i = 0; i < citiesNum; i++) {
			for(int j = i; j < citiesNum; j++) {
				if(i == j) distances[i][j] = 0.0;
				else {
					distances[i][j] = Math.sqrt((cities[i][0]-cities[j][0])*(cities[i][0]-cities[j][0])
							+(cities[i][1]-cities[j][1])*(cities[i][1]-cities[j][1]));
					distances[j][i] = distances[i][j];
				}
			}
		}
	}
	public static Double[][] getCities() {
		return cities;
	}
	public static Double[][] getDistances() {
		return distances;
	}
	public static int getCitiesNum() {
		return citiesNum;
	}
	
}
