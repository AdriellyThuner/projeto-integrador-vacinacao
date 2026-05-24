package com.example.projetointegrador;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InfoUsuario extends AppCompatActivity {
    // TextViews do XML
    TextView PerfilNome, PerfilCPF, PerfilTelefone;
    TextView PerfilCRM, PerfilONG, PerfilEmail, PerfilResumo;
    ImageButton btVoltarPets;

    String emailLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario); // confirme o nome do seu XML

        // Vincula os componentes pelos IDs do XML
        PerfilNome      = findViewById(R.id.PerfilNome);
        PerfilCPF       = findViewById(R.id.PerfilCPF);
        PerfilTelefone  = findViewById(R.id.PerfilTelefone);
        PerfilCRM       = findViewById(R.id.PerfilCRM);
        PerfilONG       = findViewById(R.id.PerfilONG);
        PerfilEmail     = findViewById(R.id.PerfilEmail);
        PerfilResumo    = findViewById(R.id.PerfilResumo);
        btVoltarPets    = findViewById(R.id.btVoltarPets);

        emailLogado = getIntent().getStringExtra("emailLogado");

        // Botão voltar
        btVoltarPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Carrega os dados do banco
        carregarDadosUsuario();
    }

    private void carregarDadosUsuario() {
        BancoControllerUsuarios bd = new BancoControllerUsuarios(getBaseContext());


        Cursor cursor = bd.buscaDadosPorEmail(emailLogado); //consulta o banco pelo email recebido

        if (cursor != null && cursor.moveToFirst()) {

            // Lê cada coluna pelo nome — os mesmos nomes usados no insereDados()
            String nome      = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            String cpf       = cursor.getString(cursor.getColumnIndexOrThrow("cpf"));
            String telefone  = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));
            String crm       = cursor.getString(cursor.getColumnIndexOrThrow("crm"));
            String ong       = cursor.getString(cursor.getColumnIndexOrThrow("ong"));
            String email     = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            // Preenche os TextViews
            PerfilNome.setText(nome);
            PerfilCPF.setText("CPF: " + cpf);
            PerfilTelefone.setText("Telefone: " + telefone);
            PerfilCRM.setText("CRM: " + crm);
            PerfilONG.setText("ONG: " + ong);
            PerfilEmail.setText("E-mail: " + email);
            PerfilResumo.setText("Usuário registrado com CRM " + crm + " vinculado à ONG " + ong);

            cursor.close(); // ✅ Sempre feche o cursor após usar

        } else {
            // Caso não haja dados cadastrados
            PerfilNome.setText("Nenhum usuário encontrado");
            PerfilResumo.setText("Realize o cadastro primeiro.");
        }
    }
}