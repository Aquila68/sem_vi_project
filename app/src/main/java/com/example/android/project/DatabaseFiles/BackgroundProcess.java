package com.example.android.project.DatabaseFiles;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by DE on 16-01-2018.
 */

public class BackgroundProcess extends Request<JSONObject>{
    String tag="Problem in sending request";
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;

    public BackgroundProcess(String url, Map<String, String> params,
                          Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }


    public BackgroundProcess(int method, String url, Map<String, String> params,
                          Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.d("Testing 123","response in parseNetworkResponse method: "+jsonString);
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
        catch(NullPointerException ne)
        {
            Log.d("Error here",ne.toString());
            return Response.error(new ParseError(ne));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}

