package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.Date;

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

    //TODO Generate PDF
    public Report(Exhibition exhibition, String invNum, Date date, String det1, String det2, String det3, String det4, String det5, String det6, String det7,
                  String det8, String det9, String genCondition, String specialCare, String crateNumber,
                  boolean supportF, boolean plastic, boolean paper, boolean noTape, boolean ethafoam, boolean foamRubber, String position) {
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

    public String getPosition() {
        return position;
    }
}
