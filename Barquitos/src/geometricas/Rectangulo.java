package geometricas;

import java.awt.Color;

import utilidades.StdDraw;

/**
 * Consideramos rectángulos horizontales. Definimos un rectángulo mediante el
 * vértice superior izquierdo y el inferior derecho.
 *
 */
public class Rectangulo {
	public Rectangulo(Punto supIzq, Punto infDer, Color color) {
		super();
		this.supIzq = supIzq;
		this.infDer = infDer;
		this.color = color;
	}

	private Punto supIzq; // El color de este punto indica el color del rectángulo
	private Punto infDer;
	private double radio = 0.002;
	private Color color=Color.black;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		if (radio <= 0)
			throw new ExcepcionRectanguloIncorrecto("El radio debe ser un número positivo en formato double");
		if (radio >= 1)
			throw new ExcepcionRectanguloIncorrecto("El radio debe ser menor de 1");
		this.radio = radio;
	}

	public Rectangulo(Punto supIzq, Punto infDer) {
		if (infDer == null)
			throw new ExcepcionRectanguloIncorrecto("Esquina inferior derecha no puede ser null");
		this.infDer = infDer;
		setSupIzq(supIzq);
		setInfDer(infDer);
	}

	public Rectangulo(Punto supIzq, Punto infDer, double radio) {
		if (infDer == null)
			throw new ExcepcionRectanguloIncorrecto("Esquina inferior derecha no puede ser null");
		this.infDer = infDer;
		setSupIzq(supIzq);
		setInfDer(infDer);
		setRadio(radio);
	}

	public Rectangulo(double xSupIzq, double ySupIzq, double xInfDer, double yInfDer) {
		this(new Punto(xSupIzq, ySupIzq), new Punto(xInfDer, yInfDer));
	}

	public Punto getSupIzq() {
		return supIzq;
	}

	public Punto getInfDer() {
		return infDer;
	}

	public String toString() {
		return "Rectangulo [supIzq=" + supIzq + ", infDer=" + infDer + "]";
	}

	public void setInfDer(Punto p) {
		if (p == null)
			throw new ExcepcionRectanguloIncorrecto("Esquina inferior derecha no puede ser null");
		if (supIzq.getX() >= p.getX() || supIzq.getY() <= p.getY())
			throw new ExcepcionRectanguloIncorrecto("Esquina inferior derecha erronea " + p);
		this.infDer = p;
	}

	public void setSupIzq(Punto p) {
		if (p == null)
			throw new ExcepcionRectanguloIncorrecto("Esquina superior izquierda no puede ser null");
		if (p.getX() >= infDer.getX() || p.getY() <= infDer.getY())
			throw new ExcepcionRectanguloIncorrecto("Esquina Superior izquierda erronea " + p);
		this.supIzq = p;
	}

	public void color(Color c) {
		supIzq.setColor(c);
	}

	// El color del rectángulo es el color del punto supIzp
	public void dibujar() {
		dibujar(true);
	}

	public void dibujar(boolean relleno) {
		StdDraw.setPenColor(color);
		if (relleno)
			StdDraw.filledRectangle(centro().getX(), centro().getY(), base() / 2, altura() / 2);
		else {
			StdDraw.setPenRadius(radio);
			StdDraw.rectangle(centro().getX(), centro().getY(), base() / 2, altura() / 2);
		}
	}
	

	public double altura() {
		return supIzq.getY() - infDer.getY();
	}

	public double base() {
		return infDer.getX() - supIzq.getX();
	}

	public double area() {
		return base() * altura();
	}

	public Punto centro() { // Devuelve punto en el centro del rectangulo
		return new Punto((supIzq.getX() + infDer.getX()) / 2, (supIzq.getY() + infDer.getY()) / 2);
	}

	public Punto centroDer() { // Devuelve punto en el centro del lado derecho
		return new Punto(infDer.getX(), infDer.getY() + altura() / 2);
	}

	public Punto centroInf() { // Devuelve punto en el centro del lado inferior
		return new Punto(infDer.getX() - base() / 2, infDer.getY());
	}

	public Punto centroIzq() { // Devuelve punto en el centro del lado izquierdo
		return new Punto(supIzq.getX(), supIzq.getY() - altura() / 2);
	}

	public Punto centroSup() { // Devuelve punto en el centro del lado superior
		return new Punto(supIzq.getX() + base() / 2, supIzq.getY());
	}

	public Punto infIzq() { // Devuelve punto vertice inferior izquierdo
		return new Punto(supIzq.getX(), infDer.getY());
	}

	public Punto supDer() { // Devuelve punto vertice superior derecho
		return new Punto(infDer.getX(), supIzq.getY());
	}

	public void movimiento(double movX, double movY) {
		supIzq.setMovX(movX);
		supIzq.setMovY(movY);
		infDer.setMovX(movX);
		infDer.setMovY(movY);
	}

	public void mover() {
		supIzq.mover();
		infDer.mover();
	}

}
