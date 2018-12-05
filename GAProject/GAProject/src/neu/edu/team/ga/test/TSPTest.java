package neu.edu.team.ga.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import neu.edu.team.ga.util.DataPreparation;
import neu.edu.team.ga.util.GenoOperation;

public class TSPTest {

	@Test
	public void testMutation() {
		Integer[] genotype = GenoOperation.generateGenome(DataPreparation.getCitiesNum());
		Integer[] original = Arrays.copyOf(genotype, genotype.length);
		Integer[] newGenotype = GenoOperation.mutate(genotype);
		System.out.println(Arrays.toString(original));
		System.out.println(Arrays.toString(genotype));
		System.out.println(Arrays.toString(newGenotype));
	}
}
