package com.example.android.employeeadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.employeeadapter.DatabaseContract.Employees;
/**
 * Created by Musharif.ahmed on 8/3/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EmployeeDB.db";

    private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + Employees.TABLE_NAME + " ("
            + Employees._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Employees.COL_FIRSTNAME + " TEXT NOT NULL, "
            + Employees.COL_SECONDNAME + " TEXT)";
//
//    private static final String ALTER_TABLE_EMPLOYEE = "ALTER TABLE "
//            + Employees.TABLE_NAME + " ADD COLUMN "
//            + Employees.COL_TELEPHONE + " TEXT";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
//        context Context: to use to open or create the database
//        name String: of the database file
//        factory SQLiteDatabase.CursorFactory: to use for creating cursor objects, or null for the default
//        version int: number of the database (starting at 1); if the database is older, onUpgrade(SQLiteDatabase, int, int) will be used to upgrade the database; if the database is newer, onDowngrade(SQLiteDatabase, int, int) will be used to downgrade the database
//        Used to allow returning sub-classes of Cursor when calling query.
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}