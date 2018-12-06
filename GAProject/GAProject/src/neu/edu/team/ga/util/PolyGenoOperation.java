package neu.edu.team.ga.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import neu.edu.team.ga.defination.PolyIndividual;
import neu.edu.team.ga.defination.PolyPopulation;

public class PolyGenoOperation {
	
	
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
	private static final double maxValue = Double.valueOf(prop.getProperty("maxValue"));
	private static final double minValue = Double.valueOf(prop.getProperty("minValue"));
	private static final int variableNum = Integer.valueOf(prop.getProperty("variableNum"));
	private static final double actMin = Double.valueOf(prop.getProperty("actMin"));
	private static final double actMax = Double.valueOf(prop.getProperty("actMax"));
	private static final double mutateVal = Double.valueOf(prop.getProperty("mutateVal"));
	private static final double[] parameters = new double[variableNum];
	private static final double[] pows = new double[variableNum];
	static {
		//generate parameter of the polynomial: 0.9, 1.9, 2.9, 3.9, 4.9, -1.1, -0.1 and so on
		for(int i = 0; i < variableNum; i++) {
			parameters[i] = (i+1)%6 - i/5 - 0.1;
		}
		//generate the power of each variable, iterate from 1 to 6
		for(int i = 0; i < variableNum; i++) {
			pows[i] = i%6+1;
		}
		//the function is : 0.9*x1+1.9*x2^2+2.9*x3^3+3.9*x4^4+4.9*x5^5-1.1*x6^6-0.1*x7+0.9x8^2...-5.1*x30^6
		//and x1,x2,x3,...,x29,x30 range from -4 to 5
	}
	
	/**
	 * calculate the function value
	 * @param variables
	 * @return
	 */
	public static double f(Double[] genotype) {
		//first, interpret genotype
		Double[] variables = new Double[variableNum];
		for(int i = 0; i < variableNum; i++) {
			variables[i] = (genotype[i] - minValue)/(maxValue - minValue)*(actMax - actMin) + actMin;
		}
		//calculate the value
		double value = 0.0;
//		value = variables[0]*variables[0]+variables[1]*variables[1]+1;
//		value = variables[0]*variables[0]+variables[1]*variables[1]+variables[2]*variables[2]+variables[3]*variables[3]+1;
		for(int i = 0; i < variableNum; i++) value+=(parameters[i]*Math.pow(variables[i], pows[i]));
		return value;
	}
	
	/**
	 * method to get a new random genome, length equals to the total numbers of variables
	 * each variable has the same constrains(same range, just to make things more simple)
	 * rescales the genome value to -50~50. Actually genotype should not depend on specific problem. It's easy to build
	 * a reflection from genotype to phenotype: -50~50 -> -4~5
	 * @return
	 */
	public static Double[] generateGenome() {
		Double[] genome = new Double[variableNum];
		for(int i = 0; i < variableNum; i++) {
			genome[i] = (Math.random() - 0.5)*(maxValue - minValue);
		}
		return genome;
	}
	

	/**
	 * a special condition of mutation. Some value turns to its reflection
	 * @param genotype
	 * @return
	 */
	public static Double[] symmetry(Double[] genotype) {
		Random random = new Random();
		int times = random.nextInt(variableNum/2);
		for(int i = 0; i < times; i++) {
			double d = genotype[random.nextInt(variableNum)];
			genotype[random.nextInt(variableNum)] = maxValue - (d - minValue);
		}
		return genotype;
	}
	/**
	 * to mutate a genotype, mutate value is defined above
	 * @param genotype
	 * @return
	 */
	public static Double[] mutate(Double[] genotype) {
		Random random = new Random();
		int position = random.nextInt(variableNum);
		genotype[position]+=((Math.random()-0.5)*mutateVal);
		if(genotype[position] > maxValue) genotype[position] = maxValue;
		if(genotype[position] < minValue) genotype[position] = minValue;
		return genotype;
	}
	/**
	 * to mutate a range of genome in a specific choromosome (exactly crossover)
	 * the mutate value range between these two genome
	 * @param chromosome
	 * @return
	 */
	public static Double[] crossover(Double[] g1, Double[] g2){
		Random random = new Random();
		int start = random.nextInt(variableNum-1);
		int end = start + 1 + random.nextInt(variableNum-start);
		//which one is better, why
//		for(int i = start; i < end; i++) g1[i] = g2[i] + Math.random()*(g1[i]-g2[i]);
//		for(int i = start; i < end; i++) g1[i] = g2[i];
		for(int i = start; i < end; i++) {
			g1[i] = g2[i] + (Math.random()*2-1)*(g1[i]-g2[i]);
			if(g1[i] > maxValue) g1[i] = maxValue;
			if(g1[i] < minValue) g1[i] = minValue;
		}
		return Arrays.copyOf(g1, g1.length);
	}
	
	/**
	 * selection operation for a certain generation
	 * in this condition, fitness calculation occurs inside this method
	 * @param pop
	 * @param totalFit
	 */
	public static List<Double> selection(PolyPopulation pop, double cutoff) {
		int lastGenerationNum = pop.getGroupCapacity();
		List<PolyIndividual> nextGeneration = new ArrayList<>();
		List<Double> fitList = new ArrayList<>();
		List<Double> valueList = new ArrayList<>();
		List<Double> actValueList = new ArrayList<>();
		double max = 0.0;
		double totalFit = 0.0;
		//calculate the value of each individual in the population
		for(int i = 0; i < pop.getGroupCapacity(); i++) {
			double val = f(pop.getIndis().get(i).getGenotype());
			valueList.add(val);
			//get the Max value, use it
			if(i == 0) max = val;
			else if(val > max) max = val;
		}
		//based on the value insists now, calculate the fitness of each individual and their total fitness
		for(int i = 0; i < pop.getGroupCapacity(); i++) {
			double fit = 1/(max - valueList.get(i) + 10);
			fitList.add(fit);
			totalFit+=fit;
		}
		
		while(nextGeneration.size() < lastGenerationNum*cutoff) {			
			double selectionVal = Math.random();
			double accumulateVal = 0.0;
			for(int i = 0; i < pop.getGroupCapacity(); i++) {
				if(selectionVal >= accumulateVal && selectionVal < (accumulateVal+fitList.get(i)/totalFit)) {
					nextGeneration.add(pop.getIndis().get(i));
					actValueList.add(valueList.get(i));
					break;
				}
				accumulateVal+=(fitList.get(i)/totalFit);
			}
		}
		pop.setIndis(nextGeneration);
		pop.setGroupCapacity(nextGeneration.size());
		return actValueList;
	}
	
}
