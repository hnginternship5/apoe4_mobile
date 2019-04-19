package hng.tech.apoe_4.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toHide)
    LinearLayout toHide;

    @BindView(R.id.hiddenUnits)
    LinearLayout hiddenUnits;

    @BindView(R.id.hiddenFeedback)
    LinearLayout hiddenFeedback;

    @BindView(R.id.hiddenAbout)
    LinearLayout hiddenAbout;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.drop)
    ImageView drop;

    @BindView(R.id.dropUnit)
    ImageView dropUnit;

    @BindView(R.id.dropFeedback)
    ImageView dropFeedback;

    @BindView(R.id.dropAbout)
    ImageView dropAbout;

    @BindView(R.id.unitRadioGroup)
    RadioGroup unitRadioGroup;

    @BindView(R.id.cmUnit)
    RadioButton cmUnit;

    @BindView(R.id.ftUnit)
    RadioButton ftUnit;

    ArrayList<String> list = DOB_page.loadWHGInfo();


    Intent intent;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        intent = getIntent();

        drop.setOnClickListener(v -> animateView(toHide, drop));
        dropUnit.setOnClickListener(v -> animateView(hiddenUnits, dropUnit));
        dropFeedback.setOnClickListener(v -> animateView(hiddenFeedback, dropFeedback));
        dropAbout.setOnClickListener(v -> animateView(hiddenAbout, dropAbout));

        //convertUnits();

        back.setOnClickListener(view -> {
            super.onBackPressed();
            finish();
        });

    }
    
    private void animateView(View view, ImageView button){
        if (view.getVisibility() == View.GONE){

            view.setVisibility(View.VISIBLE);
            view.setAlpha(0.0f);

// Start the animation
            view.animate()
                    .translationY(10)
                    .alpha(1.0f)
                    .setListener(null);
            button.setRotation(90.0f);
        }
        else {
            view.animate()
                    .translationY(0)
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                            button.setRotation(-360.0f);
                        }
                    });
        }
    }


    public void onUnitClicked(View view) {
        //GET HEIGHT FROM SHARED PREFERENCE AND CONVERT TO INTEGER VALUE
        double height;
        if(list.get(1).contains("ft")){
            height = Double.parseDouble(list.get(1).substring(0,list.get(1).lastIndexOf("f")));
        }else{
            height = Double.parseDouble(list.get(1).substring(0,list.get(1).lastIndexOf("c")));
        }
        String replaceHeight="";
        switch (view.getId()){
            case R.id.ftUnit:
                ftUnit.setChecked(ftUnit.isChecked());
                Toast.makeText(this,"FT selected",Toast.LENGTH_LONG).show();
                if(list.get(1).contains("cm")){
                    double newHeight = height/30.48;
                    replaceHeight = new DecimalFormat("##.##").format(newHeight)+"ft";
                }

                break;
            case R.id.cmUnit:
                cmUnit.setChecked(cmUnit.isChecked());
                Toast.makeText(this,"CM selected",Toast.LENGTH_LONG).show();
                if(list.get(1).contains("ft")){
                   double newHeight = height * 30.48;
                replaceHeight = String.valueOf(Math.round(newHeight))+"cm";
                }
                break;
        }
        intent.putExtra("Height",replaceHeight);
        setResult(RESULT_OK,intent);
        Log.e("TAG",replaceHeight);
    }

    public void onWeightClick(View view) {
        double weight;
        if(list.get(0).contains("lb")){
            weight = Double.parseDouble(list.get(0).substring(0,list.get(0).lastIndexOf("l")));
        }else{
            weight = Double.parseDouble(list.get(0).substring(0,list.get(0).lastIndexOf("k")));
        }
        String replaceWeight="";

        switch (view.getId()){
            case R.id.kgUnit:
                Toast.makeText(this,"KG selected",Toast.LENGTH_LONG).show();
                if(list.get(0).contains("kg")){
                    double newWeight = weight*2.205;
                    replaceWeight = new DecimalFormat("##.##").format(newWeight)+"lb";
                }

                break;
            case R.id.lbUnit:
                Toast.makeText(this,"LB selected",Toast.LENGTH_LONG).show();
                if(list.get(0).contains("lb")){
                    double newWeight = weight/2.205;
                    replaceWeight = String.valueOf(Math.round(newWeight))+"kg";
                }
                break;
        }

        intent.putExtra("Weight",replaceWeight);
        setResult(2,intent);
        Log.e("TAG",replaceWeight);

    }
}
