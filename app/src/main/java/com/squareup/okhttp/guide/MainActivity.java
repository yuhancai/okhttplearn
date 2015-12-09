package com.squareup.okhttp.guide;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.io.InputStream;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    OkHttpClient okHttpClient = new OkHttpClient();

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "hahahah", Toast.LENGTH_SHORT).show();
            }
        });
        //手工增加代码结束
        //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

        AsyncTaskGetData taskGetData = new AsyncTaskGetData();
        taskGetData.execute("1");

        AsyncTaskGetImage taskGetImage = new AsyncTaskGetImage();
        taskGetImage.execute();

    }
    public class AsyncTaskGetData extends AsyncTask<String, Void, Void> {
        private String x;

        @Override
        protected void onPostExecute(Void aVoid) {
            textView.setText(x);
        }

        @Override
        protected Void doInBackground(String... params) {
            RequestBody body = new FormEncodingBuilder()
                    .add("userId","a")
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2/sproject/get_user_name.php")
                    .post(body)
                    .build();
            Response response;
            try {
                response = okHttpClient.newCall(request).execute();
                String result = response.body().string();

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(result);


                JSONObject object;
                try {
                    object = new JSONObject(result);
                    String name = object.getString("username");
                   x = "姓名 : " + name;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    public class AsyncTaskGetImage extends AsyncTask<String, Void, Void> {
        private Bitmap bitmap;

        @Override
        protected void onPostExecute(Void aVoid) {
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected Void doInBackground(String... params) {

            Request request = new Request.Builder()
                    .url("http://10.0.2.2/android_post_test.png")
                    .build();
            Response response;
            try {
                response = okHttpClient.newCall(request).execute();
                InputStream result = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }





    public Button button;
    public View.OnClickListener listener;





    public void getRequest(final Button button) {
        button.setEnabled(false);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = null;

                // リクエストオブジェクトを作って
                Request request = new Request.Builder()
                        .url("http://httpbin.org/headers")
                        .get()
                        .build();

                // クライアントオブジェクトを作って
                OkHttpClient client = new OkHttpClient();

                // リクエストして結果を受け取って
                try {
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 返す
                return result;
            }

            @Override
            protected void onPostExecute(String result) {

            }
        }.execute();
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}


