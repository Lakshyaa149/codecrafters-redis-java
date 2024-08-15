import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisProtocolHandler {

    public void handleRequestAndResponse(Socket socket) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedReader.readLine();
        String line = bufferedReader.readLine();
        System.out.println("line "+ line);
        String response = null;

        if(line.equalsIgnoreCase("echo")) {
            bufferedReader.readLine();
            line = bufferedReader.readLine();
            response = "$" + line.length() + "\r\n" + line + "\r\n";
        }
        System.out.println("response" +response);
        socket.getOutputStream().write(response.getBytes());
    }

}