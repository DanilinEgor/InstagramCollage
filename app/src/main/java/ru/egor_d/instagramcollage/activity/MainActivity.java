package ru.egor_d.instagramcollage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.egor_d.instagramcollage.R;
import ru.egor_d.instagramcollage.api.API;


public class MainActivity extends ActionBarActivity {
    EditText mUsernameEditText;
    Button mMakeButton;
    ProgressBar mProgressBar;
    public final static String USER_ID = "userID";

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String userID = (String) msg.obj;
            if (userID.equals("")) {
                Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, ChoosePhotosActivity.class);
                intent.putExtra(USER_ID, userID);
                startActivity(intent);
            }
            mMakeButton.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mMakeButton.setEnabled(s.length() != 0);
            }
        });

        mMakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMakeButton.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                new API(mHandler).getUserID(mUsernameEditText.getText().toString().trim());
            }
        });

    }

    private void initViews() {
        mUsernameEditText = (EditText) findViewById(R.id.username_editText);
        mMakeButton = (Button) findViewById(R.id.make_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}
