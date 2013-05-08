package com.plus6.services;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FolderWatchService {
    private WatchService watcher;
    private Map<WatchKey, Path> keys;
    public Boolean stop = false;

    public FolderWatchService() {
        keys = new HashMap<WatchKey, Path>();
    }

    public void destroy() throws IOException {

        watcher.close();
        watcher = null;

        keys.clear();
        keys = null;
    }

    public void register(Path path) {
        try {

            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            // handle
            System.err.println(e);

        }
        WatchKey key = null;
        try {
            key = path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        keys.put(key, path);
    }

    public WatchService getWatcher() {
        return watcher;
    }

    public Map<WatchKey, Path> getKeys() {
        return keys;
    }

    public void kill() {
        this.stop = true;
    }

    public void run() {
        while (false == this.stop) {

            WatchKey key;

            try {
                key = this.watcher.take();

            } catch (InterruptedException x) {
                System.err.println("WatchKey request fail");
                break;

//                return;
            }
            Path dir = keys.get(key);
            System.out.println("WatchKey request path to string " + dir.toString());
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }


        }
    }
}
