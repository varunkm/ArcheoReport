package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by varunmathur on 10/03/16.
 */

public class Exhibition extends SugarRecord{
    String name;
    String location;
    long date;

    public Exhibition(){

    }
    public Exhibition(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date.getTime();
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

    public Date getDate() {
        return new Date(date);
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

}
