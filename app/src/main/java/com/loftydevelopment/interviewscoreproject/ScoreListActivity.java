package com.loftydevelopment.interviewscoreproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ScoreListActivity extends AppCompatActivity {

    private final String JSON_URL = "https://gist.githubusercontent.com/ryanneuroflow/370d19311602c091928300edd7a40f66/raw/1865ae6004142553d8a6c6ba79ccb511028a2cba/names.json";
    private ArrayList<Score> scoreList;

    private final String[] sectionHeaders = {"males", "females"};
    private ListView scoreListView;
    private SectionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        scoreList = new ArrayList<>();

        //retrieve JSON on a different thread than UI
        new Thread(new Runnable() {

            public void run() {

                final String jsonData = loadJSONFromWeb(JSON_URL);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        //run on UI thread once complete
                        parseJson(jsonData);

                        adapter = new SectionListAdapter(getApplicationContext());

                        ArrayAdapter<String> listAdapterMales = new ArrayAdapter<>(
                                getApplicationContext(), R.layout.list_item, generateOrderedSectionArray(Score.Gender.MALE));

                        ArrayAdapter<String> listAdapterFemales = new ArrayAdapter<>(
                                getApplicationContext(), R.layout.list_item, generateOrderedSectionArray(Score.Gender.FEMALE));

                        adapter.addSection(sectionHeaders[0], listAdapterMales);
                        adapter.addSection(sectionHeaders[1], listAdapterFemales);

                        scoreListView = ScoreListActivity.this.findViewById(R.id.list_journal);
                        scoreListView.setAdapter(adapter);

                        scoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {

                                TextView scoreTextView = view.findViewById(R.id.list_item_title);
                                openScoreActivity(scoreTextView.getText().toString());

                            }
                        });

                    }
                });
            }
        }).start();

    }

    private void parseJson(String jsonData){

        try {

            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray subArray = jsonObject.getJSONArray(sectionHeaders[i]);

                for(int j =0 ; j < subArray.length(); j++) {

                    JSONObject scoreJson = subArray.getJSONObject(j);
                    String name = scoreJson.getString("name");
                    String score = scoreJson.getString("score");
                    String date = scoreJson.getString("date_created");
                    Log.i("json_value", name + ", " + score + ", " + date + ", " + sectionHeaders[i].substring(0, 1));

                    scoreList.add(new Score(name + ", " + score + ", " + date + ", " + sectionHeaders[i].substring(0, 1)));

                }

            }

        } catch (JSONException e) {
            Log.i("Error", e.getMessage());
        }


    }

    public String loadJSONFromWeb(String urlString) {

        URLConnection feedUrl;
        String jsonData = "";

        try {
            feedUrl = new URL(urlString).openConnection();
            InputStream is = feedUrl.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;

            while ((line = reader.readLine()) != null) {
                jsonData = jsonData + line;
            }

            is.close();

            return jsonData;

        }catch (Exception e) {
            Toast.makeText(this, "Error reading JSON from URL. Check internet connection.", Toast.LENGTH_LONG).show();
        }

        return null;

    }

    private void openScoreActivity(String scoreData){

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("scoreData", scoreData);
        startActivity(intent);

    }

    private String[] generateOrderedSectionArray(Score.Gender gender){

        ArrayList<Score> genderList = new ArrayList<>();

        for(Score score : scoreList){

            if(score.getGender() == gender){
                genderList.add(score);
            }

        }

        Collections.sort(genderList, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o1.getDateTimeLong().compareTo(o2.getDateTimeLong());
            }
        });

        String[] scoresFormatted = new String[genderList.size()];

        for(int i = 0; i < scoresFormatted.length; i++){

            scoresFormatted[i] = genderList.get(i).getFormattedScore();

        }

        return scoresFormatted;

    }

}
