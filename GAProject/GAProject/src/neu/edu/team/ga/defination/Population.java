package neu.edu.team.ga.defination;

import java.util.ArrayList;
import java.util.List;

import neu.edu.team.ga.util.DataPreparation;
import neu.edu.team.ga.util.GenoOperation;

public class Population {

	private int groupCapacity;
	private List<TSPIndividual> indis = new ArrayList<>();
	
	public Population(int population) {
		this.groupCapacity = population;
		for(int i = 0; i < population; i++)			
			indis.add(new TSPIndividual(GenoOperation.generateGenome(DataPreparation.getCitiesNum())));
	}

	public int getGroupCapacity() {
		return groupCapacity;
	}
	public void setGroupCapacity(int groupCapacity) {
		this.groupCapacity = groupCapacity;
	}
	public List<TSPIndividual> getIndis(){
		return indis;
	}
	public void setIndis(List<TSPIndividual> indis) {
		this.indis = indis;
	}
}
