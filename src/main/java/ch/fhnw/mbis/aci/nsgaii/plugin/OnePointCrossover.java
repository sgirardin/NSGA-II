package ch.fhnw.mbis.aci.nsgaii.plugin;

import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.AbstractCrossover;
import com.debacharya.nsgaii.plugin.CrossoverParticipantCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OnePointCrossover extends AbstractCrossover {

    public OnePointCrossover(CrossoverParticipantCreator crossoverParticipantCreator) {
        super(crossoverParticipantCreator);
    }

    @Override
    public List<Chromosome> perform(Population population) {

        List<Chromosome> result = new ArrayList<>();
        List<Chromosome> selected = this.crossoverParticipantCreator.create(population);

        if(this.shouldPerformCrossover())
            for(int i = 0; i < 2; i++)
                result.add(
                        this.prepareChildChromosome(
                                selected.get(0),
                                selected.get(1)
                        )
                );
        else {
            result.add(selected.get(0).getCopy());
            result.add(selected.get(1).getCopy());
        }

        return result;
    }

    private Chromosome prepareChildChromosome(Chromosome chromosome1, Chromosome chromosome2) {

        List<AbstractAllele> geneticCode = new ArrayList<>();

        int crossOverLocus = findCrossoverAllele(chromosome1.getChromosomeAlleles().size());

        geneticCode.addAll(chromosome1.getChromosomeAlleles().subList(0, crossOverLocus));
        geneticCode.addAll(chromosome2.getChromosomeAlleles().subList(crossOverLocus, chromosome2.getChromosomeAlleles().size()));


        return new Chromosome(geneticCode);
    }

    private int findCrossoverAllele(int chromosomeSize){
        int geneSize = Configuration.calculateGeneSize();
        int randomLocus = ThreadLocalRandom.current().nextInt(0, chromosomeSize/geneSize);
        return randomLocus * geneSize;
    }
}
