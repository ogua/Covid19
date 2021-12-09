package com.moi.gov.gh.convic_19.models;

public class Case {
    private String userid;
    private String key;
    private String epinumber;
    private String whoid;
    private String Preportigndate;
    private String PreportInst;
    private String Pcase;
    private String Pdetecd;
    private String Pdateifyes;
    private String date;


    public Case() {
    }

    public Case(String userid, String key, String epinumber, String whoid, String preportigndate, String preportInst, String pcase, String pdetecd, String pdateifyes, String date) {
        this.userid = userid;
        this.key = key;
        this.epinumber = epinumber;
        this.whoid = whoid;
        Preportigndate = preportigndate;
        PreportInst = preportInst;
        Pcase = pcase;
        Pdetecd = pdetecd;
        Pdateifyes = pdateifyes;
        this.date = date;
    }

    public String getWhoid() {
        return whoid;
    }

    public void setWhoid(String whoid) {
        this.whoid = whoid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEpinumber() {
        return epinumber;
    }

    public void setEpinumber(String epinumber) {
        this.epinumber = epinumber;
    }

    public String getPreportigndate() {
        return Preportigndate;
    }

    public void setPreportigndate(String preportigndate) {
        Preportigndate = preportigndate;
    }

    public String getPreportInst() {
        return PreportInst;
    }

    public void setPreportInst(String preportInst) {
        PreportInst = preportInst;
    }

    public String getPcase() {
        return Pcase;
    }

    public void setPcase(String pcase) {
        Pcase = pcase;
    }

    public String getPdetecd() {
        return Pdetecd;
    }

    public void setPdetecd(String pdetecd) {
        Pdetecd = pdetecd;
    }

    public String getPdateifyes() {
        return Pdateifyes;
    }

    public void setPdateifyes(String pdateifyes) {
        Pdateifyes = pdateifyes;
    }

    @Override
    public String toString() {
        return "Case{" +
                "userid='" + userid + '\'' +
                ", key='" + key + '\'' +
                ", epinumber='" + epinumber + '\'' +
                ", Preportigndate='" + Preportigndate + '\'' +
                ", PreportInst='" + PreportInst + '\'' +
                ", Pcase='" + Pcase + '\'' +
                ", Pdetecd='" + Pdetecd + '\'' +
                ", Pdateifyes='" + Pdateifyes + '\'' +
                '}';
    }
}
