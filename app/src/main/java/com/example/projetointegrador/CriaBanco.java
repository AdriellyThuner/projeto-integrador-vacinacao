package com.example.projetointegrador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco_form.db";
    private static final int VERSAO = 9;
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
                + "codigo INTEGER primary key autoincrement,"
                + "nome TEXT,"
                + "cpf Text,"
                + "telefone TEXT,"
                + "crm TEXT,"
                + "ong TEXT,"
                + "email TEXT,"
                + "senha TEXT"
                + ")";
        db.execSQL(sql);

        sql = "Create table vacinas("
                + "id INTEGER primary key autoincrement,"
                + "petID INTEGER,"
                + "nomeVac TEXT,"
                + "dataVac Text,"
                + "horaVac TEXT,"
                + "vetVac TEXT,"
                + "crmVac TEXT,"
                + "dose TEXT,"
                + "detVac TEXT,"
                +"detVac1 TEXT,"
                + "detVac2 TEXT,"
                + "refVac TEXT,"
                + "Dataref TEXT,"
                + "ObsVac TEXT,"
                +"dose2 TEXT,"
                + "nomeDose TEXT,"
                + "dataDose TEXT,"
                + "checklist TEXT,"
                + "proVac TEXT"
                + ")";
        db.execSQL(sql);

        sql = "insert into usuarios (nome, email, senha, cpf, telefone, crm,ong)" +
                "values ('ADMIN', 'admin@teste.com', 'adm123', null, null, null, null)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS formulario");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS vacinas");
        onCreate(db);
    }
}
