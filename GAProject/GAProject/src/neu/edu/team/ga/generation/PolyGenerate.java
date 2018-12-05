package neu.edu.team.ga.generation;

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
import neu.edu.team.ga.util.PolyGenoOperation;

public class PolyGenerate {

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
	private static final double crossoverProbability = Double.valueOf(prop.getProperty("crossoverProbability"));
	private static final double mutationProbability = Double.valueOf(prop.getProperty("mutationProbability"));
	private static final double cutoff = Double.valueOf(prop.getProperty("cutoff"));
	private static final int population = Integer.valueOf(prop.getProperty("initialPopulation"));
	private static final int variableNum = Integer.valueOf(prop.getProperty("variableNum"));
	private static final int matingGeneration = Integer.valueOf(prop.getProperty("matingGeneration"));
	private static final int maxGeneration = Integer.valueOf(prop.getProperty("maxGeneration"));
	
	/**
	 * the basic method to operate on Polynomial Maximum Value problem
	 * @return
	 */
	public static Double[] generate() {
		int gen = 1;
		Random random = new Random();
		PolyPopulation pop = new PolyPopulation(population);
		Double[] result = new Double[variableNum];
		List<Double> valueList = new ArrayList<>();
		double largest = 0.0;
		//Terminal condition
		while(gen < maxGeneration && pop.getGroupCapacity() > 1) {
			System.out.println(gen);
			for(int m = 0; m < pop.getGroupCapacity(); m++) {
				PolyIndividual ind = pop.getIndis().get(m);
				//mutate happens
				if(Math.random() <= mutationProbability) PolyGenoOperation.mutate(ind.getGenotype());
				//crossover happens
				if(Math.random() <= crossoverProbability) {
					int mate = random.nextInt(pop.getGroupCapacity());
					while(mate == m) {
						mate = random.nextInt(pop.getGroupCapacity());
					}
					//we get 2 generation of them
					for(int i = 0; i < matingGeneration; i++) {
						pop.getIndis().add(
								new PolyIndividual(PolyGenoOperation.crossover(
										ind.getGenotype(), pop.getIndis().get(mate).getGenotype()
								))
						);
						pop.setGroupCapacity(pop.getGroupCapacity()+1);
					}
				}
			}
			System.out.println(pop.getGroupCapacity());
			//selection
			valueList = PolyGenoOperation.selection(pop, cutoff);
			//output the largest value now
			//the largest value will be the value of 'largest' after the last generation
			for(int i = 0; i < pop.getGroupCapacity(); i++) {
				if((valueList.get(i) > largest) || (gen == 1)) {
					largest = valueList.get(i);
					for(int j = 0; j < variableNum; j++) {
						result[j] = pop.getIndis().get(i).getGenotype()[j];
					}
				}
			}
			System.out.println(largest);
			System.out.println(Arrays.toString(result));
			gen++;
		}
		return result;
	}
}
