package com.example.farhan.testlinkedlist;

import android.util.Log;

/**
 * Created by Farhan on 2/28/2018.
 */

public class CustomLinkedList {

    Node head;

    public void insert(int data) {
        Node node = new Node();
        node.data = data;
        node.next = null;

        if (head == null) {
            head = node;
        } else {
            Node nNode = head;
            while (nNode.next != null) {
                nNode = nNode.next;
            }
            nNode.next = node;
        }
    }

    public void insertAtStart(int data) {
        Node node = new Node();
        node.data = data;
        node.next = null;
        node.next = head;
        head = node;
    }

    public void remove() {
        Node n = head;
        Node prev = null;
        while (n.next != null) {
            prev = n;
            n = n.next;
        }
        if (n.next == null) {
            prev.next = n.next;
        }
    }

    public void removeAllGreatValue(int data) {

        while (head.data > data) //For deleting head
        {

            head = head.next;
            Log.e("TAG", "show: " + head.data);
        }
       /* // For middle elements..................
        Node ptr, save;
        save = head;
        ptr = head.next;
        while (ptr != null) {
            if (ptr.data > data) {
                Node next = ptr.next;
                save.next = next.next;
                ptr = next;
            } else {
                save = ptr;
                ptr = ptr.next;
            }

        }*/


    }

    public void show() {
        Node node = head;
        while (node.next != null) {
            Log.e("TAG", "show: " + node.data);

            node = node.next;
        }
        Log.e("TAG", "show: " + node.data);
    }

}
