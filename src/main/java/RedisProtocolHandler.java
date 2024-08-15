import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisProtocolHandler {

    public void  handleRequestAndResponse(Socket socket) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());

        char ascii = (char)bufferedReader.read();
        System.out.println(ascii);
        if(ascii == '*'){
            ascii = (char)bufferedReader.read();
            System.out.println(ascii);
            skipCharacters(bufferedReader);
            for(int i=0;i<(int)ascii;i++){
                ascii = (char)bufferedReader.read();
                System.out.println(ascii);
                if((char)ascii == '$'){
                    outputStream.write(handleBulkString(bufferedReader).getBytes());
                }
            }

        }
    }

    private String handleSimpleString(BufferedReader bufferedReader) throws IOException {
        int ascii = -1;
        StringBuilder sb = new StringBuilder();
        while ((ascii =bufferedReader.read())!=13){
            sb.append((char)ascii);
        }
        return sb.toString();
    }

    private static String handleBulkString(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        int ascii;
        // $3\r\nhey\r\n
        char  ch = (char)bufferedReader.read();
        System.out.println("printing here"+ch);
        skipCharacters(bufferedReader);
        for(int i=0; i<(int)ch;i++){
            ch = (char)bufferedReader.read();
            System.out.println(ch);
        }
        skipCharacters(bufferedReader);
        return sb.toString();
    }

    private static void skipCharacters(BufferedReader bufferedReader) throws IOException {
        bufferedReader.skip(2);
    }
}
