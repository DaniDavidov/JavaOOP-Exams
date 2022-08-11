package glacialExpedition.core;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.StateRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private ExplorerRepository explorers;
    private StateRepository states;
    private int exploredStatesCount;

    public ControllerImpl() {
        this.explorers = new ExplorerRepository();
        this.states = new StateRepository();
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        switch (type) {
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.EXPLORER_INVALID_TYPE);
        }

        explorers.add(explorer);
        return String.format(ConstantMessages.EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        for (String exhibit : exhibits) {
            state.getExhibits().add(exhibit);
        }
        states.add(state);
        return String.format(ConstantMessages.STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer explorer = explorers.byName(explorerName);

        if (explorer == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        explorers.remove(explorer);
        return String.format(ConstantMessages.EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        State state = states.byName(stateName);
        List<Explorer> suitableExplorers = explorers.getCollection().stream()
                .filter(explorer -> explorer.getEnergy() > 50)
                .collect(Collectors.toList());
        Mission mission = new MissionImpl();

        if (suitableExplorers.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.STATE_EXPLORERS_DOES_NOT_EXISTS);
        }
        mission.explore(state, suitableExplorers);
        exploredStatesCount++;
        long countRetiredExplorers = suitableExplorers.stream()
                .filter(explorer -> !explorer.canSearch())
                .count();
        return String.format(ConstantMessages.STATE_EXPLORER, stateName, countRetiredExplorers);
    }

    @Override
    public String finalResult() {
        return String.format(ConstantMessages.FINAL_STATE_EXPLORED, exploredStatesCount) + System.lineSeparator() +
                ConstantMessages.FINAL_EXPLORER_INFO + System.lineSeparator() +
                explorers.getCollection().stream().map(Explorer::toString).collect(Collectors.joining(System.lineSeparator()));
    }

}
