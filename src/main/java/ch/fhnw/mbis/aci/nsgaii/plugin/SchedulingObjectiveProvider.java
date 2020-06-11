package ch.fhnw.mbis.aci.nsgaii.plugin;

import ch.fhnw.mbis.aci.nsgaii.objectivefunction.MaximizeEmployeeSatisfaction;
import ch.fhnw.mbis.aci.nsgaii.objectivefunction.MaximizePersonalCostOptmization;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;

import java.util.ArrayList;
import java.util.List;

public class SchedulingObjectiveProvider {

    public static List<AbstractObjectiveFunction> provideSCHObjectives() {

        List<AbstractObjectiveFunction> objectives = new ArrayList<>();
        objectives.add(new MaximizeEmployeeSatisfaction());
        objectives.add(new MaximizePersonalCostOptmization());

        return objectives;
    }
}
