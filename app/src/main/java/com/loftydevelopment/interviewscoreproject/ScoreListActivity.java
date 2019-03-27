package com.loftydevelopment.interviewscoreproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreListActivity extends AppCompatActivity {

    private String[] sampleData = {
                                    "Ryan, 63, 1546341851000, m",
                                    "Sam, 86, 1536442851000, m",
                                    "Joey, 78, 1546442992000, m",
                                    "Melissa, 91, 1540341851000, f",
                                    "Jess, 93, 1540341751000, f",
                                    "Carly, 89, 1540341651000, f"
                                };

    private final String[] sectionHeaders = {"Males", "Females"};
    private ListView scoreListView;
    private SectionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        adapter = new SectionListAdapter(this);

        ArrayAdapter<String> listAdapterMales = new ArrayAdapter<>(
                this, R.layout.list_item, generateOrderedSectionArray(sampleData, "m"));

        ArrayAdapter<String> listAdapterFemales = new ArrayAdapter<>(
                this, R.layout.list_item, generateOrderedSectionArray(sampleData, "f"));

        adapter.addSection(sectionHeaders[0], listAdapterMales);
        adapter.addSection(sectionHeaders[1], listAdapterFemales);

        scoreListView = (ListView) this.findViewById(R.id.list_journal);
        scoreListView.setAdapter(adapter);

        scoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {

                TextView scoreTextView = (TextView) view.findViewById(R.id.list_item_title);
                openScoreActivity(scoreTextView.getText().toString());

            }
        });

    }

    private void openScoreActivity(String scoreData){

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("scoreData", scoreData);
        startActivity(intent);

    }

    private String[] generateOrderedSectionArray(String[] data, String gender){

        ArrayList<Score> scoreList = new ArrayList<>();

        for(String scoreString : data){

            if(scoreString.endsWith(gender)){

                Score score = new Score(scoreString);
                scoreList.add(score);

            }

        }

        Collections.sort(scoreList, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o1.getDateTimeLong().compareTo(o2.getDateTimeLong());
            }
        });

        String[] scoresFormatted = new String[scoreList.size()];

        for(int i = 0; i < scoresFormatted.length; i++){

            scoresFormatted[i] = scoreList.get(i).getFormattedScore();

        }

        return scoresFormatted;

    }

}
