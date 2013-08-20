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
  private Animator animator;//添加一个次类的实例
  //添加图片，哎 以前都没发现这个有趣的东西，还是不够了解啊
  //走的太高，内部都没有了解啊
  private Texture  pngtexture1,pngtexture2; //png图片
  
  private float xrot;//绕X轴
  private float yrot;//绕Y轴
  private float zrot;//绕Z轴
  


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
	    gl.glClearDepth(1.0f);							// 设置深度缓存
		gl.glEnable(GL.GL_DEPTH_TEST);					// 启用深度测试
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);			// 告诉系统对透视进行修正
	    animator=new Animator(canvas);//实例化对canvas，刷新
	    
 //--------------新添加代码-------
    gl.glEnable(GL.GL_TEXTURE_2D);//启动纹理映射
    pngtexture1 = TextureLoader.load("data/jogl.png");
    pngtexture2 = TextureLoader.load("data/Crate.bmp");
  //-----------------------
  }
  
  
  public void display(GLAutoDrawable drawable)
  {
	GL gl = drawable.getGL();
	gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);    //清除颜色缓冲
	gl.glLoadIdentity();    //重置矩阵
	gl.glTranslatef(0.0f, 0.0f, -6.0f);    //向内(Z轴负方向)移动6
    gl.glTranslatef(0.0f, 0.0f, -6.0f);    //向内(Z轴负方向)移动6
    
    //----------------------新添加部分-------------------
    gl.glEnable(GL.GL_BLEND); //允许混合
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    gl.glAlphaFunc(GL.GL_GREATER, 0);
    gl.glEnable(GL.GL_ALPHA);
    //--------------------------------------------
    
    
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, -20.0f);
    //绑定第2张图片
    pngtexture2.bind();
    gl.glTexEnvf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
    gl.glBegin(GL.GL_QUADS);
    // 前面
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-3.0f, -3.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(3.0f, -3.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(3.0f, 3.0f, 1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-3.0f, 3.0f, 1.0f);

    gl.glEnd();
    gl.glPopMatrix();
    
    gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

    //gl.glBindTexture(GL.GL_TEXTURE_2D, );//指定和绑定纹理(这里是指定纹理)

    pngtexture1.bind();
    gl.glTexEnvf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
    gl.glBegin(GL.GL_QUADS);
    // 前面
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    // 后面
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, -1.0f);
    // 上面
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
    // 下面
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    // 右面
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, -1.0f, 1.0f);
    // 左面
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
	    
	    gl.glViewport(0, 0, w, h);   //视见区域
	    gl.glMatrixMode(GL.GL_PROJECTION);//哪一个矩阵堆栈
	    //gl.glLoadIdentity(); //重置矩阵
	    glu.gluPerspective(45.0, (float) w / (float) h, 1.0, 1000.0);//创建透视矩阵
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