package com.example.android.employeeadapter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.employeeadapter.DatabaseContract.Employees;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Employee> emp;
    private ArrayAdapter<Employee> employeeAdpater;
    private ListView listView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //Declare emp ArrayList
        emp = new ArrayList<>();

        //Find LisView by ID
        listView = (ListView) findViewById(R.id.list_view);

        Button addButton = (Button) findViewById(R.id.add_employee);

        Button readButton = (Button)findViewById(R.id.read_employee);

        //Create an adapter for Employee
        employeeAdpater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emp);

        //Bind it with the ListView
        listView.setAdapter(employeeAdpater);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Employee e = emp.get(position);
                manageEmployee(Long.toString(e.getId()));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Find IDs of Text fields and assign them to the variables of EditText type
                EditText fNametxt = (EditText) findViewById(R.id.first_name);
                EditText lNametxt = (EditText) findViewById(R.id.last_name);

                //Read data from user entered in text fields and add it to the emp ArrayList by calling the
                //constructor of Employee class, also convert the data toString b/c getText returns
                // Char sequence and Constructor takes string as input

//                emp.add(new Employee(fNametxt.getText().toString(), lNametxt.getText().toString()));

                dbHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Employees.COL_FIRSTNAME, fNametxt.getText().toString());
                values.put(Employees.COL_SECONDNAME, lNametxt.getText().toString());

//                long insert (String table, String nullColumnHack, ContentValues values)
//                long: 	the row ID of the newly inserted row, or -1 if an error occurred
                long newRowId = sqLiteDatabase.insert(Employees.TABLE_NAME, null, values);
                if (newRowId > 0) {
                    Toast.makeText(MainActivity.this, "New Record Inserted: " + newRowId, Toast.LENGTH_SHORT).show();
                }

                sqLiteDatabase.close(); // Closing database connection

                //Clear the text fields after reading the data
                fNametxt.setText("");
                lNametxt.setText("");
//                populateList();
//                employeeAdpater.notifyDataSetChanged();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            populateList();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.details:
                //Toast.makeText(this, "Details for Employee "+ emp.get(info.position).getfName() + " "+ emp.get(info.position).getlName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, EmployeeDetails.class);
                intent.putExtra("fName",emp.get(info.position).getfName());
                intent.putExtra("lName",emp.get(info.position).getlName());
                startActivity(intent);

                break;
            case R.id.help:
                Toast.makeText(this, "Help for Employee ",Toast.LENGTH_SHORT).show();
                break;

            case R.id.delete:
                //Removes from the ListView only, not from the database
                emp.remove(info.position);
                // Notifies the attached observers that the underlying data is no longer valid or available.
                // Once invoked this adapter is no longer valid and should not report further data set changes.
                employeeAdpater.notifyDataSetChanged();
                break;

            default:
        }
        return super.onContextItemSelected(item);
    }

    public void manageEmployee(String id){
        Intent i = new Intent(this, ManageEmployeeActivity.class);
        i.putExtra(Employees._ID,String.valueOf(id));
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateList();
    }

    private void populateList(){
        //Emptying the emp ListArray
        emp.clear();

        dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String[] columns = { Employees._ID, Employees.COL_FIRSTNAME, Employees.COL_SECONDNAME };

        //String sortOrder = Employees.COL_FIRSTNAME + " ASC";
        String sortOrder = Employees._ID + " ASC";

//        query(String table, String[] columns, String selection, String[] selectionArgs,
//        String groupBy, String having, String orderBy, String limit)
//        Query the given table, returning a Cursor over the result set.
//        Cursor is an interface that provides random read-write access to the result set returned by a database query.

        Cursor c = sqLiteDatabase.query(Employees.TABLE_NAME, columns, null, null, null, null, sortOrder);

        while (c.moveToNext()) {
            Employee e = new Employee();

//            getString(int columnIndex)
//            Returns the value of the requested column as a String.
            e.setId(c.getLong(0));
            e.setfName(c.getString(1));
            e.setlName(c.getString(2));
            emp.add(e);
        }
        employeeAdpater.notifyDataSetChanged();
        c.close();
    }
}