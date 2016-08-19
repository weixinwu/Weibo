package project.weixin.com.weibo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    String redirectUrl = null;
    String bingURL = "http://www.bing.com/";
    String app_secret ="480bf946361ec60fbc9f9898eb606a72";
    String client_id = "3937221676";
    String accessToken = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final WebView wv = (WebView)findViewById(R.id.login_webView);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setJavaScriptEnabled(true);


        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                redirectUrl = url;
                System.out.println("---------  "+redirectUrl);
                wv.setVisibility(View.INVISIBLE);
                redirectUrl = redirectUrl.substring(redirectUrl.lastIndexOf("=")+1,redirectUrl.length());

                new getAccessToken().execute();
                return false;
            }
        });
        wv.getSettings().setAppCacheEnabled(false);
        wv.loadUrl("https://api.weibo.com/oauth2/authorize?client_id=3937221676&response_type=code&display=page&redirect_uri=www.bing.com/");
        wv.clearCache(true);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public class getAccessToken extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("https://api.weibo.com/oauth2/access_token?client_id="+client_id+"&client_secret="+app_secret+"&grant_type=authorization_code&code="+redirectUrl+"&redirect_uri="+bingURL);
                System.err.println(url.toString()+"---");
                HttpURLConnection connn = (HttpURLConnection) url.openConnection();
                connn.setRequestMethod("POST");
                InputStream is = null;
                if (connn.getResponseCode()==200)
                    is = connn.getInputStream();

                if (is!=null){
                   accessToken =  getResponseBody(is).getString("access_token");
                }

                System.out.println("------"+accessToken);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public JSONObject getResponseBody(InputStream is ) throws Exception {
        if (is ==null)
            return null;

        BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
        String line;
        String body = "";
        while (null!=(line=bfr.readLine())){
            body+=line;
        }

        return new JSONObject(body);
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
