package com.example.android.project.DatabaseFiles;

public class CustTicket {
    private String mSource;
    private String mDest;
    private int mCost;
    private String mTransTime;
    public CustTicket(String mSource,String mDest,int mCost,String mTransTime){
        this.mSource=mSource;
        this.mDest=mDest;
        this.mCost=mCost;
        this.mTransTime=mTransTime;
    }

    public void setmSource(String mSource){
        this.mSource=mSource;
    }
    public String getmSource(){
        return this.mSource;
    }

    public void setmDest(String mDest){
        this.mDest=mDest;
    }
    public String getmDest(){
        return this.mDest;
    }

    public void setmCost(int mCost){
        this.mCost=mCost;
    }
    public int getmCost(){
        return this.mCost;
    }

    public void setmTransTime(String mTransTime){
        this.mTransTime=mTransTime;
    }
    public String getmTransTime(){
        return this.mTransTime;
    }
}
