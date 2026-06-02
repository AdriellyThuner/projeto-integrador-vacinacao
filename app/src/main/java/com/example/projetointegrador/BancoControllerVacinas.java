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
        banco = new CriaBanco(context);
    }

    public boolean insereDados(Vacina vacinas) {
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("petID", vacinas.getPetId()); // associa a vacina ao pet pelo ID
        valores.put("nomeVac", vacinas.getNomeVac());
        valores.put("Data_aplicacao", vacinas.getData_aplicacao());
        valores.put("Hora_aplicacao", vacinas.getHora_aplicacao());
        valores.put("NomeVac", vacinas.getNomeVac());
        valores.put("CRMV_vet", vacinas.getCRMV_vet());
        valores.put("dose", vacinas.getDose());
        valores.put("Lote",vacinas.getLote());
        valores.put("Data_val", vacinas.getData_val());
        valores.put("Fabricante",vacinas.getFabricante());
        valores.put("dose2",vacinas.getDose2());
        valores.put("NomeRef",vacinas.getNomeRef());
        valores.put("DataRef",vacinas.getDataRef());
        valores.put("Nome_pro_vac", vacinas.getNome_pro_vac());
        valores.put("Data_pro_vac",vacinas.getData_pro_vac());
        valores.put("lembrar",vacinas.getLembrar());
        valores.put("Anota_vac",vacinas.getAnota_vac());


        try {
            long resultado = db.insertOrThrow("vacinas", null, valores);
            return resultado != -1;
        } catch (Exception e) {
            Log.e("BANCO_ERRO", "Erro ao inserir vacina: " + e.getMessage());
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
                null,
                "petID = ?",                        // filtro pelo ID
                new String[]{String.valueOf(petId)},
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
                nova.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                nova.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow("petID")));
                nova.setLote(cursor.getString(cursor.getColumnIndexOrThrow("Lote")));
                nova.setData_val(cursor.getString(cursor.getColumnIndexOrThrow("Data_val")));
                nova.setFabricante(cursor.getString(cursor.getColumnIndexOrThrow("Fabricante")));
                nova.setDose2(cursor.getString(cursor.getColumnIndexOrThrow("dose2")));
                nova.setNomeRef(cursor.getString(cursor.getColumnIndexOrThrow("NomeRef")));
                nova.setDataRef(cursor.getString(cursor.getColumnIndexOrThrow("DataRef")));
                nova.setObsVac(cursor.getString(cursor.getColumnIndexOrThrow("ObsVac")));
                nova.setNome_pro_vac(cursor.getString(cursor.getColumnIndexOrThrow("Nome_pro_vac")));
                nova.setData_pro_vac(cursor.getString(cursor.getColumnIndexOrThrow("Data_pro_vac")));
                nova.setLembrar(cursor.getString(cursor.getColumnIndexOrThrow("Lembrar")));
                nova.setAnota_vac(cursor.getString(cursor.getColumnIndexOrThrow("Anota_vac")));
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

                Vacina nova = new Vacina(
                        cursor.getString(cursor.getColumnIndexOrThrow("nomeVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dataVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("horaVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("vetVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("crmVac")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dose"))
                );
                nova.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                nova.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow("petID")));
                nova.setLote(cursor.getString(cursor.getColumnIndexOrThrow("Lote")));
                nova.setData_val(cursor.getString(cursor.getColumnIndexOrThrow("Data_val")));
                nova.setFabricante(cursor.getString(cursor.getColumnIndexOrThrow("Fabricante")));
                nova.setDose2(cursor.getString(cursor.getColumnIndexOrThrow("dose2")));
                nova.setNomeRef(cursor.getString(cursor.getColumnIndexOrThrow("NomeRef")));
                nova.setDataRef(cursor.getString(cursor.getColumnIndexOrThrow("DataRef")));
                nova.setObsVac(cursor.getString(cursor.getColumnIndexOrThrow("ObsVac")));
                nova.setNome_pro_vac(cursor.getString(cursor.getColumnIndexOrThrow("Nome_pro_vac")));
                nova.setData_pro_vac(cursor.getString(cursor.getColumnIndexOrThrow("Data_pro_vac")));
                nova.setLembrar(cursor.getString(cursor.getColumnIndexOrThrow("Lembrar")));
                nova.setAnota_vac(cursor.getString(cursor.getColumnIndexOrThrow("Anota_vac")));
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

