package com.example.raifu.nearesthospitalinmap.DownloadUrl;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {
    public String readUrl(String strUrl)throws IOException{
        String data ="";
        InputStream iStream = null;
        HttpURLConnection urlConnection= null;
        try {
            URL url = new URL(strUrl);


            //create a https connection to communicate with url
            urlConnection = (HttpURLConnection)url.openConnection();

            //connection to url
            urlConnection.connect();

            //reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine())!=null){

                sb.append(line);
            }
            data = sb.toString();
            Log.d("downloadUrl",data.toString());
            br.close();
        }catch (Exception e){
            Log.d("Exception",e.toString());
        }finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
