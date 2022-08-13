package heroRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HeroRepository {
    private Collection<Hero> data;

    public HeroRepository() {
        this.data = new ArrayList<>();
    }

    public int getCount() {
        return this.data.size();
    }

    public String create(Hero hero) {
        if (hero == null) {
            throw new NullPointerException("Hero is null");
        }

        if (this.data.stream().anyMatch(h -> h.getName().equals(hero.getName()))) {
            throw new IllegalArgumentException("Hero with name %s already exists");
        }

        this.data.add(hero);
        return String.format("Successfully added hero %s with level %d", hero.getName(), hero.getLevel());
    }

    public boolean remove(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException("Name cannot be null");
        }

        return this.data.removeIf(h -> h.getName().equals(name));
    }

    public Hero getHeroWithHighestLevel() {

        return this.data
                .stream()
                .max(Comparator.comparingInt(Hero::getLevel))
                .orElse(null);
    }

    public Hero getHero(String name) {

        return this.data
                .stream()
                .filter(h -> h.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Collection<Hero> getHeroes() {
        return Collections.unmodifiableCollection(this.data);
    }
}
