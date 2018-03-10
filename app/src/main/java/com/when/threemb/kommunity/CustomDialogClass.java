package com.when.threemb.kommunity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 9/3/2017.
 */
public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes;
    public EditText title,date,time;
    String stitle,sdate,stime,suser,scomm;
    String REGISTER_URL="http://www.eugenicspharma.in/addEvent.php";

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);
        title=(EditText)findViewById(R.id.event_name);
        date=(EditText)findViewById(R.id.event_date);
        time=(EditText)findViewById(R.id.event_time);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                Toast.makeText(c,title.getText().toString(), Toast.LENGTH_SHORT).show();
                //c.finish();

                break;
            default:
                break;
        }
        dismiss();
    }


    private void searchAndFetchResults() {
        //final String search_query = search_text.getText().toString().trim();

        stitle=title.getText().toString();
        sdate=date.getText().toString();
        stime=time.getText().toString();
        suser="2";
        scomm="2";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(Search.this, "Sent successfully !", Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(Search.this, "FAILED " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //TODO put USERID
                    params.put("uid",suser);
                    params.put("cid",scomm);
                    params.put("title",stitle);
                    params.put("date",sdate);
                    params.put("time",stime);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getOwnerActivity());
            requestQueue.add(stringRequest);

    }

}