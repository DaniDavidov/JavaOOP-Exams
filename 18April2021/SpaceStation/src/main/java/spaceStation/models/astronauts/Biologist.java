package spaceStation.models.astronauts;

public class Biologist extends BaseAstronaut {
    private static final double INITIAL_OXYGEN = 70;

    public Biologist(String name) {
        super(name, INITIAL_OXYGEN);
    }

    @Override
    public void breath() {
        double decreasedOxygen = getOxygen() - 5;

        if (decreasedOxygen < 0) {
            decreasedOxygen = 0;
        }
        this.setOxygen(decreasedOxygen);
    }
}
