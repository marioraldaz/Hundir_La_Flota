package geometricas;

import java.awt.Color;
import java.util.Objects;

import utilidades.StdDraw;

public class Circulo {
	private Punto centro;
	private double radio;
	private boolean relleno = false;
	private Color color=Color.black;

	public boolean isRelleno() {
		return relleno;
	}

	public void setRelleno(boolean relleno) {
		this.relleno = relleno;
	}

	public Circulo(Punto centro, double radio) {
		setCentro(centro);
		setRadio(radio);
	}

	public Circulo(double x, double y, double rad) {
		centro = new Punto(x, y);
		setRadio(rad);
	}

	public Circulo() {
		centro = new Punto(0, 0);
		setRadio(10);
	}

	public void setRadio(double radio) {
		if (radio <= 0)
			throw new IllegalArgumentException("El radio de un círculo debe ser mayor que 0");
		this.radio = radio;
	}

	public Punto getCentro() {
		return centro;
	}

	public void setCentro(Punto centro) {
		if (centro == null)
			throw new IllegalArgumentException("Un círculo no puede tener centro nulo");
		this.centro = centro;
	}

	public double getRadio() {
		return radio;
	}

	@Override
	public String toString() {
		return "Circulo [centro=" + centro + ", radio=" + radio + ", relleno=" + relleno + "]";
	}

	public void dibujar() {
		StdDraw.setPenColor(color);
		if (relleno) {
			StdDraw.filledCircle(centro.getX(), centro.getY(), radio);
		}
		else {
			StdDraw.setPenRadius(0.003);
			StdDraw.circle(centro.getX(), centro.getY(), radio);	
		}

	}

	public void setColor(Color c) {
		this.color=c;
	}

	public void mover() {
		centro.mover();

	}

	public void movimiento(double movX, double movY) {
		centro.setMovX(movX);
		centro.setMovY(movY);
	}

	public boolean seSuperpone(Circulo c) {

		// true si el círculo(this) se superpone con otro círculo c.
		// Se superponen si la distancia entre sus centros es menor que la suma de sus
		// radios.
		return this.centro.distancia(c.getCentro()) < this.radio + c.getRadio();
	}

	public boolean seSuperpone(Rectangulo r) {
		// (px,py) será el punto del rectángulo más cercano al centro del círculo
		// El círculo se superpone si la distancia entre (px,py) y el centro del círculo
		// es menor que su radio.
		double px = centro.getX();
		if (px < r.getSupIzq().getX())
			px = r.getSupIzq().getX();
		if (px > r.getInfDer().getX())
			px = r.getInfDer().getX();

		double py = centro.getY();
		if (py > r.getSupIzq().getY())
			py = r.getSupIzq().getY();
		if (py < r.getInfDer().getY())
			py = r.getInfDer().getY();

		return centro.distancia(new Punto(px, py)) < radio;
	}

	public boolean dentroDe(Rectangulo r) {
		// true si el círculo está dentro del rectángulo
		return centro.getX() + radio < r.getInfDer().getX() && centro.getX() - radio > r.getSupIzq().getX()
				&& centro.getY() + radio < r.getSupIzq().getY() && centro.getY() - radio > r.getInfDer().getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(centro, radio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circulo other = (Circulo) obj;
		return Objects.equals(centro, other.centro)
				&& Double.doubleToLongBits(radio) == Double.doubleToLongBits(other.radio);
	}

}
