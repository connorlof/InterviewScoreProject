package com.loftydevelopment.interviewscoreproject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Score{

    private String name, score, dateTimeFormatted;
    private Long dateTimeLong;
    private Gender gender;

    private String formattedScore;

    public Score(String scoreData){

        parseUnformattedScore(scoreData);
        setFormattedScore();

    }

    enum Gender{
        MALE, FEMALE
    }

    private void parseUnformattedScore(String scoreData){

        String[] splitScoreData = scoreData.split("\\s*,\\s*");

        name = splitScoreData[0];
        score = splitScoreData[1];

        dateTimeLong = Long.parseLong(splitScoreData[2]);
        dateTimeFormatted = formatDateTime(dateTimeLong);

        gender = splitScoreData[3].equals("m") ? Gender.MALE : Gender.FEMALE;

    }

    private String formatDateTime(Long dateTimeMilliSec){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

        return simpleDateFormat.format(new Date(dateTimeMilliSec));

    }

    //Setters
    private void setFormattedScore(){
        formattedScore = name + " - " + score + "% - " + dateTimeFormatted;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getDateTimeFormatted() {
        return dateTimeFormatted;
    }

    public Gender getGender() {
        return gender;
    }

    public Long getDateTimeLong(){
        return dateTimeLong;
    }

    public String getFormattedScore(){
        return formattedScore;
    }
}
