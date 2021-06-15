package manar.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class AWebServer{

    static String rootDirectory = "files/";

    public static void main(String[] args) {
        int LISTENING_PORT = 50000;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
        }
        catch (Exception e) {
            System.out.println("Failed to create listening socket.");
            return;
        }
        System.out.println("Listening on port " + LISTENING_PORT);
        try {
            while (true) {
                Socket connection = serverSocket.accept();
                System.out.println("\nConnection from "
                        + connection.getRemoteSocketAddress());
                ConnectionThread thread = new ConnectionThread(connection);
                thread.start();
            }
        }
        catch (Exception e) {
            System.out.println("Server socket shut down unexpectedly!");
            System.out.println("Error: " + e);
            System.out.println("Exiting.");
        }
        finally{
            try{
                if(serverSocket != null){
                    serverSocket.close();
                }
            }
            catch(Exception e){
                System.err.println("Proplems with closing serversocket;");
            }
        }
    }
    

    private static void handleConnection(Socket connection) {
        try {
            Scanner input = new Scanner(connection.getInputStream());
            PrintWriter output = new PrintWriter(connection.getOutputStream()) ;
            String [] list = null;
            while(input.hasNextLine()){//find the command string and make tokens
                list = input.nextLine().split(" ");
                if(list[0].equals("GET"))
                break;
            }
            if(!list[0].equals("GET")){//Handle unimplemented command
                sendErrorResponse(501,connection.getOutputStream());
                return;
            }
            switch(list[2].strip()){//Handle bad connections
                case "HTTP/1.1":
                    break;
                case "HTTP/1.0":
                    break;
                default:
                    sendErrorResponse(400,connection.getOutputStream());
                    System.out.println(list[2]);
                    return;
            }

            //file construction
            File requestedFile = new File(rootDirectory + list[1]);
            //file validations
            if(requestedFile.exists() && !requestedFile.isDirectory() ){
                if(!requestedFile.canRead()){//Can't Access the file
                    sendErrorResponse(403,connection.getOutputStream());
                }
                else{//valid 
                    output.println("HTTP/1.1 200 OK \r\n" 
                    + "Connection: close\r\n" 
                    + "Content-Type:" + getMimeType(requestedFile.getPath()) +"\r\n"
                    + requestedFile.length()+"\r\n"
                    +"\r\n");
                    output.flush();
    
                    sendFile(requestedFile, connection.getOutputStream());
                }
            }
            else{//file doesn't exists
                sendErrorResponse(404,connection.getOutputStream());
            }
        } 
        catch (Exception e) {
            try{//error to handle all errors
                sendErrorResponse(500,connection.getOutputStream());
            }
            catch(Exception g){//problem hon thread todn't crash the server.
                System.err.println("somthing went wrong rasing error 500: " + connection);
            }
        } 
        finally{//dont forget to close connection
            try{
                connection.close();
            }
            catch(Exception e){//problem hon thread todn't crash the server.
                System.err.println("coludent close the connection: " + connection);
            }
        }
    }

    private static String getMimeType(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos < 0)  // no file extension in name
            return "x-application/x-unknown";
        String ext = fileName.substring(pos+1).toLowerCase();
        if (ext.equals("txt")) return "text/plain";
        else if (ext.equals("html")) return "text/html";
        else if (ext.equals("htm")) return "text/html";
        else if (ext.equals("css")) return "text/css";
        else if (ext.equals("js")) return "text/javascript";
        else if (ext.equals("java")) return "text/x-java";
        else if (ext.equals("jpeg")) return "image/jpeg";
        else if (ext.equals("jpg")) return "image/jpeg";
        else if (ext.equals("png")) return "image/png";
        else if (ext.equals("gif")) return "image/gif";
        else if (ext.equals("ico")) return "image/x-icon";
        else if (ext.equals("class")) return "application/java-vm";
        else if (ext.equals("jar")) return "application/java-archive";
        else if (ext.equals("zip")) return "application/zip";
        else if (ext.equals("xml")) return "application/xml";
        else if (ext.equals("xhtml")) return"application/xhtml+xml";
        else return "x-application/x-unknown";
           // Note:  x-application/x-unknown  is something made up;
           // it will probably make the browser offer to save the file.
        }

    private static void sendFile(File file, OutputStream socketOut) throws IOException {
    InputStream in = new BufferedInputStream(new FileInputStream(file));
    OutputStream out = new BufferedOutputStream(socketOut);
    while (true) {
      int x = in.read(); // read one byte from file
      if (x < 0)
         break; // end of file reached
      out.write(x);  // write the byte to the socket
   }
  
   //make sure to close connections
   out.flush();
   in.close();
   out.close();
}

//add throws exception so it can be caught and handled by Error 500
static void sendErrorResponse(int errorCode, OutputStream socketOut) throws IOException{
    File errorFile = new File(rootDirectory + "/Errors/", errorCode + ".html"); //full file path on the server.
    PrintWriter output = new PrintWriter(socketOut); //for sending http header.
    InputStream in = new BufferedInputStream(new FileInputStream(errorFile));//for reading HTML error files.
    OutputStream out = new BufferedOutputStream(socketOut);// for sending the error file the the client.

    //for constructing the header
    String status = switch (errorCode) {
        case 400 -> " Bad Request";
        case 403-> " Forbidden";
        case 404-> " Not Found";
        case 500-> " Internal Server Error";
        case 501-> " Not Implemented";
        default -> " Internal Server Error";
    };

    //sending the finilized header
    output.println("HTTP/1.1 "+ errorCode + status +"\r\n" 
            + "Connection: close\r\n" 
            + "Content-Type:" + getMimeType(errorFile.getPath()) +"\r\n"
            + errorFile.length()+"\r\n"
            +"\r\n");
            output.flush();

    while (true) {
        int x = in.read(); // read one byte from file
        if (x < 0)
            break; // end of file reached
        out.write(x);  // write the byte to the socket
    }
    out.flush();


    //making sure all readers and writers are closed
    if(output != null){
        output.flush();
        output.close();
    }
    if(in != null){
        in.close();
    }
    if(output != null){
        out.flush();
        out.close();
    }
}




private static class ConnectionThread extends Thread {
    Socket connection;
    ConnectionThread(Socket connection) {
       this.connection = connection;
    }
    public void run() {
       handleConnection(connection);
    }
 }
}