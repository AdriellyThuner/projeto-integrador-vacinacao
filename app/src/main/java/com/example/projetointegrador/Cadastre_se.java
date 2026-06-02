package com.example.projetointegrador;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Cadastre_se extends AppCompatActivity implements View.OnClickListener {


    Button btnCad;


    EditText txtCadNome;      // nome completo
    EditText txtCadCPF;       // CPF
    EditText txtCadEmail;     // e-mail
    EditText txtCadCRM;       // CRMV do veterinário
    EditText txtCadOng;       // ONG vinculada
    EditText txtCadSenha;     // senha escolhida
    EditText txtConfSenha;    // confirmação da senha
    EditText txtCadTelefone;  // telefone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastre_se);


        btnCad         = findViewById(R.id.btnCad);
        txtCadNome     = findViewById(R.id.txtCadNome);
        txtCadCPF      = findViewById(R.id.txtCadCPF);
        txtCadTelefone = findViewById(R.id.txtCadTelefone);
        txtCadCRM      = findViewById(R.id.txtCadCRM);
        txtCadOng      = findViewById(R.id.txtCadOng);
        txtCadEmail    = findViewById(R.id.txtCadEmail);
        txtCadSenha    = findViewById(R.id.txtCadSenha);
        txtConfSenha   = findViewById(R.id.txtConfSenha);

        btnCad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (!ValidaDados()) return;

        try {
            // Converte a senha para MD5 antes de enviar
            // Nunca envia a senha em texto puro
            String senhaHash = md5(txtCadSenha.getText().toString());
            cadastrarNoSupabase(senhaHash); // chama o método de cadastro
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao processar senha.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarNoSupabase(String senhaHash) {
        OkHttpClient client = new OkHttpClient();


        JsonObject json = new JsonObject();
        json.addProperty("nome",       txtCadNome.getText().toString().trim());
        json.addProperty("CPF",        txtCadCPF.getText().toString().trim());
        json.addProperty("telefone",   txtCadTelefone.getText().toString().trim());
        json.addProperty("CRMV",       txtCadCRM.getText().toString().trim());
        json.addProperty("ong",        txtCadOng.getText().toString().trim());
        json.addProperty("email",      txtCadEmail.getText().toString().trim());
        json.addProperty("senha_hash", senhaHash);


        RequestBody body = RequestBody.create(
                json.toString(), MediaType.parse("application/json"));


        Request request = new Request.Builder()
                .url(SupabaseConfig.URL + "/rest/v1/usuarios")
                .post(body)
                .addHeader("apikey",        SupabaseConfig.API_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                .addHeader("Content-Type",  "application/json")
                .addHeader("Prefer",        "return=minimal")
                .build();


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                Log.e("CADASTRO_ERRO", e.getMessage());
                runOnUiThread(() ->
                        Toast.makeText(Cadastre_se.this,
                                "Erro de conexão: " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("CADASTRO_RESP", String.valueOf(response.code()));


                runOnUiThread(() -> {
                    if (response.isSuccessful()) {

                        Toast.makeText(Cadastre_se.this,
                                "Cadastro realizado com sucesso!",
                                Toast.LENGTH_LONG).show();
                        finish();
                    } else {

                        try {
                            String erro = response.body() != null
                                    ? response.body().string() : "erro";
                            Log.e("CADASTRO_ERRO", erro); // visível no Logcat
                            Toast.makeText(Cadastre_se.this,
                                    "Erro ao cadastrar: " + erro,
                                    Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //  Valida se todos os campos foram preenchidos corretamente
    public boolean ValidaDados() {


        if (txtCadNome.getText().length() == 0) {
            Toast.makeText(this, "O Campo Nome deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadCPF.getText().length() == 0) {
            Toast.makeText(this, "O Campo CPF deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadTelefone.getText().length() == 0) {
            Toast.makeText(this, "O Campo Telefone deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadCRM.getText().length() == 0) {
            Toast.makeText(this, "O Campo CRM deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadOng.getText().length() == 0) {
            Toast.makeText(this, "O Campo ONG deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadEmail.getText().length() == 0) {
            Toast.makeText(this, "O Campo E-mail deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCadSenha.getText().length() == 0) {
            Toast.makeText(this, "O Campo Senha deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtConfSenha.getText().length() == 0) {
            Toast.makeText(this, "O Campo Confirma Senha deve ser preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }

        // Verifica se as duas senhas digitadas são iguais
        if (!txtCadSenha.getText().toString().equals(txtConfSenha.getText().toString())) {
            Toast.makeText(this, "As senhas digitadas não estão iguais!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true; //
    }


    public static String md5(String senha) throws Exception {

        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");


        byte[] hash = md.digest(senha.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));

        return sb.toString();
    }
}