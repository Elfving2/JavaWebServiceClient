import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            //connect to server
            Socket socket = new Socket("localhost", 6969);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Scanner sc = new Scanner(System.in);

            while(true) {
                String messageToServer = sc.nextLine();
                bw.write(messageToServer);
                bw.newLine();
                bw.flush();

                //System.out.println(br.readLine());
                String result = br.readLine();
                System.out.println("hej " + result);

                String[] arrOfStr = result.split(",");

                for(String a : arrOfStr) {
                    System.out.println(a);
                }


                if(messageToServer.equals("bye")) {
                    socket.close();
                    break;
                }
            }

            } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}