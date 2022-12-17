package com.example.manageabill;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=6;
    public static final String DBNAME = "manageAbill.db";
    public DBHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(user_id INTEGER primary key autoincrement, username TEXT, password TEXT)");
        db.execSQL("create table expense(expense_Id INTEGER primary key autoincrement, expenseName TEXT, expenseAmount TEXT, etDate TEXT, reminder TEXT, reminderTime TEXT, expenseNote TEXT)");
          }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION) {
            db.execSQL("drop table users");
            db.execSQL("drop table expense");
            onCreate(db);
        }
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        return result != -1;
    }
    public Boolean insertData(String expenseName, String expenseAmount, String etDate, String reminder, String reminderTime, String expenseNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("expenseName", expenseName);
        values.put("expenseAmount", String.valueOf(expenseAmount));
        values.put("etDate", etDate);
        values.put("reminder", String.valueOf(reminder));
        values.put("reminderTime", String.valueOf(reminderTime));
        values.put("expenseNote", expenseNote);

        long result = db.insert("expense", null, values);

        return result != -1;
    }

    public Boolean updateData(String expenseName, String expenseAmount, String etDate, String reminder, String reminderTime, String expenseNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("expenseAmount", String.valueOf(expenseAmount));
        values.put("etDate", etDate);
        values.put("reminder", String.valueOf(reminder));
        values.put("reminderTime", reminderTime);
        values.put("expenseNote", expenseNote);
        Cursor cursor = db.rawQuery("select * from expense where expenseName=?", new String[] {expenseName});
        if (cursor.getCount() > 0) {

            long result = db.update("expense", values, "expenseName=?", new String[]{expenseName});
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean deleteData(String expenseName) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from expense where expensename = ?", new String[] {expenseName});
        if (cursor.getCount() > 0) {
            long result = db.delete("expense", "expenseName=?", new String[]{expenseName});
            return result != -1;
        } else return false;
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from expense", null);
       return cursor;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});
        return cursor.getCount() > 0;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[] {username,password});
        return cursor.getCount() > 0;
    }




}
