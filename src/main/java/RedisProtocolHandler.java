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
            int no = bufferedReader.read();
            System.out.println(no);
            for(int i=0;i<no;i++){
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
        int noOfCharacters = bufferedReader.read();
        System.out.println(noOfCharacters);
        skipCharacters(bufferedReader);
        for(int i=0; i<noOfCharacters;i++){
            char ch = (char)bufferedReader.read();
            System.out.println(ch);
        }
        skipCharacters(bufferedReader);
        System.out.println(sb.toString());
        return sb.toString();
    }

    private static void skipCharacters(BufferedReader bufferedReader) throws IOException {
        bufferedReader.skip(2);
    }
}
