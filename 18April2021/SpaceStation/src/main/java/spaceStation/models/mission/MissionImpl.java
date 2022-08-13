package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission {

    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        ArrayDeque<String> items = new ArrayDeque<>(planet.getItems());
        for (Astronaut astronaut : astronauts) {
            while (astronaut.canBreath() && !items.isEmpty()) {
                String item = items.poll();
                astronaut.breath();
                astronaut.getBag().getItems().add(item);
                planet.getItems().remove(item);
            }
        }
    }
}
