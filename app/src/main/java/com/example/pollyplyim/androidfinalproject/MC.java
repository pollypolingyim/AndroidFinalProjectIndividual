package com.example.pollyplyim.androidfinalproject;

import java.util.DoubleSummaryStatistics;
import java.util.UUID;

/**
 * Created by pollyplyim on 2018-03-04.
 */

public class MC {
    private String question;
    private String[] choices = new String[4];
    private String userChoice;
    private String correctChoice;
    private static final String[] trueOrFalse = {"TRUE", "FALSE"};
    private String userTF;
    private String correctTF;
    private String userTextInput;
    private String correctTextInput;
    private Double userNumInput;
    private Double correctNumInput;


    public void setQuestion(String q){question=q;}
    public void setChoics (String[] c){
        choices[0] = "A. " +c[0];
        choices[1] = "B. " +c[1];
        choices[2] = "C. " +c[2];
        choices[3] = "D. " +c[3];
    }
    public void setCorrectChoice(String c){correctChoice=c;}
    public void setUserTF(String tf){userTF=tf;}
    public void setCorrectTF(String c){correctTF=c;}
    public void setTextInput(String in){userTextInput=in;}
    public void setCorrectTextInput(String cin){
        correctTextInput=cin;
    }
    public void setUserNumInput(Double in){
        userNumInput=in;
    }
    public void setCorrectNumInput(Double cin){
        correctNumInput=cin;
    }

    public String toString(){
        return question + "?\n";
    }

    public String getQuestion (){return question;}
    public String getFirstChoice(){return choices[0];}
    public String getSecondChoice(){return choices[1];}
    public String getThirdChoice(){return choices[2];}
    public String getForthChoice(){return choices[3];}
    public String getCorrectChoice(){return correctChoice;}
    public String getUserTF(){return userTF;}
    public String getCorrectTF(){return correctTF;}
    public String getUserTextInput(){return userTextInput;}
    public String getCorrectTextInput(){return correctTextInput;}
    public Double getUserNumInput(){return userNumInput;}
    public Double getCorrectNumInput(){return correctNumInput;}
    public UUID getId(){return UUID.randomUUID();}
}
