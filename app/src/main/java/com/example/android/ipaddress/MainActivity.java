package com.example.android.ipaddress;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    TextView mIpDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIpDisplay = (TextView) findViewById(R.id.ip_display);

        new IpAsync().execute("https://api.ipify.org");
    }
    public static String getResponseFromHttp(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }

    private class IpAsync extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String searchUrl = params[0];

            String ipAddressResult = null;
            try {
                URL url = new URL(searchUrl);
                ipAddressResult = getResponseFromHttp(url);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return ipAddressResult;
        }

        @Override
        protected void onPostExecute(String  ipAddressResult)
        {

            if (ipAddressResult != null && !ipAddressResult.equals("")) {
                mIpDisplay.setText(ipAddressResult);
            }
        }
    }

}