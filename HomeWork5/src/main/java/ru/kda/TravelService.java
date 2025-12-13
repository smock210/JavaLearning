package ru.kda;

import java.util.ArrayList;
import java.util.List;

/**
 * Travel Service.
 */
public class TravelService {

    // do not change type
    private final List<CityInfo> cities = new ArrayList<>();

    /**
     * Append city info.
     *
     * @param cityInfo - city info
     * @throws IllegalArgumentException if city already exists
     */
    public void add(CityInfo cityInfo) {
        // do something
        if (cities.stream().anyMatch(city -> city.getName().equals(cityInfo.getName()))) {
            throw new IllegalArgumentException("Город уже существует");
        } else {
            cities.add(cityInfo);
        }
    }

    /**
     * remove city info.
     *
     * @param cityName - city name
     * @throws IllegalArgumentException if city doesn't exist
     */
    public void remove(String cityName) {
        // do something
        if (!cities.removeIf(city -> city.getName().equals(cityName))) {
            throw new IllegalArgumentException("Удаляемый город не существует");
        }
        /*if (cities.contains(cityName)) {
            cities.remove(cityName);
        } else {
            throw new IllegalArgumentException("Удаляемый город не существует");
        }*/
    }

    /**
     * Get cities names.
     */
    public List<String> citiesNames() {

        if (cities.isEmpty()) {
            return null;
        } else {
            List<String> names = new ArrayList<>();
            cities.stream().
                    map(CityInfo::getName).
                    forEach(names::add);
            return names;
        }
    }

    /**
     * Get distance in kilometers between two cities.
     * https://www.kobzarev.com/programming/calculation-of-distances-between-cities-on-their-coordinates/
     *
     * @param srcCityName  - source city
     * @param destCityName - destination city
     * @throws IllegalArgumentException if source or destination city doesn't exist.
     */
    public int getDistance(String srcCityName, String destCityName) {

        final double EARTH_RADIUS_KM = 6371.0; // Радиус Земли в километрах
        CityInfo sitySrc = cities.stream().
                filter(city -> city.getName().equals(srcCityName)).
                findFirst().orElseThrow(() -> new IllegalArgumentException("Город "+srcCityName+"не найден"));

        CityInfo sityDest = cities.stream().
                filter(city -> city.getName().equals(destCityName)).
                findFirst().orElseThrow(() -> new IllegalArgumentException("Город " +destCityName+ " не найден"));

        // Convert coordinates to radians
        double test =sitySrc.getPosition().getLatitude();
        double lat1 = sitySrc.getPosition().getLatitude();
        double lat2 = sityDest.getPosition().getLatitude();
        double lon1 = sitySrc.getPosition().getLongitude();
        double lon2 = sityDest.getPosition().getLongitude();

        // Cosines and sines of latitudes and longitude difference
        double cosLat1 = Math.cos(lat1);
        double cosLat2 = Math.cos(lat2);
        double sinLat1 = Math.sin(lat1);
        double sinLat2 = Math.sin(lat2);
        double deltaLon = lon2 - lon1;
        double cosDeltaLon = Math.cos(deltaLon);
        double sinDeltaLon = Math.sin(deltaLon);

        // Great circle distance calculations
        double y = Math.sqrt(
                Math.pow(cosLat2 * sinDeltaLon, 2) +
                        Math.pow(cosLat1 * sinLat2 - sinLat1 * cosLat2 * cosDeltaLon, 2)
        );
        double x = sinLat1 * sinLat2 + cosLat1 * cosLat2 * cosDeltaLon;

        double angularDistance = Math.atan2(y, x);
        double distance = angularDistance * EARTH_RADIUS_KM;

        return (int) Math.round(distance);
    }

    /**
     * Get all cities near current city in radius.
     *
     * @param cityName - city
     * @param radius   - radius in kilometers for search
     * @throws IllegalArgumentException if city with cityName city doesn't exist.
     */
    public List<String> getCitiesNear(String cityName, int radius) {
        CityInfo sitySrc = cities.stream().
                filter(city -> city.getName().equals(cityName)).
                findFirst().orElseThrow(() -> new IllegalArgumentException("Город "+cityName+"не найден"));

        return cities.stream()
                .filter(city -> !city.getName().equals(cityName)) // исключаем сам город
                .filter(city -> getDistance(cityName, city.getName()) <= radius) // расстояние <= radius
                .map(CityInfo::getName) // извлекаем имена
                .collect(java.util.stream.Collectors.toList()); // собираем в List
    }
}
