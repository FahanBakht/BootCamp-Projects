package com.example.farhan.testlinkedlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomLinkedList list = new CustomLinkedList();

        list.insert(5);
        list.insert(10);
        list.insert(12);

        //list.insertAtStart(2);

        list.removeAllGreatValue(2);

       // list.remove();

        list.show();
    }
}
