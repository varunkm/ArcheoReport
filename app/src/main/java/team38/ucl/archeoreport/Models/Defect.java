package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

/**
 * Created by varunmathur on 13/03/16.
 */
public class Defect extends SugarRecord{
    String title;
    Report owner;
    public Defect()
    {}

    public Defect(String defect, Report owner) {
        this.title = defect;
        this.owner = owner;
    }
}
