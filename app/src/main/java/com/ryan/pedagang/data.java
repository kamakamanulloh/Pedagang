package com.ryan.pedagang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.ryan.pedagang.AppController;
import com.ryan.pedagang.adapter.NewsAdapter;
import com.ryan.pedagang.model.DataModel;
import com.ryan.pedagang.model.NewsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ryan.pedagang.pedagang.TAG_RESULTS;
import static com.ryan.pedagang.pedagang.TAG_VALUE;
import static com.ryan.pedagang.pedagang.url_cari;

public class data extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ListView list;
    SwipeRefreshLayout swipe;
    List<NewsData> newsList = new ArrayList<>();
    ProgressDialog pDialog;
    private static final String TAG = MainActivity.class.getSimpleName();
    String tag_json_obj = "json_obj_req";
    private static String url_list = "https://kamak.000webhostapp.com/ryan/data.php";
    private int offSet = 0;

    int no;

    NewsAdapter adapter;
    List<DataModel> listData = new ArrayList<DataModel>();

    public static final String TAG_NO = "no";
    public static final String TAG_IDnews = "ID";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_TGL = "tanggal";
    public static final String TAG_ISI = "isi";
    public static final String TAG_GAMBAR = "GAMBAR";

    public static final String TAG_NAMA = "NAMA_PEDAGANG";
    public static final String url_ket = "keterangan";
    public static final String urljenis = "jenis";
    public static final String TAG_STATUS = "status";
    String iduser, username;
    SharedPreferences sharedpreferences;


    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list_news);
        newsList.clear();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(data.this, detailnewsuser.class);

                intent.putExtra(TAG_IDnews, newsList.get(position).getId());

                startActivity(intent);
            }
        });

        adapter = new NewsAdapter(data.this, newsList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                newsList.clear();
                adapter.notifyDataSetChanged();
                callNews(0);
            }
        });

        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount && this.currentScrollState == SCROLL_STATE_IDLE) {

                    swipe.setRefreshing(true);
                    handler = new Handler();

                    runnable = new Runnable() {
                        public void run() {
                            callNews(offSet);
                        }
                    };

                    handler.postDelayed(runnable, 3000);
                }
            }

        });

    }

    public void onRefresh() {
        newsList.clear();
        adapter.notifyDataSetChanged();
        callNews(0);
    }

    private void callNews(int page) {

        swipe.setRefreshing(true);

        // Creating volley request obj
        JsonArrayRequest arrReq = new JsonArrayRequest(url_list + page, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                if (response.length() > 0) {
                    // Parsing json
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);
                            NewsData news = new NewsData();
                            no = obj.getInt(TAG_NO);

                            news.setId(obj.getString(TAG_IDnews));
                            news.setketerangan(obj.getString(TAG_TGL));
                            news.setNama(obj.getString(TAG_NAMA));
                            news.setJenis(obj.getString(urljenis));

                            if (obj.getString(TAG_GAMBAR) != "") {
                                news.setGambar(obj.getString(TAG_GAMBAR));
                            }


                            // adding news to news array
                            newsList.add(news);

                            if (no > offSet) offSet = no;

                            Log.e(TAG, "offSet " + offSet);

                        } catch (JSONException e) {
                            Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }
                swipe.setRefreshing(false);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrReq);
    }

    boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }//fungsi tekan back 2 kali
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Klik BACK dua kali untuk exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);//fungsi panggil jika tekan 1 kali
    }


}

