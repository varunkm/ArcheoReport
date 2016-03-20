package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

/**
 * Created by varunmathur on 20/03/16.
 */
public class Annotation extends SugarRecord {
    Defect defect;
    AnnotatedImage image;

    public Annotation(){

    }

    public void setImage(AnnotatedImage image) {
        this.image = image;
    }

    public Annotation(Defect defect){
        this.defect = defect;
    }
    public Annotation(Defect defect, AnnotatedImage image) {
        this.defect = defect;
        this.image = image;
    }

    public Defect getDefect() {
        return defect;
    }

    public AnnotatedImage getImage() {
        return image;
    }
}
