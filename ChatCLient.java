package test;
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true)) {

            Thread readThread = new Thread(() -> {
                String response;
                try {
                    while ((response = serverInput.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });
            readThread.start();

            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                serverOutput.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
