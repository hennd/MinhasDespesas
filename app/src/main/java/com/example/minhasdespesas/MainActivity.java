package com.example.minhasdespesas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText nome;
    private EditText categoria;
    private EditText local;
    private EditText dia;
    private EditText valor;
    private DespesaDAO dao;
    private Despesa despesa = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.edtNome);
        categoria = findViewById(R.id.edtCategoria);
        local = findViewById(R.id.edtLocal);
        dia = findViewById(R.id.edtDia);
        valor = findViewById(R.id.edtValor);
        dao = new DespesaDAO(this);

        Intent it = getIntent();

        if (it.hasExtra("despesa")) {
            despesa = (Despesa) it.getSerializableExtra("despesa");
            nome.setText(despesa.getNome());
            categoria.setText(despesa.getCategoria());
            local.setText(despesa.getLocal());
            dia.setText(despesa.getDia());
            valor.setText(despesa.getValor());

        }


    }

    public void salvar(View view) {

        if (despesa == null) {
            despesa = new Despesa();
            despesa.setNome(nome.getText().toString());
            despesa.setCategoria(categoria.getText().toString());
            despesa.setLocal(local.getText().toString());
            despesa.setDia(dia.getText().toString());
            despesa.setValor(valor.getText().toString());
            long id = dao.inserirDespesa(despesa);

            Toast.makeText(this, "Despesa inserida com id " + id, Toast.LENGTH_SHORT).show();

        } else {
            despesa.setNome(nome.getText().toString());
            despesa.setCategoria(categoria.getText().toString());
            despesa.setLocal(local.getText().toString());
            despesa.setDia(dia.getText().toString());
            despesa.setValor(valor.getText().toString());
            dao.atualizar(despesa);
            Toast.makeText(this, "Despesa Atualizada", Toast.LENGTH_SHORT).show();
        }
    }
}
