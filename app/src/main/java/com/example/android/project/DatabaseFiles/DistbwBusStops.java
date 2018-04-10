package com.example.android.project.DatabaseFiles;

import android.content.Intent;
import android.util.Log;

/**
 * Created by DE on 23-02-2018.
 */

public class DistbwBusStops {
    final public String[][] bus100Matrix=new String[][]{
            {"100"              ,"Navy Nagar","Afghan Church","Colaba Causeway","Regal Cinema","C.S.M.T."},
            {"Navy Nagar"       ,"0"         ,"0.8"          ,"2.1"            ,"3.1"         ,"4.5"     },
            {"Afghan Church"    ,"0.8"       ,"0"            ,"1.3"            ,"2.3"         ,"3.7"     },
            {"Colaba Causeway"  ,"2.1"       ,"1.3"          ,"0"              ,"1"           ,"2.4"     },
            {"Regal Cinema"     ,"3.1"       ,"2.3"          ,"1"              ,"0"           ,"1.4"     },
            {"C.S.M.T."         ,"4.5"       ,"3.7"          ,"2.4"            ,"1.4"         ,"0"       }
    };


    public float distCalc(String[][] matrix,String from,String to){
             int f=0,t=0;
             boolean boolfrom=false,boolto=false;
             try{
                 for(f=1;f<matrix.length;f++){
                 if(matrix[f][0].equals(from))
                 {
                    boolfrom=true;
                    break;
                 }
             }

             for(t=1;t<matrix.length;t++){
                  if(matrix[0][t].equals(to))
                   {
                        boolto=true;
                        break;
                   }
             }}
             catch(Exception e){
                 Log.d("error in DistbwBusStops",e.toString());
             }

                if (boolto==false && boolfrom==false)
                {
                    Log.d("DistbwBusStops","source and dest. bus stop not found");
                    return -3;
                }
                else if (boolfrom==false){
                    Log.d("DistbwBusStops","source bus stop not found");
                    return -2;
                }
                else if (boolto==false){
                    Log.d("DistbwBusStops","dest. bus stop not found");
                    return -1;
                }
                float x= Float.parseFloat(matrix[f][t]);

        return x;
    }

    public int costCalc(float dist){
        int cost=0;
        if(dist>0)
        {
            if (dist>=0.5 && dist<1.5){
             cost=8;
            }
            else if(dist>=1.5 && dist<2.5){
                cost=10;
            }
            else if(dist>=2.5 && dist<3.5){
                cost=12;
            }
            else if(dist>=3.5 && dist<4.5){
                cost=14;
            }
            else if(dist>=4.5 && dist<5.5){
                cost=16;
            }
        }
        return cost;
    }


}
