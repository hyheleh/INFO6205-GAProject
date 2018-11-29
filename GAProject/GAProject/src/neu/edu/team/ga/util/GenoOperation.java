package neu.edu.team.ga.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

public class GenoOperation {
	
	//read properties file to load predifined parameters
	private static Properties prop = new Properties();
	private static int genomeLength = Integer.valueOf(prop.getProperty("genomeLength"));
	private static double maxValue = Double.valueOf(prop.getProperty("maxFloatValue"));
	private static double minValue = Double.valueOf(prop.getProperty("minFloatValue"));
	private static double mutationRange = Double.valueOf(prop.getProperty("mutationRange"));
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
	/**
	 * method to get a new random genome
	 * @return
	 */
	public static Double[] generateGenome() {
		Double[] genome = new Double[genomeLength];
		for(int i = 0; i < genomeLength; i++) {
			genome[i] = (Math.random()-0.5)*(maxValue-minValue);
		}
		return genome;
	}
	
	/**
	 * get a 'reflection' of current genotype 
	 * @param genotype
	 * @return
	 */
	public static Double[] symmetry(Double[] genotype) {
		for(Double d : genotype) d = maxValue - (d - minValue);
		return genotype;
	}
	/**
	 * to mutate a genotype, mutate value is defined above
	 * @param genotype
	 * @return
	 */
	public static Double[] mutate(Double[] genotype) {
		for(Double d : genotype) {
			d+=((Math.random()-0.5)*mutationRange);
			if(d > maxValue) d = maxValue;
			if(d < minValue) d = minValue;
		}
		return genotype;
	}
	/**
	 * to switch a range of genome in a specific choromosome (exactly crossover)
	 * @param chromosome
	 * @return
	 */
	public static List<Double[]> crossover(List<Double[]> chromosome){
		Random random = new Random();
		int a = random.nextInt(genomeLength);
		int b = random.nextInt(genomeLength);
		if(a == b && a < genomeLength) b++;
		if(a == b && a > 0) b--;
		for(int i = (b<a?b:a); i < (a>b?a:b); i++) {
			chromosome.get(0)[i] += Math.random()*(chromosome.get(1)[i]-chromosome.get(0)[i]);
			chromosome.get(1)[i] -= Math.random()*(chromosome.get(1)[i]-chromosome.get(0)[i]);
		}
		return chromosome;
	}
	/**
	 * code genotype to phenotype
	 * @param genotype
	 * @return
	 */
	public static Object coding(Double[] genotype, double start, double end) {
		double avg = 0.0;
		for(double d : genotype) avg+=d;
		avg/=genomeLength;
		return (avg-minValue)/(maxValue-minValue)*(end-start)+start;
	}
}
