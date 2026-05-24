package com.example.projetointegrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class Cadastre_se extends AppCompatActivity implements View.OnClickListener {
    Button btnCad;
    EditText txtCadNome, txtCadCPF, txtCadEmail,txtCadCRM, txtCadOng ;
    EditText txtCadSenha, txtConfSenha, txtCadTelefone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastre_se);

        btnCad = findViewById(R.id.btnCad);
        txtCadNome  = findViewById(R.id.txtCadNome);
        txtCadCPF   = findViewById(R.id.txtCadCPF);
        txtCadTelefone = findViewById(R.id.txtCadTelefone);
        txtCadCRM = findViewById(R.id.txtCadCRM);
        txtCadOng = findViewById(R.id.txtCadOng);
        txtCadEmail = findViewById(R.id.txtCadEmail);
        txtCadSenha = findViewById(R.id.txtCadSenha);
        txtConfSenha = findViewById(R.id.txtConfSenha);
        btnCad.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (ValidaDados()) {
            // gravar os dados
            BancoControllerUsuarios bd = new BancoControllerUsuarios(getBaseContext());
            String resultado;



            resultado = bd.insereDados(
                    txtCadNome.getText().toString(),
                    txtCadCPF.getText().toString(),
                    txtCadTelefone.getText().toString(),
                    txtCadCRM.getText().toString(),
                    txtCadOng.getText().toString(),
                    txtCadEmail.getText().toString(),
                    txtCadSenha.getText().toString()
            );
            Toast.makeText(getApplicationContext(), resultado,
                    Toast.LENGTH_LONG).show();



        }else{
            // mandar mensagem de erro ao gravar os dados
            Toast.makeText(getApplicationContext(), "Erro ao gravar os dados!",
                    Toast.LENGTH_LONG).show();
        }
    }


    public boolean ValidaDados() {
        if (txtCadNome.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo Nome deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadCPF.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo CPF deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (txtCadTelefone.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo Telefone deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadCRM.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo CRM deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadOng.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo ONG deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (txtCadEmail.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo E-mail deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadSenha.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo Senha deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtConfSenha.getText().length()==0) {
            Toast.makeText(getApplicationContext(), "O Campo Confirma Senha deve ser preenchido!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (!txtCadSenha.getText().toString().equals(txtConfSenha.getText().toString())) {
            Toast.makeText(getApplicationContext(), "As senhas digitadas não estão iguais!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
