package neu.edu.team.ga.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import neu.edu.team.ga.defination.PolyIndividual;
import neu.edu.team.ga.defination.PolyPopulation;
import neu.edu.team.ga.util.PolyGenoOperation;

public class PEPTest {

	@Test
	public void testMutation() {
		Double[] genotype = PolyGenoOperation.generateGenome();
		Double[] original = Arrays.copyOf(genotype, genotype.length);
		Double[] newGenotype = PolyGenoOperation.mutate(genotype);
		assertTrue(!Arrays.equals(original, newGenotype));
	}
	
	@Test
	public void testCrossover() {
		Double[] g1 = new Double[30];
		Double[] g2 = new Double[30];
		for(int i = 0; i < 30; i++) {
			g1[i] = 30.0 - i;
			g2[i] = (double)i;
		}
		Double[] g3 = PolyGenoOperation.crossover(g1,g2);
		assertTrue(!Arrays.equals(g2, g3) || !Arrays.equals(g1, g3));
		
	}
	
	@Test
	public void testSelection() {
		Double[] g1 = new Double[30];
		Double[] g2 = new Double[30];
		for(int i = 0; i < 30; i++) {
			g1[i] = -50.0;
			g2[i] = 50.0;
		}
		PolyPopulation pop = new PolyPopulation(2);
		pop.getIndis().add(new PolyIndividual(g1));
		pop.getIndis().add(new PolyIndividual(g2));
		List<Double> value = PolyGenoOperation.selection(pop, 0.5);
		assertTrue(value.size() == 1);
	}
}
