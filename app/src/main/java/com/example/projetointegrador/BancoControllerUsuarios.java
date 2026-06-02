package com.example.projetointegrador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoControllerUsuarios {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoControllerUsuarios(Context context) {
        banco = new CriaBanco(context);
    }

    public Cursor ConsultaLogin(String email, String senha) {
        Cursor cursor;
        String[] campos = {"codigo", "nome", "email", "cpf", "senha", "telefone", "crm", "ong"};
        String where = "email = '" + email + "' and senha = '" + senha + "'";
        db = banco.getReadableDatabase();
        cursor = db.query(false, "usuarios", campos, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public String insereDados(String txtCadNome, String txtCadCPF, String txtCadTelefone,
                              String txtCadCRM, String txtCadOng,
                              String txtCadEmail, String txtCadSenha) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", txtCadNome);
        valores.put("cpf", txtCadCPF);
        valores.put("telefone", txtCadTelefone);
        valores.put("email", txtCadEmail);
        valores.put("senha", txtCadSenha);
        valores.put("crm", txtCadCRM);
        valores.put("ong", txtCadOng);

        resultado = db.insert("usuarios", null, valores);
        db.close(); //

        if (resultado == -1)
            return "Erro ao inserir registro do usuário";
        else
            return "Registro Inserido com sucesso";
    }



    //  Busca apenas o usuário pelo email
    public Cursor buscaDadosPorEmail(String email) {
        String[] campos = {"codigo", "nome", "cpf", "telefone", "crm", "ong", "email"};
        String where = "email = ?";
        String[] args = {email};
        db = banco.getReadableDatabase();
        Cursor cursor = db.query(false, "usuarios", campos, where, args, null, null, null, null); //realiza a consulta filtrando pelo email informado
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}