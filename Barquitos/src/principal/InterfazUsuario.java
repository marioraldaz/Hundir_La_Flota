package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import barcos.Barco;
import geometricas.Circulo;
import geometricas.Cruz;
import geometricas.Punto;
import geometricas.Rectangulo;
import utilidades.StdDraw;

public class InterfazUsuario {
	private static final double TAMAÑO = 9;

	public static void main(String[] args) throws InterruptedException {

		StdDraw.setXscale(-100, 100);
		StdDraw.setYscale(-100, 100);
		Random r = new Random();
		// mostrarMenu();
		Date d1 = new Date();
		Punto pTabJugador = new Punto(-95, 65);
		Punto pTabTiradas = new Punto(10, 65);
		Font font1 = new Font("font1", Font.ROMAN_BASELINE, 25);
		StdDraw.setFont(font1);
		StdDraw.text(pTabJugador.getX() + 20, pTabJugador.getY() + 20, "Mi tablero");
		StdDraw.text(pTabTiradas.getX() + 20, pTabTiradas.getY() + 20, "Mis tiradas");

		Tablero tableroJ = new Tablero(-95, 65);
		Tablero tableroT = new Tablero(10, 65);
		Tablero disparosJ = new Tablero(10, 65);
		Tablero disparosT = new Tablero(10, 65);
		Tablero tocados = new Tablero(10, 65);
		boolean[][] localizados = new boolean[10][10];

		Barco[] barcosJ = crearBarcos(tableroJ);
		Barco[] barcosT = crearBarcos(tableroT);

		for (int i = 0; i < barcosT.length; i++) {
			tableroJ.dibujarBarco(barcosJ[i]);
		}
		tableroJ.dibujar();
		tableroT.dibujar();

		int turno = 1;
		boolean recienPulsado = false;
		boolean terminar = false;
		boolean localizado = false;
		int[] disparoPrevio = { -2, -2 };
		int[] primerDisparo = { -2, -2 };
		int direccion = 1;
		boolean direccion1 = false;
		boolean direccion2 = false;
		boolean direccion3 = false;
		boolean direccion4 = false;

		int hundidosJ = 0;
		int hundidosT = 0;
		while (!terminar) {
			if (StdDraw.isKeyPressed(KeyEvent.VK_M))
				for (int i = 0; i < barcosT.length; i++) {
					tableroT.dibujarBarco(barcosT[i]);
					tableroT.dibujar();
				}
			if (StdDraw.isMousePressed()) {
				boolean tocado = false;
				boolean hundido = false;
				boolean agua = false;
				if (!recienPulsado) {
					if (turno == 1) {
						int[] disparo = disparo(StdDraw.mouseX(), StdDraw.mouseY(), tableroT);
						if (disparo[0] > -1 && !disparosT.getUsadas()[disparo[0]][disparo[1]]) {
							disparosT.getUsadas()[disparo[0]][disparo[1]] = true;
							agua = true;
							mostrarDisparo(disparo, tableroT);
							Rectangulo rAmarillo = new Rectangulo(
									tableroT.getCasillas()[disparo[0]][disparo[1]].getSupIzq(),
									tableroT.getCasillas()[disparo[0]][disparo[1]].getInfDer(), Color.yellow);
							rAmarillo.dibujar(false);
							tableroJ.dibujar();
							System.out.println();
							turno = 2;
							for (int i = 0; i < barcosJ.length; i++) {
								if (barcosT[i].tocado(disparo)) {
									tocado = true;
									if (barcosT[i].isHundido()) {
										hundidosT++;
										hundido = true;
										double x = 0;
										double y = 0;
										for (int j = 0; j < tableroJ.getCasillas().length; j++) {
											for (int j2 = 0; j2 < tableroJ.getCasillas().length; j2++) {
												if (barcosT[i].getUsadas()[j][j2]) {
													x = tableroT.getCasillas()[j][j2].centro().getX();
													y = tableroT.getCasillas()[j][j2].centro().getY();
													Color co = barcosT[i].getColor();
													tableroT.getCasillas()[j][j2].setColor(co);
													tableroT.getCasillas()[j][j2].dibujar();
													tableroT.getCasillas()[j][j2].setColor(Color.black);
													tableroT.getCasillas()[j][j2].dibujar(false);
													StdDraw.setPenColor(co);
													StdDraw.setPenRadius(0.04);
													StdDraw.point(x, y);
													(new Cruz(x, y, 2)).dibujar();
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (tocado) {
					if (hundido) {
						mensajeHundido();
					} else
						mensajeTocado();
				} else if (agua)
					mensajeAgua();
				if (hundidosJ == barcosJ.length || hundidosT == barcosT.length)
					break;
				tocado = false;
				hundido = false;
				agua = false;
				if (turno == 2) {
					int[] disparo = { -2, -2 };
					int cntToc = 0;
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							if (localizados[i][j]) {
								cntToc++;
								localizado = true;
							}
						}
					}
					if (cntToc == 0)
						localizado = false;
					if (localizado) {
						boolean valido = false;
						direccion = 1;
						while (!valido) {
							if (direccion == 1 && disparoPrevio[0] - 1 >= 0) {
								disparo[0] = disparoPrevio[0] - 1;
								disparo[1] = disparoPrevio[1];
								valido = true;
								direccion1 = true;
							} else {
								direccion1 = false;
							}
							if (direccion == 2 && disparoPrevio[0] + 1 <= 9) {
								disparo[0] = disparoPrevio[0] + 1;
								disparo[1] = disparoPrevio[1];
								valido = true;
								direccion2 = true;
							} else {
								direccion2 = false;
							}
							if (direccion == 3 && disparoPrevio[1] + 1 <= 9) {
								disparo[0] = disparoPrevio[0];
								disparo[1] = disparoPrevio[1] + 1;
								valido = true;
								direccion3 = true;
							} else {
								direccion3 = false;
							}
							if (direccion == 4 && disparoPrevio[1] - 1 >= 0) {
								disparo[0] = disparoPrevio[0];
								disparo[1] = disparoPrevio[1] - 1;
								valido = true;
								direccion4 = true;
							} else {
								direccion4 = false;
							}

							if (disparo[0] >= 0 && disparo[1] >= 0) {
								if (disparosJ.getUsadas()[disparo[0]][disparo[1]])
									valido = false;
								if (disparo[1] != 0 && tocados.getUsadas()[disparo[0]][disparo[1] - 1])
									valido = false;
								if (disparo[1] != 9 && tocados.getUsadas()[disparo[0]][disparo[1] + 1])
									valido = false;
								if (disparo[0] != 0 && tocados.getUsadas()[disparo[0] - 1][disparo[1]])
									valido = false;
								if (disparo[0] != 9 && tocados.getUsadas()[disparo[0] + 1][disparo[1]])
									valido = false;
							}
							if (!valido)
								direccion++;
							System.out.println(direccion);
							if (direccion > 4) {
								direccion = 1;
								localizados[disparoPrevio[0]][disparoPrevio[1]] = false;
								for (int i = 0; i < 10; i++) {
									for (int j = 0; j < 10; j++) {
										if (localizados[i][j]) {
											disparoPrevio[0] = i;
											disparoPrevio[1] = j;
										}
									}
								}
							}
						}
					} else {
						boolean valido = false;
						while (!valido) {
							disparo[0] = r.nextInt(0, 10);
							disparo[1] = r.nextInt(0, 10);
							if (!disparosJ.getUsadas()[disparo[0]][disparo[1]])
								valido = true;
							if (disparo[1] != 0 && tocados.getUsadas()[disparo[0]][disparo[1] - 1])
								valido = false;
							if (disparo[1] != 9 && tocados.getUsadas()[disparo[0]][disparo[1] + 1])
								valido = false;
							if (disparo[0] != 0 && tocados.getUsadas()[disparo[0] - 1][disparo[1]])
								valido = false;
							if (disparo[0] != 9 && tocados.getUsadas()[disparo[0] + 1][disparo[1]])
								valido = false;
						}
					}

					if (disparo[0] > -1) {
						Thread.sleep(0);
						disparosJ.getUsadas()[disparo[0]][disparo[1]] = true;
						agua = true;
						mostrarDisparo(disparo, tableroJ);
						turno = 1;
						Rectangulo rAmarillo = new Rectangulo(
								tableroJ.getCasillas()[disparo[0]][disparo[1]].getSupIzq(),
								tableroJ.getCasillas()[disparo[0]][disparo[1]].getInfDer(), Color.yellow);
						rAmarillo.dibujar(false);
						tableroT.dibujar();
						for (int i = 0; i < barcosT.length; i++) {
							if (barcosJ[i].tocado(disparo)) {
								localizados[disparo[0]][disparo[1]] = true;
								tocado = true;
								disparoPrevio = disparo;
								if (primerDisparo[0] < 0)
									primerDisparo = disparo;
								if (barcosJ[i].isHundido()) {
									hundidosJ++;
									direccion1 = false;
									direccion2 = false;
									direccion3 = false;
									direccion4 = false;

									direccion = 1;
									hundido = true;
									double x = 0;
									double y = 0;

									for (int j = 0; j < barcosT.length; j++) {
										for (int j2 = 0; j2 < barcosT.length; j2++) {
											if (barcosJ[i].getUsadas()[j][j2]) {
												localizados[j][j2] = false;
												tocados.getUsadas()[j][j2] = true;
												x = tableroJ.getCasillas()[j][j2].centro().getX();
												y = tableroJ.getCasillas()[j][j2].centro().getY();
												Color co = barcosJ[i].getColor();
												tableroJ.getCasillas()[j][j2].setColor(co);
												tableroJ.getCasillas()[j][j2].dibujar();
												tableroJ.getCasillas()[j][j2].setColor(Color.black);
												tableroJ.getCasillas()[j][j2].dibujar(false);
												StdDraw.setPenColor(co);
												StdDraw.setPenRadius(0.04);
												StdDraw.point(x, y);
												(new Cruz(x, y, 2)).dibujar();
											}
										}
									}
								}
							}
						}
					}

					if (tocado) {
						if (hundido) {
							mensajeHundido();
						} else
							mensajeTocado();
					} else if (agua)
						mensajeAgua();
				}
				recienPulsado = true;
			} else
				recienPulsado = false;
			if (hundidosJ == barcosJ.length || hundidosT == barcosT.length)
				break;
		}
		for (int i = 0; i < barcosT.length; i++) {
			tableroT.dibujarBarco(barcosT[i]);
			tableroT.dibujar();
			double x = 0;
			double y = 0;
			for (int j = 0; j < 10; j++) {
				for (int j2 = 0; j2 < 10; j2++) {
					if (barcosT[i].getUsadas()[j][j2]) {
						x = tableroT.getCasillas()[j][j2].centro().getX();
						y = tableroT.getCasillas()[j][j2].centro().getY();
						StdDraw.setPenColor(Color.red);
						if (barcosT[i].isHundido()) {
							(new Cruz(x, y, 2)).dibujar();
						} else
							(new Circulo(new Punto(x, y), 3)).dibujar();
					}
				}
			}
		}
		Date d2 = new Date();
		long milis = d2.getTime() - d1.getTime();
		Date d3 = new Date(milis);
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		boolean ganaCPU = false;
		if (hundidosJ == barcosJ.length)
			ganaCPU = true;
		finPartida(sdf.format(d3), ganaCPU);
	}

	public static void mensajeHundido() {
		StdDraw.setPenColor(Color.black);
		StdDraw.setFont(new Font("font1", Font.ROMAN_BASELINE, 25));
		StdDraw.text(0, -70, "! HUNDIDO ¡");
		StdDraw.pause(2000);
		Rectangulo r = new Rectangulo(-90, -50, 90, -90);
		r.setColor(Color.white);
		r.dibujar();
	}

	public static void mensajeTocado() {
		StdDraw.setPenColor(Color.black);
		StdDraw.setFont(new Font("font1", Font.ROMAN_BASELINE, 25));
		StdDraw.text(0, -70, "! TOCADO ¡");
		StdDraw.pause(2000);
		Rectangulo r = new Rectangulo(-90, -50, 90, -90);
		r.setColor(Color.white);
		r.dibujar();
	}

	public static void mensajeAgua() {
		StdDraw.setPenColor(Color.black);
		StdDraw.setFont(new Font("font1", Font.ROMAN_BASELINE, 25));
		StdDraw.text(0, -70, "! AGUA ¡");
		StdDraw.pause(2000);
		Rectangulo r = new Rectangulo(-90, -50, 90, -90);
		r.setColor(Color.white);
		r.dibujar();
	}

	private static Barco[] crearBarcos(Tablero tableroT) {
		Barco[] barcosJ = new Barco[10];
		barcosJ[0] = new Barco(4, tableroT, Color.green);
		tableroT.insertarBarco(barcosJ[0]);
		barcosJ[1] = new Barco(3, tableroT, Color.blue);
		tableroT.insertarBarco(barcosJ[1]);
		barcosJ[2] = new Barco(3, tableroT, Color.blue);
		tableroT.insertarBarco(barcosJ[2]);
		barcosJ[3] = new Barco(2, tableroT, Color.gray);
		tableroT.insertarBarco(barcosJ[3]);
		barcosJ[4] = new Barco(2, tableroT, Color.gray);
		tableroT.insertarBarco(barcosJ[4]);
		barcosJ[5] = new Barco(2, tableroT, Color.gray);
		tableroT.insertarBarco(barcosJ[5]);
		barcosJ[6] = new Barco(1, tableroT, Color.magenta);
		tableroT.insertarBarco(barcosJ[6]);
		barcosJ[7] = new Barco(1, tableroT, Color.magenta);
		tableroT.insertarBarco(barcosJ[7]);
		barcosJ[8] = new Barco(1, tableroT, Color.magenta);
		tableroT.insertarBarco(barcosJ[8]);
		barcosJ[9] = new Barco(1, tableroT, Color.magenta);
		tableroT.insertarBarco(barcosJ[9]);

		return barcosJ;
	}

	public static void mostrarMenu() {
		StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(0, 0, 100);
		StdDraw.setPenColor(Color.white);
		StdDraw.text(0, 0, "BIENVENIDO A HUNDIR LA FLOTA, PRESIONE ENTER PARA EMPEZAR");

	}

	public static void finPartida(String tiempo, boolean ganaCPU) {
		StdDraw.setPenColor(Color.black);
		StdDraw.text(0, -80, "Gracias por jugar, tiempo de partida: " + tiempo);
		if (ganaCPU)
			StdDraw.text(0, -72, "!GANA CPU!");
		else
			StdDraw.text(0, -72, "!GANA USUARIO!");

	}

	public static Rectangulo[][] generarTablero(Punto p) {
		double x = p.getX();
		double y = p.getY();
		Rectangulo[][] matriz = new Rectangulo[10][10];
		String[] numeracion = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		StdDraw.setFont();
		for (int i = 0; i < 10; i++) {
			x = p.getX();
			for (int j = 0; j < 10; j++) {
				if (i == 0)
					StdDraw.text(x + (TAMAÑO / 2), y + (TAMAÑO / 2), numeracion[j]);
				if (j == 0)
					StdDraw.text(x - (TAMAÑO / 4), y - (TAMAÑO / 2), Integer.toString(i + 1));
				Rectangulo r = new Rectangulo(new Punto(x, y), new Punto(x + TAMAÑO - 2, y - TAMAÑO), 0.002);
				matriz[i][j] = r;
				x += TAMAÑO - 2;
			}
			y -= TAMAÑO;
		}
		return matriz;
	}

	public static int[] disparo(double x, double y, Tablero t) {
		int[] posicion = new int[2];
		posicion[0] = -1;
		posicion[1] = -1;
		Circulo c = new Circulo(x, y, 0.02);
		for (int i = 0; i < t.getCasillas().length; i++) {
			for (int j = 0; j < t.getCasillas().length; j++) {
				if (c.dentroDe(t.getCasillas()[i][j])) {
					posicion[0] = i;
					posicion[1] = j;
				}
			}
		}
		return posicion;
	}

	public static void mostrarDisparo(int[] disparo, Tablero t) {
		double x = t.getCasillas()[disparo[0]][disparo[1]].centro().getX();
		double y = t.getCasillas()[disparo[0]][disparo[1]].centro().getY();
		StdDraw.setPenColor(Color.black);
		if (t.getUsadas()[disparo[0]][disparo[1]] == false) {
			StdDraw.setPenRadius(0.02);
			Punto p = new Punto(x, y);
			p.dibujar();
		} else {
			Circulo c = new Circulo(x, y, 3);
			c.setColor(Color.red);
			c.dibujar();
		}
	}
}