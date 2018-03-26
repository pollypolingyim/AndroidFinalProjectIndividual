package com.example.pollyplyim.androidfinalproject;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by pollyplyim on 2018-03-04.
 */

public class CentralMC {
    private static CentralMC centralMC;
    private ArrayList<MC> mcs;
    private Context appContext;

    private CentralMC(Context ac){
        mcs = new ArrayList<MC>();
        appContext = ac;
        for(int i=1; i<101; i++){
            MC m = new MC();
            m.setQuestion("Question "+i);
            mcs.add(m);
        }
    }

    public static CentralMC getInstance(Context c){
        if(centralMC==null)
            centralMC = new CentralMC (c.getApplicationContext());
        return centralMC;
    }

    public ArrayList<MC> getMcs(){
        return mcs;
    }

    public MC getMC(UUID id){
        for(MC m: mcs){
            if(m.getId().equals(id))
                return m;
        }
        return null;
    }
}
