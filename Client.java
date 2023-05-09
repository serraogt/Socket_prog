import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;



public class Client {


    public static void main(String[] args) throws IOException {

        String[] districts = {"Balcova", "Bornova", "Urla", "Gaziemir", "Karabaglar","Karsiyaka","Konak","Seferihisar"};

        String districtName;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = null;
        clientSocket = new Socket("localhost", 25);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));



        System.out.println("Connection established");

        for(int i=0;i<8;i++){
            outToServer.writeBytes(districts[i] + '\n');

        }
        System.out.println("Enter a district");
        String girdi= inFromUser.readLine();

        outToServer.writeBytes((girdi +'\n' ));


        String totalp= inFromServer.readLine();
        String totalt= inFromServer.readLine();
        String avg = inFromServer.readLine();

        System.out.println(totalp);
        clientSocket.close();

    }

}
