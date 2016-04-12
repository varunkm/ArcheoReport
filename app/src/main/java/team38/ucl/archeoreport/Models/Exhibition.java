package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by varunmathur on 10/03/16.
 */

public class Exhibition extends SugarRecord{
    String name;
    String location;
    long startdate;
    long enddate;
    public Exhibition(){

    }
    public Exhibition(String name, String location, Date startdate, Date enddate) {
        this.name = name;
        this.location = location;
        this.startdate = startdate.getTime();
        this.enddate = enddate.getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return new Date(startdate);
    }

    public void setStartDate(Date date) {
        this.startdate = date.getTime();
    }

    public Date getEndDate() {return new Date(enddate);}

    public void setEnddate(Date date) {this.enddate = date.getTime();}

}
