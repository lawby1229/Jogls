package HCQ;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;

import common.TextureLoader;

public class JOGL06_Texture extends JFrame implements GLEventListener, KeyListener, MouseListener
{
  private GL gl;
  private GLU glu;
  private GLCapabilities caps;
  private GLCanvas canvas;
  private MouseEvent mouse;
  private Animator animator;//���һ�������ʵ��
  //���ͼƬ���� ��ǰ��û���������Ȥ�Ķ��������ǲ����˽Ⱑ
  //�ߵ�̫�ߣ��ڲ���û���˽Ⱑ
  private Texture  pngtexture1,pngtexture2; //pngͼƬ
  
  private float xrot;//��X��
  private float yrot;//��Y��
  private float zrot;//��Z��
  


  public JOGL06_Texture()
  {
    super("JOGL06_Texture");

    caps = new GLCapabilities();
    canvas = new GLCanvas(caps);
    canvas.addGLEventListener(this);
    canvas.addKeyListener(this);
    canvas.addMouseListener(this);
    getContentPane().add(canvas);
  }

  public void run()
  {
    setSize(800, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    canvas.requestFocusInWindow();
    canvas.swapBuffers();
    animator.start();
  }

  public static void main(String[] args)
  {
    new JOGL06_Texture().run();
    
  }

  public void init(GLAutoDrawable drawable)
  {
	    gl = drawable.getGL();
	    glu = new GLU();  
	    gl.glShadeModel(GL.GL_SMOOTH);
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	    gl.glClearDepth(1.0f);							// ������Ȼ���
		gl.glEnable(GL.GL_DEPTH_TEST);					// ������Ȳ���
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);			// ����ϵͳ��͸�ӽ�������
	    animator=new Animator(canvas);//ʵ������canvas��ˢ��
	    
 //--------------����Ӵ���-------
    gl.glEnable(GL.GL_TEXTURE_2D);//��������ӳ��
    pngtexture1 = TextureLoader.load("data/jogl.png");
    pngtexture2 = TextureLoader.load("data/Crate.bmp");
  //-----------------------
  }
  
  
  public void display(GLAutoDrawable drawable)
  {
	GL gl = drawable.getGL();
	gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);    //�����ɫ����
	gl.glLoadIdentity();    //���þ���
	gl.glTranslatef(0.0f, 0.0f, -6.0f);    //����(Z�Ḻ����)�ƶ�6
    gl.glTranslatef(0.0f, 0.0f, -6.0f);    //����(Z�Ḻ����)�ƶ�6
    
    //----------------------����Ӳ���-------------------
    gl.glEnable(GL.GL_BLEND); //������
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    gl.glAlphaFunc(GL.GL_GREATER, 0);
    gl.glEnable(GL.GL_ALPHA);
    //--------------------------------------------
    
    
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, -20.0f);
    //�󶨵�2��ͼƬ
    pngtexture2.bind();
    gl.glTexEnvf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
    gl.glBegin(GL.GL_QUADS);
    // ǰ��
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-3.0f, -3.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(3.0f, -3.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(3.0f, 3.0f, 1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-3.0f, 3.0f, 1.0f);

    gl.glEnd();
    gl.glPopMatrix();
    
    gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

    //gl.glBindTexture(GL.GL_TEXTURE_2D, );//ָ���Ͱ�����(������ָ������)

    pngtexture1.bind();
    gl.glTexEnvf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
    gl.glBegin(GL.GL_QUADS);
    // ǰ��
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    // ����
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, -1.0f);
    // ����
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
    // ����
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    // ����
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, 1.0f);
    // ����
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    
    gl.glEnd();
    
    xrot += 0.3f;
    yrot += 0.2f;
    zrot += 0.4f;
    
    

  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
  {
	    GL gl = drawable.getGL();
	    
	    gl.glViewport(0, 0, w, h);   //�Ӽ�����
	    gl.glMatrixMode(GL.GL_PROJECTION);//��һ�������ջ
	    //gl.glLoadIdentity(); //���þ���
	    glu.gluPerspective(45.0, (float) w / (float) h, 1.0, 1000.0);//����͸�Ӿ���
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glLoadIdentity();
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged)
  {
	  
  }

  public void keyTyped(KeyEvent key)
  {
  }

  public void keyPressed(KeyEvent key)
  {

    switch (key.getKeyCode()) {
      case KeyEvent.VK_ESCAPE:
        System.exit(0);
        break;

      default:
        break;
    }

  }

  public void keyReleased(KeyEvent key)
  {
  }

  public void mouseClicked(MouseEvent e)
  {
  }

  public void mousePressed(MouseEvent e)
  {
  }

  public void mouseReleased(MouseEvent e)
  {
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }
} 