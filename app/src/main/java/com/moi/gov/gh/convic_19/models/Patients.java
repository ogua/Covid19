package com.moi.gov.gh.convic_19.models;

public class Patients {
    private String id;
    private String key;
    private String Pname;
    private String Pnumber;
    private String Paddress;
    private String Pdateofbirth;
    private String Page;
    private String Psex;
    private String PdiaContry;
    private String PdiaRegion;
    private String PdiaDistrict;
    private String PplaceofResident;
    private String PplaceRegion;
    private String PplaceDistrict;
    private String date;
    private String eipnumber;

    public Patients() {

    }


    public Patients(String id, String key, String pname, String pnumber, String paddress, String pdateofbirth, String page, String psex, String pdiaContry, String pdiaRegion, String pdiaDistrict, String pplaceofResident, String pplaceRegion, String pplaceDistrict, String date, String eipnumber) {
        this.id = id;
        this.key = key;
        Pname = pname;
        Pnumber = pnumber;
        Paddress = paddress;
        Pdateofbirth = pdateofbirth;
        Page = page;
        Psex = psex;
        PdiaContry = pdiaContry;
        PdiaRegion = pdiaRegion;
        PdiaDistrict = pdiaDistrict;
        PplaceofResident = pplaceofResident;
        PplaceRegion = pplaceRegion;
        PplaceDistrict = pplaceDistrict;
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

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getPnumber() {
        return Pnumber;
    }

    public void setPnumber(String pnumber) {
        Pnumber = pnumber;
    }

    public String getPaddress() {
        return Paddress;
    }

    public void setPaddress(String paddress) {
        Paddress = paddress;
    }

    public String getPdateofbirth() {
        return Pdateofbirth;
    }

    public void setPdateofbirth(String pdateofbirth) {
        Pdateofbirth = pdateofbirth;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String page) {
        Page = page;
    }

    public String getPsex() {
        return Psex;
    }

    public void setPsex(String psex) {
        Psex = psex;
    }

    public String getPdiaContry() {
        return PdiaContry;
    }

    public void setPdiaContry(String pdiaContry) {
        PdiaContry = pdiaContry;
    }

    public String getPdiaRegion() {
        return PdiaRegion;
    }

    public void setPdiaRegion(String pdiaRegion) {
        PdiaRegion = pdiaRegion;
    }

    public String getPdiaDistrict() {
        return PdiaDistrict;
    }

    public void setPdiaDistrict(String pdiaDistrict) {
        PdiaDistrict = pdiaDistrict;
    }

    public String getPplaceofResident() {
        return PplaceofResident;
    }

    public void setPplaceofResident(String pplaceofResident) {
        PplaceofResident = pplaceofResident;
    }

    public String getPplaceRegion() {
        return PplaceRegion;
    }

    public void setPplaceRegion(String pplaceRegion) {
        PplaceRegion = pplaceRegion;
    }

    public String getPplaceDistrict() {
        return PplaceDistrict;
    }

    public void setPplaceDistrict(String pplaceDistrict) {
        PplaceDistrict = pplaceDistrict;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
