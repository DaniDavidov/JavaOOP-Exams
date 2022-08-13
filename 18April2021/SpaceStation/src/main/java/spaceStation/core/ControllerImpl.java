package spaceStation.core;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;

    public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        switch (type) {
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_INVALID_TYPE);
        }

        astronautRepository.add(astronaut);
        return String.format(ConstantMessages.ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        for (String item : items) {
            planet.getItems().add(item);
        }
        planetRepository.add(planet);
        return String.format(ConstantMessages.PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        Astronaut astronaut = astronautRepository.findByName(astronautName);
        if (astronaut == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }
        astronautRepository.remove(astronaut);
        return String.format(ConstantMessages.ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        Mission mission = new MissionImpl();
        Planet planet = planetRepository.findByName(planetName);
        List<Astronaut> suitableAstronauts = astronautRepository.getModels().stream()
                .filter(astronaut -> astronaut.getOxygen() > 60)
                .collect(Collectors.toList());

        if (suitableAstronauts.size() < 1) {
            throw new IllegalArgumentException(ExceptionMessages.PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
        mission.explore(planet, suitableAstronauts);
        long count = suitableAstronauts.stream().filter(astronaut -> !astronaut.canBreath()).count();

        return String.format(ConstantMessages.PLANET_EXPLORED, planetName, count);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        long count = planetRepository.getModels().stream().filter(planet -> planet.getItems().isEmpty()).count();
        sb.append(String.format(ConstantMessages.REPORT_PLANET_EXPLORED, count)).append(System.lineSeparator())
                        .append(ConstantMessages.REPORT_ASTRONAUT_INFO).append(System.lineSeparator());

        sb.append(astronautRepository.getModels().stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator())));
        return sb.toString();
    }
}
