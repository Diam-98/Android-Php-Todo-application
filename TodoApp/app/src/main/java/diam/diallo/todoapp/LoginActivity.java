package diam.diallo.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText mail_field, mpass_field;
    Button log_btn, register_btn;
    ProgressBar progressBar;
    TextView waiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_btn = findViewById(R.id.connectBnt_id);
        register_btn = findViewById(R.id.inscriptBtn_id);
        mail_field = findViewById(R.id.mail_id);
        mpass_field = findViewById(R.id.mpass_id);
        progressBar = findViewById(R.id.ProgBar);
        waiting = findViewById(R.id.wait);

        progressBar.setVisibility(View.INVISIBLE);
        waiting.setVisibility(View.INVISIBLE);


        //redirection vers MainActivity a travers la methode userLogin()
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                waiting.setVisibility(View.VISIBLE);
                userLogin();
//                Intent i_connexion = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(i_connexion);
            }
        });

        //Redirection vers RegisterActivity()
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i_register);
            }
        });
    }

    //la methode userLogin() qui permet de verifier la connextionde l'Utilisatuer dan la base de donnees
    public void userLogin(){
        
        String logEmail = mail_field.getText().toString().trim();
        String logPass = mpass_field.getText().toString().trim();

        StringRequest logStringRequest = new StringRequest(Request.Method.POST, ApisUrls.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")){
                        Log.d("APP", response);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        String email = jsonObject.getString("mail");
                        Intent i_connexion = new Intent(LoginActivity.this, MainActivity.class);
                        i_connexion.putExtra("mail", email);
                        startActivity(i_connexion);
                        finish();
                    }else {
                        Log.d("APP", response);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            //Si l'utilisateur ne dispose pas de connexion
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", "error = "+error);
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Vous devriez disposer d'internet", Toast.LENGTH_LONG).show();
                Intent i_connexion = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i_connexion);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> logParams = new HashMap<>();
                logParams.put("email", logEmail);
                logParams.put("password", logPass);
                return logParams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(logStringRequest);
    }
}