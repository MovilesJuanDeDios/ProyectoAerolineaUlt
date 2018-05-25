package cr.ac.una.escinf.proyectoaerolinea.connection;


import android.content.Context;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class UserConnection {

    /*public boolean postUser(String...params) {
        URL url;
        HttpURLConnection urlConnection = null;
        boolean result = true;

        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.v("User-Response", "Usuario guardado exitosamente");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return result;
    }*/

}
