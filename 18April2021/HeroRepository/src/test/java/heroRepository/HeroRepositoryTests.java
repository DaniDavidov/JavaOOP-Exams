package heroRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class HeroRepositoryTests {
    private static final String HERO_NAME = "Dani";
    private static final int HERO_LEVEL = 17;
    private static final String HERO2_NAME = "Ivan";
    private static final int HERO2_LEVEL = 15;
    private static final String HERO3_NAME = "George";
    private static final int HERO3_LEVEL = 16;
    HeroRepository heroRepository;
    Hero hero;
    Hero hero2;
    Hero hero3;


    @Before
    public void setup() {
        this.heroRepository = new HeroRepository();
        this.hero = new Hero(HERO_NAME, HERO_LEVEL);
        this.hero2 = new Hero(HERO2_NAME, HERO2_LEVEL);
        this.hero3 = new Hero(HERO3_NAME, HERO3_LEVEL);
    }

    @Test
    public void testGetCountMethodShouldReturnTheCountOFHeroes() {
        Assert.assertEquals(0, heroRepository.getCount());
        fillRepository();
        Assert.assertEquals(3, heroRepository.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMethodShouldThrowExceptionWhenNullParamIsPassed() {
        heroRepository.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMethodThrowsExceptionIfTheHeroAlreadyExistsInTheCollection() {
        heroRepository.create(hero);
        heroRepository.create(new Hero(hero.getName(), HERO_LEVEL));
    }

    @Test
    public void testCreateMethodShouldAddTheHeroToTheCollection() {
        String output = heroRepository.create(hero);
        Assert.assertEquals(1, heroRepository.getHeroes().size());
        Assert.assertEquals(hero, heroRepository.getHero(HERO_NAME));
        Assert.assertEquals("Successfully added hero Dani with level 17", output);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveMethodThrowsWhenNullParamIsPassed() {
        heroRepository.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveMethodThrowsWhenNameIsWhiteSpace() {
        heroRepository.remove("");
    }

    @Test
    public void testRemoveMethodShouldRemoveTheHeroAndReturnTrue() {
        fillRepository();
        boolean bool = heroRepository.remove(HERO_NAME);
        Hero hero = heroRepository.getHero(HERO_NAME);
        Assert.assertNull(hero);
        Assert.assertTrue(bool);
    }

    @Test
    public void testRemoveMethodShouldReturnFalseWhenNoSuchHeroIsFound() {
        fillRepository();
        boolean bool = heroRepository.remove("Lily" );
        Assert.assertFalse(bool);
    }

    @Test
    public void testGetHeroWithHighestLevelReturnsNullWhenRepositoryEmpty() {
        Hero heroWithHighestLevel = heroRepository.getHeroWithHighestLevel();
        Assert.assertNull(heroWithHighestLevel);
    }

    @Test
    public void testGetHeroWithHighestLevelReturnsHero() {
        fillRepository();
        Hero heroWithHighestLevel = heroRepository.getHeroWithHighestLevel();
        Assert.assertEquals(hero, heroWithHighestLevel);
    }

    @Test
    public void testGetHeroReturnsNullWhenNoSuchHero() {
        fillRepository();
        Hero hero = heroRepository.getHero("Lily" );
        Assert.assertNull(hero);
    }

    @Test
    public void testGetHeroReturnsHero() {
        fillRepository();
        Hero foundHero = heroRepository.getHero(HERO_NAME);
        Assert.assertEquals(hero, foundHero);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetHeroesReturnsUnmodifiableCollection() {
        Collection<Hero> heroes = heroRepository.getHeroes();
        heroes.add(hero);
    }

    private void fillRepository() {
        heroRepository.create(hero);
        heroRepository.create(hero2);
        heroRepository.create(hero3);
    }
}
