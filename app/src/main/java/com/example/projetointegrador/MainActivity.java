package com.example.projetointegrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btCarteiraVacinacao, btMeusAnimais, btCalendarioGeral, iconUsuario, btMeuPerfil;
    String emailLogado;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

         emailLogado = getIntent().getStringExtra("emailLogado");

        // BOTOES
        btCarteiraVacinacao = findViewById(R.id.btCarteiraVacinacao);
        btMeusAnimais = findViewById(R.id.btMeusAnimais);
        btCalendarioGeral = findViewById(R.id.btCalendarioGeral);
        iconUsuario = findViewById(R.id.iconUsuario);
        btMeuPerfil = findViewById(R.id.btMeuPerfil);

        btCarteiraVacinacao.setOnClickListener(this);
        btMeusAnimais.setOnClickListener(this);
        btCalendarioGeral.setOnClickListener(this);
        iconUsuario.setOnClickListener(this);
        btMeuPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // se a origem do click foi no botão Tela1
        if (view.getId() == R.id.btCarteiraVacinacao){
            // Chamar a tela CarteiraVacinacao
            Intent tela1 = new Intent(this, Minhas_Vacinas.class);
            startActivity(tela1);
        }
        if (view.getId() == R.id.iconUsuario){
            // Chamar a tela CarteiraVacinacao
            Intent tela1 = new Intent(this, InfoUsuario.class);
            tela1.putExtra("emailLogado", emailLogado);
            startActivity(tela1);
        }
        // se a origem do click foi no botão Tela2
        if (view.getId() == R.id.btMeusAnimais){
            // Chamar a tela MeusAnimais
            Intent pets = new Intent(this, MeusAnimais.class);
            startActivity(pets);
        }
        // se a origem do click foi no botão Tela2
        if (view.getId() == R.id.btCalendarioGeral){
            // Chamar a tela MeusAnimais
            Intent tela2 = new Intent(this, formVac1.class);
            startActivity(tela2);
        }

        // se a origem do click foi no botão Tela2
        if (view.getId() == R.id.btMeuPerfil){
            // Chamar a tela MeusAnimais
            Intent infoUsuario = new Intent(this, InfoUsuario.class);
           infoUsuario.putExtra("emailLogado",emailLogado);
            startActivity(infoUsuario);
        }

    }
}