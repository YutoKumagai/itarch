package com.example.yutokumagai.myservicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    EditText editA;
    EditText editB;
    TextView text;

    private IMyAidlInterface myServiceIf;
    private ServiceConnection ServiceConn = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceIf = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myServiceIf = null;
        }
    };


        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        Log.i("i","onCreate");

        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.setAction(IMyAidlInterface.class.getName());
        intent.setPackage("com.example.yutokumagai.myservicetest");
        bindService(
                intent,
                ServiceConn,
                Context.BIND_AUTO_CREATE
        );

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startService(new Intent(getBaseContext(),MyService.class));

                try {
                    int a = Integer.parseInt(editA.getText().toString());
                    int b = Integer.parseInt(editB.getText().toString());
                    int sum = myServiceIf.add(a, b);
                    Log.i("i", String.valueOf(sum));
                    text.setText(String.valueOf(sum));

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                catch(NumberFormatException e){
                    text.setText("数値を入力してください");
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(ServiceConn);
        }


    protected void findViews(){
        startButton = (Button)findViewById(R.id.startButton);
        editA = (EditText)findViewById(R.id.editText1);
        editB = (EditText)findViewById(R.id.editText2);
        text = (TextView)findViewById(R.id.sumText);
    }

}
