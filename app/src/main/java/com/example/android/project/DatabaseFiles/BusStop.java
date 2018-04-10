package com.example.android.project.DatabaseFiles;

/**
 * Created by DE on 13-02-2018.
 */

public class BusStop {
   // private int id;
    private String mBusStopName;
    private String mBusesWhichStopHere;
    public BusStop(String mBusStopName,String mBusesWhichStopHere){
        this.mBusStopName=mBusStopName;
        this.mBusesWhichStopHere=mBusesWhichStopHere;

    }
    public BusStop(){    }

    //public int getId(){
    //    return id;
   // }

  //  public void setid(int id){
 //       this.id=id;
 //   }

    public String getmBusStopName(){
        return mBusStopName;
    }

    public void setmBusStopName(String mBusStopName){
        this.mBusStopName=mBusStopName;
    }

    public String getmBusesWhichStopHere(){
        return mBusesWhichStopHere;
    }

    public void setmBusesWhichStopHere(String mBusesWhichStopHere){
        this.mBusesWhichStopHere=mBusesWhichStopHere;
    }
}
