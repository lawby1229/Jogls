/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ThreeViews;

import java.util.Vector;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import org.yourorghere.V_Face;
import org.yourorghere.V_Point;
/**
 *
 * @author Law
 */
public class GLRender4 implements GLEventListener {
       static int WIDTH,HEIGHT;
    static GLAutoDrawable DRAWABLE;
    V_Face vf[];
    V_Point vp[],vp_new[];
    Vector<Double> N_direction;
    Vector<Double> N;
   static double Sizen=50;
 public void init(GLAutoDrawable drawable) {System.out.println("init");
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();

        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.



//       getSMF("smfmodel/gear.smf");
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {System.out.println("reshap");
        WIDTH=width;
        HEIGHT=height;
        DRAWABLE= drawable;
         System.out.println("宽:"+Integer.toString(WIDTH)+" 高:"+Integer.toString(HEIGHT));
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
            height = 1;
        }

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        /////case1

         double d=10;
         double n=Sizen;
//         if(n<=0)
//             n=1;
         //
        gl.glOrtho(-width/n, width/n, -height/n, height/n, -d, d);

        ///case2
//     glu.gluPerspective(45.0f, 1.0, 1.0, 20.0);
        glu.gluLookAt(N_direction.elementAt(0) , N_direction.elementAt(1), N_direction.elementAt(2), 0, 0, 0,0,1,0);//�你是
//   glu.gluLookAt(0,0,5, 0, 0, 0, 0, 1, 0);//�
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    public void display(GLAutoDrawable drawable) {System.out.println("dispalay");

        GL gl = drawable.getGL();

  ;
        setNewView(gl);
        // Move the "drawing cursor" around

      //�
//  gl.glTranslatef(0.0f,0.0f,-1.0f);

  //  gl.glMatrixMode(GL.GL_MODELVIEW);
        // Drawing Using Triangles
        gl.glLineWidth(1.0f);

//        if(isFucked)
//            gl.glColor3f(1.0f, 0.0f, 1.0f);
//        else
//        gl.glColor3f(0.0f, 0.0f, 0.0f);
//
//        gl.glBegin(GL.GL_LINES);
//	gl.glVertex3d(0,0,0);
//	gl.glVertex3d(25,0,0);
//	gl.glVertex3d(0,0,0);
//	gl.glVertex3d(0,25,0);
//        gl.glVertex3d(0,0,0);
//	gl.glVertex3d(0,0,25);
//        gl.glEnd();

       //分析smf文件
        gl.glColor3f(0.5f, 0.0f, 0.0f);

        if( vp!=null &&vf!=null)
        {
            vp_new=changeCoordinate(vp, N);
                analyzeSMF(gl);
        }
        //线消隐
     gl.glFlush();

    }
 private void setNewView(GL gl){
              GLU glu = new GLU();
                    // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glViewport(0, 0, WIDTH, HEIGHT);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        /////case1
         double d=100;
         double n=Sizen;
         if(n<=0)
             n=1;
            gl.glOrtho(-WIDTH/n, WIDTH/n, -HEIGHT/n, HEIGHT/n, -d, d);
            glu.gluLookAt(N_direction.elementAt(0) , N_direction.elementAt(1), N_direction.elementAt(2), 0, 0, 0,0,1,0);//�你是



        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }
    private void analyzeSMF( GL gl){

            gl.glLineWidth(2.0f);
            gl.glEnable(GL.GL_NORMALIZE);
            for(int i=0;i<vf.length;i++){
                            gl.glBegin(GL.GL_LINE_LOOP);
                            gl.glVertex2d(vp_new[vf[i].v1].x,vp_new[vf[i].v1].y);
                            gl.glVertex2d(vp_new[vf[i].v2].x,vp_new[vf[i].v2].y);
                            gl.glVertex2d(vp_new[vf[i].v3].x,vp_new[vf[i].v3].y);
                            gl.glEnd();
            }


    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {System.out.println("dischange");
    }
     public void setDirection(double a,double b,double c)
     {
         N_direction=new Vector<Double>(3);
         N_direction.add(a);
         N_direction.add(b);
         N_direction.add(c);
     }
      private V_Point[] changeCoordinate(V_Point vp[],Vector<Double> N)
    {
           Vector<Double> X_pie,Y_pie,Z_pie;
        V_Point[] re = new V_Point[vp.length];

        X_pie= new Vector<Double>(3);
        Y_pie= new Vector<Double>(3);
        Z_pie= new Vector<Double>(3);
        Vector<Double> y_old=new Vector<Double>(3); y_old.add(0.0);y_old.add(1.0);y_old.add(0.0);
        //计算x在新坐标系的单位向量，并用原来坐标系表示
         Vector<Double>  Xpt=xPUTA(y_old, N);
        double x_mol=Math.sqrt(Xpt.elementAt(0)*Xpt.elementAt(0)+Xpt.elementAt(1)*Xpt.elementAt(1)+Xpt.elementAt(2)*Xpt.elementAt(2));
        X_pie.add(Xpt.elementAt(0)/x_mol);
        X_pie.add(Xpt.elementAt(1)/x_mol);
        X_pie.add(Xpt.elementAt(2)/x_mol);
        //计算z在新坐标系的单位向量，并用原来坐标系表示
        double z_mol=Math.sqrt(N.elementAt(0)*N.elementAt(0)+N.elementAt(1)*N.elementAt(1)+N.elementAt(2)*N.elementAt(2));
        Z_pie.add(N.elementAt(0)/z_mol);
        Z_pie.add(N.elementAt(1)/z_mol);
        Z_pie.add(N.elementAt(2)/z_mol);
        //计算y在新坐标系的单位向量，并用原来坐标系表示
        Y_pie=xPUTA(Z_pie, X_pie);




        for(int i =0 ;i<re.length;i++)
        {
            double x,y,z;
                Vector<Double> temp= new Vector<Double>(3);
                temp.add(vp[i].x);
                temp.add(vp[i].y);
                temp.add(vp[i].z);
            x=pPUTA(temp, X_pie);
            y=pPUTA(temp, Y_pie);
            z=pPUTA(temp, Z_pie);

            re[i]= new V_Point(x,y,z);
        }

        return re;
    }
       private Vector<Double> xPUTA(Vector<Double> v12,Vector<Double> v23)
    {
            Vector<Double> Nv=new Vector<Double>();
             Nv.add(v12.elementAt(1)*v23.elementAt(2)-v23.elementAt(1)*v12.elementAt(2));
            Nv.add(v23.elementAt(0)*v12.elementAt(2)-v12.elementAt(0)*v23.elementAt(2));
            Nv.add(v12.elementAt(0)*v23.elementAt(1)-v12.elementAt(1)*v23.elementAt(0));
        return Nv;
    }
    //点乘
    private double pPUTA(Vector<Double> N1,Vector<Double> N2)
    {
        double re=0;
        re=N1.elementAt(0)*N2.elementAt(0)+
                N1.elementAt(1)*N2.elementAt(1)+
                N1.elementAt(2)*N2.elementAt(2);
        return re;
    }
}
