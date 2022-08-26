package com.poly.assignment;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyVolley {

    private static MyVolley instance;
    private RequestQueue requestQueue;
    private static Context context;

    private MyVolley(Context _context) {
        context = _context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MyVolley getInstance(Context c) {
        if (instance == null) {
            instance = new MyVolley(c);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
