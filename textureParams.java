import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;
import com.jogamp.opengl.util.texture.*;

public class textureParams
{
    
    Texture textObj;

	/**
	 * constructor
	 */
	public textureParams()
	{
        
	}
    
    /**
     * loads texture data to the GPU.
     * @param filename - The name of the texture file.
     *
     */
    public void drawTexture (String filename)
    {
        try {
         InputStream io = getClass().getResourceAsStream(filename);
         TextureData image = TextureIO.newTextureData(GLProfile.getDefault(), io, false, "jpg");
         textObj = TextureIO.newTexture(image);
        }
        catch (IOException e) {
         e.printStackTrace();
         System.exit(1);
        }
    }

    
    /**
     * sets up the parameters
     * for texture use.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpTextures (int program, GL2 gl2)
    {
        textObj.enable(gl2);
        textObj.bind(gl2);
    }
}