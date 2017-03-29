package com.soft.ramsp.appmicrosol;


import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.appcompat
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {
    private Context context;
    private static String url ="http://localhost:81/microsoljson/";
    private static final String TAG_Persona = "nombres";

    ArrayList<HashMap<String,String>> jsonlist = new ArrayList<HashMap<String,String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetJSONActivity(MainActivity.this).execute();
    }


    private class GetJSONActivity extends AsyncTask<String, Void, String> {

        private ListActivity activity;

        public GetJSONActivity(ListActivity activity){
            this.activity = activity;
            context =activity;
        }
        protected  String doInBackground(final String... args){
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.GetJSONfromUrl(url);
            for (int i=0; i< json.length();i++){
                try {
                    JSONObject c = json.getJSONObject(i);
                    String vpers = c.getString(TAG_Persona);

                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put(TAG_Persona,vpers);
                    jsonlist.add(map);

                }catch (JSONException e){
                    e.printStackTrace();
                    return "Error creando variables";

                }
            }
            return "EXITO";
        }

        protected void onPostExecute(String success){
            ListAdapter adapter = new SimpleAdapter(context, jsonlist,
                    R.layout.activity_jsonparser, new String[]  {TAG_Persona},
                    new int[] { R.id.nombrePers});
            setListAdapter(adapter);
            getListView();
        }

    }
}

