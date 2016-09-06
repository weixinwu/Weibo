package project.weixin.com.weibo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wuwei on 9/2/2016.
 */
public class GridviewImage {

    public ImageView im;
    public String url;
    private LruCache<String,Bitmap> cache;
    public GridviewImage(ImageView im, String url,LruCache<String,Bitmap> mCache) {
        this.im = im;
        this.url = url;
        this.cache = mCache;
    }
    public void setImage(){
        Bitmap bitmap = cache.get(url);
        if (bitmap!=null){
            im.setImageBitmap(bitmap);
        }
        else {
            new DownloadImage().execute(url);
        }
    }

    public Bitmap getImageFromURL(String url) throws IOException {
        Bitmap retBitmap = null;
        URL imageUrl = new URL(url);
        retBitmap= BitmapFactory.decodeStream(imageUrl.openStream());
        return retBitmap;
    }

    class DownloadImage extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap tempBitmap= null;
            try {
                tempBitmap = getImageFromURL(params[0]);
                cache.put(url,tempBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tempBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                im.setImageBitmap(bitmap);
            }
        }
    }
}
