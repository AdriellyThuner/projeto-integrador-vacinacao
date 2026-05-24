package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class formVac1 extends AppCompatActivity implements View.OnClickListener {

    Button btnProximo;
    ImageButton btVoltar, btFechar;
    EditText txtNomeVac, txtDataVac, txtHoraVa, txtVet, txtCRMVac;
    RadioGroup rgDose;

    ScrollView formVac1;

    BancoControllerVacinas bdController;
    Pet pet; // representa o dono da vacina

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_vac1); // R.layout busca a pasta layout
        //R.classe que é uma espécie de catalogo de tudo oq tem no projeto

        bdController = new BancoControllerVacinas(this);
        pet = (Pet) getIntent().getSerializableExtra("PET_OBJETO"); //recupera os dados do animal enviados da tela anterior

        //BOTOES
        btnProximo = findViewById(R.id.btnProximo);
        btVoltar = findViewById(R.id.btVoltar);
        btFechar = findViewById(R.id.btFechar);

        btnProximo.setOnClickListener(this);
        btVoltar.setOnClickListener(this);
        btFechar.setOnClickListener(this);

        //CAMPOS
        txtNomeVac = findViewById(R.id.txtNomeVac);
        txtDataVac = findViewById(R.id.txtDataVac);
        txtHoraVa = findViewById(R.id.txtHoraVa);
        txtVet = findViewById(R.id.txtVet);
        txtCRMVac = findViewById(R.id.txtCRMVac);
        rgDose = findViewById(R.id.rgDose);


        formVac1 = findViewById(R.id.formVac1);



    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnProximo) {

            String nomeVac = txtNomeVac.getText().toString();
            String dataVac = txtDataVac.getText().toString();
            String horaVac = txtHoraVa.getText().toString();
            String vetVac = txtVet.getText().toString();
            String crmVac = txtCRMVac.getText().toString();


            // CAPTURAR Dose
            int idSelecionadoVac = rgDose.getCheckedRadioButtonId();
            if (idSelecionadoVac == -1) { //se nenhum radioButton foi selecionado (-1 sgnifica sem seleção)
                Toast.makeText(this, "Selecione o tipo/dose da vacina!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioSelecionadoVac = findViewById(idSelecionadoVac);
            String dose = radioSelecionadoVac.getText().toString();

            // 1. Criamos o objeto Vacina com os dados capturados nesta tela
            Vacina vacina = new Vacina(nomeVac, dataVac, horaVac, vetVac, crmVac, dose);

            // 2. Criamos a Intent para a formVac2
            Intent intent = new Intent(this, formVac2.class);

            // 3. Como a classe Vacina é Serializable, passamos o objeto inteiro na "mala"
            intent.putExtra("VACINA_OBJ", vacina);

            intent.putExtra("PET_OBJETO", pet);

            // Ir para próxima tela
            startActivity(intent);
        }


        // VOLTAR TELA
        if (view.getId() == R.id.btVoltar) {
            finish();
        }
        // FECHAR E IR AO MENU
        if (view.getId() == R.id.btFechar) {

            //  FormBanco.limpar(); // limpa os dados
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(main);
            finish();
        }

    }

}