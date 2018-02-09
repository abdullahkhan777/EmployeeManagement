package com.example.android.employeeadapter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManageEmployeeActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String rid;
    private EditText txtFirstName, txtLastName;
    private Employee selectedEmployee;
    private Button deleteButton;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);

        dbHelper = new DatabaseHelper(ManageEmployeeActivity.this);
        deleteButton = (Button)findViewById(R.id.delete_employee_button);
        editButton = (Button)findViewById(R.id.edit_employee_button);

        //Getting intent and finding Id of the selected employee
            Intent i = getIntent();
            String employeeId = i.getStringExtra(DatabaseContract.Employees._ID);
            selectedEmployee  = getEmployeeById(employeeId);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase  db = dbHelper.getWritableDatabase();

                //Define where part of query
                String whereClause = DatabaseContract.Employees._ID+"=?";

                //Specify arguments in placeholder order
                String [] whereArgs = {String.valueOf(selectedEmployee.getId())};

                //Issue SQL statement
//                int delete (String table, String whereClause, String[] whereArgs)
//                int: the number of rows affected if a whereClause is passed in, 0 otherwise.
                int result = db.delete(DatabaseContract.Employees.TABLE_NAME, whereClause,whereArgs);
                if(result > 0){
                    Toast.makeText(ManageEmployeeActivity.this, "Record Deleted",Toast.LENGTH_SHORT).show();
                    //Returns to onActivityResult() in MainActivity
                    finish();
                }
                db.close();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase  db = dbHelper.getWritableDatabase();

                //Create a new map of values, where column names are the keys
//                This class is used to store a set of key value pairs
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.Employees.COL_FIRSTNAME, txtFirstName.getText().toString());
                values.put(DatabaseContract.Employees.COL_SECONDNAME, txtLastName.getText().toString());

                //Define where part of query
                String whereClause = DatabaseContract.Employees._ID+"=?";

                //Specify arguments in placeholder order
                String [] whereArgs = {String.valueOf(selectedEmployee.getId())};

//                int update (String table, ContentValues values, String whereClause, String[] whereArgs)
//                int: 	the number of rows affected
                int result = db.update(DatabaseContract.Employees.TABLE_NAME, values, whereClause, whereArgs);
                if(result>0){
                    Toast.makeText(ManageEmployeeActivity.this, "Record Updated",Toast.LENGTH_SHORT).show();
                    //Returns to onActivityResult() in MainActivity
                    finish();
                }
                db.close();
            }
        });
            }

    private Employee getEmployeeById(String empId){

        txtFirstName = (EditText)findViewById(R.id.first_name_edit_text);
        txtLastName = (EditText)findViewById(R.id.last_name_edit_text);

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String [] columns = {DatabaseContract.Employees._ID,
                DatabaseContract.Employees.COL_FIRSTNAME, DatabaseContract.Employees.COL_SECONDNAME};

//        query(String table, String[] columns, String selection, String[] selectionArgs,
//        String groupBy, String having, String orderBy, String limit)
//        Query the given table, returning a Cursor over the result set.
//        Cursor is an interface that provides random read-write access to the result set returned by a database query.

        Cursor c = sqLiteDatabase.query(DatabaseContract.Employees.TABLE_NAME, columns,
                DatabaseContract.Employees._ID +"=?", new String[]{empId},null, null, null, null);
        if(c!=null){
            c.moveToFirst();
            selectedEmployee = new Employee();

            selectedEmployee.setId(c.getLong(0));
            selectedEmployee.setfName(c.getString(1));
            selectedEmployee.setlName(c.getString(2));

            txtFirstName.setText(selectedEmployee.getfName());
            txtLastName.setText(selectedEmployee.getlName());
        }
        c.close();
        sqLiteDatabase.close();
        return selectedEmployee;
    }

}