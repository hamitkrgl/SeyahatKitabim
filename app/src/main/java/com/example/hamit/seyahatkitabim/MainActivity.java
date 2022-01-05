package com.example.hamit.seyahatkitabim;

import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String > isimler=new ArrayList<String>();
    static ArrayList<LatLng> yerler=new ArrayList<LatLng>();
    static ArrayAdapter arrayAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.yer_ekle,menu);


        return super.onCreateOptionsMenu(menu);
    }


        //menüye tıklandığında ne olacağıyla ilgili yapılan işlemleri
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId()==R.id.yer_ekle)
            {
                Intent ıntent=new Intent(getApplicationContext(),MapsActivity.class);
                ıntent.putExtra("bilgi","yeni");
                startActivity(ıntent);

            }
            return super.onOptionsItemSelected(item);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ListView listView=(ListView)findViewById(R.id.listView);
            try
            {
                MapsActivity.database=this.openOrCreateDatabase("Yerler",MODE_PRIVATE,null);
                Cursor cursor= MapsActivity.database.rawQuery("SELECT * FROM yerler",null);

                int isimİndex=cursor.getColumnIndex("isim");
                int enlemİndex=cursor.getColumnIndex("enlem");
                int boylamİndex=cursor.getColumnIndex("boylam");
                //cursor.moveToFirst();
                while (cursor.moveToNext())
                {
                    String veritabanıİsim=cursor.getString(isimİndex);
                    String veritabanıEnlem=cursor.getString(enlemİndex);
                    String veritabanıBoylam=cursor.getString(boylamİndex);
                    isimler.add(veritabanıİsim);
                    Double L1=Double.parseDouble(veritabanıEnlem);
                    Double L2=Double.parseDouble(veritabanıBoylam);

                    LatLng veritabanıYerler=new LatLng(L1,L2);
                    yerler.add(veritabanıYerler);

                    System.out.println("isim :"+isimler);

                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            arrayAdapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,isimler);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent ıntent=new Intent(getApplicationContext(),MapsActivity.class);
                    ıntent.putExtra("bilgi","eski");
                    ıntent.putExtra("pozisyon",i);

                    startActivity(ıntent);
                }
            });
    }
}
