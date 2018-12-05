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
}
