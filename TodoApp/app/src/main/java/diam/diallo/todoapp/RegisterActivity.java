package diam.diallo.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    //Declaration des variables components
    Button btn_conn, btn_inscrip;
    EditText emailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Definition des components
        btn_conn = findViewById(R.id.connectBntRegister_id);
        btn_inscrip = findViewById(R.id.inscriptBtnRegister_id);
        emailEdit = findViewById(R.id.mailRegister_id);
        passwordEdit = findViewById(R.id.mpassRegister_id);

        //Redirection vers l'activite MainActivity
        btn_inscrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegistration();
            }
        });

        //Redirection de l'activite vers LoginActivity
        btn_conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_con = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(int_con);
            }
        });

    }


    //La methode userRegistration() qui permet d'inserer un nouvel utilisateur dans la base de donnees
    public void userRegistration(){
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApisUrls.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")){
//                        Log.d("APP", response); pour voir les reponses dans la console logcate
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        Intent int_connexion = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(int_connexion);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Si l'utilisateur n'est pas connecte a internet
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", "ERROR = "+error);
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show(); pour voir les erreurs de connexion
                Toast.makeText(getApplicationContext(), "Veuillez activer votre internet et reessayer", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}