package diam.diallo.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static diam.diallo.todoapp.R.menu.xml_menu;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addButton;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TodoViewModel todoViewModel;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView position, temperature;
    Switch swincthing, switchTemp;
    TextView textView;

    Button deletBtn, updateBtn;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addBtn_id);
        recyclerView = findViewById(R.id.recycler_id);
        deletBtn = findViewById(R.id.deletBtn_id);
        updateBtn = findViewById(R.id.updateId);
        toolbar = findViewById(R.id.tool_id);
        position = findViewById(R.id.location);
        temperature = findViewById(R.id.tamper);
        swincthing = findViewById(R.id.switchLoc);
        switchTemp = findViewById(R.id.switchTemp);
        textView = findViewById(R.id.UserMail);


        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTask();
            }
        });

        position.setText("");
        temperature.setText("");

        //Localosation et affichage de localite
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        swincthing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swincthing.isChecked()){
                    swincthing.setText("Non ?");
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location != null){
                                    try {
                                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        Log.d("Longitude", "as = "+location.getLongitude());
                                        position.setText("" +addresses.get(0).getLocality());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    }
                }else {
                    swincthing.setText("Oui ?");
                    position.setText("");
                    position.setBackgroundResource(R.drawable.ic_location);
                }
            }
        });

        switchTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchTemp.isChecked()){
                    switchTemp.setText("Oui ?");
                    //code pour recuperer et afficher la temperature


                }else {
                    switchTemp.setText("Non ?");
                    temperature.setText("");
                    temperature.setBackgroundResource(R.drawable.ic_sunny);
                }
            }
        });

        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TodoViewModel.class);
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                adapter.setTodos(todos);
            }
        });
        getIntent();
        Intent intent = new Intent();
        String mail = intent.getStringExtra("mail");
        textView.setText(mail);
    }


    public void dialogTask(){

        TaskDialog taskDialog = new TaskDialog();
        taskDialog.show(getSupportFragmentManager(), "fragmentTask");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dec:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Deconnexion");
                alertBuilder.setMessage("Voulez-vous vous deconnecter ?");
                alertBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent int_deconnect = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(int_deconnect);
                        finish();
                    }
                }).create();
                alertBuilder.show();
                break;
            case R.id.quit:
                AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(MainActivity.this);
                alertBuilder1.setTitle("Quitter");
                alertBuilder1.setMessage("Voulez-vous quitter l'application ?");
                alertBuilder1.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
                alertBuilder1.show();
                break;
        }
        return true;
    }
}