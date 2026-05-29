package com.example.gps;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class GetAddressByGPS extends AsyncTask<Void, Void, String> {

    private final TextView textAddress;
    private final String coordinates;
    private final String token = "TOOOOOOOOOOOOKEEEEEEEEEEEEN";

    public GetAddressByGPS(String coordinates, TextView textAddress) {
        this.coordinates = coordinates;
        this.textAddress = textAddress;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String url = "https://geocode-maps.yandex.ru/1.x/?apikey=" + token +
                    "&format=json&geocode=" + coordinates + "&results=1";

            Document doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .get();

            Gson gson = new GsonBuilder().create();
            AddressResponse response = gson.fromJson(doc.text(), AddressResponse.class);

            if (response != null &&
                    response.response != null &&
                    response.response.GeoObjectCollection != null &&
                    response.response.GeoObjectCollection.featureMembers != null &&
                    !response.response.GeoObjectCollection.featureMembers.isEmpty()) {

                return response.response.GeoObjectCollection.featureMembers.get(0)
                        .GeoObject.metaDataProperty.GeocoderMetaData.text;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Не удалось определить адрес";
    }

    @Override
    protected void onPostExecute(String address) {
        if (textAddress != null && address != null) {
            textAddress.setText(address);
        }
    }
}