package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

/**
 * Created by varunmathur on 20/03/16.
 */
public class Annotation extends SugarRecord {
    String defect;
    AnnotatedImage image;

    public Annotation(){

    }


    public Annotation(String defect, AnnotatedImage image) {
        this.defect = defect;
        this.image = image;
    }

    public String getDefect() {
        return defect;
    }

    public AnnotatedImage getImage() {
        return image;
    }
}
