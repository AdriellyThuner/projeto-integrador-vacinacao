package com.example.projetointegrador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco_form.db";
    private static final int VERSAO = 8;
    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE formulario ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT,"
                + "idade INTEGER,"
                + "sexo TEXT,"
                + "data TEXT,"
                + "porte TEXT,"
                + "especie TEXT,"
                + "enfermidade TEXT,"
                + "descricao TEXT,"
                + "local TEXT,"
                + "castrado TEXT,"
                + "pelo TEXT,"
                + "docil TEXT,"
                + "anotacoes TEXT"
                + ")";
        db.execSQL(sql);

        sql = "Create table usuarios("
                + "codigo integer primary key autoincrement,"
                + "nome text,"
                + "email text,"
                + "senha text,"
                + "cpf,"
                + "telefone)";
        db.execSQL(sql);

        sql = "insert into usuarios (nome, email, senha, cpf, telefone)" +
                "values ('ADMIN', 'admin@teste.com', 'adm123', null, null)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS formulario");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
