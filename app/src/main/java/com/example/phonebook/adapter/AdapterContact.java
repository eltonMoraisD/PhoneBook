package com.example.phonebook.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import androidx.recyclerview.widget.RecyclerView;
import com.example.phonebook.R;
import com.example.phonebook.activity.AtualizarConatosActivity;
import com.example.phonebook.comon.Comon;
import com.example.phonebook.model.ContatosUsuarios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;


public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private List<ContatosUsuarios> lista;
    private ImageView btnAtualizarContato;
    private int posicao;
    private int posicaoAdapter;

    public AdapterContact(List<ContatosUsuarios> lista) {
        this.lista = lista;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_contact, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {

        ContatosUsuarios contatosUsuarios = lista.get(position);

        holder.nomeUsuario.setText(contatosUsuarios.getNomeUsuario());
        holder.telefoneUsuario.setText(contatosUsuarios.getTelefoneUsuario());
        holder.emailUsuario.setText(contatosUsuarios.getEmailUsuario());
        holder.dataNasc.setText(contatosUsuarios.getDatanascimento());
        holder.fotoUsuario.setImageBitmap(contatosUsuarios.getImagemUsuario());

        posicao = position;

        posicaoAdapter = holder.getAdapterPosition(); // pegar a posiçao do adapter

        btnAtualizarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AtualizarConatosActivity.class);
                Bitmap bitmap = ((BitmapDrawable)holder.fotoUsuario.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream);

                intent.putExtra("nomeUsuario", holder.nomeUsuario.getText().toString());
                intent.putExtra("telefoneUsuario", holder.telefoneUsuario.getText().toString());
                intent.putExtra("fotoUsuario", outputStream.toByteArray() );
                intent.putExtra("dataNasc", holder.dataNasc.getText().toString());
                intent.putExtra("emailUsuario", holder.emailUsuario.getText().toString());
                intent.putExtra("position", position);

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

        private ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoUsuario = itemView.findViewById(R.id.imagemUsuario);
            nomeUsuario = itemView.findViewById(R.id.nomeUsuario);
            telefoneUsuario = itemView.findViewById(R.id.telefoneUsuario);
            btnAtualizarContato = itemView.findViewById(R.id.btnAtualizarContato);
            emailUsuario = itemView.findViewById(R.id.emailUsuario);
            dataNasc = itemView.findViewById(R.id.dataNasc);

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

        @Override
        public boolean onMenuItemClick(final MenuItem item) {

            String numTelefone = telefoneUsuario.getText().toString();
            String uri = numTelefone.trim();
            Uri chamada = Uri.parse("tel:" + uri);
            Intent intent;

            switch (item.getItemId()) {
                case 1:
                    // efetuar a ligaçao
                    intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(chamada);
                    itemView.getContext().startActivity(intent);

                    return true;
                case 2:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", uri, null));
                    itemView.getContext().startActivity(intent);
                    return true;

                case 3:
                    //adicionar aos favoritos
                    Bitmap bitmap = ((BitmapDrawable)fotoUsuario.getDrawable()).getBitmap();
                    final ContatosUsuarios con = new ContatosUsuarios();
                    con.setNomeUsuario(nomeUsuario.getText().toString());
                    con.setTelefoneUsuario(telefoneUsuario.getText().toString());
                    con.setImagemUsuario(bitmap);

                    //con.setFavorite(true);
                   Comon.listaContatosUsuarios.get(posicao).setFavorite(true);


                    Toast.makeText(itemView.getContext(), "Contato " + con.getNomeUsuario() + " adicionado nos favoritos", Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                    return true;

                case 4:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());

                    alertDialog.setTitle("Remover");
                    alertDialog.setMessage("Deseja remover o contacto?");

                    alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Comon.listaContatosUsuarios.remove(Comon.listaContatosUsuarios.get(getAdapterPosition()));
                            //Comon.listaImagensUsuarios.remove(Comon.listaImagensUsuarios.get(getAdapterPosition()));
                            Comon.listaContatosUsuarios.remove(posicao);
                            notifyDataSetChanged();
                        }
                    });
                    alertDialog.setNegativeButton("Não", null);
                    alertDialog.show();
            }
            return true;
        }
    }
}
