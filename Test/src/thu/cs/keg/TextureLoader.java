package thu.cs.keg;

import javax.media.opengl.*;
import com.sun.opengl.util.texture.*;
import com.sun.opengl.util.*;
import java.io.*;

public class TextureLoader {
	/**
	* Texture loader utilizes JOGL's provided utilities to produce a texture.
	*
	* @param fileName relative filename from execution point
	* @return a texture binded to the OpenGL context
	*/
 
	public static Texture load(String fileName){
		Texture text = null;
		try{
			text = TextureIO.newTexture(new File(fileName), false);
			text.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			text.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("Error loading texture " + fileName);
		}
		return text;
	}
 

}
