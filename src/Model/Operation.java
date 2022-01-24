package Model;

public class Operation {
	public static int numero=0;
	private int num ;
	private String type;
	private float value;
	private Compte c;
	public Operation() {
		// TODO Auto-generated constructor stub
	}
	public Operation(String type, float value , Compte c) {
		this.num = numero;
		this.type = type;
		this.value = value;
		this.c=c;
		numero++;
	}
	public Compte getC() {
		return c;
	}
	public void setC(Compte c) {
		this.c = c;
	}
	public static int getNumero() {
		return numero;
	}
	public static void setNumero(int numero) {
		Operation.numero = numero;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Operation [num=" + num + ", type=" + type + ", value=" + value + ", c=" + c + "]";
	}
	
	
	

}
