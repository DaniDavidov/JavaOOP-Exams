package spaceStation.models.astronauts;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.bags.Backpack;
import spaceStation.models.bags.Bag;

public abstract class BaseAstronaut implements Astronaut {
    private String name;
    private double oxygen;
    private Bag bag;

    public BaseAstronaut(String name, double oxygen) {
        this.setName(name);
        this.setOxygen(oxygen);
        this.bag = new Backpack();
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.ASTRONAUT_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setOxygen(double oxygen) {
        if (oxygen < 0) {
            throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_OXYGEN_LESS_THAN_ZERO);
        }
        this.oxygen = oxygen;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getOxygen() {
        return oxygen;
    }

    @Override
    public boolean canBreath() {
        return oxygen > 0;
    }

    @Override
    public Bag getBag() {
        return bag;
    }

    @Override
    public void breath() {
        double decreasedOxygen = getOxygen() - 10;

        if (decreasedOxygen < 0) {
            decreasedOxygen = 0;
        }
        this.oxygen = decreasedOxygen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(ConstantMessages.REPORT_ASTRONAUT_NAME, name)).append(System.lineSeparator())
                .append(String.format(ConstantMessages.REPORT_ASTRONAUT_OXYGEN, oxygen)).append(System.lineSeparator());

        String itemsInfo = "";
        if (bag.getItems().isEmpty()) {
            itemsInfo = "none";
        } else {
            itemsInfo = String.join(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, bag.getItems());
        }
        sb.append(String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS, itemsInfo));
        return sb.toString();
    }
}
