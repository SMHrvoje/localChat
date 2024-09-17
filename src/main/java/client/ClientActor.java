package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.logging.ConsoleHandler;

public class ClientActor {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Consumer<String> onMessageReceived;


    public ClientActor(int port, String host, Consumer<String> onMessageReceived) throws IOException {
        this.clientSocket = new Socket(host, port);
        this.onMessageReceived= onMessageReceived;
        this.out= new PrintWriter(clientSocket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }


    public void startClient() throws IOException {
       new Thread(()->{
           try{
               String line;
               while((line=in.readLine())!=null){
                   onMessageReceived.accept(line);
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }).start();
    }

    public void sendMessage(String message) throws IOException {
        out.println(message);
    }

    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.clientSocket.close();
    }

}
