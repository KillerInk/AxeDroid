package com.osum.axedroid.ui.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import com.osum.axedroid.ui.obj.PoolTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SettingsController
{
    private SharedPreferences preferences;
    public HashMap<String, PoolTemplate> poolTemplateHashMap = new HashMap<>();
    public SettingsController(Context context)
    {
        preferences = context.getSharedPreferences("Devices", Context.MODE_PRIVATE);
        loadPools();
    }

    public void storeDevices(Set<String> ips)
    {
        preferences.edit().putStringSet("devices", ips).commit();
    }

    public Set<String> loadDevices()
    {
        return preferences.getStringSet("devices",new ArraySet<>());
    }

    public void storePoolTemplates(Set<PoolTemplate> poolTemplates)
    {
        JSONArray jsonObject = new JSONArray();
        for(PoolTemplate w : poolTemplates)
        {
            JSONObject ob = new JSONObject();
            try {
                ob.put("url",w.url.get());
                ob.put("user",w.user.get());
                ob.put("pw",w.pw.get());
                ob.put("port",w.port.get());
                jsonObject.put(ob);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String s = jsonObject.toString();
        preferences.edit().putString("pools",s).commit();
    }

    public Set<PoolTemplate> getPoolTemplates()
    {
        Set<PoolTemplate> poolTemplates = new ArraySet<>();
        String w = preferences.getString("pools","");
        if(w.length() == 0)
            return poolTemplates;
        try {
            JSONArray ar = new JSONArray(w);
            for(int i =0;i<ar.length();i++)
            {
                JSONObject ob = ar.getJSONObject(i);
                PoolTemplate t = new PoolTemplate();
                if(ob.has("url"))
                    t.url.set(ob.get("url").toString());
                if(ob.has("user"))
                    t.user.set(ob.get("user").toString());
                if(ob.has("pw"))
                    t.pw.set(ob.get("pw").toString());
                if(ob.has("port"))
                    t.port.set(ob.get("port").toString());
                poolTemplates.add(t);
                poolTemplateHashMap.put(t.url.get(),t);
            }
        } catch (JSONException e) {
           e.printStackTrace();
        }
        return poolTemplates;
    }

    public void loadPools()
    {
        getPoolTemplates();
    }

    public synchronized void savePools()
    {
        Set<PoolTemplate> ips = new ArraySet<>();
        for(Map.Entry<String,PoolTemplate> e : poolTemplateHashMap.entrySet())
        {
            ips.add(e.getValue());
        }
        storePoolTemplates(ips);
    }

    public String[] getPoolNames()
    {
        String[] p = new String[poolTemplateHashMap.size()];
        int i=0;
        for(Map.Entry<String,PoolTemplate> e : poolTemplateHashMap.entrySet())
        {
            p[i++] = e.getKey();
        }
        return p;
    }
}
