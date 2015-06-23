package com.example.zhenkondrat.brainringapp;

/**
 * Created by zhEnkondrat on 23.06.2015.
 */

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DBHelper {
    private SQLiteDatabase db = null;
    public DBHelper() {
        try {
            //db = new SQLiteDatabase(null);
        } catch (SQLException e) {
            e.printStackTrace();
            db = SQLiteDatabase.openDatabase("/sdcard/data/Comerce.db", null, SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READWRITE);
        }

    }

    public void Exec(String str)
    {
        db.execSQL(str);
    }

    public String getOnePole(String s, int nameColIndex)
    {
        Cursor c = db.rawQuery(s, null);
        c.moveToFirst();
        //int nameColIndex = c.getColumnIndex("FIO");
        String x = c.getString(nameColIndex);
        c.close();
        return x;
    }

    public String getData(String s)
    {
        Cursor c = db.query("client", null, null, null, null, null, null);
        c.moveToFirst();
        int nameColIndex = c.getColumnIndex("FIO");
        String x = c.getString(nameColIndex);
        c.close();
        return x;
    }

    public String getData(int index)
    {
        Cursor c = db.query("client", null, null, null, null, null, null);
        c.moveToPosition(index);
        int nameColIndex = c.getColumnIndex("FIO");
        String x = c.getString(nameColIndex);
        c.close();
        return x;
    }

    public ArrayList<String> getColumn(String s)
    {
        ArrayList<String> arr = new ArrayList<String>();

        Cursor c = db.query("workers", null, null, null, null, null, null);
        c.moveToFirst();
        int nameColIndex = c.getColumnIndex("FIO");
        while (c.getPosition()!=c.getCount())
        {
            String x = c.getString(nameColIndex);
            arr.add(x);
            c.moveToNext();
        }
        c.close();

        return arr;
    }

    public Bitmap getImg(int index)
    {
        try{
            Cursor c = db.query("product", null, null, null, null, null, null);
            //Cursor c=db.rawQuery("SELECT * FROM temp",null);
            //c.moveToFirst();
            c.moveToPosition(index);
            int nameColIndex = c.getColumnIndex("Picture");
            byte[] x = c.getBlob(nameColIndex);
            c.close();

            Bitmap image = BitmapFactory.decodeByteArray(x , 0, x.length);
            return image;
        }
        catch(Exception x)
        {
            return  BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.logo);
        }
    }

    public ArrayList<String> getQuery(String query)
    {
        ArrayList<String> arr = new ArrayList<String>();

        Cursor c = db.query(query, null, null, null, null, null, null);
        //c.moveToPosition(index);
        int i=0;

        //int nameColIndex = c.getColumnIndex("Count");
        while (i<=6)
        {
            String x;
            try{
                x =  c.getString(i);
            }
            catch(Exception e)
            {
                x="";
            }

            arr.add(x);
            i++;
        }
        c.close();

        return arr;
    }

    public ArrayList<String> getRow(String table, int index)
    {
        ArrayList<String> arr = new ArrayList<String>();

        Cursor c = db.query(table, null, null, null, null, null, null);
        c.moveToPosition(index);
        int i=0;

        //int nameColIndex = c.getColumnIndex("Count");
        while (i<=6)
        {
            String x;
            try{
                x =  c.getString(i);
            }
            catch(Exception e)
            {
                x="";
            }

            arr.add(x);
            i++;
        }
        c.close();

        return arr;
    }

    public int getRowCount(String table)
    {
        Cursor c = db.query(table, null, null, null, null, null, null);
        return c.getCount();
    }

    public ArrayList<String> getQuerys(String querry,int nameColIndex)
    {
        ArrayList<String> arr = new ArrayList<String>();
        Cursor c = db.rawQuery(querry, null);
        c.moveToFirst();
        //int nameColIndex = c.getColumnIndex("FIO");
        while (c.getPosition()!=c.getCount())
        {
            String x = c.getString(nameColIndex);
            arr.add(x);
            c.moveToNext();
        }
        c.close();

        return arr;
    }

    public Boolean exsistWorker(String pib)
    {
        Cursor c=db.rawQuery("SELECT * FROM workers where FIO='"+pib+"'",null);
        return c.getCount()>0;
    }
    //Bitmap bmp = intent.getExtras().get("data");
    //ByteArrayOutputStream stream = new ByteArrayOutputStream();
    //bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
    //byte[] byteArray = stream.toByteArray();

    public void finalize() {
        db.close();
    }
}