package com.example.languagetranslate;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.mlkit.nl.translate.TranslateLanguage;

import java.util.List;

public class Translate_Fragment extends Fragment {

    public Translate_Fragment() {
        // Required empty public constructor
    }

    public static Translate_Fragment newInstance() {
        Translate_Fragment fragment=new Translate_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

    }
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull View view,@NonNull Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        final Button switchButton=view.findViewById(R.id.buttonSwitchLang);
        final ToggleButton sourceSyncButton=view.findViewById(R.id.buttonSyncSource);
        final ToggleButton targetSyncButton=view.findViewById(R.id.buttonSyncTarget);
        final TextInputEditText srcTextView=view.findViewById(R.id.sourceText);
        final TextView targetTextView=view.findViewById(R.id.targetText);
        final TextView downloadModelsTextView=view.findViewById(R.id.downloadedModels);
        final Spinner targetLangSelector=view.findViewById(R.id.targetLangSelector);
        final Spinner sourceLangSelector=view.findViewById(R.id.sourceLangSelector);

        final TranslateView viewModel= ViewModelProviders.of(this).get(TranslateView.class);
        final ArrayAdapter<TranslateView.Language> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,viewModel.getAvailableLanguages());
        sourceLangSelector.setAdapter(adapter);
        targetLangSelector.setAdapter(adapter);
        sourceLangSelector.setSelected(adapter.getPosition(new TranslateView.Language("en")));
        targetLangSelector.setSelected(adapter.getPosition(new TranslateView.Language("es")));

        sourceLangSelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                setProgressText(targetTextView);
                viewModel.sourceLang.setValue((TranslateLanguage.Language) adapter.getItem(i));
            }

            public void onNothingSelected(AdapterView<?> parent){
                targetTextView.setText("");
            }
        });

        targetLangSelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                setProgressText(targetTextView);
                viewModel.targetLang.setValue((TranslateLanguage.Language) adapter.getItem(i));
            }

            public void onNothingSelected(AdapterView<?> parent){
                targetTextView.setText("");
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressText(targetTextView);
                int sourceLangPosition=sourceLangSelector.getSelectedItemPosition();
                sourceLangSelector.setSelection(targetLangSelector.getSelectedItemPosition());
                targetLangSelector.setSelection(sourceLangPosition);
            }
        });

        sourceSyncButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TranslateView.Language language=adapter.getItem(sourceLangSelector.getSelectedItemPosition());
                if(b){
                    viewModel.downloadLanguage(language);
                }
                else {
                    viewModel.deleteLanguage(language);
                }
            }
        });


        targetSyncButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TranslateView.Language language=adapter.getItem(targetLangSelector.getSelectedItemPosition());
                if(b){
                    viewModel.downloadLanguage(language);
                }
                else {
                    viewModel.deleteLanguage(language);
                }
            }
        });

        srcTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setProgressText(targetTextView);
                viewModel.sourceText.postValue(s.toString());

            }
        });

        viewModel.translatedText.observe(getViewLifecycleOwner(), new Observer<TranslateView.ResultOrError>() {
            @Override
            public void onChanged(TranslateView.ResultOrError resultOrError) {
                if(resultOrError.error !=null){
                    srcTextView.setError(resultOrError.error.getLocalizedMessage());
                }
                else {
                    targetTextView.setText(resultOrError.result);
                }
            }
        });


        viewModel.availableModels.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> translateRemoteModels) {
                String output=getContext().getString(R.string.downloaded_model_label);
                downloadModelsTextView.setText(output);
                sourceSyncButton.setChecked(translateRemoteModels.contains(adapter.getItem(sourceLangSelector.getSelectedItemPosition()).getCode()));
                targetSyncButton.setChecked(translateRemoteModels.contains(adapter.getItem(targetLangSelector.getSelectedItemPosition()).getCode()));
            }
        });
        return view;
    }

    private void setProgressText(TextView tv){
        tv.setText(getContext().getString(R.string.translate_progress));
    }


}