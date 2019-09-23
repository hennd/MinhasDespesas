package com.example.minhasdespesas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarDespesasActivity extends AppCompatActivity {


    private ListView listView;
    private DespesaDAO dao;
    private List<Despesa> despesas;
    private List<Despesa> despesasFiltradas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_despesas);

        listView = findViewById(R.id.lista_despesas);
        dao = new DespesaDAO(this);
        despesas = dao.obterTodos();
        despesasFiltradas.addAll(despesas);

        //ArrayAdapter<Despesa> adaptador = new ArrayAdapter<Despesa>(this, android.R.layout.simple_list_item_1, despesasFiltradas);

        DespesaAdapter adaptador = new DespesaAdapter(this, despesasFiltradas);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurarDespesa(s);
                System.out.println("Digitou " + s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }
    public void procurarDespesa(String nome){

        despesasFiltradas.clear();
        for(Despesa d : despesas){
            if(d.getNome().toLowerCase().contains(nome.toLowerCase())){
                despesasFiltradas.add(d);
            }
        }
        listView.invalidateViews();

    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Despesa despesaExcluir = despesasFiltradas.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja realmente excluir esta despesa?")
                .setNegativeButton("não", null)
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        despesasFiltradas.remove(despesaExcluir);
                        despesas.remove(despesaExcluir);
                        dao.excluir(despesaExcluir);
                        listView.invalidateViews();
                    }
                }).create();
            dialog.show();
    }
    public void cadastrar(MenuItem item){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);

    }

    public void atualizar(MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

       final Despesa despesaAtualizar = despesasFiltradas.get(menuInfo.position);

        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("despesa", despesaAtualizar );
        startActivity(it);
    }
    @Override
    public void onResume(){
        super.onResume();
        despesas = dao.obterTodos();
        despesasFiltradas.clear();
        despesasFiltradas.addAll(despesas);
        listView.invalidateViews();
    }
}
