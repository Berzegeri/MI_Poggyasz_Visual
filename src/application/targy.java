package application;


public class targy {
	public int a;
	public int b;
	public boolean barhogy; // van ami csak fekve fer el
	public int id;

	public targy(int a, int b, boolean ir, int i) {
		this.a = a;
		this.b = b;
		barhogy = ir;
		id = i + 1;
	}

	public void forgat() {
		if (barhogy) {
			int k = a;
			a = b;
			b = k;
		}
	}

	public void fektet() {
		if (a > b) {
			int k = a;
			a = b;
			b = k;
		}
	}

	public int szomszedok(int[][] input, int H, int W, int r, int c) { // Height, Width, row, column, a,b
		int szomszedok = H * W;
		boolean mehet = true;

		if (c + b <= W+1 && r + a <= H+1) { // ha befer

			for (int i = r; i < r + a && mehet; i++) { // ha nincs ott semmi
				for (int j = c; j < c + b && mehet; j++) {
					if (input[i][j] != 0)
						mehet = false;
				}
			}
			if (mehet) {
				szomszedok = 0;
				for (int i = c - 1; i < c + b + 1; i++) { // alatta, felette
					if (input[r + a][i] == 0)
						szomszedok++;
					if (input[r - 1][i] == 0)
						szomszedok++;
				}
				for (int i = r; i < r + a; i++) { // jobbra
					if (input[i][c + b] == 0)
						szomszedok++;
					if (input[i][c - 1] == 0)
						szomszedok++;
				}
			}
		}
		return szomszedok;
	}

}

