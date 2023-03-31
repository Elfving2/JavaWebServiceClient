import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean runProgram = true;

        while(runProgram){
            try {
                Scanner sc = new Scanner(System.in);

                //connect to server
                Socket socket = new Socket("localhost", 6969);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


                    System.out.println("1) View all users");
                    System.out.println("2) Add new user");
                    String val = sc.nextLine();
                    switch (val) {
                        case "1":
                            //sending to server
                            JSONObject jsonReturn = new JSONObject();
                            jsonReturn.put("httpURL", "users");
                            jsonReturn.put("httpMethod", "get");
                            jsonReturn.toJSONString();
                            String messageToServer = jsonReturn.toJSONString()  ;
                            bw.write(messageToServer);
                            bw.newLine();
                            bw.flush();
                            //handling response from server
                            GETMethod(br);
                            break;

                        case "2":
                            //sending to server
                            JSONObject data = POSTMethod(sc);
                            data.put("httpURL", "users");
                            data.put("httpMethod", "post");

                            //message to server
                            String message = data.toJSONString();
                            bw.write(message);
                            bw.newLine();
                            bw.flush();

                            //open up response
                            JSONParser parse = new JSONParser();
                            JSONObject resp = (JSONObject) parse.parse(br.readLine());
                            System.out.println(resp.get("success"));
                            break;

                        case "3":
                            //end progema both on server and client side
                            JSONObject close = new JSONObject();
                            close.put("close","close");
                            messageToServer = close.toJSONString();
                            bw.write(messageToServer);
                            bw.newLine();
                            bw.flush();
                            runProgram = false;
                            break;


                    }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

     static void GETMethod(BufferedReader br) {
        try {
            //Hämta response och spara i en variabel
            String resp = br.readLine();
            JSONParser parser = new JSONParser();

            JSONObject serverResponse = (JSONObject) parser.parse(resp);

            if (("200".equals(serverResponse.get("httpStatusCode").toString()))) {
                //TODO kolla vad som har returnerats

                //bygger upp ett JSONObject av den retunerade datan
                JSONArray usersArray = (JSONArray) serverResponse.get("users");

                //skapa en manuel userID, som ska skilja användare med samma namn, ålder och favorit färg
                int userID = 1;
                for (int i = 0; i < usersArray.size(); i++) {
                    userID++;
                    JSONObject userObject = (JSONObject) usersArray.get(i);
                    System.out.println("User: " + userID);
                    System.out.println("Name: " + userObject.get("name"));
                    System.out.println("Age: " + userObject.get("age"));
                    System.out.println("favoriteColor " + userObject.get("favoriteColor"));
                    System.out.println();
                }
            }
        } catch (ParseException | IOException e) {
            System.out.println(e);
        }
    }

    static JSONObject POSTMethod(Scanner sc) {
        //skapa ett person object
        JSONObject person = new JSONObject();

        System.out.println("name");
        String name = sc.nextLine();
        person.put("name", name);

        System.out.println("favorite color");
        String favoriteColor = sc.nextLine();
        person.put("favoriteColor", favoriteColor);

        System.out.println("Your age");
        int age = sc.nextInt();
        person.put("age", age);
        return person;
    }
}