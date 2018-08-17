package com.sawant.app_localization_demo;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.button)
    Button languageSlectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        languageSlectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLanguageFragmet();
            }
        });

        AppApplication.getInstance().updateBaseContextLocale(this);

    }


    private void addLanguageFragmet() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        LanguageFragment fragment = new LanguageFragment();
        fragment.show(fragmentTransaction, "LanguageFragment");
    }
}
