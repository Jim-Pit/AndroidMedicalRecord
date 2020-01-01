package com.space.jimpit.medapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    RequestQueue queue;
    ArrayList<String> medCasesList = new ArrayList<>();
    //ArrayList<ArrayList<String>> medCasesList = new ArrayList<>();
    ArrayList<String> personList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        queue = Volley.newRequestQueue(this);

        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
//                String info = medCasesList.get(i);
//                intent.putExtra("info",info);
//                startActivity(intent);
//                String medInfo="";
//                for(i=0; i<medCasesList.size(); i++){
//                    medInfo += medCasesList.get(i) + "\r\n";
//                }
                Toast.makeText(MainActivity.this,medCasesList.get(i),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void go(View view){

        String url = "http://10.0.2.2:8080/allPatients_History/";

        adapter.clear();

        JsonArrayRequest jArrReq = new
                JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String criteria = editText.getText().toString().toLowerCase();
                        if (criteria.equals("search for")||criteria.equals(""))
                            funxion(response,"");
                        else
                            funxion(response,criteria);
                        //((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        editText.setText(error.getLocalizedMessage());
                    }
                } );
        queue.add(jArrReq);
    }

    private void funxion(JSONArray response, String search){
        JSONObject jo;
        medCasesList = new ArrayList<>();
        for(int i=0; i<response.length();i++) {
            try {
                jo = response.getJSONObject(i);
                String person = (new Person((JSONObject) jo.get("person"))).toString();
                if(person.toLowerCase().contains(search)){
                    adapter.add(person);
                    // medCasesList.add(jo.get("cases").toString()); WORKS
                    // jo.get("cases") is a JSONArray, so the below Type Casting is faulty!
                    // medCasesList.add((new MedCase((JSONObject)jo.get("cases"))).toString());
                    JSONArray medData= (JSONArray)jo.get("cases");
                    String dataOfPersonX = "";
                    for (int j=0; j<medData.length(); j++){
                        dataOfPersonX += (new MedCase(medData.getJSONObject(j))).toString() + "\r\n";
                    }
                    medCasesList.add(dataOfPersonX);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
