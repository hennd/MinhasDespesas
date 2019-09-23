package com.example.minhasdespesas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public DespesaDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();

    }
    public long inserirDespesa(Despesa despesa){
        ContentValues values = new ContentValues();
        values.put("nome", despesa.getNome());
        values.put("categoria", despesa.getCategoria());
        values.put("local", despesa.getLocal());
        values.put("dia", despesa.getDia());
        values.put("valor", despesa.getValor());
      return banco.insert("despesa", null, values);

    }

    public List<Despesa> obterTodos(){
        List<Despesa> despesas = new ArrayList<>();
        Cursor cursor = banco.query("despesa", new String[]{"id", "nome", "categoria", "local", "dia", "valor"},
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Despesa d = new Despesa();
            d.setId(cursor.getInt(0));
            d.setNome(cursor.getString(1));
            d.setCategoria(cursor.getString(2));
            d.setLocal(cursor.getString(3));
            d.setDia(cursor.getString(4));
            d.setValor(cursor.getString(5));
            despesas.add(d);

        }
        return despesas;
    }

        public void excluir(Despesa d){

            banco.delete("despesa", "id = ?", new String[]{d.getId().toString()});

        }

        public void atualizar(Despesa despesa){
            ContentValues values = new ContentValues();
            values.put("nome", despesa.getNome());
            values.put("categoria", despesa.getCategoria());
            values.put("local", despesa.getLocal());
            values.put("dia", despesa.getDia());
            values.put("valor", despesa.getValor());
            banco.update("despesa", values, "id = ?", new String[]{despesa.getId().toString()});
        }
}
