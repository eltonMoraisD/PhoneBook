package com.example.phonebook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class AdicionarContatosActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ImageView fotoUsuario;
    private EditText nomeUsuario, telefone, email, dataNascimento;
    private Button cancelar, adicionar;
    private TextView latitude, longitude;
    private ContatosUsuarios contatosUsuarios;
    private ImageView localizacaoContato;
    private final int GALLERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;
    private Double latitudeMap, longitudeMap;
    private final String KEY_NOME = "Nome";
    private String nome;
    private String tel;
    private String emailUsuario;
    private String dataNasc;
    private Bitmap bitmap;
    private Double lat;
    private Double lon;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_contatos);
        nomeUsuario = findViewById(R.id.addNomeUsuario);
        if (savedInstanceState != null) {
            nome = savedInstanceState.getString(KEY_NOME);
            Log.i("T", "Entrou");
            nomeUsuario.setText(nome);
        } else {
            nomeUsuario.setText(nome);
        }

        fotoUsuario = findViewById(R.id.uploadFotoUsuario);
        localizacaoContato = findViewById(R.id.localizacaoContato);

        telefone = findViewById(R.id.addTelefoneUsuario);
        email = findViewById(R.id.addEmailUsuario);
        dataNascimento = findViewById(R.id.addDataNascimentoUsuario);
        cancelar = findViewById(R.id.buttonCancelar);
        adicionar = findViewById(R.id.buttonAdicionar);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            latitudeMap = extras.getDouble("latitude");
            longitudeMap = extras.getDouble("longitude");

            String lat = Double.toString(latitudeMap);
            String lon = Double.toString(longitudeMap);
            latitude.setText(lat);
            longitude.setText(lon);
        }

        dataNascimento.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDatePickerDialog();
                return true;
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdicionarContatosActivity.this);

                alertDialog.setTitle("Cancelar");
                alertDialog.setMessage("Deseja cancelar o cadastro?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), ContactActivity.class));

                    }
                });

                alertDialog.setNegativeButton("Não", null);
                alertDialog.show();
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contatosUsuarios = new ContatosUsuarios();

                nome = nomeUsuario.getText().toString();
                tel = telefone.getText().toString();
                emailUsuario = email.getText().toString();
                dataNasc = dataNascimento.getText().toString();
                bitmap = ((BitmapDrawable) fotoUsuario.getDrawable()).getBitmap();
                lat = Double.valueOf(latitude.getText().toString());
                lon = Double.valueOf(longitude.getText().toString());


                if (nome.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Informa um nome", Toast.LENGTH_SHORT).show();

                } else if (tel.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Informa o número de telefone", Toast.LENGTH_SHORT).show();

                } else if (emailUsuario.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Informa um email", Toast.LENGTH_SHORT).show();

                } else if (dataNasc.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Informa o número de telefone", Toast.LENGTH_SHORT).show();

                } else {
                    contatosUsuarios.setNomeUsuario(nome);
                    contatosUsuarios.setTelefoneUsuario(tel);
                    contatosUsuarios.setEmailUsuario(emailUsuario);
                    contatosUsuarios.setDatanascimento(dataNasc);
                    contatosUsuarios.setImagemUsuario(bitmap);
                    contatosUsuarios.setLatitude(lat);
                    contatosUsuarios.setLongitude(lon);

                    ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

                    contatoDAO.salvar(contatosUsuarios);
                    Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                    startActivity(intent);
                }
            }
        });

        fotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALLERIA_IMAGENS);
            }
        });

        localizacaoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);

            }

        });


        //Tratamento da permissao do usuario
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdicionarContatosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERIA_IMAGENS) {
            Uri selectedImage = data.getData();

            fotoUsuario.setImageURI(selectedImage);

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
        month = month + 1;
        String data = dayOfMonth + "/" + month + "/" + year;
        dataNascimento.setText(data);
    }

}
