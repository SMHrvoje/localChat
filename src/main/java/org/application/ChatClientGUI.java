package org.application;

import client.ClientActor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends JFrame {
    private JTextArea textArea;
    private JTextField textField;
    private ClientActor client;

    public ChatClientGUI() {
        super("Chat application");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String host = JOptionPane.showInputDialog(this,"Enter the host address");
        String portString = JOptionPane.showInputDialog(this,"Enter the port number");
        int port = 0;
        try{
            port = Integer.parseInt(portString);
        }
        catch (NumberFormatException exc){
            JOptionPane.showMessageDialog(this,"Invalid port number,exiting");
            System.exit(1);
        }

        String name = JOptionPane.showInputDialog(this,"Enter your name that will be shown to others",JOptionPane.PLAIN_MESSAGE);
        this.setTitle("Chat Application - "+name);

        textArea = new JTextArea();
        textArea.setEditable(false);

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        textField = new JTextField();
        textField.addActionListener(new ActionListener() {

            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String message ="["+ new SimpleDateFormat("HH:mm:ss").format(new Date())+
                           "] " + name + ": " + textField.getText();
                    client.sendMessage(message);
                    textField.setText("");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(textField, BorderLayout.SOUTH);

        try{
            this.client = new ClientActor(port,host,this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Error connecting to server","Error",JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }

    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(()->textArea.append(message+"\n"));
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new ChatClientGUI().setVisible(true);
        });
    }
}
