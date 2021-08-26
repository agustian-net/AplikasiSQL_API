package vsga.mobile.aplikasicrud_api;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditdanHapus extends AppCompatActivity implements View.OnClickListener {
    private EditText editTxtId;
    private EditText editTxtName;
    private EditText editTxtPosisi;
    private EditText editTxtGaji;

    private Button btnUbah;
    private Button btnHapus;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("DATA PEGAWAI");
        setContentView(R.layout.activity_editdandeletepgw);

        Intent intent = getIntent();

        id = intent.getStringExtra(Configuration.KEY_EMP_ID);

        editTxtId =  findViewById(R.id.editId);
        editTxtName =  findViewById(R.id.editNamaPgw);
        editTxtPosisi =  findViewById(R.id.editPosisi);
        editTxtGaji =  findViewById(R.id.editGaji);
        btnUbah = findViewById(R.id.btnUbahpgw);
        btnHapus = findViewById(R.id.btnHapuspgw);

        btnUbah.setOnClickListener(this);
        btnHapus.setOnClickListener(this);

        editTxtId.setText(id);

        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditdanHapus.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Configuration.URL_GET_EMP,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Configuration.TAG_NAMA);
            String posisi = c.getString(Configuration.TAG_POSISI);
            String gaji = c.getString(Configuration.TAG_GAJI);

            editTxtName.setText(name);
            editTxtPosisi.setText(posisi);
            editTxtGaji.setText(gaji);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(){
        final String name = editTxtName.getText().toString().trim();
        final String posisi = editTxtPosisi.getText().toString().trim();
        final String gaji = editTxtGaji.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditdanHapus.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditdanHapus.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Configuration.KEY_EMP_ID,id);
                hashMap.put(Configuration.KEY_EMP_NAMA,name);
                hashMap.put(Configuration.KEY_EMP_POSISI,posisi);
                hashMap.put(Configuration.KEY_EMP_GAJI,gaji);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Configuration.URL_UPDATE_EMP,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditdanHapus.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditdanHapus.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Configuration.URL_DELETE_EMP,id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Pegawai ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                (arg0, arg1) -> {
                    deleteEmployee();
                    startActivity(new Intent(EditdanHapus.this, LihatSemuaPgw.class));
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onClick(View v) {
        if(v == btnUbah){
            updateEmployee();
        }

        if(v == btnHapus){
            confirmDeleteEmployee();
        }
    }
}
