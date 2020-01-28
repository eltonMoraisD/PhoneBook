package com.example.phonebook.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.R;
import com.example.phonebook.Utils.Comon;
import com.example.phonebook.Utils.ContatoDAO;
import com.example.phonebook.activity.AtualizarContatosActivity;
import com.example.phonebook.model.ContatosUsuarios;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private List<ContatosUsuarios> lista;
    private ImageView btnAtualizarContato;
    private int posicao;
    private int posicaoAdapter;
    private Long id;
    private ContatosUsuarios contatosUsuarios;

    public AdapterContact(List<ContatosUsuarios> lista) {
        this.lista = lista;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_contact, parent, false);

        return new ContactViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {

        contatosUsuarios = lista.get(position);

        holder.nomeUsuario.setText(contatosUsuarios.getNomeUsuario());
        holder.telefoneUsuario.setText(contatosUsuarios.getTelefoneUsuario());
        holder.emailUsuario.setText(contatosUsuarios.getEmailUsuario());
        holder.dataNasc.setText(contatosUsuarios.getDatanascimento());
        holder.latitude.setText(contatosUsuarios.getLatitude().toString());
        holder.longitude.setText(contatosUsuarios.getLongitude().toString());

        if (lista.get(position).getImagemUsuario() != null) {
            holder.fotoUsuario.setImageBitmap(contatosUsuarios.getImagemUsuario());

        } else {
            //  holder.fotoUsuario.setImageResource(R.drawable.ic_contact_default_24dp);

        }
        posicao = position;

        posicaoAdapter = holder.getAdapterPosition(); // pegar a posiçao do adapter

        btnAtualizarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AtualizarContatosActivity.class);
                Bitmap bitmap = ((BitmapDrawable) holder.fotoUsuario.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                intent.putExtra("nomeUsuario", holder.nomeUsuario.getText().toString());
                intent.putExtra("telefoneUsuario", holder.telefoneUsuario.getText().toString());
                intent.putExtra("fotoUsuario", outputStream.toByteArray());
                intent.putExtra("dataNasc", holder.dataNasc.getText().toString());
                intent.putExtra("emailUsuario", holder.emailUsuario.getText().toString());
                intent.putExtra("position", position);
                intent.putExtra("id", contatosUsuarios.getID());
                intent.putExtra("lat", holder.latitude.getText().toString());
                intent.putExtra("lon", holder.longitude.getText().toString());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private ImageView fotoUsuario;
        private TextView nomeUsuario;
        private TextView telefoneUsuario;
        private TextView emailUsuario;
        private TextView dataNasc;
        private TextView latitude, longitude;

        private ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoUsuario = itemView.findViewById(R.id.imagemUsuario);
            nomeUsuario = itemView.findViewById(R.id.nomeUsuario);
            telefoneUsuario = itemView.findViewById(R.id.telefoneUsuario);
            btnAtualizarContato = itemView.findViewById(R.id.btnAtualizarContato);
            emailUsuario = itemView.findViewById(R.id.emailUsuario);
            dataNasc = itemView.findViewById(R.id.dataNasc);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opções");
            MenuItem call = menu.add(Menu.NONE, 1, 1, "Call");
            MenuItem sms = menu.add(Menu.NONE, 2, 2, "SMS");
            MenuItem favorite = menu.add(Menu.NONE, 3, 3, "Favorite");
            MenuItem remove = menu.add(Menu.NONE, 4, 4, "Remove");

            call.setOnMenuItemClickListener(this);
            sms.setOnMenuItemClickListener(this);
            favorite.setOnMenuItemClickListener(this);
            remove.setOnMenuItemClickListener(this);

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onMenuItemClick(final MenuItem item) {

            String numTelefone = telefoneUsuario.getText().toString();
            String uri = numTelefone.trim();
            final Uri chamada = Uri.parse("tel:" + uri);
            final Intent intent;

            switch (item.getItemId()) {
                case 1:
                    // efetuar a ligaçao

                    intent = new Intent(Intent.ACTION_CALL, chamada);

                    if (ActivityCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) itemView.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                    intent.setData(chamada);

                    itemView.getContext().startActivity(intent);

                    return true;
                case 2:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", uri, null));
                    itemView.getContext().startActivity(intent);
                    return true;

                case 3:
                    //adicionar aos favoritos
                    /*Bitmap bitmap = ((BitmapDrawable) fotoUsuario.getDrawable()).getBitmap();

                    contatosUsuarios.setNomeUsuario(nomeUsuario.getText().toString());
                    contatosUsuarios.setTelefoneUsuario(telefoneUsuario.getText().toString());
                    contatosUsuarios.setImagemUsuario(bitmap);
                    contatosUsuarios.setFavorite(true);
*/
                    //con.setFavorite(true);
                    //Comon.listaContatosUsuarios.get(posicao).setFavorite(1);
                    //contatosUsuarios.setFavorite(true);
                    ContatosUsuarios con =  Comon.listaContatosUsuarios.get(posicao);
                    con.setFavorite(1);
                    ContatoDAO contatoDAO = new ContatoDAO(itemView.getContext());
                    contatoDAO.favorito(con);

                    return true;

                case 4:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());

                    alertDialog.setTitle("Remover");
                    alertDialog.setMessage("Deseja remover o contacto?");

                    alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                        @RequiresApi(api = Build.VERSION_CODES.N)
                        public void onClick(DialogInterface dialog, int which) {
                            ContatosUsuarios contatosUsuarios = Comon.listaContatosUsuarios.get(getAdapterPosition());
                            ContatoDAO contatoDAO = new ContatoDAO(itemView.getContext());
/*
                            Bitmap bitmap = ((BitmapDrawable) fotoUsuario.getDrawable()).getBitmap();
                            contatosUsuarios.setNomeUsuario(nomeUsuario.getText().toString());
                            contatosUsuarios.setTelefoneUsuario(telefoneUsuario.getText().toString());
                            contatosUsuarios.setImagemUsuario(bitmap);
                            contatosUsuarios.setID(contatoDAO.listar().get(posicao).getID());*/

                            contatoDAO.deletar(contatosUsuarios);
                            Log.i("D","delete");

                        }
                    });
                    alertDialog.setNegativeButton("Não", null);
                    alertDialog.show();
            }
            return true;
        }
    }


}
