package com.example.phonebook.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.R;
import com.example.phonebook.comon.Comon;
import com.example.phonebook.model.ContatosUsuarios;

import java.util.List;

public class AdapterFavoritos extends RecyclerView.Adapter<AdapterFavoritos.FavoriteViewHolder> {

    //private List<ContatosUsuarios> lista;
    Context context;

    public AdapterFavoritos(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_favoritos, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, final int position) {
        final ContatosUsuarios contatosUsuarios = Comon.listaContatosUsuarios.get(position);

        holder.nomeUsuarioFav.setText(Comon.listaContatosUsuarios.get(position).getNomeUsuario());
        holder.telefoneUsuarioFav.setText(Comon.listaContatosUsuarios.get(position).getTelefoneUsuario());
        holder.fotoUsuarioFav.setImageBitmap(Comon.listaContatosUsuarios.get(position).getImagemUsuario());

        holder.deleteContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Remover");
                alertDialog.setMessage("Deseja remover o contacto dos favoritos?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,int which) {
                      //  Comon.listaFavoritos.remove(holder.getAdapterPosition());
                        //Comon.listaImagensFavoritos.remove(holder.getAdapterPosition());

                        Comon.listaContatosUsuarios.get(position).setFavorite(false);
                        notifyDataSetChanged();

                    }
                });

                alertDialog.setNegativeButton("Não",null);
                alertDialog.show();



            }
        });
    }

    @Override
    public int getItemCount() {
        int cont = 0;

        for (int i = 0; i < Comon.listaContatosUsuarios.size(); i++){
            if (Comon.listaContatosUsuarios.get(i).getFavorite()){
                cont ++;
            }
        }

        return cont;
    }


    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        ImageView fotoUsuarioFav;
        ImageView deleteContato;
        TextView nomeUsuarioFav;
        TextView telefoneUsuarioFav;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoUsuarioFav = itemView.findViewById(R.id.imagemUsuarioFavorito);
            nomeUsuarioFav = itemView.findViewById(R.id.nomeUsuarioFavorito);
            telefoneUsuarioFav = itemView.findViewById(R.id.telefoneUsuarioFavorito);
            deleteContato = itemView.findViewById(R.id.deleteContatoFavorito);

            itemView.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opções");
            MenuItem call = menu.add(Menu.NONE, 1, 1, "Call");
            MenuItem sms = menu.add(Menu.NONE, 2, 2, "SMS");

            call.setOnMenuItemClickListener(this);
            sms.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String numTelefone = telefoneUsuarioFav.getText().toString();
            String uri = numTelefone.trim();
            Uri chamada = Uri.parse("tel:" + uri);
            Intent intent;

            switch (item.getItemId()) {
                case 1:
                    intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(chamada);
                    itemView.getContext().startActivity(intent);
                    return true;
                case 2:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", uri, null));
                    itemView.getContext().startActivity(intent);
                    return true;

            }return true;
        }
    }
}
