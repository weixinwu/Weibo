package project.weixin.com.weibo;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.provider.DocumentsContract;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by wuwei on 8/21/2016.
 */
public class Timeline  {
    private String created_at;
    private long id;
    private String text;
    private boolean favorite;
    private String thumbnail_pic;
    private String origina_pic;
    private String source ;
    private User usr;
    private int repost_count;
    private int commint_cOunt;
    private Resources res;
    public String[] imageURL;
    public Bitmap[] bitmaps;

    public Bitmap getBitmap() {
        return bitmap;
    }

    private Bitmap bitmap;

    public Timeline(Resources res ,String[] imageURL, String created_at, long id, String text, boolean favorite, String thumbnail_pics, String origina_pics, String s, User usr, int repost_count, int commint_cOunt) throws Exception {

        this.res = res;
        this.imageURL = imageURL;
        this.created_at = created_at;
        this.id = id;
        this.text = text;
        this.favorite = favorite;
        this.thumbnail_pic = thumbnail_pics;
        this.origina_pic = origina_pics;
        this.source="";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(s)));
        Element rootElement = document.getDocumentElement();
        NodeList nodeList = rootElement.getChildNodes();
        this.source +=nodeList.item(0).getNodeValue();;


        this.usr = usr;
        this.repost_count = repost_count;
        this.commint_cOunt = commint_cOunt;


        SimpleDateFormat parserSDF=new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date d = parserSDF.parse(created_at);

        DateFormat df = new SimpleDateFormat("HH:mm");

        this.created_at = df.format(d);




    }

    public class getProfilePic extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap tempBitmap = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                if (is ==null)
                    tempBitmap = null;
                else
                    tempBitmap = BitmapFactory.decodeStream(is);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return tempBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            if (bmp!=null)
                bitmap = bmp.createScaledBitmap(bmp,bmp.getWidth()*4,bmp.getHeight()*4,false);
            else {
                bitmap = BitmapFactory.decodeResource(res,R.mipmap.ic_launcher);
            }
        }
    }

    public String getCreated_at() {
        return created_at;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public String getOrigina_pic() {
        return origina_pic;
    }

    public User getUsr() {
        return usr;
    }

    public int getRepost_count() {
        return repost_count;
    }

    public int getCommint_cOunt() {
        return commint_cOunt;
    }

    public String getSource() {
        return this.source;
    }
    public void setBitmap(Bitmap m){
        if (m!=null)
            this.bitmap =m;
        else
            this.bitmap = BitmapFactory.decodeResource(res,R.mipmap.ic_launcher);
        this.bitmap = Bitmap.createScaledBitmap(this.bitmap,this.bitmap.getWidth()*4,this.bitmap.getHeight()*4,false);
    }

    public void setBitmaps(Bitmap[] bitmaps){
        this.bitmaps = bitmaps;
        scaleBitmap();
    }
    public void scaleBitmap(){
        int size = bitmaps.length;
        for (int i = 0 ; i < size;i++){
            bitmaps[i]=Bitmap.createScaledBitmap(bitmaps[i],160,160,false);
        }
    }
}
