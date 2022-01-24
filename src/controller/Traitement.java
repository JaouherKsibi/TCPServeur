package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import Model.Compte;
import Model.Operation;
import view.MainApp;

public class Traitement extends Thread {
	Socket socket;
	String nom;
	public Traitement(){
		
	}
	public Traitement(Socket socket) {
		this.socket = socket;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
	public void run() {
		try {
			BufferedReader clientIn =new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			PrintWriter out =new PrintWriter(this.socket.getOutputStream(), true);
			String msgClient= clientIn.readLine();  
			System.out.println("message recu du client :"+msgClient);
			if (msgClient.startsWith("CREATION")) {
				//System.out.println("ok");
				String msg[] =msgClient.split("CREATION");
				boolean a=false;//le nom n'est pas contenu dans la liste 
				for (Compte c : MainApp.liste) {
					if (c.getNom().equals(msg[1])) {
						a=true;// le nom est contenu dans la liste
					}
				}
				if (a==true) {
					String msg1="erreur de creation du compte (un compte avec ce nom existe deja)";
					out.println(msg1);
				}
				else if (a==false) {
					this.nom=msg[1];
					MainApp.liste.add(new Compte(Compte.numero,msg[1],0));
					
					String msg1="le compte a ete cree avec succes number "+Compte.numero;
					out.println(msg1);
					Compte.numero++;
					while (true) {
						msgClient= clientIn.readLine();  
						System.out.println("message recu du client :"+msgClient);
						if (msgClient.startsWith("DEBIT")) {
							String msg2[]=msgClient.split("DEBIT");
							try {
								float montant=Float.parseFloat(msg2[1]);
								for (Compte c : MainApp.liste) {
									if(c.getNom().equals(this.nom)) {
										if (c.getSolde()<montant) {
											out.println("erreur de Debit ");
										}
										else {
											c.setSolde(c.getSolde()-montant);
											MainApp.listeOperation.add(new Operation("debit",montant,c));
											out.println("Debite avec succes");
										}
									}
								}
							} catch (Exception e) {
								out.println("erreur de Debit ");
							}
							
							
						}
						else if (msgClient.startsWith("CREDIT")) {
							String [] msg2=msgClient.split("CREDIT");
							try {
								float montant=Float.parseFloat(msg2[1]);
								for (Compte c : MainApp.liste) {
									if(c.getNom().equals(this.nom)) {
										c.setSolde(c.getSolde()+montant);
										MainApp.listeOperation.add(new Operation("credit",montant,c));
										out.println("credite avec succes ");
									}
								}
							} catch (Exception e) {
								out.println("erreur credit ");
							}
							
							
						}
						else if (msgClient.startsWith("TRANSFERT")) {
							String msg2[]=msgClient.split(" ");
							String nom1=msg2[1];
							System.out.println(nom1);
							try {
								float montant=Float.parseFloat(msg2[2]);
								boolean b=false;
								for (Compte c : MainApp.liste) {
									if(c.getNom().equals(this.nom)&&c.getSolde()>=montant) {
										for (Compte c1 : MainApp.liste) {
											if(c1.getNom().equals(nom1)) {
												c1.setSolde(c1.getSolde()+montant);
												c.setSolde(c.getSolde()-montant);
												MainApp.listeOperation.add(new Operation("transfet credit",montant,c1));
												MainApp.listeOperation.add(new Operation("transfet debit",montant,c));
												b=true;
											}
											
										}
									}
									/*if(c.getNom().equals(nom1)) {
										c.setSolde(c.getSolde()+montant);
										for (Compte c1 : MainApp.liste) {
											if(c1.getNom().equals(this.nom)) {
												c1.setSolde(c1.getSolde()-m)
											}
											
										}
										
										
									}*/
								}
								if (b==false) {
									out.println("echec transfert ");
								}
								else {
									out.println("transfert avec succes");
								}
							} catch (Exception e) {
								out.println("s il vous plait d entrer un montant valide ");
							}
							
						}
						else if (msgClient.equals("SOLDE")) {
							for (Compte c : MainApp.liste) {
								if(this.nom.equals(c.getNom())) {
									out.println("votre solde est de "+c.getSolde());
								}
							}
						}
						else if (msgClient.equals("HISTO")) {
							String msg3="";
							for (Compte c : MainApp.liste) {
								if(this.nom.equals(c.getNom())) {
									for (Operation op : MainApp.listeOperation) {
										if(op.getC().getNum()==c.getNum()) {
											msg3=msg3+ op.toString();
										}
									}
								}
							}
							out.println(msg3);
							
						}
						else {
							out.println("commande invalide ");
						}
					
					}
					
				}
				
			}
			else {
				out.println("rod belek il faut creer un compte");
			}
			/*while (true) {
				String msgClient= clientIn.readLine();  
				System.out.println("message recu du client :"+msgClient);
				out.println(msgClient.toUpperCase());
			
			}
			*/
			
			/*if (msgClient.startsWith("CREATION")) {
				//System.out.println("ok");
				String msg[] =msgClient.split("CREATION");
				boolean a=false;//le nom n'est pas contenu dans la liste 
				for (Compte c : MainApp.liste) {
					if (c.getNom().equals(msg[1])) {
						a=true;// le nom est contenu dans la liste
					}
				}
				if (a==true) {
					String msg1="erreur de creation du compte (un compte avec ce nom existe deja)";
					out.println(msg1);
				}
				else if (a==false) {
					this.nom=msg[1];
					MainApp.liste.add(new Compte(MainApp.liste.size(),msg[1],0));
					
					String msg1="le compte a ete cree avec succes";
					out.println("ok");
				}
				
			}
			
			
			
			
			else if (msgClient.startsWith("DEBIT")) {
				String msg1[]=msgClient.split("DEBIT");
				try {
					float montant=Float.parseFloat(msg1[1]);
					for (Compte c : MainApp.liste) {
						if(c.getNom().equals(this.nom)) {
							if (c.getSolde()<montant) {
								out.println("erreur de Debit ");
							}
							else {
								c.setSolde(c.getSolde()-montant);
								out.println("Debite avec succes");
							}
						}
					}
				} catch (Exception e) {
					out.println("erreur de Debit ");
				}
				
				
			}
			else if (msgClient.startsWith("CREDIT")) {
				String [] msg1=msgClient.split("CREDIT");
				try {
					float montant=Float.parseFloat(msg1[1]);
					for (Compte c : MainApp.liste) {
						if(c.getNom().equals(this.nom)) {
							c.setSolde(c.getSolde()+montant);
							out.println("credite avec succes ");
						}
						else {
							out.println("erreur credit ");
						}
						
					}
				} catch (Exception e) {
					out.println("erreur credit ");
				}
				
				
			}
			else if (msgClient.equals("SOLDE")) {
				for (Compte c : MainApp.liste) {
					if(this.nom.equals(c.getNom())) {
						out.println("votre solde est de "+c.getSolde());
					}
				}
			}
			
			*/
			
			//out.println(msgClient.toUpperCase());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
