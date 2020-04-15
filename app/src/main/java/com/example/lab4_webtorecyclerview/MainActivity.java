package com.example.lab4_webtorecyclerview;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    public Elements title;
    public ArrayList<String> titleList = new ArrayList<String>();
    private MyRecyclerViewAdapter adapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.recyclerView1);
        rv.setLayoutManager(new LinearLayoutManager(this));
        new NewThread().execute();
        adapter = new MyRecyclerViewAdapter(this, titleList);
    }

    public class NewThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] arg) {

            Document doc;
            try {
                doc = Jsoup.connect("https://ru.wikipedia.org/w/index.php?title=%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:%D0%92%D1%81%D0%B5_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D1%8B&from=%D0%90%D0%B0").get();
                title = doc.select("li");
                titleList.clear();
                for (Element titles : title) {
                    if(!titles.equals("")&&!titles.equals(" ")) titleList.add(titles.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            rv.setAdapter(adapter);
        }
    }
}