package vsga.mobile.aplikasicrud_api;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements onClick, View.OnClickListener {

    //deklarasikan variabel
    private EditText editTxtName;
    private EditText editTxtPosisi;
    private EditText editTxtGaji;
    private Button btnTambah;
    private Button btnLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisi dari view
        editTxtName = findViewById(R.id.editNamaPgw);
        editTxtPosisi = findViewById(R.id.editPosisi);
        editTxtGaji = findViewById(R.id.editGaji);
        btnTambah = findViewById(R.id.btnTambahPgw);
        btnLihat = findViewById(R.id.btnLihatpgw);

        //membuat fungsi klik listener
        btnTambah.setOnClickListener(this);
        btnLihat.setOnClickListener(this);

    }

    //Script CREATE
    private void addEmployee(){
        final String name = editTxtName.getText().toString().trim();
        final String posisi = editTxtPosisi.getText().toString().trim();
        final String gaji = editTxtGaji.getText().toString().trim();

        @SuppressLint("StaticFieldLeak")
        class AddEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_EMP_NAMA, name);
                params.put(Configuration.KEY_EMP_POSISI, posisi);
                params.put(Configuration.KEY_EMP_GAJI, gaji);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {

        if(v == btnTambah){
            System.out.println("Klik Tambah");
            addEmployee();
        }

        if(v == btnLihat){
            System.out.println("klik Lihat");
            startActivity(new Intent(this,LihatSemuaPgw.class));
        }
    }
}