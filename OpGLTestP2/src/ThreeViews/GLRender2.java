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
public class GLRender2 implements GLEventListener {
       static int WIDTH,HEIGHT;
    static GLAutoDrawable DRAWABLE;
    V_Face vf[];
    V_Point vp[];
    Vector<Double> N_direction;
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
        gl.glColor3f(0.0f, 0.0f, 0.0f);

        gl.glBegin(GL.GL_LINES);
	gl.glVertex3d(0,0,0);
	gl.glVertex3d(25,0,0);
	gl.glVertex3d(0,0,0);
	gl.glVertex3d(0,25,0);
        gl.glVertex3d(0,0,0);
	gl.glVertex3d(0,0,25);
        gl.glEnd();




//       //分析smf文件
        gl.glEnable(GL.GL_DEPTH_TEST);
//////        // Rasterize the polygon as a wireframe
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
        gl.glColor3f(0.0f, 0f, 0.0f);

        if( vp!=null &&vf!=null)
                analyzeSMF(gl);
        //线消隐
      
              gl.glDisable(GL.GL_LIGHTING);
              gl.glDisable(GL.GL_LIGHT0);
                gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
                gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
                gl.glPolygonOffset(1.0f, 1.0f);
                gl.glColor3f(1.0f, 1.0f, 1.0f);

                    if(vp!=null && vf!=null)
                        analyzeSMF(gl);

                 gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);
        
//////////        if(TrueLight){
//////////                gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
//////////                gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
//////////                gl.glPolygonOffset(1.0f, 1.0f);
//////////                gl.glColor3f(1.0f, 1.0f, 1.0f);
//////////
//////////                 gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
//////////
//////////            gl.glEnable(GL.GL_LIGHTING);
//////////            gl.glEnable(GL.GL_LIGHT0);
//////////            gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
//////////            gl.glEnable(GL.GL_COLOR_MATERIAL);
//////////            float ambientProperties[]={0.2f,0.2f,0.2f,1.0f};
//////////            float diffuseProperties[]={0.7f,0.7f,0.7f,1.0f};
//////////            float specularProperties[]={0.7f,0.7f,0.7f,1.0f};
//////////            float position[]={-1.0f,1.0f,1.0f,0.0f};
//////////            float direction[]={0.0f,0,0f,-1.0f};
//////////            gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, FloatBuffer.wrap(ambientProperties));
//////////            gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, FloatBuffer.wrap(diffuseProperties));
//////////            gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, FloatBuffer.wrap(specularProperties));
//////////            gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, FloatBuffer.wrap(position));
//////////    //        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, FloatBuffer.wrap(direction));
////////////            gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, FloatBuffer.wrap(ambientProperties));
//////////                 if(vp!=null && vf!=null)
//////////                        analyzeSMF(gl);
//////////
//////////                 gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);
//////////        }

//        if(FaceHideBitmap!=null)
//        {
//            gl.glPointSize(2.0f);
//            for(int i=0;i<FaceHideBitmap.length;i++)
//            {
//                for(int j =0;j<FaceHideBitmap[i].length;j++)
//                {
//                    if(FaceHideBitmap[i][j].isBack())
//                        continue;
//                    gl.glColor3f(FaceHideBitmap[i][j].r, FaceHideBitmap[i][j].g, FaceHideBitmap[i][j].b);
//                    gl.glBegin(GL.GL_POINTS);
//                         gl.glVertex3d(FaceHideBitmap[i][j].x_old, FaceHideBitmap[i][j].y_old, FaceHideBitmap[i][j].z_old);
//                    gl.glEnd();
//                }
//            }
//        }
//        // Flush all drawing operations to the graphics card

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
                            gl.glBegin(GL.GL_POLYGON);
                            gl.glVertex3d(vp[vf[i].v1].x,vp[vf[i].v1].y,vp[vf[i].v1].z);
                            gl.glVertex3d(vp[vf[i].v2].x,vp[vf[i].v2].y,vp[vf[i].v2].z);
                            gl.glVertex3d(vp[vf[i].v3].x,vp[vf[i].v3].y,vp[vf[i].v3].z);
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
}
