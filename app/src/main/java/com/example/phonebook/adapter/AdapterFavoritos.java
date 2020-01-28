package com.example.phonebook.adapter;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.R;
import com.example.phonebook.Utils.Comon;
import com.example.phonebook.Utils.ContatoDAO;
import com.example.phonebook.model.ContatosUsuarios;

import java.util.List;

public class AdapterFavoritos extends RecyclerView.Adapter<AdapterFavoritos.FavoriteViewHolder> {

    private Context context;
    private List<ContatosUsuarios> lista;
    private ContatoDAO contatoDAO;
    private ContatosUsuarios contatosUsuarios;

    public AdapterFavoritos(Context context, List<ContatosUsuarios> lista) {
        this.lista = lista;
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
        contatosUsuarios = lista.get(position);
        holder.nomeUsuarioFav.setText(contatosUsuarios.getNomeUsuario());
        holder.telefoneUsuarioFav.setText(contatosUsuarios.getTelefoneUsuario());
        holder.fotoUsuarioFav.setImageBitmap(contatosUsuarios.getImagemUsuario());

        holder.deleteContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Remover");
                alertDialog.setMessage("Deseja remover o contacto dos favoritos?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(DialogInterface dialog, int which) {

                        //lista.get(contatosUsuarios).setFavorite(false);
                        contatosUsuarios = Comon.listaContatosUsuarios.get(position);
                        contatosUsuarios.setFavorite(0);
                        contatoDAO.favorito(contatosUsuarios);



                        notifyDataSetChanged();

                    }
                });

                alertDialog.setNegativeButton("Não", null);
                alertDialog.show();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getItemCount() {
        int cont = 0;
        contatoDAO = new ContatoDAO(context);
        for (int i = 0; i < Comon.listaContatosUsuarios.size(); i++) {

            if (Comon.listaContatosUsuarios.get(i).getFavorite() == 1) {
                Log.i("cont", "Entrou no if");
                cont++;


            } else {
                Log.i("cont", "Entrou no else");

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

            }
            return true;
        }
    }


}
