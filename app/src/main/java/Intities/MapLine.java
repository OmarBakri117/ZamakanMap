package Intities;

public class MapLine {
    private double lat1 ;
    private double lat2 ;
    private double lon1 ;
    private double lon2 ;

    public MapLine(double lat1, double lat2, double lon1, double lon2) {
        this.lat1 = lat1;
        this.lat2 = lat2;
        this.lon1 = lon1;
        this.lon2 = lon2;
    }

    public double getLat1() {
        return lat1;
    }

    public double getLat2() {
        return lat2;
    }

    public double getLon1() {
        return lon1;
    }

    public double getLon2() {
        return lon2;
    }
}
