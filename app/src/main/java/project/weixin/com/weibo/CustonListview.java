package project.weixin.com.weibo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wuwei on 8/22/2016.
 */
public class CustonListview extends BaseAdapter{

    String result[];
    Context context;
    ArrayList<Timeline> alTimelines ;
    private static LayoutInflater inflater=null;

    public CustonListview(Context context,ArrayList<Timeline> timelines){
        alTimelines = timelines;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return alTimelines.size();
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
            vi = inflater.inflate(R.layout.custon_timeline_listview,null);

        Timeline tempTimeline = alTimelines.get(position);
        TextView source = (TextView) vi.findViewById(R.id.timeline_lv_source_tv);
        TextView time = (TextView) vi.findViewById(R.id.timeline_lv_created_at_time_tv);
        TextView user_name = (TextView) vi.findViewById(R.id.timeline_user_name_tv);
        Button comment = (Button) vi.findViewById(R.id.timeline_lv_comment);
        Button repost = (Button) vi.findViewById(R.id.timeline_lv_repost);
        ImageView profile_iv = (ImageView) vi.findViewById(R.id.timeline_lv_profile_pic);
        TextView timeline_text = (TextView) vi.findViewById(R.id.timeline_text);
        timeline_text.setText(tempTimeline.getText());
        profile_iv.setImageBitmap(tempTimeline.getBitmap());
        source.setText(" source "+tempTimeline.getSource());
        user_name.setText(tempTimeline.getUsr().getName());
        time.setText(tempTimeline.getCreated_at());
        
//        comment.setText(comment.getText()+" "+tempTimeline.getCommint_cOunt());
//        repost.setText(comment.getText()+" "+tempTimeline.getRepost_count());
        return vi;
    }
}
