package com.comparedost.covid_19tracker;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView recovered;
    private TextView deaths;
    private TextView activecases;
    private TextView updates;
    private WebView web_view;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewResult);
        recovered = findViewById(R.id.totalrecovered);
        deaths = findViewById(R.id.deaths);
        updates = findViewById(R.id.update);
        activecases=findViewById(R.id.activecases);
        web_view=findViewById(R.id.graph);
//        Button buttonParse = findViewById(R.id.btnParse);

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


        web_view.requestFocus();
        web_view.getSettings().setLightTouchEnabled(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setGeolocationEnabled(true);
        web_view.setSoundEffectsEnabled(true);
        web_view.loadData(Htmlcode,
                "text/html", "UTF-8");

        requestQueue = Volley.newRequestQueue(this);
//        buttonParse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    jsonParse();
//            }
//        });
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
                    updates.setText("*Last Update:"+update);
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
    protected void onStart()
    {
        jsonParse();
        super.onStart();
    }
}