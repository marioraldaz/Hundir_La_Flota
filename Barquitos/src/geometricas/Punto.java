package geometricas;

import java.awt.Color;
import java.util.Objects;

import utilidades.StdDraw;

public class Punto {
	private double x;
	private double y;
	private Color color;
	private double movX;
	private double movY;

	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
		color = Color.BLACK;
	}

	public Punto(double x, double y, Color c) {
		this.x = x;
		this.y = y;
		color = c;
	}

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

	public double distancia(Punto p) {
		return distancia(x, y, p.x, p.y);

	}

	public static double distancia(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static double distancia(Punto a, Punto b) {
		return distancia(a.x, a.y, b.x, b.y);
	}

	public void dibujar() {
		StdDraw.setPenColor(color);
		StdDraw.point(x, y);
	}

	public void mover() {
		x = x + movX;
		y = y + movY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getMovX() {
		return movX;
	}

	public void setMovX(double movX) {
		this.movX = movX;
	}

	public double getMovY() {
		return movY;
	}

	public void setMovY(double movY) {
		this.movY = movY;
	}

	public boolean dentroDe(Circulo circ) {
		return this.distancia(circ.getCentro()) < circ.getRadio();
	}

	@Override
	public String toString() {
		return "Punto [x=" + x + ", y=" + y + ", movX=" + movX + ", movY=" + movY + ", color=" + color + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Punto other = (Punto) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
	}

}
