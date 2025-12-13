package ru.kda;

/**
 * Geo position.
 */
public class GeoPosition {

    /**
     * Широта в радианах.
     */
    private double latitude;

    /**
     * Долгота в радианах.
     */
    private double longitude;

    /**
     * Ctor.
     *
     * @param latitudeGradus  - latitude in gradus
     * @param longitudeGradus - longitude in gradus
     *                        Possible values: 55, 55(45'07''), 59(57'00'')
     */
    public GeoPosition(String latitudeGradus, String longitudeGradus) {
        // parse and set latitude and longitude in radian
        this.latitude = Math.toRadians(parseCoordinate(latitudeGradus));
        this.longitude = Math.toRadians(parseCoordinate(longitudeGradus));
    }

    private double parseCoordinate(String coordStr) {
        if (coordStr == null || coordStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Координата не может быть пустой");
        }

        if (coordStr.matches("\\d+(\\.\\d+)?")) {
            return Double.parseDouble(coordStr);
        }

        if (coordStr.matches("\\d+\\(\\d+'\\d+''\\)")) {
            return parseDMS(coordStr);
        }

        throw new IllegalArgumentException("Неверный формат координаты: " + coordStr);
    }
    private double parseDMS(String dmsStr) {
        try {
            // Извлекаем числа из строки формата 55(45'07'')?
            String[] parts = dmsStr.split("[()]");
            int degrees = Integer.parseInt(parts[0]);

            String[] minutesSeconds = parts[1].split("'");
            int minutes = Integer.parseInt(minutesSeconds[0]);
            int seconds = Integer.parseInt(minutesSeconds[1].replace("''", ""));

            // Преобразуем в десятичные градусы
            return degrees + (minutes / 60.0) + (seconds / 3600.0);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка парсинга координаты: " + dmsStr);
        }
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    @Override
    public String toString() {
        return "геопозиция переданных координат:" +
                " долгота=" + latitude +
                ", широта=" + longitude ;
    }

    // gettes and toString
}
