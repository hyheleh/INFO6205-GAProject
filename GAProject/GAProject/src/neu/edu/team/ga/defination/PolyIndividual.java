package neu.edu.team.ga.defination;

public class PolyIndividual {

	private Double[] genotype;
	
	public PolyIndividual() {}
	public PolyIndividual(Double[] genotype) {
		this.genotype = genotype;
	}
	public Double[] getGenotype() {
		return genotype;
	}
	public void setGenotype(Double[] genotype) {
		this.genotype = genotype;
	}
}
