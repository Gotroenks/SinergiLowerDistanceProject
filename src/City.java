import java.util.*;

public class City{
    public String City;
    public List<Road> RoadTo = new ArrayList<>();

    List<City> city = new ArrayList<>();

    public City(String city, String roadTo, Double km) {
        City = city;
        assert false;
        RoadTo.add(new Road(roadTo, km));
    }

    public City(String city) {
        City = city;
    }
}