package com.ryan.pedagang.adapter;

/**
 * Created by chukamak on 26/11/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ryan.pedagang.R;
import com.ryan.pedagang.AppController;
import com.ryan.pedagang.model.NewsData;

import java.util.List;


public class NewsAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NewsData> newsItems;
    ImageLoader imageLoader;

    public NewsAdapter(Activity activity, List<NewsData> newsItems) {
        this.activity = activity;
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return newsItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_news, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.news_gambar);
        TextView txtnama = (TextView) convertView.findViewById(R.id.txtnama);
        TextView timestamp = (TextView) convertView.findViewById(R.id.txttgl);
        TextView txtjenis = (TextView) convertView.findViewById(R.id.txtjenis);
        TextView txtkec = (TextView) convertView.findViewById(R.id.txtkec);

        NewsData news = newsItems.get(position);

        thumbNail.setImageUrl(news.getGambar(), imageLoader);
        txtnama.setText("Nama Pedagang "+news.getAlamat());
        timestamp.setText("Terakhir Update "+news.getDatetime());
        txtjenis.setText("Jenis Dagangan "+news.getJenis());

        return convertView;
    }

}
