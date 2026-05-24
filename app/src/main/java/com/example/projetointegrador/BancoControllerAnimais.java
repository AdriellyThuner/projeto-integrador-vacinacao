package com.example.projetointegrador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BancoControllerAnimais {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoControllerAnimais(Context context) {
        banco = new CriaBanco(context); //cria o banco e insere os dados
    }

    public long insereDados(Pet pet) {
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("nome", pet.getNome());
        valores.put("idade", pet.getIdade());
        valores.put("sexo", pet.getSexo());
        valores.put("data", pet.getData());
        valores.put("porte", pet.getPorte());
        valores.put("especie", pet.getEspecie());
        valores.put("enfermidade", pet.getEnfermidade());
        valores.put("descricao", pet.getObsEnfermidade());
        valores.put("local", pet.getLocal());
        valores.put("castrado", pet.getCastrado());
        valores.put("pelo", pet.getPelo());
        valores.put("docil", pet.getDocil());
        valores.put("anotacoes", pet.getAnotacoes());

        long resultado = db.insert("formulario", null, valores);



        db.close();

        return resultado;

    }

    // LISTAR REGISTROS
    public List<Pet> listar() {
        List<Pet> lista = new ArrayList<>();
        db = banco.getReadableDatabase();
        Cursor cursor = db.query("formulario", null, null, null, null, null, "id DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Criamos o objeto puxando os dados direto do cursor por índice
                Pet animal = new Pet(
                        cursor.getString(1),  // nome
                        cursor.getInt(2),     // idade
                        cursor.getString(3),  // sexo
                        cursor.getString(4),  // data
                        cursor.getString(5),  // porte
                        cursor.getString(6),  // especie
                        cursor.getString(7),  // enfermidade
                        cursor.getString(8),  // descricao/obsEnfermidade
                        cursor.getString(9),  // local
                        cursor.getString(10), // castrado
                        cursor.getString(11), // pelo
                        cursor.getString(12), // docil
                        cursor.getString(13)  // anotacoes
                );
                animal.setId(cursor.getInt(0)); // id
                lista.add(animal);
            } while (cursor.moveToNext());
        }
        if (cursor != null) cursor.close();
        db.close();
        return lista;
    }


    public String excluirDados(int id) {
        String msg = "Registro Excluído"; //exclui

        db = banco.getWritableDatabase();

        String condicao = "id = " + id;

        int linhas;
        linhas = db.delete("formulario", condicao, null);

        if (linhas < 1) {
            msg = "Erro ao Excluir";
        }

        db.close();
        return msg;
    }
}