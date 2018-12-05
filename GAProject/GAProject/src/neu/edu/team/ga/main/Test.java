package neu.edu.team.ga.main;

import java.util.Arrays;

import neu.edu.team.ga.generation.Generate;
import neu.edu.team.ga.generation.PolyGenerate;
import neu.edu.team.ga.util.DataPreparation;
import neu.edu.team.ga.util.GenoOperation;
import neu.edu.team.ga.util.PolyGenoOperation;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataPreparation.initDatas();
//		Integer[] geno = Generate.generate();
//		System.out.println(Arrays.toString(geno));
//		System.out.println(1/GenoOperation.fitness(geno, DataPreparation.getDistances()));
		Double[] geno = PolyGenerate.generate();
		System.out.println(Arrays.toString(geno));
		System.out.println(PolyGenoOperation.f(geno));
	}

}
