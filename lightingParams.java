
//
// lightingParams.java
//
// Simple class for setting up the viewing and projection transforms
// for the Shading Assignment.
//
// Students are to complete this class.
//

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*; 

public class lightingParams
{
    // Add any global class variables you need here.

	float[] arrayAmbient = {(float) 0.5, (float) 0.1, (float) 0.9, (float) 1.0 };
	float reflectionCoefficient = (float) 0.5;
	float[]		arrayDiffuseColor = { (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0 };
		float	diffuseReflectionCoefficient  = (float) 0.7;
		float[]	specularColor = { (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0 };
			float specularExponent = (float) 10.0;
			float specularReflectionCoefficient  = (float) 1.0;
		float[]	Color = { (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0 };
				float[]	Position = { (float) 0.0, (float) 5.0, (float) 2.0, (float) 1.0 };
			float[]	ambiColor = { (float) 0.5, (float) 0.5, (float) 0.5, (float) 1.0 };
    /**
     * constructor
     */
    public lightingParams()
    {
      
    }
    /**
     * This functions sets up the lighting, material, and shading parameters
     * for the Phong shader.
     *
     * You will need to write this function, and maintain all of the values
     * needed to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) shader program to which
     * parameter values are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpPhong (int program, GL2 gl2)
    {
        // Add your code here.
    	int ambi = gl2.glGetUniformLocation( program , "reflectionCoefficient" );
        gl2.glUniform1f(ambi, reflectionCoefficient ); 
        
        int diffuse = gl2.glGetUniformLocation( program , "diffuseReflectionCoefficient" );
        gl2.glUniform1f(diffuse,diffuseReflectionCoefficient ); 
        
        int specularExp = gl2.glGetUniformLocation( program , "specularExponent" );
        gl2.glUniform1f(specularExp,specularExponent ); 
        
        int specularR = gl2.glGetUniformLocation( program , "specularReflectionCoefficient" );
        gl2.glUniform1f(specularR,specularReflectionCoefficient ); 
        
        int array1 = gl2.glGetUniformLocation( program , "arrayAmbient" );
    	gl2.glUniform4fv(array1, 1,  arrayAmbient, 0);
    	
    	int array2 = gl2.glGetUniformLocation( program , "arrayDiffuseColor" );
    	gl2.glUniform4fv(array2, 1,  arrayDiffuseColor, 0);
    	
    	int array3 = gl2.glGetUniformLocation( program , "specularColor" );
    	gl2.glUniform4fv(array3, 1,  specularColor, 0);
    	
    	
    	int lightPosition = gl2.glGetUniformLocation( program , "Position" );
    	gl2.glUniform4fv(lightPosition, 1,  Position, 0);
    	
    	int lightColor = gl2.glGetUniformLocation( program , "Color" );
    	gl2.glUniform4fv(lightColor, 1,  Color, 0);
    	
    	int ColorArray = gl2.glGetUniformLocation( program , "ambiColor" );
    	gl2.glUniform4fv(ColorArray, 1, ambiColor , 0);
        
        
    }
}
