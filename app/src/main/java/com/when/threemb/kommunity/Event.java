package com.when.threemb.kommunity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Event extends AppCompatActivity {
    ListView lv;
    ArrayList<HashMap<String,String>> datalist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd=new CustomDialogClass(Event.this);
                cdd.show();
            }
        });
        lv=(ListView)findViewById(R.id.list);
        datalist=new ArrayList<>();


        //Hashmap me insert kiya
        insertToList("Dummy1","Dummy","Dummy","Dummy");
        insertToList("Dummy2","Dummy","Dummy","Dummy");
        ListAdapter adapter=new SimpleAdapter(Event.this,datalist,R.layout.event_list_item,new String[]{"title","uname","date","time"},new int[]{R.id.title,R.id.uname,R.id.date,R.id.time});
        lv.setAdapter(adapter);
    }

    public void insertToList(String title,String uname,String date,String time)
    {
        HashMap<String,String> map=new HashMap<>();
        map.put("title",title);
        map.put("uname",uname);
        map.put("date",date);
        map.put("time",time);

        datalist.add(map);
    }
}
