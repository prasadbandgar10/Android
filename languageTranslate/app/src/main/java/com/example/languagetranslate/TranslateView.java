package com.example.languagetranslate;

import android.app.Application;
import android.media.MediaPlayer;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.security.cert.PKIXRevocationChecker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class TranslateView extends AndroidViewModel {
    private static final int NUM_TRANSLATORS = 3;

    private RemoteModelManager modelManager;
    private final LruCache<TranslatorOptions, Translator> translators = new
            LruCache<TranslatorOptions, Translator>(NUM_TRANSLATORS) {

                protected Translator create(TranslatorOptions Key) {
                    return Translation.getClient(Key);
                }

                @Override
                protected void entryRemoved(boolean evicted, TranslatorOptions key, Translator oldValue, Translator newValue) {
                    super.entryRemoved(evicted, key, oldValue, newValue);
                    oldValue.close();
                }
            };
    MutableLiveData<TranslateLanguage.Language> sourceLang =new MutableLiveData<>();
    MutableLiveData<TranslateLanguage.Language> targetLang =new MutableLiveData<>();
    MutableLiveData<String> sourceText=new MutableLiveData<>();
    MediatorLiveData<ResultOrError> translatedText=new MediatorLiveData<ResultOrError>();
    MutableLiveData<List<String>> availableModels=new MutableLiveData<>();

    public TranslateView(@NonNull Application application) {
        super(application);
    }

    public void TranslateViewModel(@NonNull Application application){
        //super(Objects.requireNonNull(application));
        modelManager=RemoteModelManager.getInstance();

        final OnCompleteListener<String> processTranslation=new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    translatedText.setValue(new ResultOrError(task.getResult(),null));
                }
                else {
                    translatedText.setValue(new ResultOrError(null,task.getException()));
                }
                fetchDownloadModels();
            }

        };

        translatedText.addSource(sourceText, new Observer() {


            @Override
            public void update(Observable o, Object arg) {

            }

            @Override
            public void onChanged(String s) {
                translate().addOnCompleteListener(processTranslation);

            }
        });

        androidx.lifecycle.Observer<Language> languageObserver=new androidx.lifecycle.Observer<Language>() {
            @Override
            public void onChanged(Language language) {
                translate().addOnCompleteListener(processTranslation);
            }
        };
        translatedText.addSource(targetLang,languageObserver);
        translatedText.addSource(targetLang,languageObserver);

        fetchDownloadModels();
    }

    List<Language> getAvailableLanguages(){
        List<Language> languages=new ArrayList<>();
        List<String> languageIds=TranslateLanguage.getAllLanguages();
        for(String languageId: languageIds){
            languages.add(new Language(TranslateLanguage.fromLanguageTag(languageId)));
        }
        return languages;
    }

    private  TranslateRemoteModel getModel(String languageCode){
        return new TranslateRemoteModel(languageCode);
    }

    void downloadLanguage(Language language){
        TranslateRemoteModel model=getModel(TranslateLanguage.fromLanguageTag(language.getCode()));
        modelManager.download(model,new DownloadConditions().Builder().build()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fetchDownloadModels();
            }
        });
    }

    void deleteLanguage(Language language){
        TranslateRemoteModel model=getModel(TranslateLanguage.fromLanguageTag(language.getCode()));
        modelManager.deleteDownloadedModel(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fetchDownloadModels();
            }
        });
    }



    public Task<String> translate(){
        final String text=sourceText.getValue();
        final Language source= (Language) sourceLang.getValue();
        final Language target= (Language) targetLang.getValue();
        if(source==null || target==null || text==null || text.isEmpty()){
            return Tasks.forResult("");
        }
        String sourceLangCode=TranslateLanguage.fromLanguageTag(source.getCode());
        String targetLangCode=TranslateLanguage.fromLanguageTag(target.getCode());
        TranslatorOptions options=new TranslatorOptions.Builder().setSourceLanguage(sourceLangCode).setTargetLanguage(targetLangCode).build();
        return translators.get(options).downloadModelIfNeeded().continueWithTask(new Continuation<Void, Task<String>>() {
            @Override
            public Task<String> then(@NonNull Task<Void> task) throws Exception {
                if(task.isSuccessful()){
                    return translators.get(options).translate(text);
                }
                else {
                    Exception e=task.getException();
                    if(e==null)
                    {
                        e=new Exception(getApplication().getString(R.string.common_google_play_services_unknown_issue));
                    }
                    return Tasks.forException(e);
                }
            }
        });
    }

    private void fetchDownloadModels(){
        modelManager.getDownloadedModels(TranslateRemoteModel.class).addOnSuccessListener(new OnSuccessListener<Set<TranslateRemoteModel>>() {
            @Override
            public void onSuccess(Set<TranslateRemoteModel> translateRemoteModels) {
                List<String> modelCodes =new ArrayList<String>(TranslateRemoteModel.);
                for(TranslateRemoteModel model: translateRemoteModels){
                    modelCodes.add(model.getLanguage());
                }
                Collections.sort(modelCodes);
                availableModels.setValue(modelCodes);
            }
        });
    }

    static class ResultOrError<result, error> {
        final @NonNull
        String result;
        final @NonNull
        Exception error;

        ResultOrError(@NonNull String result, @NonNull Exception error) {
            this.error = error;
            this.result = result;
        }
    }

    static class Language implements Comparable<Language>{
        private String code;
        public Language(String code)
        {
            this.code=code;
        }
        String getDisplayName()
        {
            return new Locale(code).getDisplayName();
        }
        String getCode(){
            return code;
        }
        public boolean equals(Object o){
            if(o==this)
            {
                return true;
            }
            if(!(o instanceof Language))
            {
                return false;
            }
            Language otherLang=(Language) o;
            return otherLang.code.equals(code);
        }

        @Override
        public String toString() {
            return code+"  "+getDisplayName();
        }

        @Override
        public int hashCode() {
            return code.hashCode();
        }

        @Override
        public int compareTo(Language o) {
            return this.getDisplayName().compareTo(o.getDisplayName());
        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        translators.evictAll();
    }
}