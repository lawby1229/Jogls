package thu.cs.keg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.yourorghere.GL_Color;
import org.yourorghere.V_Face;
import org.yourorghere.V_Point;

import com.sun.opengl.util.texture.Texture;

public class GLRender implements GLEventListener, KeyListener {

	static int WIDTH, HEIGHT;
	static GLAutoDrawable DRAWABLE;
	int r;
	static int Sizen = 50;
	int delta;
	String Version = "";
	Vector<Integer> x, y, z;
	int cooLength = 50;
	private Texture pngtexture1, pngtexture2; // png图片

	public void init(GLAutoDrawable drawable) {
		System.out.println("init");

		x = new Vector<Integer>();
		y = new Vector<Integer>();
		z = new Vector<Integer>();
		x.add(cooLength);
		x.add(0);
		x.add(0);
		y.add(0);
		y.add(cooLength);
		y.add(0);
		z.add(0);
		z.add(0);
		z.add(cooLength);
		// drawCordinate(x, y, z, gl);
		// Use debug pipeline
		// drawable.setGL(new DebugGL(drawable.getGL()));

		GL gl = drawable.getGL();
		GLU glu = new GLU();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		System.err.println("INIT GL IS: " + gl.getClass().getName());

		// Enable VSync
		gl.setSwapInterval(1);

		// Setup the drawing area and shading mode
		// gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see
										// what happens.
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glEnable(GL.GL_TEXTURE_2D);// 启动纹理映射

		pngtexture1 = TextureLoader.load("data/map2.png");
		gl.glBindTexture(GL.GL_TEXTURE_2D, pngtexture1.getTarget());

		// getSMF("smfmodel/gear.smf");
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		System.out.println("reshap");
		WIDTH = width;
		HEIGHT = height;
		DRAWABLE = drawable;
		System.out.println("宽:" + Integer.toString(WIDTH) + " 高:"
				+ Integer.toString(HEIGHT));
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	public void display(GLAutoDrawable drawable) {
		System.out.println("dispalay");

		GL gl = drawable.getGL();
		setNewView(gl);
		r += 1;
		if (r > 360)
			r = 0;//

		gl.glPushMatrix();
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		// gl.glRotatef(r, 0.0f, 1.0f, 0.0f); // 整体旋转
		pngtexture1.bind();
		gl.glTexEnvf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_ENV_MODE, GL.GL_BLEND);
		float a = 20;
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(a, 0, a);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(a, 0, -a);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-a, 0, -a);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-a, 0, a);

		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();

		gl.glPointSize(4); // 点的大小
		// gl.glTranslatef(0.0f, 4.0f, 0.0f);
		// gl.glRotatef(r, 0.0f, 1.0f, 0.0f); // 整体旋转

		gl.glColor3f(1.0f, 0.0f, 0.0f);

		gl.glBegin(GL.GL_POLYGON);// 单个顶点
		gl.glVertex3f(0.0f, 1.0f, -1.0f);// a点
		gl.glVertex3f(-1.0f, -1.0f, 0.0f);// b点
		gl.glVertex3f(1.0f, -1.0f, 0.0f);// c点
		gl.glEnd();

		gl.glPointSize(7);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex3d(4, 0, 4);// c点
		gl.glVertex3d(-4, 0, 4);// c点
		gl.glEnd();
		gl.glPopMatrix();
		//

		//
		// 画坐标轴
		drawCordinate(x, y, z, gl);
		gl.glFlush();

	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		System.out.println("dischange");
	}

	private void setNewView(GL gl) {
		GLU glu = new GLU();
		// Clear the drawing area
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		// Reset the current matrix to the "identity"
		// gl.glLoadIdentity();
		gl.glViewport(0, 0, WIDTH, HEIGHT);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		// ///case1
		double d = 100;
		double n = Sizen;
		if (n <= 0)
			n = 1;

		gl.glOrtho(-WIDTH / n, WIDTH / n, -HEIGHT / n, HEIGHT / n, -d, d);

		// glu.gluPerspective(40.0f, 1.0, 1.0, 50.0);
		// 眼睛的坐标，眼睛看的地方的坐标，脑袋顶上方的坐标
		glu.gluLookAt(20, 15, 20, 0, 0, 0, 0, 1, 0);

	}

	// 叉乘
	private Vector<Double> xPUTA(Vector<Double> v12, Vector<Double> v23) {
		Vector<Double> Nv = new Vector<Double>();
		Nv.add(v12.elementAt(1) * v23.elementAt(2) - v23.elementAt(1)
				* v12.elementAt(2));
		Nv.add(v23.elementAt(0) * v12.elementAt(2) - v12.elementAt(0)
				* v23.elementAt(2));
		Nv.add(v12.elementAt(0) * v23.elementAt(1) - v12.elementAt(1)
				* v23.elementAt(0));
		return Nv;
	}

	// 点乘
	private double pPUTA(Vector<Double> N1, Vector<Double> N2) {
		double re = 0;
		re = N1.elementAt(0) * N2.elementAt(0) + N1.elementAt(1)
				* N2.elementAt(1) + N1.elementAt(2) * N2.elementAt(2);
		return re;
	}

	// 坐标转换
	public static void drawCordinate(Vector<Integer> x, Vector<Integer> y,
			Vector<Integer> z, GL gl) {
		gl.glPushMatrix();
		gl.glBegin(GL.GL_LINES);
		gl.glColor3f(1.0f, 0, 0);
		gl.glVertex3i(0, 0, 0);
		gl.glVertex3i(x.elementAt(0), x.elementAt(1), x.elementAt(2));
		gl.glColor3f(0, 1.0f, 0);
		gl.glVertex3i(0, 0, 0);
		gl.glVertex3i(y.elementAt(0), y.elementAt(1), y.elementAt(2));
		gl.glColor3f(0, 0, 1.0f);
		gl.glVertex3i(0, 0, 0);
		gl.glVertex3d(z.elementAt(0), z.elementAt(1), z.elementAt(2));
		gl.glEnd();
		gl.glPopMatrix();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
