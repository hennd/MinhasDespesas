package com.example.minhasdespesas;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DespesaAdapter extends BaseAdapter {

    private List<Despesa> despesas;
    private Activity activity;


    public DespesaAdapter(Activity activity, List<Despesa> despesas ) {
        this.activity = activity;
        this.despesas = despesas;
    }

    @Override
    public int getCount() {
        return despesas.size();
    }

    @Override
    public Object getItem(int i) {
        return despesas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return despesas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.lista, viewGroup,false);
        TextView nome = v.findViewById(R.id.txtNome);
        TextView categoria = v.findViewById(R.id.txtCategoria);
        TextView local = v.findViewById(R.id.txtLocal);
        TextView dia = v.findViewById(R.id.txtDia);
        TextView valor = v.findViewById(R.id.txtValor);
        Despesa d = despesas.get(i);
        nome.setText(d.getNome());
        categoria.setText(d.getCategoria());
        local.setText(d.getLocal());
        dia.setText(d.getDia());
        valor.setText(d.getValor());

        return v;
    }
}
