package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class formVac2 extends AppCompatActivity implements View.OnClickListener {

    Button btnProximo2;
    ImageButton btVoltar2, btFechar2;
    EditText txtDetVac, txtDetVac1, txtDetVac2, txtNomeRef, txtDatRef, txtObsVac;
    RadioGroup rgDose2;

    ScrollView formVac2;

    BancoControllerVacinas bdController;

    Pet pet; // representa o dono da vacina

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_vac2); // R.layout busca a pasta layout
        //R.classe que é uma espécie de catalogo de tudo oq tem no projeto

        bdController = new BancoControllerVacinas(this);
        pet = (Pet) getIntent().getSerializableExtra("PET_OBJETO");

        //BOTOES
        btnProximo2 = findViewById(R.id.btnProximo2);
        btVoltar2 = findViewById(R.id.btVoltar2);
        btFechar2 = findViewById(R.id.btFechar2);

        btnProximo2.setOnClickListener(this);
        btVoltar2.setOnClickListener(this);
        btFechar2.setOnClickListener(this);

        //CAMPOS
        txtDetVac = findViewById(R.id.txtDetVac);
        txtDetVac1 = findViewById(R.id.txtDetVac1);
        txtDetVac2 = findViewById(R.id.txtDetVac2);
        rgDose2 = findViewById(R.id.rgDose2);
        txtNomeRef = findViewById(R.id.txtNomeRef);
        txtDatRef = findViewById(R.id.txtDatRef);
        txtObsVac = findViewById(R.id.txtObsVac);


        formVac2 = findViewById(R.id.formVac2);


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnProximo2) {

            String detVac = txtDetVac.getText().toString();
            String detVac1 = txtDetVac1.getText().toString();
            String detVac2 = txtDetVac2.getText().toString();
            String refVac = txtNomeRef.getText().toString();
            String Dataref = txtDatRef.getText().toString();
            String ObsVac = txtObsVac.getText().toString();

            Vacina vacina = (Vacina)getIntent().getSerializableExtra("VACINA_OBJ"); // recupera as informações do primeiro formulario

            if (vacina== null){
                Toast.makeText(this, "Erro ao carregar vacina!", Toast.LENGTH_SHORT).show();
                return;
            }



            // CAPTURAR dose
            int idSelecionadoVac = rgDose2.getCheckedRadioButtonId();
            if (idSelecionadoVac == -1) {
                Toast.makeText(this, "Selecione o tipo/dose da vacina!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioSelecionadoVac = findViewById(idSelecionadoVac);
            String dose2 = radioSelecionadoVac.getText().toString();

            vacina.setDetVac(detVac);
            vacina.setDetVac1(detVac1);
            vacina.setDetVac2(detVac2);
            vacina.setRefVac(refVac);
            vacina.setDataref(Dataref);
            vacina.setObsVac(ObsVac);
            vacina.setDose2(dose2);


            // 2. Criamos a Intent para a formVac2
            Intent intent = new Intent(this, form_vac3.class);

            // 3. Como a classe Vacina é Serializable, passamos o objeto inteiro na "mala"
            intent.putExtra("VACINA_OBJ", vacina); // passa o objeto completo (formVac1 + fomrVac2)
            intent.putExtra("PET_OBJETO", pet);

            // Ir para próxima tela
            startActivity(intent);
        }


        // VOLTAR TELA
        if (view.getId() == R.id.btVoltar2) {
            Intent form1 = new Intent(this, formVac1.class);
            form1.putExtra("PET_OBJETO", pet); // ← em vez de só finish()
            startActivity(form1);
            finish();
        }
        // FECHAR E IR AO MENU
        if (view.getId() == R.id.btFechar2) {

            //  FormBanco.limpar(); // limpa os dados
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(main);
            finish();
        }

    }

}