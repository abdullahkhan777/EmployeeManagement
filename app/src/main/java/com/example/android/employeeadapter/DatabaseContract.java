package com.example.android.employeeadapter;

import android.provider.BaseColumns;

/**
 * Created by Musharif.ahmed on 8/3/2017.
 * A final class cannot be subclassed/extended.
 */
public final class DatabaseContract {

    public DatabaseContract(){}

    public static abstract class Employees implements BaseColumns{

        public static final String TABLE_NAME = "employee";
        public static final String COL_FIRSTNAME ="firstname";
        public static final String COL_SECONDNAME = "secondname";
    }

}
