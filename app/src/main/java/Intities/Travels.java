package Intities;

public class Travels {

   private String countryOfTravel ;
   private int yearOfTravel ;
   private double lonTravel ;
   private double latTravel ;

    public Travels() {
    }

    public Travels(String countryOfTravel, int yearOfTravel, double lonTravel, double latTravel) {
        this.countryOfTravel = countryOfTravel;
        this.yearOfTravel = yearOfTravel;
        this.lonTravel = lonTravel;
        this.latTravel = latTravel;
    }

    public String getCountryOfTravel() {
        return countryOfTravel;
    }

    public void setCountryOfTravel(String countryOfTravel) {
        this.countryOfTravel = countryOfTravel;
    }

    public int getYearOfTravel() {
        return yearOfTravel;
    }

    public void setYearOfTravel(int yearOfTravel) {
        this.yearOfTravel = yearOfTravel;
    }

    public double getLonTravel() {
        return lonTravel;
    }

    public void setLonTravel(double lonTravel) {
        this.lonTravel = lonTravel;
    }

    public double getLatTravel() {
        return latTravel;
    }

    public void setLatTravel(double latTravel) {
        this.latTravel = latTravel;
    }

    @Override
    public String toString() {
        return "Travels{" +
                "countryOfTravel='" + countryOfTravel + '\'' +
                ", yearOfTravel=" + yearOfTravel +
                ", lonTravel=" + lonTravel +
                ", latTravel=" + latTravel +
                '}';
    }
}
