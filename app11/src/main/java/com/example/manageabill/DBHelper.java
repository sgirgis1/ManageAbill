package com.example.manageabill;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper {

    /*@*//*Override*/
            /*public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);}*/
    public static final int DATABASE_VERSION=2;
    public int user_id[];
    public static final String DBNAME = "manageAbill.db";
    public DBHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(user_id INTEGER primary key autoincrement, username TEXT, password TEXT)");
        db.execSQL("create table expense(expense_Id INTEGER primary key autoincrement, expenseName TEXT, expenseAmount TEXT, etDate TEXT, reminder TEXT, reminderTime TEXT, expenseNote TEXT)");
       /* db.execSQL("create table expense(expense_Id INTEGER primary key autoincrement, user_id INTEGER not null, expenseName, expenseAmount, etDate, reminder, expenseNote )");*/
       /* db.execSQL("create table expense(expense_Id INTEGER primary key autoincrement, user_id INTEGER , expenseName, expenseAmount, etDate, reminder, expenseNote, foreign key (user_id) references users(user_id))");*/
        /*db.execSQL("create table expense(expense_Id INTEGER, user_id INTEGER, expenseName, expenseAmount, etDate, reminder, expenseNote, primary key (expense_id asc, user_id), foreign key (user_id) references users(user_id))");*/
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION) {
            db.execSQL("drop table users");
            db.execSQL("drop table expense");
            onCreate(db);
        }
    }


    /*public Boolean checkExpenseName(String expenseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from expense where expenseName=?", new String[] {expenseName});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }*/

   /* public Boolean checkRemainingFields(String expenseName, String expenseAmount, String etDate, String reminder, String expenseNote ) {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from expense where expenseName=? and expenseAmount=? and etDate=? and reminder=? and expenseNote=?", new String[] {expenseName, String.valueOf(expenseAmount), String.valueOf(etDate), String.valueOf(reminder),expenseNote});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }*/

    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result ==-1) return false;
        else
            return true;
    }
    public Boolean insertData(String expenseName, String expenseAmount, String etDate, String reminder, String reminderTime, String expenseNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        /*values.put("user_id", String.valueOf(user_id));*/
        values.put("expenseName", expenseName);
        values.put("expenseAmount", String.valueOf(expenseAmount));
        values.put("etDate", etDate);
        values.put("reminder", String.valueOf(reminder));
        values.put("reminderTime", String.valueOf(reminderTime));
        values.put("expenseNote", expenseNote);

        long result = db.insert("expense", null, values);

       /* long result= db.insert("expense", null, values);*/
        if (result ==-1) return false;
        else
            return true;
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
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteData(String expenseName) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("select * from expense where expensename = ?", new String[] {expenseName});
        if (cursor.getCount() > 0) {

            long result = db.delete("expense", "expenseName=?", new String[]{expenseName});
            if (result == -1) {
                return false;
            } else return true;
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
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[] {username,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public void addReminder(String expenseName, String reminder, String reminderTime) {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("expenseAmount", expenseName);                                                          //Inserts  data into sqllite database
        contentValues.put("reminder", reminder);
        contentValues.put("reminderTime", reminderTime);

    }


    /*public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[] {username,password});
        user_id = new int[cursor.getCount()];
        int i =0;

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
               user_id[i] = cursor.getInt(Integer.parseInt("user_id"));
               i++;
            } while (cursor.moveToNext());
            return true;
        } else
            return false;
    }*/

}
