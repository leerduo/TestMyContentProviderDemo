package me.chenfuduo.testmycontentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddData = (Button) findViewById(R.id.addData);
        Button btnQueryData = (Button) findViewById(R.id.queryData);
        Button btnUpdateData = (Button) findViewById(R.id.updateData);
        Button btnDeleteData = (Button) findViewById(R.id.deleteData);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://me.chenfuduo.mycontentproviderdemo.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","Beauty of math");
                values.put("author","wujun");
                values.put("pages",398);
                values.put("price",22.98);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
                Log.e("Test","newId:" + newId);
                Log.e("Test","newUri:" + newUri.toString());
            }
        });
        btnQueryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://me.chenfuduo.mycontentproviderdemo.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.e("Test","name--->" + name + "  author--->" + author+ "  pages--->" + pages + "  price--->" + price);
                    }
                    cursor.close();
                }
            }
        });

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://me.chenfuduo.mycontentproviderdemo.provider/book/" + newId);
                ContentValues values = new ContentValues();
                values.put("name","updateData");
                getContentResolver().update(uri,values,null,null);
            }
        });

        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://me.chenfuduo.mycontentproviderdemo.provider/book//" + newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
