package com.example.projetointegrador;

import java.io.Serializable;

public class Vacina implements Serializable { //permite enviar os objetos dela entre telas
    private int id_vacina;
    private int petId;
    private String NomeVac;
    private String dose;
    private String Data_aplicacao;
    private String Hora_aplicacao;
    private String Nome_vet;
    private String CRMV_vet;

    private String Lote;
    private String Data_val;
    private String Fabricante;
    private String dose2;
    private String NomeRef;
    private String DataRef;
    private String ObsVac;

    private String Nome_pro_vac;
    private String Data_pro_vac;
    private String lembrar;
    private String Anota_vac;

    // Construtor completo para usar ao recuperar do banco ou criar no form
    public Vacina (String NomeVac, String dose, String Data_aplicacao, String Hora_aplicacao,
                   String Nome_vet, String CRMV_vet) {
        this.NomeVac = NomeVac;
        this.dose = dose;
        this.Data_aplicacao = Data_aplicacao;
        this.Hora_aplicacao = Hora_aplicacao;
        this.Nome_vet = Nome_vet;
        this.CRMV_vet = CRMV_vet;

    }

    public Vacina() {}

    // Getters e Setters
    public int getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(int id_vacina) {
        this.id_vacina = id_vacina;
    }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public String getNomeVac() {
        return NomeVac;
    }

    public String getDose() {
        return dose;
    }

    public String getData_aplicacao() {
        return Data_aplicacao;
    }

    public String getHora_aplicacao() {
        return Hora_aplicacao;
    }

    public String getNome_vet() {
        return Nome_vet;
    }

    public String getCRMV_vet() {
        return CRMV_vet;
    }

    public String getLote() { return Lote; }
    public void setLote(String Lote) { this.Lote = Lote; }

    public String getData_val() { return Data_val; }
    public void setData_val(String Data_agendada) { this.Data_val = Data_val; }

    public String getFabricante() { return Fabricante; }
    public void setFabricante(String Fabricante) { this.Fabricante = Fabricante; }

    public String getNomeRef() { return NomeRef; }
    public void setNomeRef(String NomeRef) { this.NomeRef = NomeRef; }

    public String getDose2(){return dose2;}
    public void setDose2 (String dose2){this.dose2=dose2;}

    public String getDataRef() { return DataRef; }
    public void setDataRef(String DataRef) { this.DataRef = DataRef; }

    public String getObsVac() { return ObsVac; }
    public void setObsVac(String obsVac) { this.ObsVac = obsVac; }

    public String getNome_pro_vac() { return Nome_pro_vac; }
    public void setNome_pro_vac(String Nome_pro_vac) { this.Nome_pro_vac = Nome_pro_vac; }

    public String getData_pro_vac() { return Data_pro_vac; }
    public void setData_pro_vac(String Data_pro_vac) { this.Data_pro_vac = Data_pro_vac; }

    public String getLembrar() { return lembrar; }
    public void setLembrar(String lembrar) { this.lembrar = lembrar; }

    public String getAnota_vac() { return Anota_vac; }
    public void setAnota_vac(String Anota_vac) { this.Anota_vac = Anota_vac; }

    public void setNomeVac(String NomeVac) { this.NomeVac = NomeVac; }

    public void setDose(String dose) { this.dose = dose; }
    public void setData_aplicacao(String Data_aplicacao) { this.Data_aplicacao = Data_aplicacao; }
    public void setHora_aplicacao(String Hora_aplicacao) { this.Hora_aplicacao = Hora_aplicacao; }
    public void setNome_vet(String Nome_vet) { this.Nome_vet = Nome_vet; }
    public void setCRMV_vet(String CRMV_vet) { this.CRMV_vet = CRMV_vet; }

}