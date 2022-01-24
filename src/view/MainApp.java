package view;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Model.Compte;
import Model.Operation;
import controller.Traitement;

public class MainApp {
	public static List<Compte> liste;
	public static List<Operation> listeOperation;
	public static void main(String[] args) {
		try {
			liste=new ArrayList<Compte>();
			listeOperation=new ArrayList<Operation>();
			ServerSocket socket =new ServerSocket(3500);
			while(true) {
				Socket s=socket.accept();
				Traitement t=new Traitement(s);
				t.start();
			}
			
		
		} catch (Exception e) {
		}
		
		
	}
	

}
