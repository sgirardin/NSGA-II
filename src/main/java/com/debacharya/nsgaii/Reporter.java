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

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.GraphPlot;

import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Reporter {

    private static final int fileHash = ThreadLocalRandom.current().nextInt(10000, 100000);
    private static final StringBuilder writeContent = new StringBuilder();
    private static final GraphPlot allGenerationGraph = new GraphPlot("ALL GENERATIONS");

    public static boolean silent = false;
    public static boolean plotGraph = true;
    public static boolean plotCompiledGraphForEveryGeneration = true;
    public static boolean plotGraphForEveryGeneration = false;
    public static boolean writeToDisk = true;
    public static String filename = "NSGA-II-report-" + Reporter.fileHash + ".txt";

    public static void init(Configuration configuration) {

        if (silent && !writeToDisk) return;

        p("\n[ " + java.time.LocalDateTime.now() + " ]");
        p("\n** To stop reporter from printing to console, change Reporter.silent to true or call Configuration.beSilent()");
        p(
                "** Reporter is" +
                        (Reporter.writeToDisk ? "" : " not") +
                        " writing to disk" +
                        (Reporter.writeToDisk ? (" at " + Reporter.filename) : "") +
                        "."
        );

        if (plotGraph)
            p("** Plotting pareto front.");

        if (plotCompiledGraphForEveryGeneration)
            p("** Plotting compiled graph for all generations");

        if (plotGraphForEveryGeneration)
            p("** Plotting separate graph for every generation. !! Note that this might cause performance issues!");

        p("** To change this behavior, change Reporter.writeToDisk or call Cofiguration.writeToDisk(boolean).");
        p("** To change location and file name of where to save the file, change Reporter.filename.\n");
        p("------------------------------------------------");
        p("   NON-DOMINATED SORTING GENETIC ALGORITHM-II   ");
        p("------------------------------------------------");
        p(configuration.toString());
    }

    public static void reportGeneration(Population parent, Population child, int generation) {

        if (plotGraph && plotCompiledGraphForEveryGeneration)
            Reporter.allGenerationGraph.addData(child, "gen. " + generation);

        if (plotGraph && plotGraphForEveryGeneration)
            Reporter.plot2DPopulation(child, "GENERATION " + generation);

        if (silent && !writeToDisk) return;

        p("\n++++++++++++++++ GENERATION: " + generation + " ++++++++++++++++\n");
        p("[ START ]\n");
        p("Parent Population: " + parent.size());
        p("Child Population: " + child.size());
        p("\n======== PARENT ========\n");
        Reporter.reportPopulation(parent);
        p("\n======== CHILD ========\n");
        Reporter.reportPopulation(child);
        p("\n[ END ]");
    }

    public static void reportPopulation(Population population) {

        if (silent && !writeToDisk) return;

        for (Chromosome chromosome : population.getPopulace())
            Reporter.reportChromosome(chromosome);
    }

    public static void reportChromosome(Chromosome chromosome) {

        if (silent && !writeToDisk) return;

        Reporter.reportGeneticCode(chromosome.getChromosomeAlleles());

        p(">> " + chromosome.toString());
    }

    public static void reportGeneticCode(List<AbstractAllele> geneticCode) {

        if (silent && !writeToDisk) return;

        StringBuilder code = new StringBuilder("# [ ");

        for (AbstractAllele allele : geneticCode)
            code.append(allele.toString()).append(" ");

        p(code.append("]").toString());
    }

    public static void plot2DPopulation(Population population, String key) {

        if (!GraphPlot.isCompatible()) return;

        GraphPlot graph = new GraphPlot(key);

        graph.addData(population);
        graph.plot();
    }

    public static void plot2DParetoFront(Population finalParent) {
    	Population paretoParent = sortParetoFrontPopulation(finalParent);
        p("---PARETO FRONT---");
    	reportPopulation(paretoParent);
        Reporter.plot2DPopulation(paretoParent, "PARETO FRONT");
    }

    public static void plot2DGraphForAllGenerations() {

        if (!GraphPlot.isCompatible()) return;

        Reporter.allGenerationGraph.plot();
    }

    public static void plotGraphs(Population finalParent, List<Population> allGenerations) {

        if (!GraphPlot.isCompatible()) return;
        if (plotGraph) Reporter.plot2DParetoFront(finalParent);
        if (plotCompiledGraphForEveryGeneration) Reporter.plot2DGraphForAllGenerations();
    }

    public static void terminate(Population finalChild, List<Population> allParentGenerations) {

        Reporter.plotGraphs(finalChild, allParentGenerations);

        if (silent && !writeToDisk) return;

        p("------------------------------------------------");
        p("NSGA-II ENDED SUCCESSFULLY\n");

        if (writeToDisk) {
            Reporter.writeToFile();
            p("** Output saved at " + filename + "\n");
        }
    }

    private static void writeToFile() {

        try {
            FileWriter writer = new FileWriter(Reporter.filename);

            writer.write(Reporter.writeContent.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("\n!!! COULD NOT WRITE FILE TO DISK!\n\n");
        }
    }

    private static void p(String s) {
        if (writeToDisk) Reporter.writeContent.append(s).append(System.lineSeparator());
        if (!silent) System.out.println(s);
    }


    private static Population sortParetoFrontPopulation(Population finalParent) {
        SortedMap<Double, Chromosome> generationChromosomes = Service.returnSortedXAxisChromosomes(finalParent.getPopulace());
        SortedMap<Double, Chromosome> paretoChromosomes = new TreeMap<>();

        Chromosome worstSolution = generationChromosomes.get(generationChromosomes.lastKey());
        paretoChromosomes.put(worstSolution.getObjectiveValues().get(0), worstSolution);

        List<Chromosome> generationChromosomeList = new ArrayList(generationChromosomes.values());

        for (int i = generationChromosomes.size()-1 ; i >= 0 ; i--) {
            Chromosome chromosome = generationChromosomeList.get(i);
            if (dominatesInAll2Dimensions(chromosome, paretoChromosomes)) {
                List<Double> objectiveValues = chromosome.getObjectiveValues();
                double xValueChromosome = objectiveValues.get(0);
                paretoChromosomes.put(xValueChromosome, chromosome);

            }
        }

        return new Population(new ArrayList<>(paretoChromosomes.values()));
    }


    private static boolean dominatesInAll2Dimensions(Chromosome chromosomeToCheck, SortedMap<Double, Chromosome> paretoChromosomes) {
        boolean isDominatingInAllDimensions = false;
        List<Double> objectiveValues = chromosomeToCheck.getObjectiveValues();
        double xValueChromosomeToCheck = objectiveValues.get(0);
        double yValueChromosomeToCheck = objectiveValues.get(1);

        double highestXValueInCurrentParetoFront = paretoChromosomes.firstKey();

        Chromosome dominatedChromosome = paretoChromosomes.get(highestXValueInCurrentParetoFront);
        double yValueDominatedChromosome = dominatedChromosome.getObjectiveValues().get(1);
        if (yValueChromosomeToCheck > yValueDominatedChromosome) {
            isDominatingInAllDimensions = true;
        }

        return isDominatingInAllDimensions;
    }
}
