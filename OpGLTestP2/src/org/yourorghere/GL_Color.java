/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yourorghere;

/**
 *
 * @author Law
 */
public class GL_Color {
   float r,g,b;
   double x_old,y_old,z_old;//在渲染坐标系中的坐标
//    double x_new,y_new,z_new;//在渲染坐标系中的坐标
   double Z_Buffer=Double.NEGATIVE_INFINITY;//在当前坐标系中Z的深度
   private boolean isBack=true;
    public GL_Color(){
//        this.r=(float)Math.random();
//        this.g=(float)Math.random();
//        this.b=(float)Math.random();
    }
    public GL_Color(float r,float g,float b) {
        this.r=r;
        this.g=g;
        this.b=b;
    }

    public void setOldPosition(double x,double y,double z)
    {
        this.x_old=x;
        this.y_old=y;
        this.z_old=z;
    }
      public void setOldPosition(V_Point p)
    {
        this.x_old=p.x;
        this.y_old=p.y;
        this.z_old=p.z;
    }
//    public void setNewPosition(double x,double y,double z)
//    {
//        this.x_new=x;
//        this.y_new=y;
//        this.z_new=z;
//    }
    public boolean isBack()
    {
        return isBack;
    }
    public void setBack(boolean b)
    {
        isBack=b;
        if(b)
        {
          this.r=0;
          this.g=0;
          this.b=0;
        }
    }
        public void setColor(float r,float g,float b)
    {
        this.r=r;
        this.g=g;
        this.b=b;
    }
}
