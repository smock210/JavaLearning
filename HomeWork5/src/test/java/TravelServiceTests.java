import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import ru.kda.TravelService;
import ru.kda.CityInfo;
import ru.kda.GeoPosition;

public class TravelServiceTests {

    private TravelService service = new TravelService();

    @Test
    public void testAddAndCitiesNames() {
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
        service.add(new CityInfo("Kazan", new GeoPosition("55(48'00'')", "49(07'00'')")));

        List<String> names = service.citiesNames();
        assertNotNull(names);
        assertEquals(2, names.size());
        assertTrue(names.contains("Moscow"));
        assertTrue(names.contains("Kazan"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateCity() {
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
    }

    @Test
    public void testRemoveCity() {
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
        service.remove("Moscow");

        List<String> names = service.citiesNames();
        assertTrue(names == null || names.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistentCity() {
        service.remove("Unknown");
    }

    @Test
    public void testCitiesNamesReturnsNullWhenEmpty() {
        assertNull(service.citiesNames());
    }

    @Test
    public void testGetDistance() {
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
        service.add(new CityInfo("Orel", new GeoPosition("52(58'00'')", "36(03'00'')")));

        int distance = service.getDistance("Moscow", "Orel");
        assertTrue(distance > 0);
        assertTrue("Расстояние должно быть около 300 км", distance < 500);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDistanceSourceNotFound() {
        service.add(new CityInfo("Kazan", new GeoPosition("55(48'00'')", "49(07'00'')")));
        service.getDistance("Moscow", "Kazan");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDistanceDestinationNotFound() {
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
        service.getDistance("Moscow", "Orel");
    }

    @Test
    public void testGetCitiesNear() {
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
        service.add(new CityInfo("Orel", new GeoPosition("52(58'00'')", "36(03'00'')")));
        service.add(new CityInfo("Kazan", new GeoPosition("55(48'00'')", "49(07'00'')")));

        List<String> near = service.getCitiesNear("Moscow", 500);

        assertNotNull(near);
        assertTrue(near.contains("Orel"));
        assertFalse(near.contains("Kazan"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCitiesNearCityNotFound() {
        service.getCitiesNear("Unknown", 100);
    }

    @Test
    public void testGetCitiesNearNoCitiesInRange() {
        service.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "37(36'56'')")));
        service.add(new CityInfo("Kazan", new GeoPosition("55(48'00'')", "49(07'00'')")));
        int dist =service.getDistance("Moscow", "Kazan"); // 300 км, 500 км,
        service.add(new CityInfo("Orel", new GeoPosition("52(58'00'')", "36(03'00'')")));
        List<String> near = service.getCitiesNear("Moscow", 100);
        assertTrue(near.isEmpty());
    }
}