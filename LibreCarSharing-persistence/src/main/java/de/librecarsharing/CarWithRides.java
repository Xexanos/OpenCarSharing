package de.librecarsharing;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by fred on 30.06.17.
 */
public class CarWithRides {
    private DBCar dbCar;
    public CarWithRides(DBCar dbCar)
    {

        this.dbCar=dbCar;
        this.dbCar.getRides().size();
    }
    public long getId()
    {
        return this.dbCar.getId();

    }
    public String getName()
    {
        return this.dbCar.getName();
    }
    public String getType() {return this.dbCar.getType().getName();}
    public String getInfo(){return this.dbCar.getInfo();}
    public Set<RideNoRef> getRides()
    {
        return this.dbCar.getRides().stream().map(RideNoRef::new).collect(Collectors.toSet());

    }
    public String getImageFile() {return dbCar.getImageFile();}
    public int getSeats(){return this.dbCar.getSeats();}
    public int getColor(){return this.dbCar.getColor();}
    public boolean isStatus(){return this.dbCar.isStatus();}
    public String getLicencePlate(){return this.dbCar.getLicencePlate();}
}
