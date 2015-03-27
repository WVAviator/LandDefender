package com.wvaviator.LandDefender;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Reference {

	public final static String MOD_ID = "landdefender";
	public final static String MOD_NAME = "Land Defender";
	public final static String VERSION = "0.0.1";
	
//	public static UUID getOnlineUUID(String name)
  //  {
  //          if (!Strings.isNullOrEmpty(name)) {
 //                   try {
 //                           URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
//                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
 //                           connection.setRequestMethod("GET");
 //                           connection.setRequestProperty("Content-Type", "application/json");
 //                           connection.setUseCaches(false);
 //                           connection.setDoInput(true);
 //                           connection.setDoOutput(true);
 //                           JsonObject profile = (JsonObject)(new JsonParser()).parse(new InputStreamReader(connection.getInputStream()));
 //                           return UUID.fromString(fullUUID(profile.get("id").toString()));
 //                   } catch (Exception e) {}
  //          }
 //           return null;
//    }
	
//	 public static String fullUUID(String uuid)
 //    {
 //            uuid = cleanUUID(uuid);
  //           uuid = (uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
  //           return uuid;
  //   }
	 
	// public static String cleanUUID(String uuid)
 //    {
    //         return uuid.replaceAll("[^a-zA-Z0-9]", "");
  //   }
	
	
}
