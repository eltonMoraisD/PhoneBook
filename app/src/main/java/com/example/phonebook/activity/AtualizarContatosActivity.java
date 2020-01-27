package com.example.phonebook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.R;
import com.example.phonebook.Utils.ContatoDAO;
import com.example.phonebook.model.ContatosUsuarios;

import java.util.Calendar;

public class AtualizarContatosActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final int GALLERIA_IMAGENS = 1;
    private ImageView updateFotoUsuario, updateLocalizacaoContato;
    private EditText updateNomeUsuario, updateTelefoneUsuario, updateEmailUsuario, updateDataNasc;
    private Button cancelar, update;
    private String nome, telefone, email, dataNasc;
    private int position;
    private Long id;
    private Bitmap imagem;
    private String latitude, longitude;
    private TextView tvLat, tvLon;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_contatos);

        updateFotoUsuario = findViewById(R.id.updateFotoUsuario);
        updateNomeUsuario = findViewById(R.id.updateNomeUsuario);
        updateTelefoneUsuario = findViewById(R.id.updateTelefoneUsuario);
        updateEmailUsuario = findViewById(R.id.updateEmailUsuario);
        updateDataNasc = findViewById(R.id.updateDataNascimentoUsuario);
        tvLat = findViewById(R.id.latitudeUpdate);
        tvLon = findViewById(R.id.longitudeUpdate);
        cancelar = findViewById(R.id.buttonCancelarUpdate);
        update = findViewById(R.id.buttonUpdate);
        updateLocalizacaoContato = findViewById(R.id.updateLocalizacaoContato);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            nome = extras.getString("nomeUsuario");
            telefone = extras.getString("telefoneUsuario");
            email = extras.getString("emailUsuario");
            imagem = BitmapFactory.decodeByteArray(getIntent()
                    .getByteArrayExtra("fotoUsuario"), 0, getIntent().getByteArrayExtra("fotoUsuario").length);
            dataNasc = extras.getString("dataNasc");
            position = extras.getInt("position");
            id = extras.getLong("id");
            latitude = extras.getString("lat");
            longitude = extras.getString("lon");

            updateDataNasc.setText(dataNasc);
            updateFotoUsuario.setImageBitmap(imagem);
            updateNomeUsuario.setText(nome);
            updateTelefoneUsuario.setText(telefone);
            updateEmailUsuario.setText(email);
            tvLat.setText(latitude);
            tvLon.setText(longitude);

        }

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AtualizarContatosActivity.this);

                alertDialog.setTitle("Cancelar");
                alertDialog.setMessage("Deseja cancelar a atualização?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                    }
                });

                alertDialog.setNegativeButton("Não", null);
                alertDialog.show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (updateNomeUsuario.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Introduza um novo nome", Toast.LENGTH_SHORT).show();

                } else if (updateTelefoneUsuario.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Introduza o novo número", Toast.LENGTH_SHORT).show();

                } else if (updateEmailUsuario.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Atualiza o seu e-mail", Toast.LENGTH_SHORT).show();

                } else if (updateDataNasc.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Campo não pode ser vazio", Toast.LENGTH_SHORT).show();

                } else {

                    Bitmap bitmap = ((BitmapDrawable) updateFotoUsuario.getDrawable()).getBitmap();
                    ContatosUsuarios contatosUsuarios = new ContatosUsuarios();
                    contatosUsuarios.setNomeUsuario(updateNomeUsuario.getText().toString());
                    contatosUsuarios.setTelefoneUsuario(updateTelefoneUsuario.getText().toString());
                    contatosUsuarios.setEmailUsuario(updateEmailUsuario.getText().toString());
                    contatosUsuarios.setDatanascimento(updateDataNasc.getText().toString());
                    contatosUsuarios.setID(id);


                    contatosUsuarios.setImagemUsuario(bitmap);

                    ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

                    contatoDAO.atualizar(contatosUsuarios);
                    startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                }
            }
        });

        updateFotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALLERIA_IMAGENS);
            }
        });

        updateDataNasc.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDatePickerDialog();
                return true;
            }
        });

        updateLocalizacaoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            /*String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);

            c.close();
            Bitmap bitmap = (BitmapFactory.decodeFile(picturePath));
            updateFotoUsuario.setImageBitmap(bitmap);*/
            updateFotoUsuario.setImageURI(selectedImage);
        }
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String data = dayOfMonth + "/" + month + "/" + year;
        updateDataNasc.setText(data);
    }
}
