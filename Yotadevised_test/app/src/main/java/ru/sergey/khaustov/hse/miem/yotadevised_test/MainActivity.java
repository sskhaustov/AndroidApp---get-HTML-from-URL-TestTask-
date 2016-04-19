package ru.sergey.khaustov.hse.miem.yotadevised_test;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String address = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vertical);
    }

    public void onClick(View _v) throws Exception {
        try {
            TextView _tv = (TextView) findViewById(R.id.T1);
            TextView _outp = (TextView) findViewById(R.id.TV1);
            if (_tv.getText().length() == 0) {
                Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
                return;
            }

            address = _tv.getText().toString();
            appTask _task = new appTask();
            _task.execute(address);
            _outp.setText("");
            _outp.setText(_task.get());
        }
        catch (Exception _ex){
            Toast.makeText(this, "Error: " + _ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //нужно переоперделить метод doInBackground класса AsyncTask, чтобы при команде execute он считал html-код
    class appTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params){
            StringBuilder _code = new StringBuilder();
            String _line = "";
            try{
                URL _page = new URL(params[0]);
                BufferedReader _br = new BufferedReader(new InputStreamReader(_page.openConnection().getInputStream()));
                while ((_line = _br.readLine()) != null)
                    _code.append(_line + "\n");
            }
            catch (Exception _ex){}
            return _code.toString();
        }
    }

}
