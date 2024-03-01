package ru.mirea.tepsikoevsa.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final String PROFILE_PREFS = "ProfilePrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";

    private EditText nameEditText, surnameEditText;
    private Button saveButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        surnameEditText = view.findViewById(R.id.surnameEditText);
        saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });

        loadProfileData();

        return view;
    }

    private void saveProfileData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PROFILE_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, nameEditText.getText().toString());
        editor.putString(KEY_SURNAME, surnameEditText.getText().toString());
        editor.apply();
    }

    private void loadProfileData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PROFILE_PREFS, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, "");
        String surname = sharedPreferences.getString(KEY_SURNAME, "");
        nameEditText.setText(name);
        surnameEditText.setText(surname);
    }
}
