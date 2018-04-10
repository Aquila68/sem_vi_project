package com.example.android.project.DatabaseFiles;

/**
 * Created by DE on 11-02-2018.
 */

public class Bus {
    private int mBusNumber;
    private String mBusRoute;
    public Bus(int mBusNumber, String mBusRoute){
        this.mBusNumber=mBusNumber;
        this.mBusRoute=mBusRoute;
    }

    public Bus(){}

    public void setmBusNumber(int mBusNumber){
        this.mBusNumber=mBusNumber;
    }

    public int getmBusNumber(){
        return mBusNumber;
    }

    public void setmBusRoute(String mBusRoute){
        this.mBusRoute=mBusRoute;
    }

    public String getmBusRoute(){
        return mBusRoute;
    }

}
