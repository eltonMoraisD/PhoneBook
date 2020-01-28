package com.example.phonebook.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbContatosUsuarios extends SQLiteOpenHelper {
    public static int VERSION = 2;
    public static String NOME_DB = "DB_CONTATOS";
    public static String TABELA_CONTATOS = "contatos";


    public DbContatosUsuarios(@Nullable Context context) {

        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_CONTATOS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL, " +
                " telefone TEXT NOT NULL, " +
                " email TEXT NOT NULL," +
                " dataNasc TEXT NOT NULL, " +
                " isFav BOOLEAN, " +
                " foto BLOB NOT NULL, " +
                " lat DOUBLE, " +
                " lon DOUBLE);";

        try {
            db.execSQL(sql);
            Log.d("INFO DB", "Tabela criada com sucesso");

        } catch (Exception e) {
            Log.d("INFO DB", "Erro ao criar tabela" + e.getMessage());
        }
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "ALTER TABLE " + TABELA_CONTATOS + " ADD COLUMN status VARCHAR(1)";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.d("INFO DB", "Tabela criada com sucesso");

        } catch (Exception e) {
            Log.d("INFO DB", "Erro ao criar tabela" + e.getMessage());
        }

    }
}
