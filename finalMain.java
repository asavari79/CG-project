//
// shaderMain.java
//
// Main class for lighting / shading assignment.
//
// Students should not be modifying this file.
//

import java.awt.*;
import java.nio.*;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;

public class finalMain implements GLEventListener, KeyListener
{

    /**
     * We need four vertex buffers and four element buffers:
     * two for the torus (flat shading and non-flat shading) and
     * two for the teapot (flat shading and non-flat shading).
     *
     * Array layout:
     *         column 0      column 1
     * row 0:  torus flat    torus non-flat
     * row 1:  teapot flat   teapot non-flat
     */
    private int vbuffer[][];
    private int ebuffer[][];
    private int numVerts[][];

    /**
     * Animation control
     */
    Animator anime;
    boolean animating;
    
    public textureParams texture1;
    public textureParams texture2;
    public textureParams texture3;
    
    /**
     * Initial animation rotation angles
     */
    float angles[];

    /**
     * Current shader type:  flat vs. non-flat
     */
    int currentShader;

    /**
     * Program IDs - current, and all variants
     */
    public int program;
    public int flat;
    public int phong;
    public int gouraud;

    /**
     * Shape info
     */
    shapes myShape;

    /**
     * Lighting information
     */
    lightingParams myPhong;

    /**
     * Viewing information
     */
    viewParams myView;

    /**
     * My canvas
     */
    GLCanvas myCanvas;

    /**
     * Constructor
     */
    public finalMain( GLCanvas G )
    {
        vbuffer = new int[5][2];
        ebuffer = new int[5][2];
        numVerts = new int[5][2];

        angles = new float[5];

        animating = false;
        currentShader = shapes.SHADE_FLAT;

        angles[0] = 0.0f;
        angles[1] = 0.0f;

        myCanvas = G;
        texture1 = new textureParams();
        texture2 = new textureParams();
        texture3 = new textureParams();
        // Initialize lighting and view
        
        myPhong = new lightingParams();
        myView = new viewParams();

        // Set up event listeners
        G.addGLEventListener (this);
        G.addKeyListener (this);
    }

    private void errorCheck (GL2 gl2)
    {
        int code = gl2.glGetError();
        if (code == GL.GL_NO_ERROR)
            System.err.println ("All is well");
        else
            System.err.println ("Problem - error code : " + code);

    }


    /**
     * Simple animate function
     */
    public void animate() {
   
    	angles[shapes.OBJ_DrawCube1] += 1;
    	angles[shapes.OBJ_Cyl] += 1;
    	angles[shapes.OBJ_Cyl1] += 1;
    	angles[shapes.OBJ_DrawCube2] += 1;
    }

    /**
     * Called by the drawable to initiate OpenGL rendering by the client.
     */
    public void display(GLAutoDrawable drawable)
    {
        // get GL
        GL2 gl2 = (drawable.getGL()).getGL2();
        
        // clear and draw params..
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
  
        // set up Phong illumination
        myPhong.setUpPhong( program, gl2 );

        // set up viewing and projection parameters
        myView.setUpFrustum( program, gl2 );

        // set up the camera
        myView.setUpCamera( program, gl2,
            5.2f, 6.5f, 6.5f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f
        );

        gl2.glUseProgram( program );
        
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, vbuffer[1][0] );
       gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[1][0] );
        
       int loc = gl2.glGetUniformLocation(program,"callType");
       gl2.glUniform1i(loc,1);
       myCylinder1(gl2);
        loc = gl2.glGetUniformLocation(program,"callType");
       gl2.glUniform1i(loc,2);
       myCylinder(gl2);
     
        loc = gl2.glGetUniformLocation(program,"callType");
       gl2.glUniform1i(loc,3);
      myCube1(gl2);
       loc = gl2.glGetUniformLocation(program,"callType");
      gl2.glUniform1i(loc,4);
      myCube(gl2);
        
       loc = gl2.glGetUniformLocation(program,"callType");
      gl2.glUniform1i(loc,5);
      myCube2(gl2);
    //    makeCy1(gl2);
        // perform any required animation for the next time
        if( animating ) {
            animate();
        }
    }

	

	private void myCube2(GL2 gl2) {
		long dataSize = numVerts[4][currentShader] * 4l * 4l;

	       int  vTex = gl2.glGetAttribLocation (program, "textureCoor");
	        gl2.glEnableVertexAttribArray ( vTex );
	        gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,0, dataSize);

	        // setup uniform variables for texture
	        texture3.setUpTextures (program, gl2);
	        
	        
	        myView.setUpTransforms( program, gl2,
	        		1.0f, 1.0f, 1.0f,
	                angles[shapes.OBJ_DrawCube2],
	                -10.0f + angles[shapes.OBJ_DrawCube2],
	                angles[shapes.OBJ_DrawCube2],
	                0.0f, 0.0f, 1.0f
	            );

	        
	        // draw your shapes
	        selectBuffers( gl2, shapes.OBJ_DrawCube2, currentShader );
	        gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[4][currentShader], GL.GL_UNSIGNED_SHORT, 0l); 
	}

	private void myCylinder1(GL2 gl2) {
		long dataSize = numVerts[3][currentShader] * 4l * 4l;

	       int  vTex = gl2.glGetAttribLocation (program, "textureCoor");
	        gl2.glEnableVertexAttribArray ( vTex );
	        gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,0, dataSize);

	        // setup uniform variables for texture
	        texture3.setUpTextures (program, gl2);    	        
	        
	        myView.setUpTransforms( program, gl2,
	        		0.85f, 1.50f, 0.75f,
	                  90.0f + angles[shapes.OBJ_Cyl1],
	                  10.0f + angles[shapes.OBJ_Cyl1],
	                10.0f   +  angles[shapes.OBJ_Cyl1],
	                  -1.0f, 1.15f, -0.5f
	            );
	        // draw your shapes
	        selectBuffers( gl2, shapes.OBJ_Cyl1, currentShader );
	        gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[3][currentShader], GL.GL_UNSIGNED_SHORT, 0l); 
		
	}

	private void myCube1(GL2 gl2) {
		// TODO Auto-generated method stub
		long dataSize = numVerts[2][currentShader] * 4l * 4l;

	       int  vTex = gl2.glGetAttribLocation (program, "textureCoor");
	        gl2.glEnableVertexAttribArray ( vTex );
	        gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,0, dataSize);

	        // setup uniform variables for texture
	        texture3.setUpTextures (program, gl2);
	        
	        
	        myView.setUpTransforms( program, gl2,
	        		3.95f, .75f, 0.75f,
	                angles[shapes.OBJ_Cube1],
	                -10.0f + angles[shapes.OBJ_Cube1],
	                angles[shapes.OBJ_Cube1],
	                0.25f, 1.75f, 0.0f
	            );

	        
	        // draw your shapes
	        selectBuffers( gl2, shapes.OBJ_Cube1, currentShader );
	        gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[2][currentShader], GL.GL_UNSIGNED_SHORT, 0l); 
		
	}

	private void myCube(GL2 gl2) {
		// TODO Auto-generated method stub
    	long dataSize = numVerts[0][currentShader] * 4l * 4l;

    	       int  vTex = gl2.glGetAttribLocation (program, "textureCoor");
    	        gl2.glEnableVertexAttribArray ( vTex );
    	        gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,0, dataSize);

    	        // setup uniform variables for texture
    	        texture3.setUpTextures (program, gl2);    	        
    	        
    	        myView.setUpTransforms( program, gl2,
    	                0.75f, 0.75f, 0.75f,
    	                angles[shapes.OBJ_DrawCube1],
    	                -10.0f + angles[shapes.OBJ_DrawCube1],
    	                angles[shapes.OBJ_DrawCube1],
    	                -0.75f, 2.5f, 0.0f
    	            );
    	        // draw your shapes
    	        selectBuffers( gl2, shapes.OBJ_DrawCube1, currentShader );
    	        gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[0][currentShader], GL.GL_UNSIGNED_SHORT, 0l); 
	}

	private void myCylinder(GL2 gl2) {
    	long dataSize1 = numVerts[1][currentShader] * 4l * 4l;

        int  vTex3 = gl2.glGetAttribLocation (program, "textureCoor");
        gl2.glEnableVertexAttribArray ( vTex3 );
        gl2.glVertexAttribPointer (vTex3, 2, GL.GL_FLOAT, false,0, dataSize1);
          
          
          texture3.setUpTextures (program, gl2);
          myView.setUpTransforms( program, gl2,
                  0.75f, 1.50f, 0.75f,
                  90.0f +  angles[shapes.OBJ_Cyl],
                  10.0f + angles[shapes.OBJ_Cyl],
                10.0f + angles[shapes.OBJ_Cyl],
                  1.25f, 1.15f, -0.15f
              );

          
          // draw your shapes
          selectBuffers( gl2, shapes.OBJ_Cyl, currentShader );
          gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[1][currentShader],
  	    GL.GL_UNSIGNED_SHORT, 0l);
		
	}

	/**
     * Notifies the listener to perform the release of all OpenGL
     * resources per GLContext, such as memory buffers and GLSL
     * programs.
     */
    public void dispose(GLAutoDrawable drawable)
    {
    }

    /**
     * Verify shader creation
     */
    private void checkShaderError( shaderSetup myShaders, int program,
        String which )
    {
        if( program == 0 ) {
            System.err.println( "Error setting " + which +
                " shader - " +
                myShaders.errorString(myShaders.shaderErrorCode)
            );
            System.exit( 1 );
        }
    }

    /**
     * Called by the drawable immediately after the OpenGL context is
     * initialized.
     */
    public void init(GLAutoDrawable drawable)
    {
        // get the gl object
        GL2 gl2 = drawable.getGL().getGL2();
     //   tex2.loadTexture("chessboard.jpg");
      
        
        // create the Animator now that we have the drawable
        anime = new Animator( drawable );

        // Load shaders, verifying each
        shaderSetup myShaders = new shaderSetup();

        flat = myShaders.readAndCompile( gl2, "flat.vert", "flat.frag" );
        checkShaderError( myShaders, flat, "flat" );

        gouraud = myShaders.readAndCompile(gl2,"gouraud.vert","gouraud.frag");
        checkShaderError( myShaders, gouraud, "gouraud" );

        phong = myShaders.readAndCompile( gl2, "phong.vert", "phong.frag" );
        checkShaderError( myShaders, phong, "phong" );

        // Default shader program
        program = flat;   
        
        // Other GL initialization
      //gl2.glEnable(GL.GL_ACTIVE_TEXTURE);
      
        gl2.glEnable( GL.GL_DEPTH_TEST );
        gl2.glEnable( GL.GL_CULL_FACE );
        gl2.glCullFace(  GL.GL_BACK );
        gl2.glFrontFace( GL.GL_CCW );
        gl2.glClearColor( 0.0f, 0.0f, 0.25f, 1.0f );
        gl2.glDepthFunc( GL.GL_LEQUAL );
        gl2.glClearDepth( 1.0f );
        
        
        
        
        createShape( gl2, shapes.OBJ_Cyl1, shapes.SHADE_FLAT );
        createShape( gl2, shapes.OBJ_Cyl1, shapes.SHADE_NOT_FLAT );
        texture3.drawTexture("wood4.jpg");

        createShape( gl2, shapes.OBJ_Cyl, shapes.SHADE_FLAT );
        createShape( gl2, shapes.OBJ_Cyl, shapes.SHADE_NOT_FLAT );
        texture3.drawTexture("wood4.jpg");
        
        createShape( gl2, shapes.OBJ_Cube1, shapes.SHADE_FLAT );
        createShape( gl2, shapes.OBJ_Cube1, shapes.SHADE_NOT_FLAT );
        texture3.drawTexture("wood4.jpg");
        
        createShape( gl2, shapes.OBJ_DrawCube1, shapes.SHADE_FLAT );
        createShape( gl2, shapes.OBJ_DrawCube1, shapes.SHADE_NOT_FLAT );
        texture3.drawTexture("wood4.jpg");
        
        createShape( gl2, shapes.OBJ_DrawCube2, shapes.SHADE_FLAT );
        createShape( gl2, shapes.OBJ_DrawCube2, shapes.SHADE_NOT_FLAT );
        
    }


    /**
     * Called by the drawable during the first repaint after the component
     * has been resized.
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                     int height)
    {
    }


    /**
     * Create vertex and element buffers for a shape
     */
    public void createShape(GL2 gl2, int obj, int flat )
    {
        // clear the old shape
        myShape = new shapes();

        // make the shape
        myShape.makeShape( obj, flat );

        // save the vertex count
        numVerts[obj][flat] = myShape.nVertices();

        // get the vertices
        Buffer points = myShape.getVertices();
        long dataSize = myShape.nVertices() * 4l * 4l;

        // get the normals
        Buffer normals = myShape.getNormals();
        long ndataSize = myShape.nVertices() * 3l * 4l;

        // get the element data
        Buffer elements = myShape.getElements();
        long edataSize = myShape.nVertices() * 2l;

        // generate the vertex buffer
        int bf[] = new int[1];

        gl2.glGenBuffers( 1, bf, 0 );
        vbuffer[obj][flat] = bf[0];
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, vbuffer[obj][flat] );
        gl2.glBufferData( GL.GL_ARRAY_BUFFER, dataSize + ndataSize, null,
        GL.GL_STATIC_DRAW );
        gl2.glBufferSubData( GL.GL_ARRAY_BUFFER, 0, dataSize, points );
        gl2.glBufferSubData( GL.GL_ARRAY_BUFFER, dataSize, ndataSize,
        normals );

        // generate the element buffer
        gl2.glGenBuffers (1, bf, 0);
        ebuffer[obj][flat] = bf[0];
        gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[obj][flat] );
        gl2.glBufferData( GL.GL_ELEMENT_ARRAY_BUFFER, edataSize, elements,
            GL.GL_STATIC_DRAW );

    }

    /**
     * Bind the correct vertex and element buffers
     *
     * Assumes the correct shader program has already been enabled
     */
    private void selectBuffers( GL2 gl2, int obj, int flat )
    {
        // bind the buffers
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, vbuffer[obj][flat] );
        gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[obj][flat] );

        // calculate the number of bytes of vertex data
        long dataSize = numVerts[obj][flat] * 4l * 4l;

        // set up the vertex attribute variables
        int vPosition = gl2.glGetAttribLocation( program, "vPosition" );
        gl2.glEnableVertexAttribArray( vPosition );
        gl2.glVertexAttribPointer( vPosition, 4, GL.GL_FLOAT, false,
                                       0, 0l );
        
       
          
        int vNormal = gl2.glGetAttribLocation( program, "vNormal" );
        gl2.glEnableVertexAttribArray( vNormal );
        gl2.glVertexAttribPointer( vNormal, 3, GL.GL_FLOAT, false,
                                   0, dataSize );

        
        
        
    }

    /**
     * Because I am a Key Listener...we'll only respond to key presses
     */
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
        // Get the key that was pressed
        char key = e.getKeyChar();

        // Respond appropriately
        switch( key ) {

            case '1':    // flat shading
                program = flat;
                currentShader = shapes.SHADE_FLAT;
                break;

            case '2':    // Gouraud shading
                program = gouraud;
                currentShader = shapes.SHADE_NOT_FLAT;
                break;

            case '3':    // phong shading
                program = phong;
                currentShader = shapes.SHADE_NOT_FLAT;
                break;

            case 'a':    // animate
                animating = true;
                anime.start();
                break;

            case 's':    // stop animating
                animating = false;
                anime.stop();
                break;

            case 'q': case 'Q':
                System.exit( 0 );
                break;
        }

        // do a redraw
        myCanvas.display();
       // myCanvas.display1();
    }


    /**
     * main program
     */
    public static void main(String [] args)
    {
        // GL setup
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        // create your tessMain
        finalMain myMain = new finalMain(canvas);


        Frame frame = new Frame("CG - Project");
        frame.setSize(600, 600);
        frame.add(canvas);
        frame.setVisible(true);

        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}