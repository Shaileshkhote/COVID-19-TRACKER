package com.comparedost.covid_19tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.utils.LogcatLogger;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CareFragment extends Fragment {

private TextView text;
    private TextView no;
private RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.nav_care_2, container, false);

        text=view.findViewById(R.id.region);
         no=view.findViewById(R.id.no);
        requestQueue = Volley.newRequestQueue(getActivity());
        return  view;
    }
    private void jsonParse() {
        String url = "https://api.rootnet.in/covid19-in/contacts.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject res= response.getJSONObject("data");
                    JSONObject resp=res.getJSONObject("contacts");
                    JSONArray respo=resp.getJSONArray("regional");

                    for (int i = 0; i < respo.length(); i++) {
                        //gets each JSON object within the JSON array
                        JSONObject jsonObject = respo.getJSONObject(i);

                        // Retrieves the string labeled "colorName" and "hexValue",
                        // and converts them into javascript objects
                        String region = jsonObject.getString("loc");
                        String contact = jsonObject.getString("number");
                        text.append(region+"\n-----------------------------------------\n");
                        no.append(contact+"\n----------------------------------------------\n");
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
        jsonParse();
        super.onStart();
    }
}
