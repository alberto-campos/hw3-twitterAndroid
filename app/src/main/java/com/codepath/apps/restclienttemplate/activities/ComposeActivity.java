package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ComposeActivity extends AppCompatActivity implements Serializable {

    private final int REQUEST_CODE = 1344;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        populateUserProfile();
    }

    private void populateUserProfile() {
        TextView tvName;
        tvName = (TextView) findViewById(R.id.tvComposeName);
        tvName.setText(getIntent().getStringExtra("username"));

        TextView tvScreenname;
        tvScreenname = (TextView) findViewById(R.id.tvCompuseScreenname);
        tvScreenname.setText("@" + getIntent().getStringExtra("screenname"));

        ImageView tvProfileImage;
        tvProfileImage = (ImageView) findViewById(R.id.ivComposeProfile);
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("url")).into(tvProfileImage);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_profile, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        return super.onOptionsItemSelected(item);
//    }

    public void onSubmit(View v) {
        Toast.makeText(getApplicationContext(), "onSubmit", Toast.LENGTH_SHORT).show();

       EditText etMessage = (EditText) findViewById(R.id.etComposeMessage);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("message", etMessage.getText().toString());
       // data.putExtra("code", 200); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    public void onCancel(View view) {
        Toast.makeText(getApplicationContext(), "Canceling", Toast.LENGTH_SHORT).show();
        setResult(RESULT_CANCELED);
        this.finish();
    }

}
