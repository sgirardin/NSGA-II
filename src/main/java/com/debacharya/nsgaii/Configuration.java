/*
 * MIT License
 *
 * Copyright (c) 2019 Debabrata Acharya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.debacharya.nsgaii;

import ch.fhnw.mbis.aci.nsgaii.plugin.SchedulingObjectiveProvider;
import ch.fhnw.mbis.aci.nsgaii.plugin.SchedulingPluginProvider;
import ch.fhnw.mbis.aci.nsgaii.population.TrainCrewSet;
import ch.fhnw.mbis.aci.nsgaii.population.TrainServiceSet;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import com.debacharya.nsgaii.plugin.*;

import java.util.List;

public class Configuration {

	public static final String CONFIGURATION_NOT_SETUP = "The NSGA-II configuration object is not setup properly!";

	public static final int HARD_CONSTRAINT_PENALTY = - 1000;
	public static final float CHROMOSOME_MUTATION_PROBABILITY = 0.04f;

	public static List<AbstractObjectiveFunction> objectives;

	private int populationSize;
	private int generations;
	private int chromosomeLength;
	private int geneLength;
	private PopulationProducer populationProducer;
	private ChildPopulationProducer childPopulationProducer;
	private GeneticCodeProducer geneticCodeProducer;
	private AbstractCrossover crossover;
	private AbstractMutation mutation;


	public Configuration(int populationSize,
						 int nbGenerations,
						 PopulationProducer populationProducer,
						 AbstractCrossover crossover,
						 AbstractMutation mutation) {
		this(populationSize, nbGenerations,
			populationProducer,
			SchedulingPluginProvider.defaultChildPopulationProducer(),
			SchedulingPluginProvider.defaultGeneticCodeProducer(),
			SchedulingObjectiveProvider.provideSCHObjectives(),
			crossover,
			mutation,
			false,
			true,
			true
		);
	}

	public Configuration(int populationSize,
						 int generations,
						 PopulationProducer populationProducer,
						 ChildPopulationProducer childPopulationProducer,
						 GeneticCodeProducer geneticCodeProducer,
						 List<AbstractObjectiveFunction> objectives,
						 AbstractCrossover crossover,
						 AbstractMutation mutation,
						 boolean silent,
						 boolean plotGraph,
						 boolean writeToDisk) {

		this.populationSize = populationSize;
		this.generations = generations;
		this.geneLength = calculateGeneSize() * TrainServiceSet.getPopulation().size();
		this.chromosomeLength = this.geneLength;
		this.populationProducer = populationProducer;
		this.childPopulationProducer = childPopulationProducer;
		this.geneticCodeProducer = geneticCodeProducer;
		Configuration.objectives = objectives;
		this.crossover = crossover;
		this.mutation = mutation;

		Reporter.silent = silent;
		Reporter.plotGraph = plotGraph;
		Reporter.writeToDisk = writeToDisk;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		if(populationSize < 1)
			throw new UnsupportedOperationException("Population size cannot be less than 1.");
		this.populationSize = populationSize;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		if(generations < 1)
			throw new UnsupportedOperationException("Generations cannot be less than 1.");
		this.generations = generations;
	}

	public int getChromosomeLength() {
		return chromosomeLength;
	}

	public void setChromosomeLength(int chromosomeLength) {
		if(chromosomeLength < 1)
			throw new UnsupportedOperationException("Chromosome length cannot be less than 1.");
		this.chromosomeLength = chromosomeLength;
	}

	public PopulationProducer getPopulationProducer() {
		return populationProducer;
	}

	public void setPopulationProducer(PopulationProducer populationProducer) {
		this.populationProducer = populationProducer;
	}

	public ChildPopulationProducer getChildPopulationProducer() {
		return childPopulationProducer;
	}

	public void setChildPopulationProducer(ChildPopulationProducer childPopulationProducer) {
		this.childPopulationProducer = childPopulationProducer;
	}

	public GeneticCodeProducer getGeneticCodeProducer() {
		return geneticCodeProducer;
	}

	public void setGeneticCodeProducer(GeneticCodeProducer geneticCodeProducer) {
		this.geneticCodeProducer = geneticCodeProducer;
	}

	public AbstractCrossover getCrossover() {
		return crossover;
	}

	public void setCrossover(AbstractCrossover crossover) {
		this.crossover = crossover;
	}

	public AbstractMutation getMutation() {
		return mutation;
	}

	public void setMutation(AbstractMutation mutation) {
		this.mutation = mutation;
	}

	public boolean isSetup() {
		return (
			this.populationSize != 0 				&&
			this.generations != 0 					&&
			this.chromosomeLength != 0 				&&
			this.populationProducer != null 		&&
			this.childPopulationProducer != null	&&
			this.geneticCodeProducer != null 		&&
			Configuration.objectives != null		&&
			!Configuration.objectives.isEmpty()
		);
	}

	public void beSilent() {
		Reporter.silent = true;
	}

	public void plotGraph(boolean value) {
		Reporter.plotGraph = value;
	}

	public void writeToDisk(boolean value) {
		Reporter.writeToDisk = value;
	}

	public void completeSilence() {
		this.beSilent();
		this.plotGraph((false));
		this.writeToDisk(false);
	}

	@Override
	public String toString() {
		return "\nPopulation Size: " 																				+
				this.populationSize 																				+
				" [" 																								+
				(this.populationSize > 0 ? "valid" : "invalid") 													+
				"]" 																								+
				"\nGenerations: " 																					+
				this.generations 																					+
				" [" 																								+
				(this.generations > 0 ? "valid" : "invalid")														+
				"]" 																								+
				"\nGene Length: " 																					+
				this.geneLength				 																		+
				" [" 																								+
				(this.geneLength > 0 ? "valid" : "invalid") 														+
				"]" 																								+
				"\nChromosome Length: " 																			+
				this.chromosomeLength				 																+
				" [" 																								+
				(this.chromosomeLength > 0 ? "valid" : "invalid") 													+
				"]" 																								+
				"\nPopulation Producer: " 																			+
				"[" 																								+
				(this.populationProducer != null ? "valid" : "invalid") 											+
				"]" 																								+
				"\nChild Population Producer: " 																	+
				"[" 																								+
				(this.childPopulationProducer != null ? "valid" : "invalid")				 						+
				"]" 																								+
				"\nGenetic Code Producer: " 																		+
				"[" 																								+
				(this.geneticCodeProducer != null ? "valid" : "invalid") 											+
				"]" 																								+
				"\nObjectives: " 																					+
				"[" 																								+
				((Configuration.objectives != null && !Configuration.objectives.isEmpty()) ? "valid" : "invalid")	+
				"]"																									+
				"\nCrossover Operator: " 																			+
				"[" 																								+
				(this.crossover != null ? "provided" : "not provided") 												+
				"]" 																								+
				"\nMutation Operator: " 																			+
				"[" 																								+
				(this.mutation != null ? "provided" : "not provided") 												+
				"]";
	}

	public static int calculateGeneSize(){
		int geneSizeInBinary = 0;
		geneSizeInBinary = Integer.toBinaryString(TrainCrewSet.getPopulation().size()).length();

		return geneSizeInBinary;
	}

}
