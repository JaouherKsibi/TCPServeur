package Model;

public class Compte {
	public static int numero=0;
	private int num;
	private String nom;
	private float solde;
	public Compte() {
		// TODO Auto-generated constructor stub
	}
	public Compte(int num, String nom, float solde) {
		super();
		this.num = num;
		this.nom = nom;
		this.solde = solde;
	}
	@Override
	public String toString() {
		return "Compte [num=" + num + ", nom=" + nom + ", solde=" + solde + "]";
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public float getSolde() {
		return solde;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	
	

}
