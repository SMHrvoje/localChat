package server;

import client.ClientActor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerActorHandler implements Runnable{

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private List<ServerActorHandler> clients = new ArrayList<>();

    public ServerActorHandler(Socket socket, List<ServerActorHandler> clients){
        this.socket=socket;
        this.clients=clients;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(this.socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String inputLine;

            while((inputLine=in.readLine())!=null){
                if(inputLine.equals("bye")){
                    out.println("bye");
                    break;
                }
                for (ServerActorHandler actor: clients){
                    actor.out.println(inputLine);
                }
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
