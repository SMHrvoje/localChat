package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerActor {
    private int port;
    private ServerSocket serverSocket;
    private static List<ServerActorHandler> sockets= new ArrayList<>();

    public ServerActor(int port)  {
        this.port=port;
    }

    public void start() throws IOException {
        serverSocket= new ServerSocket(port);
        while(true) {
            Socket socket= serverSocket.accept();
            ServerActorHandler actorHandler= new ServerActorHandler(socket,sockets);
            this.sockets.add(actorHandler);
            new Thread(actorHandler).start();
        }
    }

    public void stop() throws IOException {
        this.serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        ServerActor serverActor= new ServerActor(14444);
        serverActor.start();
        serverActor.stop();
    }
}

