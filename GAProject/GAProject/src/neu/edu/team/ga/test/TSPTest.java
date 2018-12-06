package neu.edu.team.ga.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import neu.edu.team.ga.util.DataPreparation;
import neu.edu.team.ga.util.GenoOperation;

public class TSPTest {

	@Test
	public void testMutation() {
		Integer[] genotype = GenoOperation.generateGenome(DataPreparation.getCitiesNum());
		Integer[] original = Arrays.copyOf(genotype, genotype.length);
		Integer[] newGenotype = GenoOperation.mutate(genotype);
		assertTrue(newGenotype[0]==1);
		assertTrue(newGenotype[newGenotype.length-1]==1);
		assertTrue(!Arrays.equals(original, newGenotype));
	}
	
	@Test
	public void testCrossover() {
		Integer[] g1 = {1,3,6,5,2,4,1};
		Integer[] g2 = {1,2,6,5,4,3,1};
		Integer[] g3 = GenoOperation.crossover(g1,g2);
		assertTrue(g3[0]==1);
		assertTrue(g3[6]==1);
		assertTrue(!(Arrays.equals(g2, g3) || Arrays.equals(g1, g3)));
		
	}
	
	@Test
	public void testFitness() {
		Integer [] g = {1,2,3,2,1};
		Double [][] distance = {{0.0,1.0,2.0},{1.0,0.0,3.0},{2.0,3.0,0.0}};
		double res = GenoOperation.fitness(g,distance);
		assertTrue(res == 0.125);
	}
}
