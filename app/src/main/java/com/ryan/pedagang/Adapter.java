package com.ryan.pedagang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ryan.pedagang.R;
import com.ryan.pedagang.model.DataModel;

import java.util.List;


/**
 * Created by chukamak on 20/03/2018.
 */


public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;

    public Adapter(Activity activity, List<DataModel> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        TextView txt_nama = (TextView) convertView.findViewById(R.id.txt_nama);
        TextView txtjenis = (TextView) convertView.findViewById(R.id.txtjenis);
        TextView txtkec = (TextView) convertView.findViewById(R.id.txtkec);
        TextView txttgl=(TextView)convertView.findViewById(R.id.txttgl);

        txttgl.setText("Terakhir Update "+item.get(position).getTgl());
        txt_nama.setText("Nama Pedagang "+item.get(position).getNama());
        txtjenis.setText("Jenis Dagangan "+item.get(position).getJenis());
        txtkec.setText("Kecamatan "+item.get(position).getKec());

        return convertView;
    }
}