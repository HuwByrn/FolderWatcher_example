package com.plus6.handler;


import java.nio.file.WatchEvent;

public interface WatchEventHandler {

    void onCreate(WatchEvent e );
    void onModify(WatchEvent e );
    void onDelete(WatchEvent e );
}
