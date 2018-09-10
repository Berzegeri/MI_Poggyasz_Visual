package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable {
	@FXML
	TextArea input = new TextArea();
	@FXML
	VBox jobb = new VBox();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		input.setText("5 7\r\n" + "10\r\n" + "4 2\r\n" + "3 1\r\n" + "1 2\r\n" + "2 1\r\n" + "1 1\r\n" + "2 4\r\n"
				+ "1 2\r\n" + "2 2\r\n" + "2 1\r\n" + "3 1");
	}

	@FXML
	private void klikk(MouseEvent e) {
		start();
	}

	private void start() {
		String inp = input.getText();
		String d = inp.replace('\n', ' ');
		String in[] = d.split(" ");
		List<targy> targyak = new ArrayList<targy>();
		int H = Integer.parseInt(in[0]);
		int W = Integer.parseInt(in[1]);
		int meret = Integer.parseInt(in[2]);
		int summ = 0;

		if (H > W) { // fekszik
			int k = H;
			H = W;
			W = k;
		}

		for (int i = 0; i < meret; i++) { // befernek-e a targyak + beolvasas
			int a = Integer.parseInt(in[3 + (i * 2)]);
			int b = Integer.parseInt(in[4 + (i * 2)]);
			if (a <= H) {
				if (b <= H)
					targyak.add(new targy(a, b, true, i));
				else if (b <= W)
					targyak.add(new targy(a, b, false, i));
				else {
					System.out.println("nem lehet betenni");
					System.exit(0);
				}
			} else if (a <= W) {
				if (b <= H)
					targyak.add(new targy(a, b, false, i));
				else {
					System.out.println("nem lehet betenni");
					System.exit(0);
				}
			} else {
				System.out.println("nem lehet betenni");
				System.exit(0);
			}
		}

		for (int i = 0; i < meret; i++) {
			summ += targyak.get(i).a * targyak.get(i).b;
		}

		if (summ > H * W) {
			System.out.println("nem lehet betenni");
			System.exit(0);
		}

		for (int i = 0; i < meret; i++) { // targyak is
			targyak.get(i).fektet();
		}

		int[][] poggyasz = new int[H + 2][W + 2];
		for (int j = 0; j < H + 2; j++)
			poggyasz[j][0] = poggyasz[j][W + 1] = 1;

		for (int j = 0; j < W + 2; j++)
			poggyasz[0][j] = poggyasz[H + 1][j] = 1;

		for (int j = 1; j < H + 1; j++) {
			for (int k = 1; k < W + 1; k++)
				poggyasz[j][k] = 0;
		}
		// szelesseg majd hosszusag szerint NOVEKVO! sorba rendezve
		Collections.sort(targyak, new Comparator<Object>() {

			public int compare(Object o1, Object o2) {

				Integer x1 = ((targy) o1).b;
				Integer x2 = ((targy) o2).b;
				int sComp = x1.compareTo(x2);

				if (sComp != 0) {
					return sComp;
				} else {
					Integer x3 = ((targy) o1).a;
					Integer x4 = ((targy) o2).a;
					return x3.compareTo(x4);
				}
			}
		});

		for (int p = meret - 1; p >= 0; p--) { // targyakon
			int best = H * W;
			int bc = 0;
			int br = 0;
			for (int r = 1; r < H + 1; r++) { // sorok
				for (int c = 1; c < W + 1; c++) { // oszlopok
					if (poggyasz[r][c] == 0) { // ha ures
						int act = targyak.get(p).szomszedok(poggyasz, H, W, r, c);
						if (act <= best) {
							best = act;
							bc = c;
							br = r;
						}
						targyak.get(p).forgat();
						act = targyak.get(p).szomszedok(poggyasz, H, W, r, c);
						if (act <= best) {
							best = act;
							bc = c;
							br = r;
						}
					}
				}
			}
			if (best != targyak.get(p).szomszedok(poggyasz, H, W, br, bc))
				targyak.get(p).forgat();
			for (int m = br; m < br + targyak.get(p).a; m++) {
				for (int n = bc; n < bc + targyak.get(p).b; n++) {
					poggyasz[m][n] = targyak.get(p).id;
				}
			}
		}
		jobb.getChildren().removeAll(jobb.getChildren());
		Color c[] = new Color[11];
		c[0] = Color.DARKGRAY;
		c[1] = Color.GREEN;
		c[2] = Color.LIGHTCORAL;
		c[3] = Color.YELLOW;
		c[4] = Color.BROWN;
		c[5] = Color.DODGERBLUE;
		c[6] = Color.ORANGE;
		c[7] = Color.FLORALWHITE;
		c[8] = Color.ORANGERED;
		c[9] = Color.TAN;
		c[10] = Color.RED;
		for (int i = 1; i < H + 1; i++) {
			HBox sor = new HBox();
			for (int j = 1; j < W + 1; j++) {
				Rectangle r = new Rectangle();
				r.setHeight(50.0);
				r.setWidth(50.0);
				r.setX(500.0 + j * 50.0);
				r.setY(500.0 + i * 50.0);
				r.setStroke(Color.BLACK);
				r.setStrokeWidth(2);
				r.setArcWidth(5);
				r.setArcHeight(5);
				r.setFill(c[poggyasz[i][j]]);
				sor.getChildren().add(r);
			}
			jobb.getChildren().add(sor);
		}
	}

}
