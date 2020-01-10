package com.example.phonebook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonebook.R;
import com.example.phonebook.comon.Comon;
import com.example.phonebook.model.ContatosUsuarios;
import java.util.Calendar;

public class AdicionarContatosActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ImageView fotoUsuario;
    private EditText nomeUsuario,telefone,email,dataNascimento;
    private Button cancelar, adicionar;
    private ContatosUsuarios contatosUsuarios;

    private final int GALLERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_contatos);

        fotoUsuario = findViewById(R.id.uploadFotoUsuario);
        nomeUsuario = findViewById(R.id.addNomeUsuario);
        telefone = findViewById(R.id.addTelefoneUsuario);
        email = findViewById(R.id.addEmailUsuario);
        dataNascimento = findViewById(R.id.addDataNascimentoUsuario);
        cancelar = findViewById(R.id.buttonCancelar);
        adicionar = findViewById(R.id.buttonAdicionar);

        dataNascimento.setOnTouchListener(new View.OnTouchListener(){

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

                    public void onClick(DialogInterface dialog,int which) {
                        startActivity(new Intent(getApplicationContext(),ContactActivity.class));

                    }
                });

                alertDialog.setNegativeButton("Não",null);
                alertDialog.show();
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contatosUsuarios = new ContatosUsuarios();

                String nome = nomeUsuario.getText().toString();
                String tel = telefone.getText().toString();
                String emailUsuario = email.getText().toString();
                String dataNasc = dataNascimento.getText().toString();
                Bitmap bitmap = ((BitmapDrawable)fotoUsuario.getDrawable()).getBitmap();

                if (nome.equals("")){
                    Toast.makeText(getApplicationContext(),"Informa um nome",Toast.LENGTH_SHORT).show();

                }else if (tel.equals("")){
                    Toast.makeText(getApplicationContext(),"Informa o número de telefone",Toast.LENGTH_SHORT).show();

                }else if (emailUsuario.equals("")){
                    Toast.makeText(getApplicationContext(),"Informa um email",Toast.LENGTH_SHORT).show();

                }else if (dataNasc.equals("")){
                    Toast.makeText(getApplicationContext(),"Informa o número de telefone",Toast.LENGTH_SHORT).show();

                }

                else {
                    contatosUsuarios.setNomeUsuario(nome);
                    contatosUsuarios.setTelefoneUsuario(tel);
                    contatosUsuarios.setEmailUsuario(emailUsuario);
                    contatosUsuarios.setDatanascimento(dataNasc);
                    contatosUsuarios.setImagemUsuario(bitmap);

                    Comon.listaContatosUsuarios.add(contatosUsuarios);

                    Intent intent = new Intent(getApplicationContext(),ContactActivity.class);
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


        //Tratamento da permissao do usuario
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {

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
            String[] filePath = { MediaStore.Images.Media.DATA };

            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap bitmap = (BitmapFactory.decodeFile(picturePath));
            fotoUsuario.setImageBitmap(bitmap);

            fotoUsuario.setImageURI(selectedImage);


        }
    }

    public void showDatePickerDialog(){
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
