package org.application;

import server.ServerActor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        ServerActor serverActor=new ServerActor(14444);
        serverActor.start();
        serverActor.stop();

    }
}


