package project.weixin.com.weibo;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    String redirectUrl = null;
    String bingURL = "http://www.bing.com/";
    String app_secret ="480bf946361ec60fbc9f9898eb606a72";
    String client_id = "3937221676";
    String accessToken = null;
    String getTimelineURL ="https://api.weibo.com/2/statuses/public_timeline.json?";
    private ListView timeline_LV;


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
        timeline_LV = (ListView) findViewById(R.id.timeline_lv);
        timeline_LV.setVisibility(View.INVISIBLE);





    }

    public class getTimeline extends AsyncTask<Void,Void,ArrayList<Timeline>>{

        @Override
        protected ArrayList<Timeline> doInBackground(Void... params) {
            ArrayList<Timeline> alTimeline = new ArrayList<Timeline>();
            try {
                URL getPublicTimeline = new URL(getTimelineURL+"access_token="+accessToken);
                HttpURLConnection conn = (HttpURLConnection) getPublicTimeline.openConnection();
                if (conn.getResponseCode()!=200)
                    return null;
                else {
                    JSONObject responseBody = getResponseBody(conn.getInputStream());
                    JSONArray jsonArray = responseBody.optJSONArray("statuses");
                    int size = jsonArray.length();
                    for (int i = 0 ; i < size;i++){

                        JSONObject indexObj = jsonArray.optJSONObject(i);
                        JSONObject userObj = indexObj.optJSONObject("user");
                        User user = new User(userObj.optInt("id"),userObj.optString("screen_name"),userObj.optString("name"),
                                userObj.optInt("province"),userObj.optInt("city"),userObj.optString("location"),userObj.optString("description"),
                                userObj.optString("profile_image_url"),userObj.optString("profile_url"),userObj.optString("gender"),
                                userObj.optInt("followers_count"),userObj.optInt("friends_count"),userObj.optInt("favourites_count"),
                                userObj.optBoolean("geo_enabled"),userObj.optBoolean("verified"),userObj.optString("avatar_large"),userObj.optString("avatar_hd"),
                                userObj.optBoolean("follow_me"),userObj.optInt("online_status"),userObj.optInt("bi_followers_count"));

                        Timeline tempTimeline = new Timeline(getResources(),indexObj.optString("created_at"),indexObj.optLong("id"),indexObj.optString("text"),indexObj.getBoolean("favorited"),
                                indexObj.optString("thumbnail_pic"),indexObj.optString("original_pic"),indexObj.optString("source"),user,indexObj.optInt("reposts_count"),indexObj.optInt("comments_count"));
                        alTimeline.add(tempTimeline);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return alTimeline;
        }

        @Override
        protected void onPostExecute(ArrayList<Timeline> timelines) {
            super.onPostExecute(timelines);
            new getProfilePic().execute(timelines);



        }
    }

    public class getProfilePic extends AsyncTask<ArrayList<Timeline>,Void,ArrayList<Timeline>>{

        @Override
        protected ArrayList<Timeline> doInBackground(ArrayList<Timeline>... params) {
            int size = params[0].size();
            for (int i = 0 ; i < size;i++){

                String pic_url =  params[0].get(i).getUsr().getProfile_pic_url();
                try {
                    URL url = new URL(pic_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    params[0].get(i).setBitmap(BitmapFactory.decodeStream(conn.getInputStream()));
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(ArrayList<Timeline> timelines) {
            int size = timelines.size();
            ArrayList<String>timelineText = new ArrayList<String>();

            CustonListview adapter = new CustonListview(getBaseContext() ,timelines);


            timeline_LV.setAdapter(adapter);
            timeline_LV.setVisibility(View.VISIBLE);
        }
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new getTimeline().execute();
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
