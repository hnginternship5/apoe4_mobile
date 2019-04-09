package hng.tech.apoe_4;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class splashScreen extends Activity {
    ViewPager slideviewpager;
    LinearLayout dotlayout;

    private TextView[] mdots;
    private slideAdapter slideAdapter;

    private Button back;
    private Button skip;
    private  int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        slideviewpager =  findViewById(R.id.slideViewpager);
        dotlayout =  findViewById(R.id.dotlayout);
        slideAdapter = new slideAdapter(this);
        slideviewpager.setAdapter(slideAdapter);
        addDotsIndicator(0);
        slideviewpager.addOnPageChangeListener(viewListener);
        back=findViewById(R.id.buttonBack);
        skip=findViewById(R.id.buttonSkip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideviewpager.setCurrentItem(currentPage+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                slideviewpager.setCurrentItem(currentPage-1);
            }
        });
    }

    public void addDotsIndicator(int position) {
        mdots = new TextView[3];
        dotlayout.removeAllViews();
        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.colorAccent));
            dotlayout.addView(mdots[i]);

        }

        if (mdots.length > 0) {
            mdots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currentPage=i;

            if (i==0){
                skip.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);
                skip.setText("Skip");
                back.setText("");
            }else if(i==mdots.length-1){

                skip.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                skip.setText("Finish");
                back.setText("<");
            }else{
                skip.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                skip.setText("Skip");
                back.setText("<");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };



}