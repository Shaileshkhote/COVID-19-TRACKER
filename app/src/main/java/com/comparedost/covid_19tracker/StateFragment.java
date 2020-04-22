package com.comparedost.covid_19tracker;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.utils.LogcatLogger;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.forms.sti.progresslitieigb.ProgressLoadingJIGB;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StateFragment extends Fragment {
    private TextView textView;
    private TextView recovered;
    private TextView deaths;
    private TextView stateshowing;
    private TextView activecases;
    private MaterialSpinner spinner;
    private int sel;
   private String it;

    private String [] str = new String[50];
 private RequestQueue requestQueue;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.nav_state_2, container, false);



        spinner = view.findViewById(R.id.spinner);
        textView = view.findViewById(R.id.textViewResult);
        recovered = view.findViewById(R.id.totalrecovered);
        deaths = view.findViewById(R.id.deaths);
        stateshowing=view.findViewById(R.id.stateshowing);

        activecases=view.findViewById(R.id.activecases);


        LottieDialogFragment dialog=new LottieDialogFragment().newInstance();

                dialog.show(getActivity().getSupportFragmentManager(),"");




         Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();


                spinner.setItems("----Select State----","Andhra Pradesh",
                        "Arunachal Pradesh",
                        "Assam",
                        "Bihar",
                        "Chhattisgarh",
                        "Goa",
                        "Gujarat",
                        "Haryana",
                        "Himachal Pradesh",
                        "Jammu and Kashmir",
                        "Jharkhand",
                        "Karnataka",
                        "Kerala",
                        "Madhya Pradesh",
                        "Maharashtra",
                        "Manipur",
                        "Meghalaya",
                        "Mizoram",
                        "Nagaland",
                        "Odisha",
                        "Punjab",
                        "Rajasthan",
                        "Sikkim",
                        "Tamil Nadu",
                        "Telangana",
                        "Tripura",
                        "Uttarakhand",
                        "Uttar Pradesh",
                        "West Bengal",
                        "Andaman and Nicobar Islands",
                        "Chandigarh",
                        "Dadra and Nagar Haveli",
                        "Daman and Diu",
                        "Delhi",
                        "Lakshadweep",
                        "Puducherry");
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        sel=position;
                        it=item;
                        jsonParse();



                    }
                });
            }
        }, 3000);




        requestQueue = Volley.newRequestQueue(getActivity());
        return  view;
    }
    private void showProgressDialog(){

        //dialog.setCanceledOnTouchOutside(false);
    }



    private void jsonParse() {





        String url = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject res= response.getJSONObject("data");
                    JSONArray respo=res.getJSONArray("statewise");

                   // JSONObject resp=respo.getJSONObject(sel);
                    for(int i=0;i<respo.length();i++){

                        JSONObject respon=respo.getJSONObject(i);
                        Log.e("it",it);
                        if(it.equalsIgnoreCase(respon.getString("state"))){

                            String totalConfirmed = respon.getString("confirmed");
                            String totalRecovered = respon.getString("recovered");
                            String totalDead = respon.getString("deaths");
                            String totalActive = respon.getString("active");



                            stateshowing.setText(respon.getString("state"));
                            textView.setText(totalConfirmed);
                            recovered.setText(totalRecovered);
                            deaths.setText(totalDead);
                            activecases.setText(totalActive);


                        }
                        else{
                            Log.e("throw","Data not found");
                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
    @Override
    public void onStart()
    {



        super.onStart();
    }
}