package team38.ucl.archeoreport.Models;

import android.view.View;
import android.widget.LinearLayout;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team38.ucl.archeoreport.Models.Exhibition;

/**
 * Created by varunmathur on 11/03/16.
 *
 */

public class Report extends SugarRecord {


    Exhibition exhibition;
    String invNum;
    long date;
    String det1;
    String det2;
    String det3;
    String det4;
    String det5;
    String det6;
    String det7;
    String det8;
    String det9;

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

    String genCondition;
    String specialCare;
    String crateNumber;
    boolean supportF;
    boolean plastic;
    boolean paper;
    boolean noTape;
    boolean ethafoam;
    boolean foamRubber;
    String position;

    public Report(Exhibition exhibition, String invNum, Date date, String det1, String det2, String det3, String det4, String det5, String det6, String det7, String det8, String det9, boolean def1, boolean def2, boolean def3, boolean def4, boolean def5, boolean def6, boolean def7, boolean def8, boolean def9, boolean def10, boolean def11, boolean def12, String genCondition, String specialCare, String crateNumber, boolean supportF, boolean plastic, boolean paper, boolean noTape, boolean ethafoam, boolean foamRubber, String position) {
        this.exhibition = exhibition;
        this.invNum = invNum;
        this.date = date.getTime();
        this.det1 = det1;
        this.det2 = det2;
        this.det3 = det3;
        this.det4 = det4;
        this.det5 = det5;
        this.det6 = det6;
        this.det7 = det7;
        this.det8 = det8;
        this.det9 = det9;
        this.def1 = def1;
        this.def2 = def2;
        this.def3 = def3;
        this.def4 = def4;
        this.def5 = def5;
        this.def6 = def6;
        this.def7 = def7;
        this.def8 = def8;
        this.def9 = def9;
        this.def10 = def10;
        this.def11 = def11;
        this.def12 = def12;
        this.genCondition = genCondition;
        this.specialCare = specialCare;
        this.crateNumber = crateNumber;
        this.supportF = supportF;
        this.plastic = plastic;
        this.paper = paper;
        this.noTape = noTape;
        this.ethafoam = ethafoam;
        this.foamRubber = foamRubber;
        this.position = position;
    }

    //TODO Generate PDF


    public Report() {
    }

    public Date getDate()
    {
        return new Date(date);
    }


    public Exhibition getExhibition() {
        return exhibition;
    }

    public String getInvNum() {
        return invNum;
    }

    public String getDet1() {
        return det1;
    }

    public String getDet2() {
        return det2;
    }

    public String getDet3() {
        return det3;
    }

    public String getDet4() {
        return det4;
    }

    public String getDet5() {
        return det5;
    }

    public String getDet6() {
        return det6;
    }

    public String getDet7() {
        return det7;
    }

    public String getDet8() {
        return det8;
    }

    public String getDet9() {
        return det9;
    }

    public String getGenCondition() {
        return genCondition;
    }

    public String getSpecialCare() {
        return specialCare;
    }

    public String getCrateNumber() {
        return crateNumber;
    }

    public boolean isSupportF() {
        return supportF;
    }

    public boolean isPlastic() {
        return plastic;
    }

    public boolean isPaper() {
        return paper;
    }

    public boolean isNoTape() {
        return noTape;
    }

    public boolean isEthafoam() {
        return ethafoam;
    }

    public boolean isFoamRubber() {
        return foamRubber;
    }


    public String wrappingToString(){
        String s = "";
        if(paper)
        {
            s+="Paper: YES\n";
        }
        if(plastic){
            s+="Plastic: YES\n";
        }
        if(!noTape){
            s+="Tape: YES";
        }
        return s;
    }

    public String protectionToString(){
        String s  = "";
        if(ethafoam){
            s+="Ethafoam: YES\n";
        }
        if(foamRubber){
            s+="Foam rubber: YES\n";
        }
        return s;
    }
    public String getPosition() {
        return position;
    }



    public List<Detail> getDetailsAsList()
    {
        ArrayList<Detail> dets = new ArrayList<>();
        dets.add(new Detail("Oggetto",det1));
        dets.add(new Detail("Tecnica",det2));
        dets.add(new Detail("Dimens",det3));
        dets.add(new Detail("Datazione",det4));
        dets.add(new Detail("Collocazione",det5));
        dets.add(new Detail("Stato Conserv",det6));
        dets.add(new Detail("Priorita",det7));
        dets.add(new Detail("Necesita Di",det8));
        dets.add(new Detail("Interventi Fatti",det9));
        dets.add(new Detail("General Condition",genCondition));
        dets.add(new Detail("Special Care",specialCare));
        dets.add(new Detail("Crate Number",crateNumber));
        return dets;
    }
    public List<Defect> getDefectAsList()
    {
        ArrayList<Defect> defs = new ArrayList<>();
        defs.add(new Defect("def1",def1));
        defs.add(new Defect("def2",def2));
        defs.add(new Defect("def3",def3));
        defs.add(new Defect("def4",def4));
        defs.add(new Defect("def5",def5));
        defs.add(new Defect("def6",def6));
        defs.add(new Defect("def7",def7));
        defs.add(new Defect("def8",def8));
        defs.add(new Defect("def9",def9));
        defs.add(new Defect("def10",def10));
        defs.add(new Defect("def11",def11));
        defs.add(new Defect("def12",def12));
        return defs;
    }

}