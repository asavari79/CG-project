import java.util.ArrayList;

//
//  shapes.java
//
//  Students should not be modifying this file.
//
//  @author Vasudev
//

public class shapes extends simpleShape {

    /**
     * Object selection variables
     */
  //  public static final int OBJ_TORUS = 0;
 //   public static final int OBJ_TEAPOT = 1;
	public static final int OBJ_DrawCube1 = 0;
	public static final int OBJ_Cyl = 1;
	public static final int OBJ_Cyl1 =3;
	public static final int OBJ_Cube1 = 2;
	public static final int OBJ_DrawCube2  = 4;
    /**
     * Shading selection variables
     */
    public static final int SHADE_FLAT = 0;
   public static final int SHADE_NOT_FLAT = 1;

    /**
     * Constructor
     */
    
    public shapes() {
    }

 
    public void makeShape( int choice, int shadingType ) {
        if( choice == shapes.OBJ_DrawCube1 )
            makeCube( 1 );
        else if( choice == shapes.OBJ_Cyl)
        	makeCylinder( 0.5f, 20, 3);
        else if( choice == shapes.OBJ_Cube1)
        	makeCube1(1);
        else if( choice == shapes.OBJ_Cyl1)
        	makeCylinder1( 0.5f, 20, 3);
        else if(choice == shapes.OBJ_DrawCube2) 
        	makeCube2(20);
    }
    

	private void makeCube2(int subdivisions) {
		if (subdivisions < 1)
			subdivisions = 1;

		
		for (float i = 0; i < subdivisions; i++) {
			for (float j = 0; j < subdivisions; j++) {
				float a = i / subdivisions;
				float a1 = (i + 1) / subdivisions;
				float b = j / subdivisions;
				float b1 = (j + 1) / subdivisions;
				float pxprev = a - 0.5f;
				float pyprev = b - 0.5f;
				float pxnext = a1 - 0.5f;
				float pynext = b1 - 0.5f;
				L.add(new float[] { pxprev, pyprev, pxnext, pynext });

			}
		}
		drawXy();
		drawYz();
		drawXz();
		L.clear();
		
	}


	private void makeCylinder1(float radius, int radialDivisions,
			int heightDivisions) {
		if (radialDivisions < 3)
			radialDivisions = 3;

		if (heightDivisions < 1)
			heightDivisions = 1;

		/*
		 * Approximates disks on the y-axis centered at (0,0). Draws triangles
		 * by connecting points at Ti, T(i+1), and the center.
		 */

		float r = radius;
		int n = radialDivisions;
		int rect = heightDivisions;
		float xprev, zprev, xnext, znext, t0, t1;

		t0 = 0;
		xprev = (float) r;
		zprev = 0;

		int i = 0;
		while (i < n) {

			t1 = t0 + (float) 360 / n;

			xnext = r * (float) Math.cos(Math.toRadians(t1));
			znext = r * (float) Math.sin(Math.toRadians(t1));

			addTriangle(0f, -0.5f, 0f, xprev, -0.5f, zprev, xnext, -0.5f, znext);

			addTriangle(xnext, 0.5f, znext, xprev, 0.5f, zprev, 0f, 0.5f, 0f);

			L1.add(new float[] { xprev, zprev, xnext, znext });

			xprev = xnext;
			zprev = znext;
			t0 = t1;
			i++;
		}
		drawHeight(rect);
		L1.clear();
	}
		
	


	private void makeCube1(int subdivisions) {
		if (subdivisions < 1)
			subdivisions = 1;

		
		for (float i = 0; i < subdivisions; i++) {
			for (float j = 0; j < subdivisions; j++) {
				float a = i / subdivisions;
				float a1 = (i + 1) / subdivisions;
				float b = j / subdivisions;
				float b1 = (j + 1) / subdivisions;
				float pxprev = a - 0.5f;
				float pyprev = b - 0.5f;
				float pxnext = a1 - 0.5f;
				float pynext = b1 - 0.5f;
				L.add(new float[] { pxprev, pyprev, pxnext, pynext });

			}
		}
		drawXy();
		drawYz();
		drawXz();
		L.clear();

        }   
	



	/**
	 * makeCube - Create a unit cube, centered at the origin, with a given
	 * number of subdivisions in each direction on each face.
	 *
	 * @param subdivision
	 *            - number of equal subdivisons to be made in each direction
	 *            along each face
	 *
	 *            Can only use calls to addTriangle()
	 */
	ArrayList<float[]> L = new ArrayList<float[]>();

	public void makeCube(int subdivisions) {
		if (subdivisions < 1)
			subdivisions = 1;

		
		for (float i = 0; i < subdivisions; i++) {
			for (float j = 0; j < subdivisions; j++) {
				float a = i / subdivisions;
				float a1 = (i + 1) / subdivisions;
				float b = j / subdivisions;
				float b1 = (j + 1) / subdivisions;
				float pxprev = a - 0.5f;
				float pyprev = b - 0.5f;
				float pxnext = a1 - 0.5f;
				float pynext = b1 - 0.5f;
				L.add(new float[] { pxprev, pyprev, pxnext, pynext });

			}
		}
		drawXy();
		drawYz();
		drawXz();
		L.clear();

	}

	// draw X-Y Plane for cube
	public void drawXy() {
		for (float[] t : L) {
			// front cube in X-Y plane
			addTriangle(t[0], t[1], 0.5f, t[0], t[3], 0.5f, t[2], t[1], 0.5f);
			addTriangle(t[2], t[3], 0.5f, t[2], t[1], 0.5f, t[0], t[3], 0.5f);
			// Back cube in X-Y plane
			addTriangle(t[0], t[1], -0.5f, t[2], t[1], -0.5f, t[0], t[3], -0.5f);
			addTriangle(t[2], t[3], -0.5f, t[0], t[3], -0.5f, t[2], t[1], -0.5f);

		}
	}

	// draw Y-Z plane for cube
	public void drawYz() {
		for (float[] t : L) {
			// Left side
			addTriangle(-0.5f, t[0], t[1], -0.5f, t[2], t[1], -0.5f, t[0], t[3]);
			addTriangle(-0.5f, t[2], t[3], -0.5f, t[0], t[3], -0.5f, t[2], t[1]);
			// Right side
			addTriangle(0.5f, t[0], t[1], 0.5f, t[0], t[3], 0.5f, t[2], t[1]);
			addTriangle(0.5f, t[2], t[3], 0.5f, t[2], t[1], 0.5f, t[0], t[3]);

		}
	}

	// draw X-Z plane for cube
	public void drawXz() {
		for (float[] t : L) {

			// Top of the cube in x-z plane
			addTriangle(t[0], 0.5f, t[1], t[2], 0.5f, t[1], t[0], 0.5f, t[3]);
			addTriangle(t[2], 0.5f, t[3], t[0], 0.5f, t[3], t[2], 0.5f, t[1]);
			// Bottom of the cube in x-z plane
			addTriangle(t[0], -0.5f, t[1], t[0], -0.5f, t[3], t[2], -0.5f, t[1]);
			addTriangle(t[2], -0.5f, t[3], t[2], -0.5f, t[1], t[0], -0.5f, t[3]);
		}

	}

	
	ArrayList<float[]> L1 = new ArrayList<float[]>();

	public void makeCylinder(float radius, int radialDivisions,
			int heightDivisions) {
		if (radialDivisions < 3)
			radialDivisions = 3;

		if (heightDivisions < 1)
			heightDivisions = 1;

		/*
		 * Approximates disks on the y-axis centered at (0,0). Draws triangles
		 * by connecting points at Ti, T(i+1), and the center.
		 */

		float r = radius;
		int n = radialDivisions;
		int rect = heightDivisions;
		float xprev, zprev, xnext, znext, t0, t1;

		t0 = 0;
		xprev = (float) r;
		zprev = 0;

		int i = 0;
		while (i < n) {

			t1 = t0 + (float) 360 / n;

			xnext = r * (float) Math.cos(Math.toRadians(t1));
			znext = r * (float) Math.sin(Math.toRadians(t1));

			addTriangle(0f, -0.5f, 0f, xprev, -0.5f, zprev, xnext, -0.5f, znext);

			addTriangle(xnext, 0.5f, znext, xprev, 0.5f, zprev, 0f, 0.5f, 0f);

			L1.add(new float[] { xprev, zprev, xnext, znext });

			xprev = xnext;
			zprev = znext;
			t0 = t1;
			i++;
		}
		drawHeight(rect);
		L1.clear();
	}

	// method draw triangles in vertical manner
	public void drawHeight(int rect) {
		float yprev, ynext;

		for (float t[] : L1) {
			yprev = -0.5f;
			for (int i = 0; i < rect; i++) {
				ynext = yprev + (float) 1 / rect;

				addTriangle(t[0], ynext, t[1], t[2], ynext, t[3], t[0], yprev,
						t[1]);

				addTriangle(t[2], ynext, t[3], t[2], yprev, t[3], t[0], yprev,
						t[1]);

				yprev = ynext;
			}
		}

	}
    
}