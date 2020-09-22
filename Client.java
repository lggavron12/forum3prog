package aula20200803.introducaoIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private static final String SERVER_ADDRESS = "localhost";
	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Client client = new Client();
		client.doIt();
	}

	private void doIt() throws Exception {
		InputStream consoleInput = System.in;
		OutputStream consoleOutput = System.out;

		String comando = "";
		Scanner scannerConsole = new Scanner(consoleInput);
		while (!comando.equalsIgnoreCase("sair")) {
			System.out.print("Digite um comando>>  ");
			comando = scannerConsole.nextLine();

			String response = handleServerCommunication(comando);
			if (comando.equalsIgnoreCase("ping")) {
				for (int i = 0; i < 1000; i++) {
					long tempoInicial = System.currentTimeMillis();
					long tempoTotal = 0;
					long menorTempo = (System.currentTimeMillis() - tempoInicial);
					long maiorTempo = (System.currentTimeMillis() - tempoInicial);
					System.out.printf("%s %.3f ms%n", response, (System.currentTimeMillis() - tempoInicial) / 1000d);
					if (System.currentTimeMillis() - tempoInicial < menorTempo) {
						menorTempo = System.currentTimeMillis() - tempoInicial;
					}
					if (System.currentTimeMillis() - tempoInicial > maiorTempo) {
						maiorTempo = System.currentTimeMillis() - tempoInicial;
					}
					tempoTotal = tempoTotal + (System.currentTimeMillis() - tempoInicial);
					System.out.println("Tempo total: " + tempoTotal);
					System.out.println("Tempo médio: " + tempoTotal / 1000);
					System.out.println("Menor Tempo: " + menorTempo);
					System.out.println("Maior Tempo: " + maiorTempo);
				}
				break;
			}
		}
		System.out.println("Saiu.");
	}

	private String handleServerCommunication(String comando) throws UnknownHostException, IOException {
		Socket connection = new Socket(SERVER_ADDRESS, PORT);
		Scanner serverInput = new Scanner(connection.getInputStream());
		PrintWriter serverOutput = new PrintWriter(connection.getOutputStream());
		serverOutput.println(comando);
		serverOutput.flush();
		String response = serverInput.nextLine();
		connection.close();
		return response;
	}

}
