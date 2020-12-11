package com.example.FlowFireHub.Utilities;


import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class SteamManager {

    public String getSteamData(String steamid) {
        final String uri = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=0A018ABE207C1FDFA4A5D364AD89E330&steamids=" + steamid;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    public String getKey(String data, String key) {
        JSONObject obj = new JSONObject(data);
        JSONObject value = obj.getJSONObject("response").getJSONArray("players").getJSONObject(0);
        String result = value.getString(key);
        return result;
    }
    
}
