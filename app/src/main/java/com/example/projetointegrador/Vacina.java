package com.example.projetointegrador;



import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;


public class Vacina implements Serializable { //permite enviar os objetos dela entre telas
    private int id;
    private int petId;
    private String nomeVac;
    private String dataVac;
    private String horaVac;
    private String vetVac;
    private String crmVac;
    private String dose;

    private String detVac;
    private String detVac1;
    private String detVac2;
    private String dose2;
    private String refVac;
    private String Dataref;
    private String ObsVac;

    private String nomeDose;
    private String dataDose;
    private String checklist;
    private String proVac;



    // Construtor completo para usar ao recuperar do banco ou criar no form
    public Vacina (String nomeVac, String dataVac, String horaVac, String vetVac,
               String crmVac, String dose) {
        this.nomeVac = nomeVac;
        this.dataVac = dataVac;
        this.horaVac = horaVac;
        this.vetVac = vetVac;
        this.crmVac = crmVac;
        this.dose = dose;


    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public String getNomeVac() {
        return nomeVac;
    }

    public String getDataVac() {
        return dataVac;
    }

    public String getHoraVac() {
        return horaVac;
    }

    public String getVetVac() {
        return vetVac;
    }

    public String getCrmVac() {
        return crmVac;
    }

    public String getDose() {
        return dose;
    }

    public String getDetVac() { return detVac; }
    public void setDetVac(String detVac) { this.detVac = detVac; }

    public String getDetVac1() { return detVac1; }
    public void setDetVac1(String detVac1) { this.detVac1 = detVac1; }

    public String getDetVac2() { return detVac2; }
    public void setDetVac2(String detVac2) { this.detVac2 = detVac2; }

    public String getRefVac() { return refVac; }
    public void setRefVac(String refVac) { this.refVac = refVac; }

    public String getDose2(){return dose2;}
    public void setDose2 (String dose2){this.dose2=dose2;}

    public String getDataref() { return Dataref; }
    public void setDataref(String dataref) { this.Dataref = dataref; }

    public String getObsVac() { return ObsVac; }
    public void setObsVac(String obsVac) { this.ObsVac = obsVac; }

    public String getNomeDose() { return nomeDose; }
    public void setNomeDose(String nomeDose) { this.nomeDose = nomeDose; }

    public String getDataDose() { return dataDose; }
    public void setDataDose(String dataDose) { this.dataDose = dataDose; }

    public String getChecklist() { return checklist; }
    public void setChecklist(String checklist) { this.checklist = checklist; }

    public String getProVac() { return proVac; }
    public void setProVac(String proVac) { this.proVac = proVac; }

}


