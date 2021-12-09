package com.moi.gov.gh.convic_19.models;

public class Clinical {
    private String id;
    private String key;
    private  String Poffsetdate;
    private String patoffsetsyme;
    private String patfirstseen;
    private String patadmitted;
    private String patdateofadmission;
    private String admhospital;
    private String addhosregnumber;
    private String PisolationDate;
    private String date;
    private String eipnumber;

    public Clinical() {

    }

    public Clinical(String id, String key, String poffsetdate, String patoffsetsyme, String patfirstseen, String patadmitted, String patdateofadmission, String admhospital, String addhosregnumber, String pisolationDate, String date, String eipnumber) {
        this.id = id;
        this.key = key;
        Poffsetdate = poffsetdate;
        this.patoffsetsyme = patoffsetsyme;
        this.patfirstseen = patfirstseen;
        this.patadmitted = patadmitted;
        this.patdateofadmission = patdateofadmission;
        this.admhospital = admhospital;
        this.addhosregnumber = addhosregnumber;
        PisolationDate = pisolationDate;
        this.date = date;
        this.eipnumber = eipnumber;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEipnumber() {
        return eipnumber;
    }

    public void setEipnumber(String eipnumber) {
        this.eipnumber = eipnumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoffsetdate() {
        return Poffsetdate;
    }

    public void setPoffsetdate(String poffsetdate) {
        Poffsetdate = poffsetdate;
    }

    public String getPatoffsetsyme() {
        return patoffsetsyme;
    }

    public void setPatoffsetsyme(String patoffsetsyme) {
        this.patoffsetsyme = patoffsetsyme;
    }

    public String getPatfirstseen() {
        return patfirstseen;
    }

    public void setPatfirstseen(String patfirstseen) {
        this.patfirstseen = patfirstseen;
    }

    public String getPatadmitted() {
        return patadmitted;
    }

    public void setPatadmitted(String patadmitted) {
        this.patadmitted = patadmitted;
    }

    public String getPatdateofadmission() {
        return patdateofadmission;
    }

    public void setPatdateofadmission(String patdateofadmission) {
        this.patdateofadmission = patdateofadmission;
    }

    public String getAdmhospital() {
        return admhospital;
    }

    public void setAdmhospital(String admhospital) {
        this.admhospital = admhospital;
    }

    public String getAddhosregnumber() {
        return addhosregnumber;
    }

    public void setAddhosregnumber(String addhosregnumber) {
        this.addhosregnumber = addhosregnumber;
    }

    public String getPisolationDate() {
        return PisolationDate;
    }

    public void setPisolationDate(String pisolationDate) {
        PisolationDate = pisolationDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
