package com.example.uzivatel.currcon;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Menu myMenu;
/*


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_calc_from_czk:
                    mTextMessage.setText(R.string.title_calc_from_czk);
                    return true;
                case R.id.navigation_calc_to_czk:
                    mTextMessage.setText(R.string.title_calc_to_czk);
                    return true;
            }
            return false;
        }
    };
*/
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;

    ArrayList<Mena> meny = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        //adapter = new ArrayAdapter<String>(this, R.layout.listview_currencies, listItems);
        //setListAdapter(adapter);

        listView = findViewById(R.id.listview);

        listView.setAdapter(adapter);

        mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        myMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.navigation_calc_from_czk:
                //Toast.makeText(this, "You have selected Bookmark Menu", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, CalcFromCZK.class);
                startActivity(intent);
                //return true;
                break;

            case R.id.navigation_calc_to_czk:
                //Toast.makeText(this, "You have selected Bookmark Menu", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, CalcToCZK.class);
                startActivity(intent);
                //return true;
                break;
        }
        return true;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public void download(View v)
{

    new Downloadxml().execute(getApplicationContext());
}

    public void showCurrencies() {

        meny = Helper.getCurrencies();
        adapter.clear();

        for (int i = 0; i < meny.size(); i++) {
            adapter.add(meny.get(i).toString());
        }

        //adapter.add("");
        /*

        //currencies = curr2;


        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            // auto-detect the encoding from the stream
            //stream = new URL(rssFeed).openConnection().getInputStream();
            File file = new File(getApplicationContext().getFilesDir(), "kurzy.xml");
            //Stream stream =  Files.lines(Paths.get(file.getAbsolutePath()));

            File initialFile = new File(file.getAbsolutePath());
            stream = new FileInputStream(initialFile);
            parser.setInput(stream, null);

            int eventType = parser.getEventType();
            boolean done = false;
            //RssItem item = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        meny.clear();
                        listItems.clear();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();

                        if (name.equalsIgnoreCase("radek")) {

                            Mena mena = new Mena();
                            mena.setKod(parser.getAttributeValue(0));
                            mena.setMena(parser.getAttributeValue(1));
                            mena.setMnozstvo(Integer.parseInt(parser.getAttributeValue(2)));
                            mena.setKurz(Double.parseDouble(parser.getAttributeValue(3).replace(",", ".")));
                            mena.setKrajina(parser.getAttributeValue(4));

                            //meny.add(mena);

                            listItems.add(mena.toString());

                            Log.i("tag:", name);
                            Log.i("atribut:", parser.getAttributeValue(0));
                        }


                        break;

                }
                eventType = parser.next();
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //return list;
        */

        //adapter.add("");


    }

    public class Downloadxml extends AsyncTask<Context, String, String> {
        @Override

        protected String doInBackground(Context... contexts) {
            try {
                URL url = new URL("http://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");

                urlConnection.setDoOutput(true);

                urlConnection.connect();

                File SDCardRoot = new File("/sdcard/"+"CurrCon");

                File file = new File(contexts[0].getFilesDir(),"kurzy.xml");

                FileOutputStream fileOutput = new FileOutputStream(file);

                InputStream inputStream = urlConnection.getInputStream();

                int totalSize = urlConnection.getContentLength();

                int downloadedSize = 0;

                byte[] buffer = new byte[1024];

                int bufferLength = 0;

                while ( (bufferLength = inputStream.read(buffer)) > 0 )

                {

                    fileOutput.write(buffer, 0, bufferLength);

                    downloadedSize += bufferLength;

                    int progress=(int)(downloadedSize*100/totalSize);

                }
                fileOutput.close();




            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            myMenu.findItem(R.id.navigation_calc_from_czk).setEnabled(true);
            myMenu.findItem(R.id.navigation_calc_to_czk).setEnabled(true);
            showCurrencies();
        }
    }


}
