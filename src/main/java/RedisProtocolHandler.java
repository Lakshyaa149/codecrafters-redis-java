import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisProtocolHandler {

    public void handleRequestAndResponse(Socket socket) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = bufferedReader.readLine();
        String response = null;
        while (line.equalsIgnoreCase("echo")) {
            bufferedReader.readLine();
            line = bufferedReader.readLine();
            response = "$" + line.length() + "\r\n" + line + "\r\n";
        }
        socket.getOutputStream().write(response.getBytes());
    }

}