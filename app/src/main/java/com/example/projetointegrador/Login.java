package com.example.projetointegrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText txtEmail, txtSenha;
    Button btEntrar;
    TextView txtCadastroLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtLogEmail);
        txtSenha = findViewById(R.id.txtLogSenha);
        btEntrar = findViewById(R.id.btEntrar);
        txtCadastroLink = findViewById(R.id.txtCadastroLink);

        btEntrar.setOnClickListener(this);
        txtCadastroLink.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btEntrar) {

            String msg;
            if (txtEmail.getText().length() == 0) {
                msg = "O campo de Email deve ser preenchido!";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            } else {
                if (txtSenha.getText().length() == 0) {
                    msg = "O campo de Senha deve ser preenchido!";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    BancoControllerUsuarios bd = new BancoControllerUsuarios(getBaseContext());

                    Cursor dados = bd.ConsultaLogin(txtEmail.getText().toString(),
                            txtSenha.getText().toString());

                    if (dados.moveToFirst()) {
                        Intent tela = new Intent(this, MainActivity.class);
                        tela.putExtra("emailLogado", txtEmail.getText().toString());
                        //passa o e-mail do usuario para a MainActivity
                        startActivity(tela);
                        finish();
                    } else {
                        msg = "O E-mail/Senha não estão cadastrados, cadastre-se!";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
        if (v.getId() == R.id.txtCadastroLink) {
            Intent tela = new Intent(this, Cadastre_se.class); // COLOQUE AQUI O LINK DA TELA DE CADASTRO
            startActivity(tela);
        }
    }
}