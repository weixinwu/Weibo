package project.weixin.com.weibo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by wuwei on 8/24/2016.
 */
public class CustomGridviewAdapter extends BaseAdapter{
    private Context context;
    private String[] imageURL;
    private static LayoutInflater inflater=null;
    private static LruCache<String,Bitmap> cache;
    public CustomGridviewAdapter(Context context, String[] imageURL, LruCache<String,Bitmap> mCache){
        this.context = context;
        this.imageURL = imageURL;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cache = mCache;
    }
    @Override
    public int getCount() {
        return imageURL.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi==null)
            vi = inflater.inflate(R.layout.custom_gridview,null);

        ImageView imageView = (ImageView)   vi.findViewById(R.id.imageview_in_gridview);
        Bitmap temp = BitmapFactory.decodeResource(context.getResources(),R.drawable.rsz_default);
        temp = temp.createScaledBitmap(temp,120,120,false);
        imageView.setImageBitmap(temp);
        GridviewImage gridviewImage = new GridviewImage(imageView,imageURL[position],cache);
        gridviewImage.setImage();
        return vi;
    }
}
