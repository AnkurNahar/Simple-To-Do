package com.ankur.simpletodo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {
    ArrayList<String> item;
    ArrayAdapter<String> itemadaptor;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);


        item = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        readItems();
        itemadaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
        listView.setAdapter(itemadaptor);
        setupListViewListener();



    }

    public void additem(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        itemadaptor.add(editText.getText().toString());
        editText.setText("");
        saveItems();

    }

    private void setupListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long rowId) {
                AlertDialog alertDialog = new AlertDialog.Builder(TodoActivity.this).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are You Sure?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                item.remove(position);
                                itemadaptor.notifyDataSetChanged();
                                dialog.dismiss();

                                saveItems();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


                return true;

            }
        });
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            item = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch (IOException e){
            item = new ArrayList();
            e.printStackTrace();
        }
    }



    private void saveItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try
        {
            FileUtils.writeLines(todoFile, item);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
