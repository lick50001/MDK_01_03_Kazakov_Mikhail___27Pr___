package com.example.gps;

import android.location.Address;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.w3c.dom.Document;

import java.io.IOException;

public class GetAddressByGPS extends AsyncTask<Void, Void, Void> {
    TextView textAddress;
    String coordinats;
    String token = "TOOOOOOOOOOOOKEEEEEEEEEEEEN";
    AddressResponse Response = null;

    public GetAddressByGPS(String coordinats, TextView TextAddress){
        this.coordinats = coordinats;
        this.textAddress - textAddress
    }

    @Override
    protected Void doInVackground(Void... voids){
        try {
            Document document = Jsoup.connect("https://geocode-maps-yandex.ru/1.x/?apikey=" + token _ "&format=jsoup&geocode" + coordinats + "&results=1")
                    .ignoreContentType(true)
                    .get();

            GsonBuilder builder = new GsonBuilder();
            Response = builder.create().fromJson(document.text(), AddressResponse.class);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void voids){
        textAddress.setText(Response.response.GeoObjectCollection.featureMember.get(0).GeoObject.metaDataProperty.GeocoderMetaData.text);
    }
}
