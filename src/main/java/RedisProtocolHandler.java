import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisProtocolHandler {

    public void  handleRequestAndResponse(Socket socket) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream outputStream = socket.getOutputStream();
        String line = null;
        bufferedReader.readLine();
        bufferedReader.readLine();
        String command = bufferedReader.readLine();
        System.out.println(command);
        if(command.equalsIgnoreCase("echo")){
            String dollarString = bufferedReader.readLine();
            String no=dollarString.substring(1);

            String message =  bufferedReader.readLine();
            String response = "$"+no+"\r\n"+message+"\r\n";
            System.out.println(message);
            outputStream.write(response.getBytes());
            outputStream.flush();
        }



//
//        char asciiNo = (char)bufferedReader.read();
//        System.out.println(asciiNo);
//        if(asciiNo == '*'){
//            asciiNo = (char)bufferedReader.read();
//            System.out.println("here main"+Character.getNumericValue(asciiNo));
//            for(int i=0;i<Character.getNumericValue(asciiNo);i++){
//                skipCharacters(bufferedReader);
//                char ascii = (char)bufferedReader.read();
//                System.out.println("reading here"+ascii);
//                if((char)ascii == '$'){
//                    String resp = handleBulkString(bufferedReader);
//                    outputStream.write(resp.getBytes());
//                    System.out.println("response"+resp);
//                }
//                System.out.println("outer loop"+i);
//            }
//
//        }
    }

    private String handleSimpleString(BufferedReader bufferedReader) throws IOException {
        int ascii = -1;
        StringBuilder sb = new StringBuilder();
        while ((ascii =bufferedReader.read())!=13){
            sb.append((char)ascii);
        }
        return sb.toString();
    }

    private String handleBulkString(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        int ascii;
        // $3\r\nhey\r\n
        char  chNo = (char)bufferedReader.read();
        System.out.println("printing here"+chNo);
        skipCharacters(bufferedReader);
        for(int i=0; i<Character.getNumericValue(chNo);i++){
            System.out.println("i="+i);
            char ch = (char)bufferedReader.read();
            sb.append(ch);
            System.out.println(ch);
        }
        skipCharacters(bufferedReader);
        System.out.println("returning string"+sb.toString());
        return sb.toString();
    }

    private static void skipCharacters(BufferedReader bufferedReader) throws IOException {
        bufferedReader.skip(2);
    }
}
