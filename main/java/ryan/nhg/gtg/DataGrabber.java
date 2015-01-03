package ryan.nhg.gtg;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ryan on 12/25/14.
 */
public class DataGrabber extends AsyncTask<String, Void, JSONObject>
{
    private static String MTD_API_KEY = "0a2a340384c4430aa1b1e6d61f1453e1";

    public static final int     GET_LOCATION_BUS_STOPS = 0,
                                GET_SEARCH_BUS_STOPS = 1,
                                GET_BUSES_FOR_STOP = 2,
                                GET_QUICK_SEARCH_BUS_STOPS = 3;
    private int source;

    private BusStopList busStopList;
    private BusList busList;

    public DataGrabber(BusStopList busStopList)
    {
        this.busStopList = busStopList;
    }

    public DataGrabber(BusList busList)
    {
        this.busList = busList;
    }

    private static JSONObject getBusStops(String query)
    {
        String url = "https://developer.cumtd.com/api/v2.2/json/GetStopsBySearch" +
                "?key=" + MTD_API_KEY +
                "&query=" + query.replace(" ","%20");

        return getJsonObject(url);
    }

    private static JSONObject getQuickBusStops(String query)
    {
        String url = "http://www.cumtd.com/autocomplete/Stops/v1.0/json/search" +
                "?query=" + query.replace(" ","%20");

        JSONArray array = getJsonArray(url);
        JSONObject obj = new JSONObject();
        try {
            obj.put("stops",array);
            System.out.println(obj.toString());
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject getBusStops(double lat, double lon)
    {
        String url =    "https://developer.cumtd.com/api/v2.2/json/GetStopsByLatLon?" +
                "key=" + MTD_API_KEY +
                "&lat=" + lat +
                "&lon=" + lon;

        return getJsonObject(url);
    }

    private static JSONObject getBuses(String stopId)
    {
        String url = "https://developer.cumtd.com/api/v2.2/json/GetDeparturesByStop" +
                "?key=" + MTD_API_KEY +
                "&stop_id=" + stopId;

        return getJsonObject(url);
    }

    private static JSONObject getJsonObject(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                try {
                    return new JSONObject(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private static JSONArray getJsonArray(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                try {
                    return new JSONArray(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected JSONObject doInBackground(String... params)
    {
        source = Integer.parseInt(params[0]);

        switch(source)
        {
            case GET_LOCATION_BUS_STOPS:
                double lat = Double.parseDouble(params[1]);
                double lon = Double.parseDouble(params[2]);
                return getBusStops(lat, lon);
            case GET_SEARCH_BUS_STOPS:
                String query = params[1];
                return getBusStops(query);
            case GET_BUSES_FOR_STOP:
                String stop_id = params[1];
                return getBuses(stop_id);
            case GET_QUICK_SEARCH_BUS_STOPS:
                String quick_query = params[1];
                if(quick_query.equals("")) return null;
                return getQuickBusStops(quick_query);
            default: return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject obj)
    {
        super.onPostExecute(obj);

        if(obj==null) return;

        switch(source)
        {
            case GET_BUSES_FOR_STOP:
                busList.addStops(obj);
                return;
            default:
                busStopList.addStops(obj,source);
        }

    }


}
