package uce.optativa3.com.registrologin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterUsuarios extends BaseAdapter{
    private Context context;
    private ArrayList<Estudiante> items;

    public AdapterUsuarios(Context context, ArrayList<Estudiante> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Estudiante> category) {
        for (int i =0 ; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Estudiante Item=(Estudiante) getItem(position);

        convertView=LayoutInflater.from(context).inflate(R.layout.item,null);


        TextView user = (TextView) convertView.findViewById(R.id.User);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView apellido = (TextView) convertView.findViewById(R.id.apellido);
        TextView email = (TextView) convertView.findViewById(R.id.Email);
        TextView celular = (TextView) convertView.findViewById(R.id.celular);
        TextView fecha = (TextView) convertView.findViewById(R.id.fechaN);
        TextView genero = (TextView) convertView.findViewById(R.id.Genero);
        TextView beca = (TextView) convertView.findViewById(R.id.Becado);
        Estudiante dir = items.get(position);
        user.setText(dir.getUsuario());
        user.setText(dir.getNombre());
        user.setText(dir.getApellido());
        user.setText(dir.getEmail());
        user.setText(dir.getCelular());
        user.setText(dir.getFecha_nacimiento());
        user.setText(dir.getGenero());
        user.setText(dir.getBeca());
        return convertView;
    }
}
