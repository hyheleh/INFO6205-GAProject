package neu.edu.team.ga.defination;

public class TSPIndividual {

	private Integer[] genotype;
	
	public TSPIndividual() {}
	public TSPIndividual(Integer[] genotype) {
		this.genotype = genotype;
	}
	public Integer[] getGenotype() {
		return genotype;
	}
	public void setGenotype(Integer[] genotype) {
		this.genotype = genotype;
	}
}
