import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


class Districts implements Runnable {
    private String name;

    public Districts(String name,HashMap<String, int[]> stringHashMap) {
        this.name=name;
    }

    public void run() {
        int districtCount=0;
        int population = 0;
        int day = 0;

        try {
            File file = new File(name + ".csv");
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            System.out.println("Parsing " + file.getName()+"...");

            while ((line = bufferedReader.readLine()) != null) {
                day++;

                String[] wordlistOfOneLine = line.split(",");

                String date= wordlistOfOneLine[0];
                String dailyNumberOfPassengers = wordlistOfOneLine[3];


                if(date.equals("Date")||dailyNumberOfPassengers.equals("Number of Passengers")) {
                    continue;
                }
                else
                {
                    population= Integer.parseInt(dailyNumberOfPassengers);
                    population+=population;
                    districtCount++;
                    int dailyAverage=population/districtCount;
                    Main.districtPassengerCount.put(date, new int[] {population, dailyAverage});
                }
            }


            bufferedReader.close();
            reader.close();

         /*   if (day != 0) {
                int average = population / districtCount;
                Main.districtPassengerCount.put(name, new int[]{population, average});
            } */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class Server {

    public static void main(String[] args) throws IOException {

        String ClientSentence;
        String capitalizedSentence;

        ServerSocket welcomeSocket = null;
        welcomeSocket = new ServerSocket(25);


            Socket connectionSocket= null;

            connectionSocket = welcomeSocket.accept();

            System.out.println("Ready for connection");


            BufferedReader inFromClient = null;

            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            System.out.println("Connection established");


            HashMap<String, int[]> districtPassengerCount = new HashMap<>();


            for(int i=0;i<8;i++){


                int totalpass=0;
                int totaltrip=0;

                String input = inFromClient.readLine() ;
                System.out.println(input);
                System.out.println("recieved query for " + input);

                File file = new File(input + ".csv");
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;
                System.out.println("Parsing " + file.getName()+"...");

                while ((line = bufferedReader.readLine()) != null) {

                    String[] wordlistOfOneLine = line.split(",");

                    String passengers= wordlistOfOneLine[3];
                    String numOfTrips= wordlistOfOneLine[2];


                    if(!numOfTrips.equals("Number of Trips")||!passengers.equals("Number of Passengers")) {
                        int a= Integer.parseInt(passengers);
                        totalpass +=a;
                        int b= Integer.parseInt(numOfTrips);
                        totaltrip +=b;
                    }
                }

                districtPassengerCount.put(input, new int[] {totalpass, totaltrip, totalpass/totaltrip});


            }

            String myInput = inFromClient.readLine();
        System.out.println(myInput);
            int[] counts = districtPassengerCount.get(myInput);


            outToClient.writeBytes(String.valueOf(counts[0]) + '\n');
            outToClient.writeBytes(String.valueOf(counts[1]) +'\n');
            outToClient.writeBytes(String.valueOf(counts[2]) +'\n');



        }
}









