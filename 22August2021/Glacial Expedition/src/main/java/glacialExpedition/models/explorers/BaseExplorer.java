package glacialExpedition.models.explorers;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.suitcases.Carton;
import glacialExpedition.models.suitcases.Suitcase;

public class BaseExplorer implements Explorer {
    private String name;
    private double energy;
    private Suitcase suitcase;

    public BaseExplorer(String name, double energy) {
        this.setName(name);
        this.setEnergy(energy);
        this.suitcase = new Carton();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.EXPLORER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setEnergy(double energy) {
        if (energy < 0) {
            throw new IllegalArgumentException(ExceptionMessages.EXPLORER_ENERGY_LESS_THAN_ZERO);
        }
        this.energy = energy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public boolean canSearch() {
        return energy > 0;
    }

    @Override
    public Suitcase getSuitcase() {
        return suitcase;
    }

    @Override
    public void search() {
        double decreasedEnergy = getEnergy() - 15;

        if (decreasedEnergy < 0) {
            decreasedEnergy = 0;
        }
        setEnergy(decreasedEnergy);
    }

    @Override
    public String toString() {
        return String.format(ConstantMessages.FINAL_EXPLORER_NAME, name) + System.lineSeparator() +
                String.format(ConstantMessages.FINAL_EXPLORER_ENERGY, energy) + System.lineSeparator() +
                String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS,
                        (suitcase.getExhibits().size() == 0 ?
                                "None" :
                                String.join(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER, suitcase.getExhibits())));
    }
}
