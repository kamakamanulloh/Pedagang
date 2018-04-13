package com.ryan.pedagang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.ryan.pedagang.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class detailnewsuser extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    NetworkImageView thumb_image;
    TextView nama, tgl, keterangan, alamat, status,txtket,txtjenis,txtharga,txtnohp;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    SwipeRefreshLayout swipe;
    String id_news;
    Button kirimkomentar,lvkomen;
    private static final String TAG = detailnewsuser.class.getSimpleName();

    public static final String TAG_ID = "id";

    public static final String TAG_NAMA = "nama";

    public static final String TAG_KETERANGAN = "keterangan";
    public static final String TAG_GAMBAR = "gambar";
    public static final String url_jenis = "jenis";
    public static final String url_kec = "kecamatan";

    String iduser, username;
    SharedPreferences sharedpreferences;
    EditText isikoment;
    View dialogView;
    EditText txtkoment;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    ProgressDialog pDialog;
    TextView txtkec;

    EditText komentar;
    public static final String TAG_IDnews = "ID";
    private static final String url_detail  = "https://kamak.000webhostapp.com/ryan/detail.php";
    Button nav;

    String tag_json_obj = "json_obj_req";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    String id_user,name;
    Button btnkomen,btnlihat;
    public static final String url_long = "longitude";
    public static final String url_lat = "lattitude";
    String goolgeMap = "com.google.android.apps.maps";
    Uri gmmIntentUri;
    Intent mapIntent;
    String lokasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        thumb_image = (NetworkImageView) findViewById(R.id.gambar_news);
        Button nav=(Button)findViewById(R.id.btnlok);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmmIntentUri = Uri.parse("google.navigation:q=" + lokasi);

                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);


                mapIntent.setPackage(goolgeMap);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(detailnewsuser.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        txtket=(TextView)findViewById(R.id.txtket);
        txtjenis=(TextView)findViewById(R.id.txtjenis);
        txtkec=(TextView)findViewById(R.id.txtkec);
        txtnohp=(TextView)findViewById(R.id.txtnohp);
        txtharga=(TextView)findViewById(R.id.ttxharga);


        id_news = getIntent().getStringExtra(TAG_IDnews);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           if (!id_news.isEmpty()) {
                               callDetailNews(id_news);
                           }
                       }
                   }
        );


    }

    private void callDetailNews(final String id) {
        swipe.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);


                    String Gambar = obj.getString(TAG_GAMBAR);
                    String jenis =obj.getString("jenis");
                    String harga =obj.getString("harga");
                    String no_hp =obj.getString("no_hp");
                    String kec =obj.getString(url_kec);

                    String Keterangan = obj.getString(TAG_KETERANGAN);

                    String longi = obj.getString(url_long);
                    String lat = obj.getString(url_lat);
                    lokasi=(lat + "," + longi);






                    txtjenis.setText("Jenis Dagangan " + jenis);
                    txtkec.setText("Kecamatan " + kec);
                    txtket.setText("Keterangan " + Html.fromHtml(Keterangan));
                    txtharga.setText("Harga "+harga);
                    txtnohp.setText("No HP "+no_hp);

                    if (obj.getString(TAG_GAMBAR) != "") {
                        thumb_image.setImageUrl(Gambar, imageLoader);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail News Error: " + error.getMessage());
                Toast.makeText(detailnewsuser.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        callDetailNews(id_news);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }




}



