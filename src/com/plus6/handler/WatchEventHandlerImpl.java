package com.plus6.handler;

import com.sun.tools.corba.se.idl.constExpr.Terminal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class WatchEventHandlerImpl implements WatchEventHandler {

    @Override
    public void onCreate(WatchEvent e) {
        Path filename = (Path) e.context();

        if (!(filename.getFileName().toString().endsWith(".airi"))) {
            System.out.println("Not an airi not handling ");
        } else {
            int exitCode = -1;
            Process process;

            try {
                process = Runtime.getRuntime().exec("/usr/bin/open -a Terminal /Applications/Utilities/");

                exitCode = process.waitFor();
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InterruptedException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            System.out.println(exitCode + " exitCode");

        }

    }

    @Override
    public void onModify(WatchEvent e) {
        Path filename = (Path) e.context();
    }


    @Override
    public void onDelete(WatchEvent e) {
        Path filename = (Path) e.context();
    }
}
