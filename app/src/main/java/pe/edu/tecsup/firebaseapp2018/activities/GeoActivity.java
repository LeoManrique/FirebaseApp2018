package pe.edu.tecsup.firebaseapp2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.android.gms.location.places.ui.PlacePicker;

import pe.edu.tecsup.firebaseapp2018.R;

public class GeoActivity extends AppCompatActivity {

    private static String TAG = GeoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        startPlacePickerActivity();
    }
    private void startPlacePickerActivity()
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try
        {
            Intent intent = builder.build(this);
            startActivityForResult(intent, 1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data)
    {
        setResult(1,data);
        finish();
    }

}
