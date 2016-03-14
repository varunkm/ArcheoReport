package team38.ucl.archeoreport.Models;

import android.content.Context;
import android.content.res.Resources;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;

import team38.ucl.archeoreport.R;

/**
 * Created by varunmathur on 14/03/16.
 */
public class DefectSet extends SugarRecord{
    boolean def1;
    boolean def2;
    boolean def3;
    boolean def4;
    boolean def5;
    boolean def6;
    boolean def7;
    boolean def8;
    boolean def9;
    boolean def10;
    boolean def11;
    boolean def12;
    Report owner;

    @Ignore
    String[] defnames;
    @Ignore
    Context context;
    @Ignore
    boolean[] defects = new boolean[12];
    public DefectSet()
    {

    }

    public DefectSet(boolean def1, boolean def2, boolean def3, boolean def4, boolean def5, boolean def6,
                     boolean def7, boolean def8, boolean def9, boolean def10, boolean def11, boolean def12,
                     Context context, Report owner) {
        this.def1 = def1;   defects[0] = def1;
        this.def2 = def2;   defects[1] = def2;
        this.def3 = def3;   defects[2] = def3;
        this.def4 = def4;   defects[3] = def4;
        this.def5 = def5;   defects[4] = def5;
        this.def6 = def6;   defects[5] = def6;
        this.def7 = def7;   defects[6] = def7;
        this.def8 = def8;   defects[7] = def8;
        this.def9 = def9;   defects[8] = def9;
        this.def10 = def10; defects[9] = def10;
        this.def11 = def11; defects[10] = def11;
        this.def12 = def12; defects[11] = def12;
        this.owner = owner;
        this.context = context;
        Resources res = context.getResources();
        defnames = res.getStringArray(R.array.defect_choices);
    }

    public boolean isDef12() {
        return def12;
    }

    public boolean isDef1() {
        return def1;
    }

    public boolean isDef2() {
        return def2;
    }

    public boolean isDef3() {
        return def3;
    }

    public boolean isDef4() {
        return def4;
    }

    public boolean isDef5() {
        return def5;
    }

    public boolean isDef6() {
        return def6;
    }

    public boolean isDef7() {
        return def7;
    }

    public boolean isDef8() {
        return def8;
    }

    public boolean isDef9() {
        return def9;
    }

    public boolean isDef10() {
        return def10;
    }

    public boolean isDef11() {
        return def11;
    }

    public ArrayList<String> getDefectsAsListofStrings(){
        ArrayList<String> def_strings = new ArrayList<>();
        for(int i = 0; i < defects.length;i++) {
            if (defects[i])
                def_strings.add(defnames[i]);
        }
        return def_strings;
    }


}
