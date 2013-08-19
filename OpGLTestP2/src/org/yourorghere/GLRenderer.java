package org.yourorghere;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.Vector;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import javax.swing.RepaintManager;


/**
 * GLRenderer.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class GLRenderer implements GLEventListener {
    static int WIDTH,HEIGHT;
    static GLAutoDrawable DRAWABLE;

    static boolean drawCondinateble=false;
    static Vector<Double> a,b,c;

    Vector<Double> N= new Vector<Double>();
     Vector<Double> N2_xz= new Vector<Double>();
      Vector<Double> N2_py= new Vector<Double>();
    static Vector<Double> N_BASE= new Vector<Double>();
    static Vector<Double> N2_BASE_xz= new Vector<Double>();
     static Vector<Double> N2_BASE_py= new Vector<Double>();
    static  int Sizen=50;
    static  float far=-5;
     String Version="";
     int  Ver=0;
     int Face=0;
    V_Point vp[];
    V_Face  vf[];
    GL_Color[][] FaceHideBitmap;

    boolean LineHide=false;
    boolean TrueLight=false;
    boolean DelBackFace=false;

    boolean isZty=true;
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
        N_BASE.add(3.0);
        N_BASE.add(3.0);
        N_BASE.add(3.0);
        N.add(N_BASE.get(0));
        N.add(N_BASE.get(1));
        N.add(N_BASE.get(2));
       

        N2_BASE_py.add(0.0);
        N2_BASE_py.add(0.0);
        N2_py.add(0.0);
        N2_py.add(0.0);

         N2_BASE_xz.add(0.0);
        N2_xz.add(0.0);
        N2_xz.add(0.0);
        N2_xz.add(0.0);
        N2_xz.add(0.0);


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
        glu.gluLookAt(N.elementAt(0) , N.elementAt(1), N.elementAt(2), 0, 0, 0,0,1,0);//�你是
//   glu.gluLookAt(0,0,5, 0, 0, 0, 0, 1, 0);//�
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
       
    }

    public void display(GLAutoDrawable drawable) {System.out.println("dispalay");

        GL gl = drawable.getGL();

 
        setNewView(gl);

        // Drawing Using Triangles
        gl.glLineWidth(1.0f);


        gl.glColor3f(0.0f, 0.0f, 0.0f);

        gl.glBegin(GL.GL_LINES);
	gl.glVertex3d(0,0,0);
	gl.glVertex3d(25,0,0);
	gl.glVertex3d(0,0,0);
	gl.glVertex3d(0,25,0);
        gl.glVertex3d(0,0,0);
	gl.glVertex3d(0,0,25);
        gl.glEnd();

        if(drawCondinateble){
              gl.glColor3f(0.5f, 0f, 0.3f);
            drawCordinate(a, b, c, gl);
         }
       

       //分析smf文件
        gl.glEnable(GL.GL_DEPTH_TEST);
////        // Rasterize the polygon as a wireframe
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
        gl.glColor3f(0.0f, 0f, 0.0f);
     
        if(vp!=null && vf!=null)
                analyzeSMF(gl);
        //线消隐
        if(LineHide){
              gl.glDisable(GL.GL_LIGHTING);
              gl.glDisable(GL.GL_LIGHT0);
                gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
                gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
                gl.glPolygonOffset(1.0f, 1.0f);
                gl.glColor3f(1.0f, 1.0f, 1.0f);

                    if(vp!=null && vf!=null)
                        analyzeSMF(gl);

                 gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);
        }
        if(TrueLight){
                gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
                gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
                gl.glPolygonOffset(1.0f, 1.0f);
                gl.glColor3f(1.0f, 1.0f, 1.0f);
               
                 gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

            gl.glEnable(GL.GL_LIGHTING);
            gl.glEnable(GL.GL_LIGHT0);
            gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
            gl.glEnable(GL.GL_COLOR_MATERIAL);
            float ambientProperties[]={0.2f,0.2f,0.2f,1.0f};
            float diffuseProperties[]={0.7f,0.7f,0.7f,1.0f};
            float specularProperties[]={0.7f,0.7f,0.7f,1.0f};
            float position[]={-1.0f,1.0f,1.0f,0.0f};
            float direction[]={0.0f,0,0f,-1.0f};
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, FloatBuffer.wrap(ambientProperties));
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, FloatBuffer.wrap(diffuseProperties));
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, FloatBuffer.wrap(specularProperties));
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, FloatBuffer.wrap(position));
    //        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, FloatBuffer.wrap(direction));
//            gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, FloatBuffer.wrap(ambientProperties));
                 if(vp!=null && vf!=null)
                        analyzeSMF(gl);

                 gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);
        }

        if(FaceHideBitmap!=null)
        {
            gl.glPointSize(2.0f);
            for(int i=0;i<FaceHideBitmap.length;i++)
            {
                for(int j =0;j<FaceHideBitmap[i].length;j++)
                {
                    if(FaceHideBitmap[i][j].isBack())
                        continue;
                    gl.glColor3f(FaceHideBitmap[i][j].r, FaceHideBitmap[i][j].g, FaceHideBitmap[i][j].b);
                    gl.glBegin(GL.GL_POINTS);
                         gl.glVertex3d(FaceHideBitmap[i][j].x_old, FaceHideBitmap[i][j].y_old, FaceHideBitmap[i][j].z_old);
                    gl.glEnd();
                }
            }
        }
//        // Flush all drawing operations to the graphics card

     gl.glFlush();

    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {System.out.println("dischange");
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
         if(isZty){
            gl.glOrtho(-WIDTH/n, WIDTH/n, -HEIGHT/n, HEIGHT/n, -d, d);
            glu.gluLookAt(N.elementAt(0) , N.elementAt(1), N.elementAt(2), 0, 0, 0,0,1,0);//�你是
         }
         else{
             glu.gluPerspective(50.0f, 1.0, 1.0, 50.0);
             gl.glTranslated(N2_py.elementAt(0), N2_py.elementAt(1), far);
             gl.glRotated( N2_xz.elementAt(0)
                     ,  N2_xz.elementAt(1),  N2_xz.elementAt(2), N2_xz.elementAt(3));
         }
        
    
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }
    public void loadSMF(String filename){
        getSMF(filename);
    }
    private void getSMF(String filename)
    {
            int ps=0;
            int fs=0;
            try{
                 BufferedReader br=new BufferedReader(new FileReader(filename));
                 int j=0;
                  for(int i=0;;i++){
                    String s;
                        s=br.readLine();
                    if(s==null)
                        break;

                    String[]t=s.split(new String("\\s"));//�ַ�ָ���־
                    //�
                    if(i==0)
                    {
                        Version=t[1];
                    }
                    else if(i==1)
                    {
                        Ver=Integer.parseInt(t[1]);
                        vp=new V_Point[Ver];
                    }
                    else if(i==2)
                    {
                        Face=Integer.parseInt(t[1]);
                        vf=new V_Face[Face];
                    }
                    else if(i>2  && ps<Ver)
                    {
                            if(t[0].equals("v")){
                                  if(t.length==4){
                                        vp[ps]=new V_Point(Double.parseDouble(t[1]),
                                         Double.parseDouble(t[2]), Double.parseDouble(t[3]));
                                  }
                                  else{
                                      double a[]=new double[3];
                                      int k=0;
                                      for(int h=1;h<t.length;h++)
                                      {
                                          if(!t[h].equals("") && !t[h].equals(" "))
                                          {
                                              a[k]=Double.parseDouble(t[h]);
                                              k++;
                                          }

                                      }
                                       vp[ps]=new V_Point(a[0],a[1],a[2]);
                                  }
                                   ps++;
                            }
                    }
                    else if(i>2+Ver && fs<Face)
			{
                             if(t.length!=4){
                                      double a[]=new double[3];
                                      int k=1;
                                      for(int h=1;h<t.length;h++)
                                      {
                                          if(!t[h].equals("") && !t[h].equals(" "))
                                          {
                                              t[k]=t[h];
                                              k++;
                                          }

                                      }
                                  }
                            if(t[0].equals("f")){
                                if(Version.equals("1.0")){
                                    int a=Integer.parseInt(t[1]);
                                    int b=Integer.parseInt(t[2]);
                                    int c=Integer.parseInt(t[3]);
                                        vf[fs]=new V_Face(a, b, c);
                                 }
                                else if(Version.equals("1.1"))
                                {
                                    int a=Integer.parseInt(t[1])-1;
                                    int b=Integer.parseInt(t[2])-1;
                                    int c=Integer.parseInt(t[3])-1;
                                        vf[fs]=new V_Face(a, b, c);
                                }
                                fs++;
                            }
			}
                  }
            }
            catch(Exception e)
            {
                System.out.println("���错误读smf����"+e);
            }
    }
    private void analyzeSMF( GL gl){
//            gl.glColor3f(1.0f, 0.0f, 0.0f);
            gl.glLineWidth(2.0f);
            gl.glEnable(GL.GL_NORMALIZE);
            for(int i=0;i<vf.length;i++){
                            if(!isFrontFace(vp[vf[i].v1],vp[vf[i].v2],vp[vf[i].v3])){
                                vf[i].isBackFace=true;
                                if(DelBackFace)
                                    continue;
                            }
                            else
                                vf[i].isBackFace=false;
                            gl.glBegin(GL.GL_POLYGON);
                            gl.glVertex3d(vp[vf[i].v1].x,vp[vf[i].v1].y,vp[vf[i].v1].z);
                            gl.glVertex3d(vp[vf[i].v2].x,vp[vf[i].v2].y,vp[vf[i].v2].z);
                            gl.glVertex3d(vp[vf[i].v3].x,vp[vf[i].v3].y,vp[vf[i].v3].z);
                            gl.glEnd();
            }
 

    }
    //判断是否为前向面
    private boolean isFrontFace(V_Point v1,V_Point v2,V_Point v3)
    {
        double J;
        Vector<Double> v12=new Vector<Double>();
        Vector<Double> v23=new Vector<Double>();
        v12.add(v2.x-v1.x);v12.add(v2.y-v1.y);v12.add(v2.z-v1.z);
        v23.add(v3.x-v2.x);v23.add(v3.y-v2.y);v23.add(v3.z-v2.z);
        J=pPUTA(N, xPUTA(v12, v23));
        if(J>=0)
            return true;
        else
            return false;
    }
    //叉乘
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
    public void moveUp(double y)
    {
        if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
                vp[i].y=vp[i].y+y;
        }
    }
    public void moveDown(double y)
    {
        if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
                vp[i].y=vp[i].y-y;
        }
    }
    public void moveLeft(double x)
    {
        if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
                vp[i].x=vp[i].x-x;
        }
    }
    public void moveRight(double x)
    {
        if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
                vp[i].x=vp[i].x+x;
        }
    }
    public void moveIn(double z)
    {
        if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
                vp[i].z=vp[i].z-z;
        }
    }
    public void moveOut(double z)
    {
        if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
                vp[i].z=vp[i].z+z;
        }
    }
    public void turnXZ(double fai)
    {
         if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
            {
                double x_temp=vp[i].x;
                double z_temp=vp[i].z;
                vp[i].x=x_temp*Math.cos(fai)-z_temp*Math.sin(fai);
                vp[i].z=z_temp*Math.cos(fai)+x_temp*Math.sin(fai);
            }
        }
    }
        public void turnYZ(double fai)
    {
         if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
            {
                double y_temp=vp[i].y;
                double z_temp=vp[i].z;
                vp[i].y=y_temp*Math.cos(fai)-z_temp*Math.sin(fai);
                vp[i].z=z_temp*Math.cos(fai)+y_temp*Math.sin(fai);
            }
        }
    }
           public void turnXY(double fai)
    {
         if(vp!=null)
        {
            for(int i=0;i<vp.length;i++)
            {
                double y_temp=vp[i].y;
                double x_temp=vp[i].x;
                vp[i].y=y_temp*Math.cos(fai)-x_temp*Math.sin(fai);
                vp[i].x=x_temp*Math.cos(fai)+y_temp*Math.sin(fai);
            }
        }
    }
    //坐标转换
    public static void drawCordinate(Vector<Double> a,Vector<Double> b,Vector<Double> c, GL gl)
    {
        gl.glBegin(GL.GL_LINES);
	gl.glVertex3d(0,0,0);
	gl.glVertex3d(a.elementAt(0),a.elementAt(1),a.elementAt(2));
	gl.glVertex3d(0,0,0);
	gl.glVertex3d(b.elementAt(0),b.elementAt(1),b.elementAt(2));
        gl.glVertex3d(0,0,0);
	gl.glVertex3d(c.elementAt(0),c.elementAt(1),c.elementAt(2));
        gl.glEnd();
    }
}

