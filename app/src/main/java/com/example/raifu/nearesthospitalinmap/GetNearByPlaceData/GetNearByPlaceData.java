package com.example.raifu.nearesthospitalinmap.GetNearByPlaceData;

import android.os.AsyncTask;
import android.util.Log;

import com.example.raifu.nearesthospitalinmap.DataParser.DataParser;
import com.example.raifu.nearesthospitalinmap.DownloadUrl.DownloadUrl;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class GetNearByPlaceData extends AsyncTask<Object,String,String> {
    String googlePlaceData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {
        try {
            Log.d("GetNearbyPlacesData","donInBackground entered");
            mMap = (GoogleMap)objects[0];
            url = (String)objects[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlaceData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask","doInBackground Exit");
        }catch (Exception e){
            Log.d("GooglePlacesReadTask",e.toString());
        }
        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String reault) {
        Log.d("GooglePlacesReadTask","onPostExecute Entered");
        List<HashMap<String,String>>nearPlacesList = null;

        DataParser dataParser = new DataParser();
        nearPlacesList = dataParser.parse(reault);
        ShowNearbyPlaces(nearPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearPlacesList) {

        for (int i=0; i <nearPlacesList.size();i++){
            Log.d("onPostExecute","Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String,String>googlePlace = nearPlacesList.get(i);

            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");

            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+" : "+vicinity);
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


            //move map camera

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }
    }
}
