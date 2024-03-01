package ru.mirea.tepsikoevsa.mireaproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.yandex.mapkit.MapKitFactory;

import java.util.ArrayList;


public class LocationsFragment extends Fragment {


    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private Button buttonAdd;
    private ListView listViewPlaces;
    private ArrayList<Location> placesList;
    private ArrayAdapter<Location> adapter;
    public LocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locations, container, false);
        MapKitFactory.initialize(requireContext());

        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextLatitude = view.findViewById(R.id.editTextLatitude);
        editTextLongitude = view.findViewById(R.id.editTextLongitude);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        listViewPlaces = view.findViewById(R.id.listViewPlaces);

        editTextName.setText("РТУ-МИРЭА");
        editTextDescription.setText("Мой любимый ВУЗ");
        editTextLatitude.setText("55.794500");
        editTextLongitude.setText("37.701442");

        placesList = new ArrayList<>();
        adapter = new PlaceAdapter(getContext(), placesList);
        listViewPlaces.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                if (!name.isEmpty() && !description.isEmpty() &&
                        !editTextLatitude.getText().toString().isEmpty() &&
                        !editTextLongitude.getText().toString().isEmpty()) {
                    double latitude = Double.parseDouble(editTextLatitude.getText().toString().trim());
                    double longitude = Double.parseDouble(editTextLongitude.getText().toString().trim());

                    Location place = new Location(name, description, latitude, longitude);
                    placesList.add(place);
                    adapter.notifyDataSetChanged();
                    editTextName.getText().clear();
                    editTextDescription.getText().clear();
                    editTextLatitude.getText().clear();
                    editTextLongitude.getText().clear();
                } else {
                    Toast.makeText(getContext(), "Пожалуйста введите название, описание, широту и долготу", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listViewPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Зажмите, чтобы открыть место на карте", Toast.LENGTH_SHORT).show();
            }
        });

        listViewPlaces.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Location place = placesList.get(position);
                String uri = "geo:" + place.getLatitude() + "," + place.getLongitude() + "?q=" + place.getLatitude() + "," + place.getLongitude() + "(" + place.getName() + ")";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getContext(), "Картографических сервисов нет. Откроем в отдельном окне", Toast.LENGTH_SHORT).show();
                    // Создаем Intent для перехода к MapActivity
                    Intent intent = new Intent(getContext(), MapActivity.class);
                    // Передаем координаты в Intent
                    intent.putExtra("latitude", place.getLatitude());
                    intent.putExtra("longitude", place.getLongitude());
                    startActivity(intent); // Запускаем MapActivity
                }

                return true;
            }
        });

        return view;
    }


}
