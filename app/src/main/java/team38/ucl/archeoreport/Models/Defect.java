package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

/**
 * Created by varunmathur on 13/03/16.
 */
public class Defect
{
    String title;
    boolean present;

    public Defect(String defect, boolean present) {
        this.title = defect;
        this.present = present;
    }

    public String toString()
    {
        if(present){
            return title;
        }
        else
            return "";
    }

    public boolean equals(Defect other){
        return this.toString().equals(other.toString());
    }
}
