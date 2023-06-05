package principal;

import geometricas.Rectangulo;
import utilidades.StdDraw;

import java.awt.Color;
import java.awt.Font;

import barcos.Barco;
import geometricas.Punto;

public class Tablero {
	private boolean usadas[][] = new boolean[10][10];
	private Rectangulo casillas[][] = new Rectangulo[10][10];
	private double x;
	private double y;
	private final double TAMAÑO = 9;


	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean[][] getUsadas() {
		return usadas;
	}

	public void setUsadas(boolean[][] usadas) {
		this.usadas = usadas;
	}

	public Rectangulo[][] getCasillas() {
		return casillas;
	}

	public void setCasillas(Rectangulo[][] casillas) {
		this.casillas = casillas;
	}

	Tablero(double x, double y) {
		this.x = x;
		this.y = y;
		for (int i = 0; i < casillas.length; i++) {
			x = this.x;
			for (int j = 0; j < casillas[0].length; j++) {
				casillas[i][j] = new Rectangulo(new Punto(x, y), new Punto(x + TAMAÑO, y - TAMAÑO));
				x += TAMAÑO;
			}
			y -= TAMAÑO;
		}
	}

	public void dibujar() {
		StdDraw.setPenColor(Color.black);
		StdDraw.setFont(new Font("font1", Font.ROMAN_BASELINE, 12));
		double x=this.x;
		double y=this.y;
		String[] numeracion = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		for (int i = 0; i < casillas.length; i++) {
			x=this.x;
			for (int j = 0; j < casillas[0].length; j++) {
				if (i == 0)
					StdDraw.text(x + (TAMAÑO / 2), y + (TAMAÑO / 2), numeracion[j]);
				if (j == 0)
					StdDraw.text(x - (TAMAÑO / 4), y - (TAMAÑO / 2), Integer.toString(i + 1));
				x+=TAMAÑO;
				casillas[i][j].setColor(Color.black);
				casillas[i][j].dibujar(false);
			}
			y-=TAMAÑO;
		}
	}
	
	public void dibujarBarco(Barco b) {
		for (int i = 0; i < usadas.length; i++) {
			for (int j = 0; j < usadas.length; j++) {
				if (b.getUsadas()[i][j] == true) {
					casillas[i][j].setColor(b.getColor());
					casillas[i][j].dibujar(true);
				}
			}
		}
	}
	public void insertarBarco(Barco b) {
		
		for (int i = 0; i < usadas.length; i++) {
			for (int j = 0; j < usadas.length; j++) {
				if (b.getUsadas()[i][j] == true) {
					usadas[i][j]=true;
				}
			}
		}
	}
}
