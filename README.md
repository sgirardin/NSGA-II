# Train rostering using NSGA-II
**an NSGA-II implementation for assigning randomly Train Services to train crews**

**Original implementation of the algorithm: [here](https://github.com/onclave/NSGA-II) v3.0.1**
**Original readme.md [here](https://github.com/sgirardin/NSGA-II/wiki/Original-readme.md)**

## Installation
You will need:
1. Have Java >= 13 
1. Maven 3+
1. Then clone the repository locally

## How to run
### With your favorite IDE (prefered way)
1. Run the main class ```NSGA2Test.java```

### Command line
1. Go in project root folder with a terminal
2. Run ```mvn install -Dgpg.skip exec:java```
3. Do modification in the configuration and do the command at point above.

## Configuration
In the ```NSGA2Test:main``` method you can change the following parameters:
1. Population size
2. Number of generations
3. Mutation probability
4. Which crossover operator (for the moment we have UniformCrossover or OnePointMutation)
5. Size of Train Crew against which 63 Services will be distributed

## Data sets
* ```ch.fhnw.mbis.aci.nsgaii.sets.models.TrainService.java``` for the train services
* ```ch.fhnw.mbis.aci.nsgaii.sets.models.TrainCrew.java ```for the train drivers