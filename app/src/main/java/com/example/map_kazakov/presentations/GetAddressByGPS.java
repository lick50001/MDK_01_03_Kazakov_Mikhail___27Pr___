package com.example.map_kazakov.presentations;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class GetAddressByGPS extends AsyncTask<Void, Void, Void> {
    TextView textAddress;
    String coordinats;
    String token = "1bc96f90-1daf-4320-8068-5576dfa9621f";
    AddressResponse Response = null;

    public GetAddressByGPS(String coordinats, TextView TextAddress) {
        this.coordinats = coordinats;
        textAddress = TextAddress;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("https://geocode-maps.yandex.ru/1.x/?apikey=" +
                            token + "&format=json&geocode=" + coordinats + "&results=1")
                    .ignoreContentType(true)
                    .get();

            GsonBuilder builder = new GsonBuilder();
            Response = builder.create().fromJson(document.text(), AddressResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        textAddress.setText(Response.response.GeoObjectCollection.featureMember.get(0).GeoObject.metaDataProperty.GeocoderMetaData.text);
    }
}
