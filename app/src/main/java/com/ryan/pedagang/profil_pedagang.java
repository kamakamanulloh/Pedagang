package com.ryan.pedagang;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class profil_pedagang extends AppCompatActivity {

    TextView txtket, txtkec, txtjenis;

    public static final String TAG_ID = "ID";
    public static final String TAG_NAMA = "NAMA_PEDAGANG";
    public static final String url_kec = "kecamatan";

    public static final String url_ket = "KETERANGAN";
    public static final String urljenis = "jenis";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";
    public static final String TAG_iduser = "iduser";
    public static final String TAG_nm = "nm_pembeli";
    public static final String url_gbr = "path";
    String Nama, id, username, nm_pembeli, ket, jns, kec,iduser;
    private static final String url_detail = "https://kamak.000webhostapp.com/ryan/profil_pedagang.php?ID=";
    String tag_json_obj = "json_obj_req";
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    NetworkImageView thumb_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_pedagang);

        iduser = getIntent().getStringExtra(TAG_iduser);
        nm_pembeli = getIntent().getStringExtra(TAG_nm);
        id = getIntent().getStringExtra(TAG_ID);

        username = getIntent().getStringExtra(TAG_NAMA);
        ket = getIntent().getStringExtra(url_ket);
        kec = getIntent().getStringExtra(url_kec);
        jns = getIntent().getStringExtra(urljenis);
        txtjenis = (TextView) findViewById(R.id.txtjenis);
        txtkec = (TextView) findViewById(R.id.txtkec);
        txtket = (TextView) findViewById(R.id.txtket);
        thumb_image = (NetworkImageView) findViewById(R.id.gambar_news);
        getEmployee();
    }
    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(profil_pedagang.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }


            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(url_detail, id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
   username = c.getString(TAG_NAMA);
            ket = c.getString("KETERANGAN");
            kec = c.getString("kecamatan");
            jns = c.getString("jenis");
           String gbr = c.getString("gambar");



            txtjenis.setText(jns);
            txtkec.setText(kec);
            txtket.setText(ket);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
