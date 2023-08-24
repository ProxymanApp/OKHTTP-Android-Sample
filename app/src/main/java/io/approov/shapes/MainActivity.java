//
// MIT License
//
// Copyright (c) 2016-present, Critical Blue Ltd.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
// (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
// publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
// ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
// THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package io.approov.shapes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// *** UNCOMMENT THE LINE BELOW FOR APPROOV ***
//import io.approov.service.okhttp.ApproovService;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Activity activity;
    private TextView statusTextView = null;
    private Button helloCheckButton = null;
    private Button shapesCheckButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        // find controls
        statusTextView = findViewById(R.id.txtStatus);
        helloCheckButton = findViewById(R.id.btnConnectionCheck);
        shapesCheckButton = findViewById(R.id.btnShapesCheck);

        // handle hello connection check
        helloCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://httpbin.org/get").newBuilder();
                urlBuilder.addQueryParameter("id", "1");
                urlBuilder.addQueryParameter("name", "Proxyman");
                String url = urlBuilder.build().toString();
                makeRequest(url);
            }
        });

        // handle getting shapes
        shapesCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://httpbin.proxyman.app/get").newBuilder();
                urlBuilder.addQueryParameter("id", "1");
                urlBuilder.addQueryParameter("name", "Proxyman");
                String url = urlBuilder.build().toString();
                makeRequest(url);
            }
        });
    }

    private void makeRequest(String url) {
        // make a new Request
        Request request = new Request.Builder()
                .url(url).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "Hello call failed: " + e.getMessage());
                final String msg = "Request failed: " + e.getMessage();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statusTextView.setText(msg);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final int imgId;
                final String msg = "Http status code " + response.code();
                if (response.isSuccessful()){
                    Log.d(TAG,"Hello call successful");
                } else {
                    Log.d(TAG,"Hello call unsuccessful");
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statusTextView.setText(msg);
                    }
                });
            }
        });
    }
}
