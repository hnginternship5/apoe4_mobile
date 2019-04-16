package hng.tech.apoe_4.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);



        drop.setOnClickListener(v -> animateView(toHide, drop));
        dropUnit.setOnClickListener(v -> animateView(hiddenUnits, dropUnit));
        dropFeedback.setOnClickListener(v -> animateView(hiddenFeedback, dropFeedback));
        dropAbout.setOnClickListener(v -> animateView(hiddenAbout, dropAbout));

        back.setOnClickListener(v -> super.onBackPressed());

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
}
