package com.example.shah.translator.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.shah.translator.R;
import com.example.shah.translator.databinding.FragmentChatBinding;
import com.example.shah.translator.databinding.FragmentHomeBinding;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;


public class ChatFragment extends Fragment {


    public ChatFragment() {

    }

    FragmentChatBinding binding;
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
    int fromlanguagecode, tolanguagecode;
    String from_spinner, to_spinner;

    int fromSelectedPosition = 0;
    int toSelectedPosition = 0;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.fspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterview, View view, int i, long id) {
                fromlanguagecode = getlanguagecode(fromlanguage[i]);
                from_spinner = adapterview.getItemAtPosition(i).toString();

                fromSelectedPosition = i;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("fromSelectedPosition", fromSelectedPosition);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter fromadapter = new ArrayAdapter(getContext(), R.layout.spinner_item, fromlanguage);
        fromadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fspinner.setAdapter(fromadapter);
        binding.tospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                tolanguagecode = getlanguagecode(fromlanguage[i]);
                to_spinner = adapterView.getItemAtPosition(i).toString();
                fromSelectedPosition = i;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("toSelectedPosition", toSelectedPosition);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter toadapter = new ArrayAdapter(getContext(), R.layout.spinner_item, tolanguage);
        fromadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.tospinner.setAdapter(toadapter);


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

}