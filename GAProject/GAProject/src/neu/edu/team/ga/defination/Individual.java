package neu.edu.team.ga.defination;

public class Individual {

	private Integer[] genotype;
	
	public Individual() {}
	public Individual(Integer[] genotype) {
		this.genotype = genotype;
	}
	public Integer[] getGenotype() {
		return genotype;
	}
}
