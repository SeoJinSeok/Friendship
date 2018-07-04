package com.friendship.REST;

import com.friendship.Objects.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RESTClient {
    final private static String defaultURL = "http://192.168.219.112:8081/Friendship/";
    private static RESTClient restclient = null;
    private String urlString = null;
    private HttpURLConnection conn = null;
    private static Session session = null;

    private RESTClient() {
        super();
    }

    public static synchronized RESTClient getInstance(String path) {
        if (restclient == null) restclient = new RESTClient();
        restclient.urlString = defaultURL + path;
        return restclient;
    }

    Object GET() {
        try {
            setConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            if (conn.getResponseCode() == 404)  return true;
            else{
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[2 ^ 20];
                while ((nRead = is.read(data)) != -1) buf.write(data, 0, nRead);
                buf.flush();

                if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
                    if (buf.toString().equals("")) return true;
                    else if (urlString.contains("logout") || urlString.equals(defaultURL))
                        return true;
                    else return buf.toString();
                } else return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    Object POST(String obj) {
        try {
            setConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(obj);
            os.flush();

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
                if (session == null)    session = new Session(conn.getHeaderFields().get("Set-Cookie").get(1).split(";")[0].split("=")[1]);
                conn.disconnect();
                return true;
            } else {
                conn.disconnect();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setConnection() throws IOException{
        URL url = new URL(urlString);
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(36000);
        conn.setReadTimeout(36000);
        if (session != null)    conn.setRequestProperty("member_no", session.getId());
    }

    public String getID(){
        return session.getId();
    }
}