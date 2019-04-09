package hng.tech.apoe_4;

import android.content.Context;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class slideAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;
    public slideAdapter(Context context){
this.context=context;
    }
public int [] slidde_immage ={
       R.drawable.privacy,
        R.drawable.safety,
        R.drawable.care
};


public String[]slide_headings={
"Your privacy and Time is",
        "Your Privacy and Time is",
        "Individual care for every"
    };

    public String[]slide_subheadings={
            "            Safe With US",
            "            Safe With US",
            "               Patients"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject( View view,  Object o) {
        return view==( RelativeLayout)o;
    }
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slidelayout,container,false);

ImageView slideImageView=view.findViewById(R.id.imageView);
TextView slideheadings=(TextView)view.findViewById(R.id.editText);
        TextView slidesubheadings=view.findViewById(R.id.subheading);

slideImageView.setImageResource(slidde_immage[position]);
slideheadings.setText(slide_headings[position]);
slidesubheadings.setText(slide_subheadings[position]);
container.addView(view);
        return view;
    }
    public void destroyItem(ViewGroup container, int position, Object object){
container.removeView((RelativeLayout)object);
    }
}
