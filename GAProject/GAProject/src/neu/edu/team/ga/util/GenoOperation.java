package neu.edu.team.ga.util;

import java.util.Random;

public class GenoOperation {
	
	/**
	 * method to get a new random genome
	 * @return
	 */
	public static Integer[] generateGenome(int length) {
		Integer[] genome = new Integer[length+1];
		for(int i = 0; i < length; i++) {
			genome[i] = i+1;
		}
		//randomly switch two value of the genome
		for(int i = 0; i < length*2; i++) {
			Random random = new Random();
			shuffle(random.nextInt(length-1)+1, random.nextInt(length-1)+1, genome);
		}
		genome[length] = genome[0];//back to the start point
		return genome;
	}
	

	/**
	 * get a 'reflection' of current genotype 
	 * @param genotype
	 * @return
	 */
	public static Integer[] symmetry(Integer[] genotype) {
		for(Integer d : genotype) d = genotype.length - d;
		return genotype;
	}
	/**
	 * to mutate a genotype, mutate value is defined above
	 * @param genotype
	 * @return
	 */
	public static Integer[] mutate(Integer[] genotype) {
		Random random = new Random();
		shuffle(random.nextInt(genotype.length-2)+1, random.nextInt(genotype.length-2)+1, genotype);
		return genotype;
	}
	/**
	 * to switch a range of genome in a specific choromosome (exactly crossover)
	 * @param chromosome
	 * @return
	 */
	public static Integer[] crossover(Integer[] g1, Integer[] g2){
		Integer[] g = new Integer[g1.length];
		for(int i = 0; i < g1.length; i++) g[i] = g1[i];
		Random random = new Random();
		int start = random.nextInt(g1.length - 2) + 1;
		int end = random.nextInt(g1.length - 1 - start) + start + 1;
		Integer[] sub = new Integer[end - start + 1];
		for(int i = 0; i < end - start + 1; i++) {
			sub[i] = g1[start+i];
		}
		rearrange(g2, sub, start, end);
		return g;
	}

	/**
	 * code genotype to phenotype
	 * @param genotype
	 * @return
	 */
	public static double fitness(Integer[] genotype, Double[][] distances) {
		double distance = 0d;
		for(int i = 0; i < genotype.length-1; i++) {
			distance += distances[genotype[i]-1][genotype[i+1]-1];
		}
		return 1.0/distance;
	}
	
	private static void shuffle(int i, int j, Integer[] array) {
		// TODO Auto-generated method stub
		int key = array[i];
		array[i] = array[j];
		array[j] = key;
	}
	
	private static void rearrange(Integer[] g2, Integer[] sub, int start, int end) {
		// TODO Auto-generated method stub
		for(int i = 0; i < g2.length-1; i++) {
			for(int j = 0; j < sub.length; j++) {
				if(g2[i] == sub[j] && i-start != j) {
					shuffle(i, start+j, g2);
					break;
				}
			}
		}
	}
}
