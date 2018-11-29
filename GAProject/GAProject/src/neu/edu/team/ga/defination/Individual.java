package neu.edu.team.ga.defination;

import java.util.List;
import java.util.Vector;

public class Individual {

	private Double[] genotype;
	private List<Double[]> chromosome = new Vector<>();
	
	public Individual() {}
	public Individual(Double[] genotype) {
		this.genotype = genotype;
	}
}
