package com.example.uzivatel.currcon;

import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Uzivatel on 28.02.2018.
 */

public class Helper {

    public static ArrayList<Mena> getCurrencies() {
        //List<RssItem> list = new ArrayList<RssItem>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        ArrayList<Mena> meny = new ArrayList<Mena>();
        try {
            // auto-detect the encoding from the stream
            //stream = new URL(rssFeed).openConnection().getInputStream();
            File file = new File(App.getAppContext().getFilesDir(), "kurzy.xml");
            //Stream stream =  Files.lines(Paths.get(file.getAbsolutePath()));

            File initialFile = new File(file.getAbsolutePath());

            if (!initialFile.exists()) {
                throw new Exception("File not loaded. Please check internet connection.");
            }
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
                        //listItems.clear();
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

                            meny.add(mena);

                            //listItems.add(mena.toString());

                            Log.i("tag:", name);
                            Log.i("atribut:", parser.getAttributeValue(0));
                        }


                        break;

                }
                eventType = parser.next();
            }



        } catch (Exception e) {
            //throw new RuntimeException(e);
            Toast.makeText(App.getAppContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
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

        return meny;
    }
}
