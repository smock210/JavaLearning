import org.junit.Test;
import ru.kda.GeoPosition;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GeoPositionTests {

    @Test
    public void testSimpleDegrees() {
        GeoPosition pos = new GeoPosition("55", "37");
        assertEquals(Math.toRadians(55), pos.getLatitude(), 1e-9);
        assertEquals(Math.toRadians(37), pos.getLongitude(), 1e-9);
    }

    @Test
    public void testDMSFormat() {
        GeoPosition pos = new GeoPosition("55(45'07'')", "37(36'56'')");

        double expectedLat = Math.toRadians(55 + 45.0/60 + 7.0/3600);
        double expectedLon = Math.toRadians(37 + 36.0/60 + 56.0/3600);

        assertEquals(expectedLat, pos.getLatitude(), 1e-9);
        assertEquals(expectedLon, pos.getLongitude(), 1e-9);
    }

    @Test
    public void testZeroMinutesSeconds() {
        GeoPosition pos = new GeoPosition("45(00'00'')", "90(00'00'')");

        assertEquals(Math.toRadians(45), pos.getLatitude(), 1e-9);
        assertEquals(Math.toRadians(90), pos.getLongitude(), 1e-9);
    }

    @Test
    public void testSingleDigitDegrees() {
        GeoPosition pos = new GeoPosition("5(30'15'')", "8(45'30'')");

        double expectedLat = Math.toRadians(5 + 30.0/60 + 15.0/3600);
        double expectedLon = Math.toRadians(8 + 45.0/60 + 30.0/3600);

        assertEquals(expectedLat, pos.getLatitude(), 1e-9);
        assertEquals(expectedLon, pos.getLongitude(), 1e-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullLatitude() {
        new GeoPosition(null, "37");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullLongitude() {
        new GeoPosition("55", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyLatitude() {
        new GeoPosition("", "37");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyLongitude() {
        new GeoPosition("55", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormat() {
        new GeoPosition("55x", "37");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDMSFormat() {
        new GeoPosition("55(45'7'')", ""); // seconds should be two digits
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMalformedDMS() {
        new GeoPosition("55(45'07')", "37"); // missing one quote
    }

    @Test
    public void testDecimalDegrees() {
        GeoPosition pos = new GeoPosition("55.5", "37.25");
        assertEquals(Math.toRadians(55.5), pos.getLatitude(), 1e-9);
        assertEquals(Math.toRadians(37.25), pos.getLongitude(), 1e-9);
    }

    @Test
    public void testGetters() {
        GeoPosition pos = new GeoPosition("55", "37");
        assertEquals(Math.toRadians(55), pos.getLatitude(), 1e-9);
        assertEquals(Math.toRadians(37), pos.getLongitude(), 1e-9);
    }

    @Test
    public void testToString() {
        GeoPosition pos = new GeoPosition("55", "37");
        String str = pos.toString();
        assertTrue(str.contains("долгота="));
        assertTrue(str.contains("широта="));
    }
}