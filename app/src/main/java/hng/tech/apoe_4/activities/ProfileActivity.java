package hng.tech.apoe_4.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.presenters.ProfilePresenter;
import hng.tech.apoe_4.retrofit.responses.Data;
import hng.tech.apoe_4.views.ProfileView;

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.age)
    TextView age;

    @BindView(R.id.contactEmail)
            TextView email;
    @BindView(R.id.contactName)
            TextView name;

    @BindView(R.id.nok)
            TextView nok;

    ProfilePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        presenter = new ProfilePresenter(this);
    }

    @Override
    public void beginDataFetch() {
        //Show progress bar?
    }

    @Override
    public void onSuccess(String msg) {
        //Toast a message?
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finiishProcess() {
        //Hide progress bar?
    }

    @Override
    public void updateUI(Data user) {
        Glide.with(this).load(user.getAvatar()).into(imageView);
        age.setText(user.getId());
        nok.setText(user.getFirstName());
        name.setText(user.getLastName());
    }
}
