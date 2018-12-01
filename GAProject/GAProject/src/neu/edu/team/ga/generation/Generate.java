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

import neu.edu.team.ga.defination.Individual;
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
	private static final int population = Integer.valueOf(prop.getProperty("initialPopulation"));
	private static final int matingGeneration = Integer.valueOf(prop.getProperty("matingGeneration"));
	private static final int maxGeneration = Integer.valueOf(prop.getProperty("maxGeneration"));
	
	public static Integer[] generate() {
		int gen = 1;
		Random random = new Random();
		Population pop = new Population(population);
		double bestFit = 0.0;
		int bestIndex = 0;
		while(gen < maxGeneration) {
			System.out.println(gen);
			List<Double> fitnesses = new ArrayList<Double>();
			int position = 0;
			double totalFit = 0.0;
			for(int m = 0; m < pop.getGroupCapacity(); m++) {
				Individual ind = pop.getIndis().get(m);
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
								new Individual(GenoOperation.crossover(
										ind.getGenotype(), pop.getIndis().get(mate).getGenotype()
								))
						);
						pop.setGroupCapacity(pop.getGroupCapacity()+1);
					}
				}
				fitnesses.add(GenoOperation.fitness(ind.getGenotype(), DataPreparation.getDistances()));
				totalFit+=fitnesses.get(position);
				position++;
			}
			double avgFit = totalFit/(position+1);
			System.out.println(1/avgFit);
			System.out.println(pop.getGroupCapacity());
			//selection
			for(int i = 0; i < position; i++){
				if(fitnesses.get(i) < avgFit) {
					pop.getIndis().remove(i);
					fitnesses.remove(i);
					pop.setGroupCapacity(pop.getGroupCapacity()-1);
					i--;
					position--;
				}
			}
			//the largest fitness of the last generation population will be the result
			if(gen == maxGeneration-1) {
				for(int i = 0; i < fitnesses.size(); i++) {
					if(fitnesses.get(i) > bestFit) {
						bestFit = fitnesses.get(i);
						bestIndex = i;
					}
				}
			}
			gen++;
		}
		return pop.getIndis().get(bestIndex).getGenotype();
	}
}
