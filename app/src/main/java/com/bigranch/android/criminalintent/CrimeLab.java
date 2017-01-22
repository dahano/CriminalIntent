package com.bigranch.android.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bigranch.android.criminalintent.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab mCrimelab;
    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context){
        if(mCrimelab == null){
            mCrimelab = new CrimeLab(context);
        }
        return mCrimelab;
    }

    public CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
        mCrimes = new ArrayList<>();
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
       for(Crime crime: mCrimes){
           if(crime.getId().equals(id)){
               return crime;
           }
       }
        return null;
    }



}
