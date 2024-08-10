import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


class RedisConcurrentTask implements Runnable{

    @Override
    public void run() {


    }
}

public class Main {



  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
    ExecutorService executorService = Executors.newFixedThreadPool(10);

      //Uncomment this block to pass the first stage
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 6379;
        try {
          serverSocket = new ServerSocket(port);
          // Since the tester restarts your program quite often, setting SO_REUSEADDR
          // ensures that we don't run into 'Address already in use' errors
          serverSocket.setReuseAddress(true);

          while( true){
              // Wait for connection from client.
              clientSocket = serverSocket.accept();
              Socket finalClientSocket = clientSocket;
              executorService.submit(()-> processRequest(finalClientSocket));
          }
        } catch (IOException e) {
          System.out.println("IOException: " + e.getMessage());
        } finally {
          try {
            if (clientSocket != null) {
              clientSocket.close();
            }
          } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
          }
        }
  }

    private static void processRequest(Socket clientSocket)  {

      try {
          String clientInput = null;
          OutputStream outputStream = clientSocket.getOutputStream();
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          while ((clientInput = bufferedReader.readLine()) != null) {
              if (clientInput.equalsIgnoreCase("ping")) {
                  outputStream.write("+PONG\r\n".getBytes());
              }
          }
      }
      catch (Exception e){
          System.out.println(e);
      }
  }
}
