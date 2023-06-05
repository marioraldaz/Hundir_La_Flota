package geometricas;

import java.awt.Color;

import utilidades.StdDraw;

public class Cruz {
	public Cruz(double x, double y, double l) {
		super();
		setX(x);
		setY(y);
		setL(l);
	}
	public Cruz(double x, double y, double l,Color c) {
		super();
		setX(x);
		setY(y);
		setL(l);
		setC(c);
	}
	private double x;
	private double y;
	private double l;
	private Color c=Color.red;
	public Color getC() {
		return c;
	}
	public void setC(Color c) {
		this.c = c;
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
	public double getL() {
		return l;
	}
	public void setL(double l) {
		this.l = l;
	}
	
	public void dibujar() {
		StdDraw.setPenRadius(0.003);
		StdDraw.setPenColor(c);
		StdDraw.line(x-l, y-l, x+l, y+l);
		StdDraw.line(x-l, y+l, x+l, y-l);

	}
}
