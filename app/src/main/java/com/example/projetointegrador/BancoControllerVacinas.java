package com.example.projetointegrador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BancoControllerVacinas {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoControllerVacinas(Context context) {
        banco = new CriaBanco(context); //cria o banco e insere os dados
    }

    public boolean insereDados(Vacina vacinas) { // metodo que salva a vacina completa no banco
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("petID", vacinas.getPetId()); // associa a vacina ao pet pelo ID
        valores.put("nomeVac", vacinas.getNomeVac());
        valores.put("dataVac", vacinas.getDataVac());
        valores.put("horaVac", vacinas.getHoraVac());
        valores.put("vetVac", vacinas.getVetVac());
        valores.put("crmVac", vacinas.getCrmVac());
        valores.put("dose", vacinas.getDose());
        valores.put("detVac",vacinas.getDetVac());
        valores.put("detVac1", vacinas.getDetVac1());
        valores.put("detVac2",vacinas.getDetVac2());
        valores.put("refVac",vacinas.getRefVac());
        valores.put("Dataref",vacinas.getDataref());
        valores.put("ObsVac",vacinas.getObsVac());
        valores.put("dose2", vacinas.getDose2());
        valores.put("nomeDose",vacinas.getNomeDose());
        valores.put("dataDose",vacinas.getDataDose());
        valores.put("checklist",vacinas.getChecklist());
        valores.put("proVac",vacinas.getProVac());

        try { //erros que podem ocorrer
            long resultado = db.insertOrThrow("vacinas", null, valores); // ← troca insert por insertOrThrow
            return resultado != -1;
        } catch (Exception e) {
            Log.e("BANCO_ERRO", "Erro ao inserir vacina: " + e.getMessage()); // ← mostra o erro real
            return false;
        } finally {
            db.close();
        }
    }



    public List<Vacina> listarPorPet(int petId) { //metodo que busca todas as vacinas por pet especifico, recebe o id e retorna as vacinas
        List<Vacina> lista = new ArrayList<>(); //cria uma lista vazia que será preenchida e retornada
        db = banco.getReadableDatabase();

        Cursor cursor = db.query(
                "vacinas",
                null, // null= seleciona todas as colunas
                "petID = ?",                        // filtro pelo ID
                new String[]{String.valueOf(petId)}, // valor do filtro
                null, null,
                "id DESC" //ordena do mais recente para o mais antigo
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Vacina nova = new Vacina(
                        cursor.getString(cursor.getColumnIndexOrThrow("nomeVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dataVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("horaVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("vetVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("crmVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dose"))
                );
                nova.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                nova.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow("petID")));
                nova.setDetVac(cursor.getString(cursor.getColumnIndexOrThrow("detVac")));
                nova.setDetVac1(cursor.getString(cursor.getColumnIndexOrThrow("detVac1")));
                nova.setDetVac2(cursor.getString(cursor.getColumnIndexOrThrow("detVac2")));
                nova.setDose2(cursor.getString(cursor.getColumnIndexOrThrow("dose2")));
                nova.setRefVac(cursor.getString(cursor.getColumnIndexOrThrow("refVac")));
                nova.setDataref(cursor.getString(cursor.getColumnIndexOrThrow("Dataref")));
                nova.setObsVac(cursor.getString(cursor.getColumnIndexOrThrow("ObsVac")));
                nova.setNomeDose(cursor.getString(cursor.getColumnIndexOrThrow("nomeDose")));
                nova.setDataDose(cursor.getString(cursor.getColumnIndexOrThrow("dataDose")));
                nova.setChecklist(cursor.getString(cursor.getColumnIndexOrThrow("checklist")));
                nova.setProVac(cursor.getString(cursor.getColumnIndexOrThrow("proVac")));
                lista.add(nova); // adiciona o objeto Vacina completo a lista
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    // LISTAR REGISTROS
    public List<Vacina> listar() {
        List<Vacina> lista = new ArrayList<>(); //metodo que busca todas as vacinas cadastradas
        db = banco.getReadableDatabase();
        Cursor cursor = db.query("vacinas", null, null, null, null, null, "id DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Criamos o objeto puxando os dados direto do cursor por índice
                Vacina nova = new Vacina(
                        cursor.getString(cursor.getColumnIndexOrThrow("nomeVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dataVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("horaVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("vetVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("crmVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dose"))
                );
                nova.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                nova.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow("petID")));
                nova.setDetVac(cursor.getString(cursor.getColumnIndexOrThrow("detVac")));
                nova.setDetVac1(cursor.getString(cursor.getColumnIndexOrThrow("detVac1")));
                nova.setDetVac2(cursor.getString(cursor.getColumnIndexOrThrow("detVac2")));
                nova.setDose2(cursor.getString(cursor.getColumnIndexOrThrow("dose2")));
                nova.setRefVac(cursor.getString(cursor.getColumnIndexOrThrow("refVac")));
                nova.setDataref(cursor.getString(cursor.getColumnIndexOrThrow("Dataref")));
                nova.setObsVac(cursor.getString(cursor.getColumnIndexOrThrow("ObsVac")));
                nova.setNomeDose(cursor.getString(cursor.getColumnIndexOrThrow("nomeDose")));
                nova.setDataDose(cursor.getString(cursor.getColumnIndexOrThrow("dataDose")));
                nova.setChecklist(cursor.getString(cursor.getColumnIndexOrThrow("checklist")));
                nova.setProVac(cursor.getString(cursor.getColumnIndexOrThrow("proVac")));
                lista.add(nova);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }


    public String excluirDados(int id) {
        db = banco.getWritableDatabase();
        int linhas = db.delete("vacinas", "id = " + id, null);
        db.close();
        return linhas < 1 ? "Erro ao Excluir" : "Registro Excluído";
    }
}

