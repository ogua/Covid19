package com.moi.gov.gh.convic_19.models;

public class PatientInfo {
    private String id;
    private String key;
    private String Pventilated;
    private String PhealthStatud;
    private String Pdateoddeath;
    private String Psymtons;
    private String PsymtonsPain;
    private String Psymtonsothers;
    private String PatientTemp;
    private String Psigns;
    private String Pconditions;
    private String date;
    private String eipnumber;

    public PatientInfo() {

    }


    public PatientInfo(String id, String key, String pventilated, String phealthStatud, String pdateoddeath, String psymtons, String psymtonsPain, String psymtonsothers, String patientTemp, String psigns, String pconditions, String date, String eipnumber) {
        this.id = id;
        this.key = key;
        Pventilated = pventilated;
        PhealthStatud = phealthStatud;
        Pdateoddeath = pdateoddeath;
        Psymtons = psymtons;
        PsymtonsPain = psymtonsPain;
        Psymtonsothers = psymtonsothers;
        PatientTemp = patientTemp;
        Psigns = psigns;
        Pconditions = pconditions;
        this.date = date;
        this.eipnumber = eipnumber;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPventilated() {
        return Pventilated;
    }

    public void setPventilated(String pventilated) {
        Pventilated = pventilated;
    }

    public String getPhealthStatud() {
        return PhealthStatud;
    }

    public void setPhealthStatud(String phealthStatud) {
        PhealthStatud = phealthStatud;
    }

    public String getPdateoddeath() {
        return Pdateoddeath;
    }

    public void setPdateoddeath(String pdateoddeath) {
        Pdateoddeath = pdateoddeath;
    }

    public String getPsymtons() {
        return Psymtons;
    }

    public void setPsymtons(String psymtons) {
        Psymtons = psymtons;
    }

    public String getPsymtonsPain() {
        return PsymtonsPain;
    }

    public void setPsymtonsPain(String psymtonsPain) {
        PsymtonsPain = psymtonsPain;
    }

    public String getPsymtonsothers() {
        return Psymtonsothers;
    }

    public void setPsymtonsothers(String psymtonsothers) {
        Psymtonsothers = psymtonsothers;
    }

    public String getPatientTemp() {
        return PatientTemp;
    }

    public void setPatientTemp(String patientTemp) {
        PatientTemp = patientTemp;
    }

    public String getPsigns() {
        return Psigns;
    }

    public void setPsigns(String psigns) {
        Psigns = psigns;
    }

    public String getPconditions() {
        return Pconditions;
    }

    public void setPconditions(String pconditions) {
        Pconditions = pconditions;
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
}
