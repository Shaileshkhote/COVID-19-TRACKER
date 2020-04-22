package com.comparedost.covid_19tracker;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MapFragment extends DialogFragment {

    WebView web_view;

    public static MapFragment newInstance() {


        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.activity_map, container, false);
        web_view = view.findViewById(R.id.maps);

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

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
    }
}