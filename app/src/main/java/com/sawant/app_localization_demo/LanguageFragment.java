package com.sawant.app_localization_demo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageFragment extends DialogFragment implements View.OnClickListener {


    //@formatter:off
    @BindView(R.id.rg_app_lang)         RadioGroup languageRadioGroup;
    @BindView(R.id.rb_english)          RadioButton radioButtonEnglish;
    @BindView(R.id.rb_marathi)          RadioButton radioButtonMarathi;
    @BindView(R.id.rb_hindi)            RadioButton radioButtonHindi;
    @BindView(R.id.tv_language_cancel)  TextView textViewCancle;
    @BindView(R.id.tv_language_ok)      TextView textViewOk;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    //@formatter:on

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_language, container, false);
        ButterKnife.bind(this, rootView);
        initializeResources();
        setSelectedLanguage();
        return rootView;
    }

    private void setSelectedLanguage() {
        String lang = prefs.getString(AppConstants.SELECTED_LANGUAGE, "");
        if (!lang.isEmpty()) {
            if (lang.equals(AppConstants.APP_MARATHI_LOCALE)) {
                radioButtonMarathi.setChecked(true);
            } else if (lang.equals(AppConstants.APP_HINDI_LOCALE)) {
                radioButtonHindi.setChecked(true);
            } else if (lang.equals(AppConstants.APP_ENGLISH_LOCALE)) {
                radioButtonEnglish.setChecked(true);
            }
        }
    }

    private void initializeResources() {
        prefs = getActivity().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        editor = prefs.edit();
        textViewCancle.setOnClickListener(this);
        textViewOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_language_ok:

                if (radioButtonMarathi.isChecked()) {
                    editor.putString("SELECTED_LANGUAGE", AppConstants.APP_MARATHI_LOCALE);
                    editor.apply();
                } else if (radioButtonEnglish.isChecked()) {
                    editor.putString("SELECTED_LANGUAGE", AppConstants.APP_ENGLISH_LOCALE);
                    editor.apply();
                } else if (radioButtonHindi.isChecked()) {
                    editor.putString("SELECTED_LANGUAGE", AppConstants.APP_HINDI_LOCALE);
                    editor.apply();
                }
                AppApplication.getInstance().updateBaseContextLocale(getActivity());
                refreshApp();
                break;
            case R.id.tv_language_cancel:
                dismiss();
                break;
        }
    }


    private void refreshApp() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
