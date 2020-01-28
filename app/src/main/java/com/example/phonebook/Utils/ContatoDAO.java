package com.example.phonebook.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.phonebook.DB.DbContatosUsuarios;
import com.example.phonebook.model.ContatosUsuarios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO implements IContatoDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;
    private Context context;

    public ContatoDAO(Context context) {
        DbContatosUsuarios dbContatosUsuarios = new DbContatosUsuarios(context);
        escreve = dbContatosUsuarios.getWritableDatabase();
        ler = dbContatosUsuarios.getReadableDatabase();
    }

    @Override
    public boolean salvar(ContatosUsuarios contatosUsuarios) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = contatosUsuarios.getImagemUsuario();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imagem = stream.toByteArray();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", contatosUsuarios.getNomeUsuario());
        contentValues.put("telefone", contatosUsuarios.getTelefoneUsuario());
        contentValues.put("email", contatosUsuarios.getEmailUsuario());
        contentValues.put("dataNasc", contatosUsuarios.getDatanascimento());
        contentValues.put("foto", imagem);
        contentValues.put("isFav",contatosUsuarios.getFavorite());
        contentValues.put("lon", contatosUsuarios.getLongitude());

        try {
            escreve.insert(DbContatosUsuarios.TABELA_CONTATOS, null, contentValues);
            Log.i("INFO", "Contato salvo com sucesso");


        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar contato" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(ContatosUsuarios contatosUsuarios) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = contatosUsuarios.getImagemUsuario();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imagem = stream.toByteArray();

        ContentValues contentValues = new ContentValues();

        contentValues.put("nome", contatosUsuarios.getNomeUsuario());
        contentValues.put("telefone", contatosUsuarios.getTelefoneUsuario());
        contentValues.put("email", contatosUsuarios.getEmailUsuario());
        contentValues.put("dataNasc", contatosUsuarios.getDatanascimento());
        contentValues.put("foto", imagem);
        contentValues.put("lat", contatosUsuarios.getLatitude());
        contentValues.put("lon", contatosUsuarios.getLongitude());
        try {

            String[] ids = {contatosUsuarios.getID().toString()};
            escreve.update(DbContatosUsuarios.TABELA_CONTATOS, contentValues, "id=?", ids);
            Log.i("INFO", "Contato Atualizado com sucesso");


        } catch (Exception e) {
            Log.e("INFO", "Erro ao Atualizar contato" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(ContatosUsuarios contatosUsuarios) {
/*
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = contatosUsuarios.getImagemUsuario();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imagem = stream.toByteArray();

        ContentValues contentValues = new ContentValues();

        contentValues.put("nome", contatosUsuarios.getNomeUsuario());
        contentValues.put("telefone", contatosUsuarios.getTelefoneUsuario());
        contentValues.put("email", contatosUsuarios.getEmailUsuario());
        contentValues.put("dataNasc", contatosUsuarios.getDatanascimento());
        contentValues.put("foto", imagem);
        contentValues.put("isFav",contatosUsuarios.getFavorite());
        contentValues.put("lat", contatosUsuarios.getLatitude());
        contentValues.put("lon", contatosUsuarios.getLongitude());*/
        try {

            String[] ids = {contatosUsuarios.getID().toString()};
            escreve.delete(DbContatosUsuarios.TABELA_CONTATOS, "id=?", ids);
            Log.i("INFO", "Contato " + contatosUsuarios.getNomeUsuario() + " Deletado com sucesso");


        } catch (Exception e) {
            Log.e("INFO", "Erro ao Deletar contato" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean favorito(ContatosUsuarios contatosUsuarios) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("isFav",contatosUsuarios.getFavorite());

        try {

            String[] ids = {contatosUsuarios.getID().toString()};
            escreve.update(DbContatosUsuarios.TABELA_CONTATOS, contentValues, "id=?", ids);
            Log.i("INFO", "Contato Atualizado com sucesso");


        } catch (Exception e) {
            Log.e("INFO", "Erro ao Atualizar contato" + e.getMessage());
            return false;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<ContatosUsuarios> listar() {

        List<ContatosUsuarios> contatosUsuarios = new ArrayList<>();

        String sql = "SELECT * FROM " + DbContatosUsuarios.TABELA_CONTATOS + " ;";
        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            ContatosUsuarios contatos = new ContatosUsuarios();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String telefone = cursor.getString(cursor.getColumnIndex("telefone"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String dataNasc = cursor.getString(cursor.getColumnIndex("dataNasc"));
            Double latitude = cursor.getDouble(cursor.getColumnIndex("lat"));
            Double longitude = cursor.getDouble(cursor.getColumnIndex("lon"));
            int isfav = cursor.getInt(cursor.getColumnIndex("isFav"));

            Log.i("ID", id.toString());
            byte[] foto = cursor.getBlob(cursor.getColumnIndex("foto"));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(foto);
            Bitmap fotoBitmap = BitmapFactory.decodeStream(inputStream);

            contatos.setID(id);
            contatos.setImagemUsuario(fotoBitmap);
            contatos.setNomeUsuario(nome);
            contatos.setTelefoneUsuario(telefone);
            contatos.setEmailUsuario(email);
            contatos.setDatanascimento(dataNasc);
            contatos.setLatitude(latitude);
            contatos.setLongitude(longitude);
            contatos.setFavorite(isfav);
            contatosUsuarios.add(contatos);
        }
        cursor.close();

        return contatosUsuarios;
    }

}
