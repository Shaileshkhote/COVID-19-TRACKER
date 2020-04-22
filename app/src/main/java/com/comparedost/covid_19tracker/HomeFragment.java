package com.comparedost.covid_19tracker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TextView textView;
    private TextView recovered;
    private TextView deaths;
    private TextView activecases;
   // private TextView updates;
//    private WebView web_view;
    private Button mapview;
    private CardView graphical;
    private CardView geographical;
    private RequestQueue requestQueue;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.nav_home_constraint, container, false);
        textView = view.findViewById(R.id.textViewResult);
        recovered = view.findViewById(R.id.totalrecovered);
        deaths = view.findViewById(R.id.deaths);
       // updates = view.findViewById(R.id.update);
        activecases=view.findViewById(R.id.activecases);
//        web_view=view.findViewById(R.id.graph);

        graphical=view.findViewById(R.id.graphical);
        geographical=view.findViewById(R.id.geographical);

        graphical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Htmlcode="<!DOCTYPE HTML>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<script>\n" +
                        "window.onload = function() {\n" +
                        "\n" +
                        "var dataPoints = [];\n" +
                        "\n" +
                        "var chart = new CanvasJS.Chart(\"chartContainer\", {\n" +
                        "\tanimationEnabled: true,\n" +
                        "\ttheme: \"light2\",\n" +
                        "\ttitle: {\n" +
                        "\t\ttext: \"Total confirmed cases\"\n" +
                        "\t},\n" +
                        "\taxisY: {\n" +
                        "\t\ttitle: \"Total cases\",\n" +
                        "\t\ttitleFontSize: 16\n" +
                        "\t},\n" +
                        "\tdata: [{\n" +
                        "\t\ttype: \"column\",\n" +
                        "\t\tyValueFormatString: \"#,### Units\",\n" +
                        "\t\tdataPoints: dataPoints\n" +
                        "\t}]\n" +
                        "});\n" +
                        "\n" +
                        "function addData(data) {\n" +
                        "\n" +
                        "\n" +
                        "\tfor (var i = 30; i < data.cases_time_series.length; i++) {\n" +
                        "\t\tdataPoints.push({\n" +
                        "\t\t\tlabel:data.cases_time_series[i].date,\n" +
                        "\t\t\ty: parseInt(data.cases_time_series[i].totalconfirmed)\n" +
                        "\t\t});\n" +
                        "\t}\n" +
                        "\tchart.render();\n" +
                        "\n" +
                        "}\n" +
                        "\n" +
                        "$.getJSON(\"https://api.covid19india.org/data.json\", addData);\n" +
                        "\n" +
                        "}\n" +
                        "</script>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div id=\"chartContainer\" style=\"height: 200px; width: 100%;\"></div>\n" +
                        "<script src=\"https://canvasjs.com/assets/script/jquery-1.11.1.min.js\"></script>\n" +
                        "<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>\n" +
                        "</body>\n" +
                        "</html>";



                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Graph View(Click Bar for info)");

                WebView wvw = new WebView(getActivity());
                wvw.requestFocus();
                wvw.getSettings().setLightTouchEnabled(true);
                wvw.getSettings().setJavaScriptEnabled(true);
                wvw.getSettings().setGeolocationEnabled(true);
                wvw.setSoundEffectsEnabled(true);
                wvw.loadData(Htmlcode,
                        "text/html", "UTF-8");
                wvw.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.requestFocus();
                        view.getSettings().setLightTouchEnabled(true);
                        view.getSettings().setJavaScriptEnabled(true);
                        view.getSettings().setGeolocationEnabled(true);
                        view.setSoundEffectsEnabled(true);
                        view.loadData(Htmlcode,
                                "text/html", "UTF-8");

                        return true;

            }
        });
                alert.setView(wvw);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        geographical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url="<html>\n" +
                        "\n" +
                        "    <head>\n" +
                        "        <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                        "        <script type=\"text/javascript\">\n" +
                        "        \n" +
                        "const fun = async()=>{\n" +
                        "    const result= await fetch(\"https://api.covid19india.org/data.json\")\n" +
                        "    const res= await result.json()\n" +
                        "    return res\n" +
                        "}\n" +
                        "let arr=[]\n" +
                        " fun().then(resp =>{\n" +
                        "     arr = resp.statewise\n" +
                        "     console.log(\"arr\",arr,resp);\n" +
                        " })\n" +
                        "     \n" +
                        "        \n" +
                        "        \n" +
                        "        \n" +
                        "        \n" +
                        "        \n" +
                        "            google.charts.load('current', {\n" +
                        "                'packages': ['geochart'],\n" +
                        "                // Note: you will need to get a mapsApiKey for your project.\n" +
                        "                // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings\n" +
                        "                'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'\n" +
                        "            });\n" +
                        "            google.charts.setOnLoadCallback(drawRegionsMap);\n" +
                        "\n" +
                        "            function drawRegionsMap() {\n" +
                        "                let DataArray=[]\n" +
                        "                arr.forEach(element => {\n" +
                        "                    if (element.state!=='Total') {\n" +
                        "                        arrayToPush=[element.state ,parseInt(element.active),parseInt(element.confirmed)]\n" +
                        "                        DataArray.push(arrayToPush)\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                console.log(DataArray);\n" +
                        "                var data = google.visualization.arrayToDataTable([\n" +
                        "                    ['State', 'Active cases', 'Confirmed cases'],\n" +
                        "                    ...DataArray\n" +
                        "                     ]);\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                var options = {\n" +
                        "                      region: 'IN', // Africa\n" +
                        "                       resolution: 'provinces', //If you want to display provinces in India\n" +
                        "                     colorAxis: { colors: ['#FCB8B8', '#e84118'] }\n" +
                        "                };\n" +
                        "\n" +
                        "                var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));\n" +
                        "\n" +
                        "                chart.draw(data, options);\n" +
                        "            }\n" +
                        "        </script>\n" +
                        "    </head>\n" +
                        "\n" +
                        "    <body>\n" +
                        "        <div id=\"regions_div\" onclick='(e)=>console.log(e)' style=\"width: 100%; height:100%;\"></div>\n" +
                        "    </body>\n" +
                        "\n" +
                        "</html>";

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Map View(Click state for info)");

                WebView wv = new WebView(getActivity());
                wv.requestFocus();
                wv.getSettings().setLightTouchEnabled(true);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.getSettings().setGeolocationEnabled(true);
                wv.setSoundEffectsEnabled(true);
                wv.loadData(url,
                        "text/html", "UTF-8");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.requestFocus();
                        view.getSettings().setLightTouchEnabled(true);
                        view.getSettings().setJavaScriptEnabled(true);
                        view.getSettings().setGeolocationEnabled(true);
                        view.setSoundEffectsEnabled(true);
                        view.loadData(url,
                                "text/html", "UTF-8");

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });


        LottieDialogFragment dialog=new LottieDialogFragment().newInstance();

        dialog.show(getActivity().getSupportFragmentManager(),"");



//
//
//
//        web_view.requestFocus();
//        web_view.getSettings().setLightTouchEnabled(true);
//        web_view.getSettings().setJavaScriptEnabled(true);
//        web_view.getSettings().setGeolocationEnabled(true);
//        web_view.setSoundEffectsEnabled(true);
//        web_view.loadData(Htmlcode,
//                "text/html", "UTF-8");
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },3000);


        requestQueue = Volley.newRequestQueue(getActivity());
        return  view;
    }

    private void jsonParse() {
        String url = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("cases_time_series");
//                    for (int i = jsonArray.length()-1; i < jsonArray.length(); i++) {
//                        JSONObject regional = jsonArray.getJSONObject(i);

                    JSONObject res= response.getJSONObject("data");
                    JSONObject resp=res.getJSONObject("total");
                    String totalConfirmed = resp.getString("confirmed");
                    String totalRecovered = resp.getString("recovered");
                    String totalDead = resp.getString("deaths");
                    String totalActive = resp.getString("active");
                    String update = res.getString("lastRefreshed");

//                        String totalrecovered = resp.getString("totalrecovered");
//                        String totaldeceased = resp.getString("totaldeceased");
//                        String loc = resp.getString("date");
//                        textView.setText("Total Confirmed Cases:"+totalConfirmed +"\nTotal Recovered:"+totalrecovered +"\nTotal Deaths:"+totaldeceased + "\n Date: " + loc +"\n\n");
                    textView.setText(totalConfirmed);
                    recovered.setText(totalRecovered);
                    deaths.setText(totalDead);
                    activecases.setText(totalActive);
                   // updates.setText("*Last Update:"+update);
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
