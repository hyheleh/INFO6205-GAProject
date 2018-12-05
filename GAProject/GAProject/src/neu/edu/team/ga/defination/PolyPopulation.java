package neu.edu.team.ga.defination;

import java.util.ArrayList;
import java.util.List;

import neu.edu.team.ga.util.PolyGenoOperation;

public class PolyPopulation {

	private int groupCapacity;
	private List<PolyIndividual> indis = new ArrayList<>();
	
	public PolyPopulation(int population) {
		this.groupCapacity = population;
		for(int i = 0; i < population; i++)			
			indis.add(new PolyIndividual(PolyGenoOperation.generateGenome()));
	}

	public int getGroupCapacity() {
		return groupCapacity;
	}
	public void setGroupCapacity(int groupCapacity) {
		this.groupCapacity = groupCapacity;
	}
	public List<PolyIndividual> getIndis(){
		return indis;
	}
	public void setIndis(List<PolyIndividual> indis) {
		this.indis = indis;
	}
}
