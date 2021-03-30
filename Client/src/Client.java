import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		try {
			Socket socket = new Socket("192.168.1.62", 8888);
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String clientMessage = "", serverMessage = "";

			serverMessage = inStream.readUTF();
			System.out.println(serverMessage);
			serverMessage = inStream.readUTF();
			System.out.println(serverMessage);
			clientMessage = br.readLine();
			outStream.writeUTF(clientMessage);
			outStream.flush();

			while (!clientMessage.equals("close")) {

				System.out.print("Enter your request	:  ");
				clientMessage = br.readLine();
				outStream.writeUTF(clientMessage);
				outStream.flush();

				if (clientMessage.contains("get data")) {
					System.out.println("> Server: ");
					for (int i = 0; i < 50; i++) {
						serverMessage = inStream.readUTF();
						System.out.println("		" + serverMessage);
					}
				} else if (clientMessage.contains("get date")) {
					serverMessage = inStream.readUTF();
					System.out.println("> Server: " + serverMessage);
				} else if (clientMessage.contains("send to")) {
					serverMessage = inStream.readUTF();
					System.out.println("> Server: " + serverMessage);
					serverMessage = inStream.readUTF();
					int availableClients = Integer.parseInt(serverMessage);
					System.out.println("> Available clients : " + serverMessage);
					for (int i = 0; i < availableClients; i++) {
						System.out.println(inStream.readUTF());
					}
				} else {
					serverMessage = inStream.readUTF();
					System.out.println("> Server: " + serverMessage);
				}
			}
			outStream.close();
			outStream.close();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
