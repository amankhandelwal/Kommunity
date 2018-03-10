package com.when.threemb.kommunity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient googleApiClient;
    LocationListener locationListener;
    LocationRequest locationRequest;
    protected Location mLastLocation;
    EditText search_text;
    TextView txtOutput;
    int flag=0;
    GoogleMap m_map;
    boolean ready=false;
    MarkerOptions  myMarker;
    String REGISTER_URL="http://www.eugenicspharma.in/check.php";
    double latitude,longitude;
    boolean whereareyou=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search_text=(EditText) findViewById(R.id.search_text);
        txtOutput=(TextView)findViewById(R.id.txtOutput);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPosMarker(latitude,longitude);
            }
        });

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API) // for location services
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        MapFragment mf=(MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);

        myMarker=new MarkerOptions();

    }
    @Override
    public void onMapReady(GoogleMap map)
    {
        ready=true;
        m_map=map;
        m_map.setOnMarkerClickListener(this);
        LatLng kolkata=new LatLng(22.5726,88.3639);
        CameraPosition cp=CameraPosition.builder().target(kolkata).zoom(14).tilt(12).build();
        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

        /*markers(new LatLng(22.5817,88.3544),"BurraBazar");
        markers(new LatLng(22.5803,88.3640),"College Street");
        markers(new LatLng(48.8584,2.2945),"Eiffel Tower");*/

        /*m_map.addPolyline(new PolylineOptions().geodesic(true).add(new LatLng(22.5817,88.3544))
                .add(new LatLng(22.5803,88.3640)).add(new LatLng(48.8584,2.2945)));*/
        //m_map.addCircle(new CircleOptions().center(new LatLng(22.5817,88.3544)).radius(1000.0));


    }

    public void myPosMarker(double latitude,double longitude){//Kolkata
        if(ready)
        {
            //markers(new LatLng(latitude,longitude),"Me");

            LatLng kolkata=new LatLng(latitude,longitude);
            CameraPosition cp=CameraPosition.builder().target(kolkata).zoom(18).tilt(60).build();
            m_map.animateCamera(CameraUpdateFactory.newCameraPosition(cp),2500,null);
        }
    }

    public void markers(LatLng ll,String a,String b){
        MarkerOptions abc=new MarkerOptions();
        abc.position(ll).title(a).snippet(b);
        //abc.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        m_map.addMarker(abc);
    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected())
            googleApiClient.disconnect();
    }



    //HOW TO SETUP LOCATION REQUESTS
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //FOR UPDATING LOCATION JUST ONCE :-

        //FOR CONTINUOUSLY UPDATING LOCATION :-

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(500);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "NO PERMISSION", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(
                    Search.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }


    }
    //FOR ALERT BOX REQUESTING LOCATION PERMISSION
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        else
            Toast.makeText(this, "CANNOT FUNCTION WITHOUT LOCATION PERMISSION", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionSuspended(int i) {
        txtOutput.setText("SUSPENDED");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        txtOutput.setText("FAILED");
    }

    //GETTING CHANGED LOCATION EVERY TIME LOCATION CHANGES
    @Override
    public void onLocationChanged(Location location) {
        if(whereareyou) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            txtOutput.setText("My location:"+latitude+" "+longitude);
        myMarker.position(new LatLng(latitude,longitude)).title("Me");
        //abc.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
            m_map.addMarker(myMarker);
            myPosMarker(latitude, longitude);
            whereareyou=false;
        }
    }

   /* public void onResult(Status status) {
        if (status.isSuccess()) {
            Toast.makeText(this, "Successfully added activity detection.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error adding or removing activity detection:", Toast.LENGTH_SHORT).show();
        }
    }*/

    public boolean isInternetConnected()
    {

        //Toast.makeText(Main2Activity.this, "Please Wait !!", Toast.LENGTH_SHORT).show();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            Toast.makeText(this, "Connected to web", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            // display error
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return false;


        }

    }

    private void searchAndFetchResults() {
        final String search_query = search_text.getText().toString().trim();

        if (isInternetConnected()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Search.this, "Sent successfully", Toast.LENGTH_LONG).show();
                            try {
                                JSONArray arr = new JSONArray(response);
                                JSONObject obj;
                                for(int i=0;i<arr.length();i++)
                                {
                                    String uid,username,comidARR;
                                            double latitude,longitude;
                                    obj=arr.optJSONObject(i);
                                    uid=obj.optString("uid");
                                    username=obj.optString("username");
                                    latitude=Double.parseDouble(obj.optString("latitude"));
                                    longitude=Double.parseDouble(obj.optString("longitude"));
                                    comidARR=obj.optString("comidARR");
                                    markers(new LatLng(latitude,longitude),comidARR,username);
                                }
                            }
                            catch(JSONException j)
                            {
                                Toast.makeText(Search.this, "BAD JSON", Toast.LENGTH_SHORT).show();
                            }
                            //finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Search.this, "FAILED " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //TODO put USERID
                    params.put("userid","2");
                    params.put("search_query", search_query);
                    params.put("latitude", Double.toString(latitude));
                    params.put("longitude",Double.toString(longitude));
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(this,"Ehh ... Bad connection",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "Marked:"+marker.getTitle().toString(), Toast.LENGTH_SHORT).show();
        callAlertBox(marker.getTitle().toString());
        return false;
    }

    public void onSearchClicked(View view)
    {
        Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
        searchAndFetchResults();
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void callAlertBox(String comm_name)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Search.this);
        alertDialogBuilder.setTitle("Join Community");
        alertDialogBuilder.setMessage(comm_name);


        /*alertDialogBuilder.setNegativeButton("Attend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });*/

        alertDialogBuilder.setNeutralButton("Join Community", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        /*alertDialogBuilder.setPositiveButton("BUNK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}