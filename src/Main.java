import java.util.ArrayList;

public class Main{

    public static void main(String[] args) {

        var city = new ArrayList<City>();

        city.add(new City("A"));
        city.add(new City("B"));
        city.add(new City("C"));
        city.add(new City("D"));
        city.add(new City("E"));
        city.add(new City("F"));
        city.add(new City("G"));
        city.add(new City("H"));
        city.add(new City("I"));
        city.add(new City("J"));

        city.stream().filter(x -> x.City == "A").findFirst().get().RoadTo.add(new Road("B", 3.6));
        city.stream().filter(x -> x.City == "A").findFirst().get().RoadTo.add(new Road("C", 5.0));
        city.stream().filter(x -> x.City == "A").findFirst().get().RoadTo.add(new Road("H", 9.2));
        city.stream().filter(x -> x.City == "B").findFirst().get().RoadTo.add(new Road("A", 3.6));
        city.stream().filter(x -> x.City == "B").findFirst().get().RoadTo.add(new Road("E", 8.0));
        city.stream().filter(x -> x.City == "C").findFirst().get().RoadTo.add(new Road("A", 5.0));
        city.stream().filter(x -> x.City == "C").findFirst().get().RoadTo.add(new Road("D", 3.1));
        city.stream().filter(x -> x.City == "C").findFirst().get().RoadTo.add(new Road("G", 6.3));
        city.stream().filter(x -> x.City == "C").findFirst().get().RoadTo.add(new Road("I", 3.1));
        city.stream().filter(x -> x.City == "C").findFirst().get().RoadTo.add(new Road("J", 5.6));
        city.stream().filter(x -> x.City == "D").findFirst().get().RoadTo.add(new Road("C", 3.1));
        city.stream().filter(x -> x.City == "D").findFirst().get().RoadTo.add(new Road("F", 5.3));
        city.stream().filter(x -> x.City == "D").findFirst().get().RoadTo.add(new Road("E", 7.2));
        city.stream().filter(x -> x.City == "E").findFirst().get().RoadTo.add(new Road("B", 8.0));
        city.stream().filter(x -> x.City == "E").findFirst().get().RoadTo.add(new Road("D", 7.2));
        city.stream().filter(x -> x.City == "E").findFirst().get().RoadTo.add(new Road("F", 4.4));
        city.stream().filter(x -> x.City == "E").findFirst().get().RoadTo.add(new Road("G", 7.0));
        city.stream().filter(x -> x.City == "F").findFirst().get().RoadTo.add(new Road("D", 5.3));
        city.stream().filter(x -> x.City == "F").findFirst().get().RoadTo.add(new Road("E", 4.4));
        city.stream().filter(x -> x.City == "F").findFirst().get().RoadTo.add(new Road("G", 3.6));
        city.stream().filter(x -> x.City == "G").findFirst().get().RoadTo.add(new Road("C", 6.3));
        city.stream().filter(x -> x.City == "G").findFirst().get().RoadTo.add(new Road("E", 7.0));
        city.stream().filter(x -> x.City == "G").findFirst().get().RoadTo.add(new Road("F", 3.6));
        city.stream().filter(x -> x.City == "H").findFirst().get().RoadTo.add(new Road("A", 9.2));
        city.stream().filter(x -> x.City == "H").findFirst().get().RoadTo.add(new Road("J", 6.0));
        city.stream().filter(x -> x.City == "I").findFirst().get().RoadTo.add(new Road("C", 3.1));
        city.stream().filter(x -> x.City == "J").findFirst().get().RoadTo.add(new Road("C", 5.6));
        city.stream().filter(x -> x.City == "J").findFirst().get().RoadTo.add(new Road("H", 6.0));

        var notFinish = true;

        var origem = "A"; //Origem do trajeto
        var destino = "E"; //Final do trajeto

        var lstRoad = new ArrayList<Road>();
        var bestRoad = new Road();

        var possibilidades = city.stream().filter(x -> x.City.equals(origem)).toList(); // cada rota é armazenada
                                                                                                  // para posteriormente serem tratadas

        while (notFinish) {
            var lstLastCity = new ArrayList<String>();

            for (var road : possibilidades) {

                if(lstRoad.isEmpty()) {
                    for(var to : road.RoadTo) {
                        lstLastCity.add(to.Rota);
                        lstRoad.add(new Road(origem + to.Rota, to.Km));
                    }
                }
                else {
                    var now = lstRoad.stream().filter(x -> x.Rota.contains(road.City)).findFirst();

                    if(now.isPresent()) {
                        var possibilidadesNext = road.RoadTo.stream().filter(x -> !now.get().Rota.contains(x.Rota)).toList();

                        for(var next : possibilidadesNext) {
                            lstLastCity.add(next.Rota);
                            lstRoad.add(new Road(now.get().Rota + next.Rota, now.get().Km + next.Km));
                        }

                        lstRoad.remove(now.get());
                    }

                    if(bestRoad.Rota != null) {
                        var lstRoadTemp = lstRoad;

                        for(var verifyRoad : lstRoadTemp.stream().toList()) {
                            if(verifyRoad.Km >= bestRoad.Km)
                                lstRoad.remove(verifyRoad);
                        }

                        if(lstRoad.isEmpty())
                            notFinish = false;
                    }

                }

                possibilidades = city.stream().filter(x -> lstLastCity.contains(x.City)).toList();
            }

            if(lstRoad.stream().anyMatch(x -> x.Rota.startsWith(origem) && x.Rota.endsWith(destino))) {
                var lstBestRoad = lstRoad.stream().filter(x -> x.Rota.startsWith(origem) && x.Rota.endsWith(destino)).toList();
                var lowerRoad = lstBestRoad.stream().map(y -> y.Km).min(Double::compare);

                bestRoad = lstBestRoad.stream().filter(x -> x.Km == lowerRoad.get()).findFirst().get();

                System.out.println("Melhor trajeto: " + bestRoad.Rota + "\ndistância: " + bestRoad.Km);
            }
        }
    }
}

