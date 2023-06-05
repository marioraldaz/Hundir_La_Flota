package barcos;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import principal.Tablero;
import utilidades.StdDraw;

public class Barco {
	private boolean[][] usadas = new boolean[10][10];
	private Color color;
	private int nCasillas;
	private int hp;
	private boolean hundido;

	public void setUsadas(boolean[][] usadas) {
		this.usadas = usadas;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setnCasillas(int nCasillas) {
		this.nCasillas = nCasillas;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean[][] getUsadas() {
		return usadas;
	}

	public int getnCasillas() {
		return nCasillas;
	}

	public boolean isHundido() {
		return hundido;
	}

	public void setHundido(boolean hundido) {
		this.hundido = hundido;
	}

	Random r = new Random();

	public Barco(int ncasillas, Tablero t, Color color) {
		setnCasillas(ncasillas);
		setHp(nCasillas);
		this.color = color;
		boolean[][] aux = new boolean[10][10];
		aux = new boolean[10][10];
		int cnt = 0;
		boolean repetirBarco = true;
		int i = 0;
		int j = 0;

		do {
			int direccion = r.nextInt(1, 5);
			if (repetirBarco) {
				i = r.nextInt(0, 9);
				j = r.nextInt(0, 9);
				aux = new boolean[10][10];
				repetirBarco = false;
			}

			if (direccion == 1) { // hacia la derecha
				boolean ok = true;
				if (j == 9)
					ok = false;
				if (ok)
					j++;
				if (t.getUsadas()[i][j])
					ok = false;
				if (i != 9 && t.getUsadas()[i + 1][j])
					ok = false;
				if (i != 0 && t.getUsadas()[i - 1][j])
					ok = false;
				if (j != 0 && t.getUsadas()[i][j - 1])
					ok = false;
				if (j != 9 && t.getUsadas()[i][j + 1])
					ok = false;
				if (ok) {
					aux[i][j] = true;
				}
			}

			if (direccion == 2) { // hacia abajo
				boolean ok = true;
				if (i == 0)
					ok = false;
				if (ok)
					i--;
				if (ok && t.getUsadas()[i][j])
					ok = false;
				if (i != 0 && t.getUsadas()[i - 1][j])
					ok = false;
				if (i != 9 && t.getUsadas()[i + 1][j])
					ok = false;
				if (j != 0 && t.getUsadas()[i][j - 1])
					ok = false;
				if (j != 9 && t.getUsadas()[i][j + 1])
					ok = false;
				if (ok) {
					aux[i][j] = true;
				}
			}
			if (direccion == 3) { // hacia arriba
				boolean ok = true;
				if (i != 9)
					ok = false;
				if (ok)
					i++;
				if (t.getUsadas()[i][j])
					ok = false;
				if (i != 0 && t.getUsadas()[i - 1][j])
					ok = false;
				if (i != 9 && t.getUsadas()[i + 1][j])
					ok = false;
				if (j != 0 && t.getUsadas()[i][j - 1])
					ok = false;
				if (j != 9 && t.getUsadas()[i][j + 1])
					ok = false;
				if (ok) {
					aux[i][j] = true;
				}
			}
			if (direccion == 4) { // hacia la izquierda
				boolean ok = true;
				if (j == 0)
					ok = false;
				if (ok)
					j--;
				if (t.getUsadas()[i][j])
					ok = false;
				if (i != 0 && t.getUsadas()[i - 1][j])
					ok = false;
				if (i != 9 && t.getUsadas()[i + 1][j])
					ok = false;
				if (j != 0 && t.getUsadas()[i][j - 1])
					ok = false;
				if (j != 9 && t.getUsadas()[i][j + 1])
					ok = false;
				if (ok) {
					aux[i][j] = true;
				}
			}
			
			if (!aux[i][j])
				repetirBarco = true;
			cnt=0;
			
			for (int k = 0; k < aux.length; k++) {
				for (int k2 = 0; k2 < aux.length; k2++) {
					if(aux[k][k2])
						cnt++;
				}
			}
		} while (cnt < nCasillas);

		setUsadas(aux);
	}

	public boolean tocado(int[] disparo) {
		boolean tocado = false;

		if (usadas[disparo[0]][disparo[1]] == true) {
			tocado = true;
			hp--;
		}
		if (hp == 0) {
			hundido = true;
		}
		return tocado;
	}

}
