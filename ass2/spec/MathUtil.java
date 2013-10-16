package ass2.spec;

/**
 * A collection of useful math methods
 * 
 * TODO: The methods you need to complete are at the bottom of the class
 * 
 * @author malcolmr
 */
public class MathUtil {

	final public boolean debug = true;

	/**
	 * Normalise an angle to the range (-180, 180]
	 * 
	 * @param angle
	 * @return
	 */
	static public double normaliseAngle(double angle) {
		return ((angle + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
	}

	/**
	 * Clamp a value to the given range
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */

	public static double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}

	/**
	 * Multiply two matrices
	 * 
	 * @param p
	 *            A 3x3 matrix
	 * @param q
	 *            A 3x3 matrix
	 * @return
	 */
	public static double[][] multiply(double[][] p, double[][] q) {

		double[][] m = new double[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				m[i][j] = 0;
				for (int k = 0; k < 4; k++) {
					m[i][j] += p[i][k] * q[k][j];
				}
			}
		}

		return m;
	}

	/**
	 * Multiply a vector by a matrix
	 * 
	 * @param m
	 *            A 3x3 matrix
	 * @param v
	 *            A 3x1 vector
	 * @return
	 */
	public static double[] multiply(double[][] m, double[] v) {

		double[] u = new double[4];

		for (int i = 0; i < 4; i++) {
			u[i] = 0;
			for (int j = 0; j < 4; j++) {
				// System.out.println("FLAG:"+m[i][j]);
				// System.out.println(" "+v[j]);

				u[i] += m[i][j] * v[j];
				// System.out.println("FLAG!!:"+i+" "+j);
				// System.out.println("FLAG:"+m[i][j]+" "+v[j]);
			}
		}

		return u;
	}

	// ===========================================
	// COMPLETE THE METHODS BELOW
	// ===========================================

	/**
	 * TODO: A 2D translation matrix for the given offset vector
	 * 
	 * @param pos
	 * @return
	 */
	public static double[][] translationMatrix(double[] v) {

		double[][] r = new double[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (j == i) {
					r[i][j] = 1;
				} else if (j == 3) {
					r[i][j] = v[i];
				} else {
					r[i][j] = 0;
				}
			}
		}

		return r;
	}

	/**
	 * TODO: A 2D rotation matrix for the given angle
	 * 
	 * @param angle
	 * @return
	 */
	public static double[][] rotationMatrix(double angle, boolean x, boolean y,
			boolean z) {
		double radius = angle * Math.PI / 180;
		double[][] r = new double[4][4];

		if (x == true) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if ((i == 0 && j == 0) || (i == 3 && j == 3)) {
						r[i][j] = 1;
					} else if (i == 1 && j == 1) {
						r[i][j] = Math.cos(radius);
					} else if (i == 1 && j == 2) {
						r[i][j] = Math.sin(radius) * (-1);
					} else if (i == 2 && j == 1) {
						r[i][j] = Math.sin(radius);
					} else if (i == 2 && j == 2) {
						r[i][j] = Math.cos(radius);
					} else {
						r[i][j] = 0;
					}
				}
			}
		} else if (y == true) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if ((i == 1 && j == 1) || (i == 3 && j == 3)) {
						r[i][j] = 1;
					} else if (i == 0 && j == 0) {
						r[i][j] = Math.cos(radius);
					} else if (i == 0 && j == 2) {
						r[i][j] = Math.sin(radius);
					} else if (i == 2 && j == 0) {
						r[i][j] = Math.sin(radius) * (-1);
					} else if (i == 2 && j == 2) {
						r[i][j] = Math.cos(radius);
					} else {
						r[i][j] = 0;
					}
				}
			}
		} else if (z == true) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if ((i == 2 && j == 2) || (i == 3 && j == 3)) {
						r[i][j] = 1;
					} else if (i == 0 && j == 0) {
						r[i][j] = Math.cos(radius);
					} else if (i == 0 && j == 1) {
						r[i][j] = Math.sin(radius) * (-1);
					} else if (i == 1 && j == 0) {
						r[i][j] = Math.sin(radius);
					} else if (i == 1 && j == 1) {
						r[i][j] = Math.cos(radius);
					} else {
						r[i][j] = 0;
					}
				}
			}
		} else {
			// this shouldn't happen
		}

		/*
		 * System.out.print(Math.cos(90)+"|||"); for(int i = 0; i<3 ; i++){
		 * for(int j = 0 ; j<3 ; j++){ System.out.print(r[i][j]+" "); }
		 * System.out.println(); } System.out.println("---------------");
		 */
		return r;
	}

	/**
	 * TODO: A 2D scale matrix that scales both axes by the same factor
	 * 
	 * @param scale
	 * @return
	 */
	public static double[][] scaleMatrix(double scale) {
		double[][] r = new double[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i == j && i == 0) {
					r[i][j] = scale;
				} else if (i == j && i == 1) {
					r[i][j] = scale;
				} else if (i == j && i == 2) {
					r[i][j] = scale;
				} else if (i == j && i == 3) {
					r[i][j] = 1;
				} else {
					r[i][j] = 0;
				}
			}
		}
		return r;
	}

	public static int factorial(double k) {
		int result = 1;
		for (int i = 1; i <= k; i++) {
			result *= i;
		}
		return result;
	}

	public static double[] normal(double[] a, double[] b, double[] c) {
		double[] n = new double[2];
		// 点1-点2得向量1
		double[] v1 = { a[0] - b[0], a[1] - b[1], a[2] - b[2], a[3] };
		// 点1-点3得向量2
		double[] v2 = { a[0] - c[0], a[1] - c[1], a[2] - c[2], a[3] };
		// 向量1和向量2 叉乘 得法线向量的方向
		double[] roughN = { v1[1] * v2[2] - v1[2] * v2[1],
				v1[2] * v2[0] - v1[0] * v2[2], v1[0] * v2[1] - v1[1] * v2[0] };
		//a * b = [a2b3 -a3b2, a3b1 -a1b3, a1b2- a2b1]
		// 法线向量单位化
		double abs = Math.sqrt(roughN[0] * roughN[0] + roughN[1] * roughN[1]
				+ roughN[2] * roughN[2]);
		n[0] = roughN[0]/abs;
		n[1] = roughN[1]/abs;
		n[2] = roughN[2]/abs;
		return n;
	}

}
