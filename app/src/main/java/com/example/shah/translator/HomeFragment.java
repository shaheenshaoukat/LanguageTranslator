package com.example.shah.translator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.shah.translator.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    FragmentHomeBinding binding;
    String[] fromlanguage = {"From", "Albanian", "Arabic", "Belarusian", "Bangali", "Chinese"
            , "Croatian", "Danish", "Dutch", "English", "Estonian", "Filipino", "Finnish", "French",
            "Galician", "Georgian", "German", "Greek", "Haitian Creole", "Hindi", "Hungarian", "Icelandic", "Indonesian", "Irish",
            "Italian", "Japanese", "Korean", "Latvian", "Lithuanian", "Macedonian", "Malay", "Maltese", "Norwegian", "Persian", "Polish", "Portuguese",
            "Romanian", "Russian", "Slovak", "Slovenian", "Spanish", "Swahili", "Swedish", "Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese", "Welsh"
    };
    String[] tolanguage = {"To", "Albanian", "Arabic", "Belarusian", "Bangali", "Chinese"
            , "Croatian", "Danish", "Dutch", "English", "Estonian", "Filipino", "Finnish", "French",
            "Galician", "Georgian", "German", "Greek", "Haitian Creole", "Hindi", "Hungarian", "Icelandic", "Indonesian", "Irish",
            "Italian", "Japanese", "Korean", "Latvian", "Lithuanian", "Macedonian", "Malay", "Maltese", "Norwegian", "Persian", "Polish", "Portuguese",
            "Romanian", "Russian", "Slovak", "Slovenian", "Spanish", "Swahili", "Swedish", "Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese", "Welsh"
    };
    private boolean isFavorite = false;

    int fromSelectedPosition = 0;
    int toSelectedPosition = 0;
    TextToSpeech textToSpeech;
    private static final int REQUEST_PERMISSION_CODE = 1;
    int languagecode, fromlanguagecode, tolanguagecode;
    String from_spinner, to_spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Retrieve the previously selected positions from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SpinnerPrefs", Context.MODE_PRIVATE);
        fromSelectedPosition = sharedPreferences.getInt("fromSelectedPosition", 0);
        toSelectedPosition = sharedPreferences.getInt("toSelectedPosition", 0);

        binding.swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempPosition = fromSelectedPosition;
                fromSelectedPosition = toSelectedPosition;
                toSelectedPosition = tempPosition;

                // Swap the selections in the spinners
                binding.fspinner.setSelection(fromSelectedPosition);
                binding.tospinner.setSelection(toSelectedPosition);
            }
        });
        binding.fspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromlanguagecode = getlanguagecode(fromlanguage[i]);
                from_spinner = adapterView.getItemAtPosition(i).toString();
                // Update the selected position
                fromSelectedPosition = i;
                // Save the selected position in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("fromSelectedPosition", fromSelectedPosition);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter fromadapter = new ArrayAdapter(getContext(), R.layout.spinner_item, fromlanguage);
        fromadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fspinner.setAdapter(fromadapter);

        binding.tospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tolanguagecode = getlanguagecode(tolanguage[i]);
                to_spinner = adapterView.getItemAtPosition(i).toString();
                // Update the selected position
                toSelectedPosition = i;
                // Save the selected position in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("toSelectedPosition", toSelectedPosition);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter toadapter = new ArrayAdapter(getContext(), R.layout.spinner_item, tolanguage);
        toadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.tospinner.setAdapter(toadapter);


// Set the previously selected positions when the spinners are re-initialized
        binding.fspinner.setSelection(fromSelectedPosition);
        binding.tospinner.setSelection(toSelectedPosition);

        binding.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        binding.btntranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                binding.translatetv.setVisibility(View.VISIBLE);
                binding.translatetv.setText("");
                if (binding.editsource.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter text to translate", Toast.LENGTH_LONG).show();
                } else if (fromlanguagecode == 0) {
                    Toast.makeText(getContext(), "Please Select Source Language", Toast.LENGTH_LONG).show();

                } else if (tolanguagecode == 0) {
                    Toast.makeText(getContext(), "Please Select the language to make translation", Toast.LENGTH_LONG).show();

                } else {
                    translatetext(fromlanguagecode, tolanguagecode, binding.editsource.getText().toString());


                    long duration = TimeUnit.SECONDS.toMillis(5);
                    new CountDownTimer(duration, 1000) {
                        @Override
                        public void onTick(long l) {
                            String sduration = String.format(Locale.ENGLISH, "%02d:%02d"
                                    , TimeUnit.MILLISECONDS.toMinutes(1),
                                    TimeUnit.MILLISECONDS.toSeconds(1) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(1)));
                        }

                        @Override
                        public void onFinish() {


                            String text = binding.editsource.getText().toString();
                            String text2 = binding.translatetv.getText().toString();

                            if (to_spinner.equals("")) {

                            }
                            if (from_spinner.equals("")) {

                            }
                            if (text.equals("")) {

                            }
                            if (text2.equals("")) {


                            }
                            if (text2.equals("Downloading model please wait")) {

                            }
                            if (text2.equals("Translation....")) {

                            } else {



                                /*  processinsert(to_spinner, from_spinner, text, text2);
                                 */


                            }
                        }
                    }.start();

                }

            }
        });

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                // Get the text from the TextView
                CharSequence text = binding.translatetv.getText();

                // Create a SpannableString with the same text
                SpannableString spannableString = new SpannableString(text);

                // Get the current font family of the TextView
                Typeface currentFont = binding.translatetv.getTypeface();

                // Set the font for the entire SpannableString
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    spannableString.setSpan(new TypefaceSpan(currentFont), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                // Copy the SpannableString to the clipboard
                clipboardManager.setPrimaryClip(ClipData.newPlainText("Textview", spannableString));

                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        binding.mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something to translate");
                try {

                    startActivityForResult(intent, REQUEST_PERMISSION_CODE);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG);
                }

            }
        });

        return view;
    }

    private int getlanguagecode(String language) {
        int languagecode = 0;
        switch (language) {


            case "Albanian":
                languagecode = FirebaseTranslateLanguage.SQ;
                break;

            case "Arabic":
                languagecode = FirebaseTranslateLanguage.AR;
                break;
            case "Belarusian":
                languagecode = FirebaseTranslateLanguage.BE;
                break;

            case "Bangali":
                languagecode = FirebaseTranslateLanguage.BN;
                break;

            case "Chinese":
                languagecode = FirebaseTranslateLanguage.ZH;
                break;
            case "Croatian":
                languagecode = FirebaseTranslateLanguage.HR;
                break;

            case "Danish":
                languagecode = FirebaseTranslateLanguage.DA;
                break;
            case "Dutch":
                languagecode = FirebaseTranslateLanguage.NL;
                break;
            case "English":
                languagecode = FirebaseTranslateLanguage.EN;
                break;
            case "Estonian":
                languagecode = FirebaseTranslateLanguage.ET;
                break;
            case "Filipino":
                languagecode = FirebaseTranslateLanguage.TL;

                break;
            case "Finnish":
                languagecode = FirebaseTranslateLanguage.FI;
                break;
            case "French":
                languagecode = FirebaseTranslateLanguage.FR;
                break;
            case "Galician":
                languagecode = FirebaseTranslateLanguage.GL;
                break;
            case "Georgian":
                languagecode = FirebaseTranslateLanguage.KA;
                break;
            case "German":
                languagecode = FirebaseTranslateLanguage.DE;
                break;
            case "Greek":
                languagecode = FirebaseTranslateLanguage.EL;
                break;
            case "Haitian Creole":
                languagecode = FirebaseTranslateLanguage.HT;
                break;
            case "Hindi":
                languagecode = FirebaseTranslateLanguage.HI;
                break;
            case "Hungarian":
                languagecode = FirebaseTranslateLanguage.HU;
                break;
            case "Icelandic":
                languagecode = FirebaseTranslateLanguage.IS;
                break;
            case "Indonesian":
                languagecode = FirebaseTranslateLanguage.ID;
                break;
            case "Irish":
                languagecode = FirebaseTranslateLanguage.GA;
                break;

            case "Italian":
                languagecode = FirebaseTranslateLanguage.IT;
                break;

            case "Japanese":
                languagecode = FirebaseTranslateLanguage.JA;
                break;
            case "Korean":
                languagecode = FirebaseTranslateLanguage.KO;
                break;
            case "Latvian":
                languagecode = FirebaseTranslateLanguage.LV;
                break;
            case "Lithuanian":
                languagecode = FirebaseTranslateLanguage.LT;
                break;
            case "Macedonian":
                languagecode = FirebaseTranslateLanguage.MK;
                break;
            case "Malay":
                languagecode = FirebaseTranslateLanguage.MS;
                break;
            case "Maltese":
                languagecode = FirebaseTranslateLanguage.MT;
                break;
            case "Norwegian":
                languagecode = FirebaseTranslateLanguage.NO;


                break;
            case "Persian":
                languagecode = FirebaseTranslateLanguage.FA;
                break;
            case "Polish":
                languagecode = FirebaseTranslateLanguage.PL;
                break;
            case "Portuguese":
                languagecode = FirebaseTranslateLanguage.PT;
                break;
            case "Romanian":
                languagecode = FirebaseTranslateLanguage.RO;
                break;
            case "Russian":
                languagecode = FirebaseTranslateLanguage.RU;
                break;
            case "Slovak":
                languagecode = FirebaseTranslateLanguage.SK;
                break;
            case "Slovenian":
                languagecode = FirebaseTranslateLanguage.SL;
                break;
            case "Spanish":
                languagecode = FirebaseTranslateLanguage.ES;
                break;
            case "Swahili":
                languagecode = FirebaseTranslateLanguage.SW;
                break;
            case "Swedish":
                languagecode = FirebaseTranslateLanguage.SV;
                break;
            case "Thai":
                languagecode = FirebaseTranslateLanguage.TH;
                break;
            case "Turkish":
                languagecode = FirebaseTranslateLanguage.TR;
                break;
            case "Ukrainian":
                languagecode = FirebaseTranslateLanguage.UK;
                break;
            case "Urdu":
                languagecode = FirebaseTranslateLanguage.UR;
                break;
            case "Vietnamese":
                languagecode = FirebaseTranslateLanguage.VI;
                break;
            case "Welsh":
                languagecode = FirebaseTranslateLanguage.CY;
                break;

            default:
                languagecode = 0;
        }
        return languagecode;


    }

    private void translatetext(int fromlanguagecode, int tolanguagecode, String source) {
        binding.translatetv.setText("Downloading model please wait");
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromlanguagecode)
                .setTargetLanguage(tolanguagecode)
                .build();
        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                binding.translatetv.setText("Translation....");
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        binding.translatetv.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to translation try again" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "failed to download model ,check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.editsource.setText(result.get(0));
        }
    }
    public void clear() {
        binding.editsource.setText("");
        binding.translatetv.setText("");
    }
}