package neu.edu.team.ga.generation;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import neu.edu.team.ga.defination.TSPIndividual;
import neu.edu.team.ga.defination.Population;
import neu.edu.team.ga.util.DataPreparation;
import neu.edu.team.ga.util.GenoOperation;

public class Generate {

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
	private static final int matingGeneration = Integer.valueOf(prop.getProperty("matingGeneration"));
	private static final int maxGeneration = Integer.valueOf(prop.getProperty("maxGeneration"));
	
	/**
	 * the basic method to operate on TSP problem
	 * @return
	 */
	public static Integer[] generate() {
		int gen = 1;
		Random random = new Random();
		Population pop = new Population(population);
		Integer[] result = new Integer[DataPreparation.getCitiesNum()+1];
		double bestFit = 0.0;
		//Terminal condition
		while(gen < maxGeneration && pop.getGroupCapacity() > 1) {
			System.out.println(gen);
			List<Double> fitnesses = new ArrayList<Double>();
			double totalFit = 0.0;
			for(int m = 0; m < pop.getGroupCapacity(); m++) {
				TSPIndividual ind = pop.getIndis().get(m);
				//mutate happens
				if(Math.random() <= mutationProbability) GenoOperation.mutate(ind.getGenotype());
				//crossover happens
				if(Math.random() <= crossoverProbability) {
					int mate = random.nextInt(pop.getGroupCapacity());
					while(mate == m) {
						mate = random.nextInt(pop.getGroupCapacity());
					}
					//we get 2 generation of them
					for(int i = 0; i < matingGeneration; i++) {
						pop.getIndis().add(
								new TSPIndividual(GenoOperation.crossover(
										ind.getGenotype(), pop.getIndis().get(mate).getGenotype()
								))
						);
						pop.setGroupCapacity(pop.getGroupCapacity()+1);
					}
				}
				fitnesses.add(GenoOperation.fitness(ind.getGenotype(), DataPreparation.getDistances()));
				totalFit+=fitnesses.get(m);
			}
			System.out.println(pop.getGroupCapacity());
			//selection
			fitnesses = GenoOperation.selection(pop, fitnesses, cutoff, totalFit);
			//output the largest fitness of each generation
			//the largest fitness of the last generation population will be the result
			for(int i = 0; i < fitnesses.size(); i++) {
				if(fitnesses.get(i) > bestFit) {
					bestFit = fitnesses.get(i);
					for(int j = 0; j < DataPreparation.getCitiesNum()+1; j++) {
						result[j] = pop.getIndis().get(i).getGenotype()[j];
					}
				}
			}
			System.out.println(1/bestFit);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resultTSP.csv"), true))){
				bw.write(gen+","+(1/bestFit)+",\n");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			gen++;
		}
		return result;
	}
}
