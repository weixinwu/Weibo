package project.weixin.com.weibo;

import android.content.Context;
import android.graphics.Bitmap;
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
    public CustomGridviewAdapter(Context context, String[] imageURL){
        this.context = context;
        this.imageURL = imageURL;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        GridviewImage gridviewImage = new GridviewImage(imageView,imageURL[position]);
        gridviewImage.setImage();
        return vi;
    }
}
