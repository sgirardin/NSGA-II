import ch.fhnw.mbis.aci.nsgaii.plugin.OnePointCrossover;
import ch.fhnw.mbis.aci.nsgaii.plugin.SchedulingPluginProvider;
import ch.fhnw.mbis.aci.nsgaii.population.TrainCrewSet;
import ch.fhnw.mbis.aci.nsgaii.population.TrainServiceSet;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.TrainCrewPopulationSize;
import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.CrossoverParticipantCreatorProvider;
import com.debacharya.nsgaii.plugin.PopulationProducer;
import com.debacharya.nsgaii.plugin.SinglePointMutation;
import com.debacharya.nsgaii.plugin.UniformCrossover;

public class NSGA2Test {

	public static void main(String[] args) {
		// Set parameters
		int populationSize = 3;
		int nbGenerations = 1;
		float mutationProbability = 0.4f;
		TrainCrewPopulationSize crewPopulationSize = TrainCrewPopulationSize.TWENTY;

		//Implemented Mutations and crossovers
		UniformCrossover uniformCrossover = new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection());
		OnePointCrossover onePointCrossover = new OnePointCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection());
		SinglePointMutation singlePointMutation = new SinglePointMutation(mutationProbability);


		//Initialize Population sets
		TrainServiceSet.setupServiceSet();
		TrainCrewSet.setupPopulation(crewPopulationSize);

		PopulationProducer populationProducer = SchedulingPluginProvider.defaultPopulationProducer();

		Configuration configuration = new Configuration(populationSize,nbGenerations, populationProducer, uniformCrossover, singlePointMutation);

		NSGA2 nsga2 = new NSGA2(configuration);
		Population paretoFront = nsga2.run();
	}
}
