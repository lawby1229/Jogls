/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yourorghere;

import java.util.Vector;

/**
 *
 * @author Law
 */
public class FaceHide_Facade {
    Vector<Double> N;
    Vector<Double> X_pie,Y_pie,Z_pie;
    Face_Detail F_face[];//每个多边形的具体信息
    V_Point vp_new[];
    double x_Left=Double.POSITIVE_INFINITY, x_Right=Double.NEGATIVE_INFINITY;
    double y_Down=Double.POSITIVE_INFINITY, y_Up=Double.NEGATIVE_INFINITY;
    static double delta_=0.01;//x增量
    public FaceHide_Facade(V_Point vp_old[],V_Face vf[],Vector<Double> N){
        this.N=N;

        //把每个点先转换坐标
         vp_new=changeCoordinate(vp_old,N);

         //得到新的面的结构数组
         F_face=getF_Detail(vp_new,vp_old,vf);

         //将新得到每个面的结构数组按照y升次排序
         quick_by_minY(F_face,0,F_face.length-1);
         int i=0;
    }
    private V_Point[] changeCoordinate(V_Point vp[],Vector<Double> N)
    {
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
      
        System.out.println("新坐标");
        for(int i=0 ;i<3;i++){
        System.out.print((X_pie.elementAt(i)).toString()+"， ");
        } System.out.println();
          for(int i=0 ;i<3;i++){
        System.out.print((Y_pie.elementAt(i)).toString()+"， ");
        } System.out.println();
          for(int i=0 ;i<3;i++){
        System.out.print((Z_pie.elementAt(i)).toString()+", ");
        }System.out.println();
        GLRenderer.drawCondinateble=true;
        GLRenderer.a=X_pie;
        GLRenderer.b=Y_pie;
        GLRenderer.c=Z_pie;


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
            x=formatDN(x);
            y=formatDN(y);
            z=formatDN(z);
            if(x<x_Left)
                x_Left=x;
            if(x>x_Right)
                x_Right=x;
            if(y<y_Down)
                y_Down=y;
            if(y>y_Up)
                y_Up=y;

            re[i]= new V_Point(x,y,z);
        }
//        x_Left=formatDN(x_Left-delta_);
//        x_Right=formatDN(x_Right+delta_);
//        y_Down=formatDN(y_Down-delta_);
//        y_Up=formatDN(y_Up+delta_);
//        x_Left=(double)((int)(x_Left/delta_-1));
//        x_Right=(double)((int)(x_Right/delta_+1));
//        y_Down=(double)((int)(y_Down/delta_-1));
//        y_Up=(double)((int)(y_Up/delta_+1));
        return re;
    }
   private  Face_Detail[] getF_Detail(V_Point vp_new[],V_Point vp_old[],V_Face vf[])
   {
       V_Point v1,v2,v3,v1p,v2p,v3p;
      Face_Detail  F_f[]= new Face_Detail[vf.length];
      for(int i =0; i<vf.length;i++)
      {
          v1=vp_new[vf[i].v1];//保存在当前坐标系的点
          v2=vp_new[vf[i].v2];
          v3=vp_new[vf[i].v3];
          v1p=vp_old[vf[i].v1];//保存在原坐标系中的点
          v2p=vp_old[vf[i].v2];
          v3p=vp_old[vf[i].v3];
          F_f[i]= new Face_Detail(v1, v2, v3,v1p,v2p,v3p,N,vf[i].isBackFace,GLRenderer.Sizen);
      }
      return F_f;
   }
private void quick_by_minY(Face_Detail a[], int zuo, int you) /* 从小到大*/
{
       if(zuo >= you) return;

	int i, j;
        double key;
        Face_Detail temp;
	key = a[you].min_Y;
	i = zuo -1;
	if(zuo >= you) return;
	for(j = zuo; j< you; j++)
	{
		if(a[j].min_Y <= key)
		{
			i++;
			temp = a[j];
			a[j] = a[i];
			a[i] = temp;
		}
	}
	i++;
	temp = a[j];
	a[j] = a[i];
	a[i] = temp;

	quick_by_minY(a, zuo, i - 1);
	quick_by_minY(a, i + 1, you);

}
public GL_Color[][] runFaceHide()
{
    int width=(int)((x_Right-x_Left)/delta_);
    int height=(int)((y_Up-y_Down)/delta_);
    GL_Color[][] bitmap=new GL_Color[height+1][width+1];
    for(int i=0;i<bitmap.length;i++)
    {
        for(int j=0;j<bitmap[i].length;j++)
        {
            bitmap[i][j]=new GL_Color(0,0,0);
        }
    }
    double j=y_Down;
    Vector<Face_Detail> APT=new Vector<Face_Detail>();
    Vector<EdgePair> AET= new Vector<EdgePair>();
    int APT_num=0;
    int  bm_p_i=(int)((j-y_Down)/delta_);
    int bm_p_j=0;
    while(j<y_Up )
    {
      
    //1,2,3
        while(APT_num<F_face.length &&F_face[APT_num].min_Y<=j ){
            if(F_face[APT_num].isFlatFace ||F_face[APT_num].isBackFace )
            {
                APT_num++;
                continue;
            }
            V_Line[] lr=F_face[APT_num].chooserTwoLines();//取多边形的两条边
             F_face[APT_num].chooserTwoLines();

//            APT.add(F_face[APT_num]);//将多边形加到多边形表中
            AET.add(new EdgePair(lr[0], lr[1], APT_num));//将其边对加入边对表中
           
            APT_num++;
        }
    //4
        for(int i =0;i<AET.size();i++)
        {
            EdgePair temp_EP=AET.elementAt(i);
            int face_num=temp_EP.FaceNum;
            double tempxd=temp_EP.xl;
            while(tempxd<=temp_EP.xr)
            {
                
                bm_p_j=(int)((tempxd-x_Left)/delta_);
//                if(bm_p_j>95 && bm_p_j<100 && i==1)
//                    System.out.print(tempxd);
                if(temp_EP.zl>bitmap[bm_p_i][bm_p_j].Z_Buffer)
                {
                    bitmap[bm_p_i][bm_p_j].setBack(false);
                    bitmap[bm_p_i][bm_p_j].Z_Buffer=temp_EP.zl;
                    bitmap[bm_p_i][bm_p_j].setOldPosition(
                            getOld_Point(new V_Point(tempxd, j, bitmap[bm_p_i][bm_p_j].Z_Buffer)));//
                    bitmap[bm_p_i][bm_p_j].setColor(F_face[face_num].R,
                            F_face[face_num].G, F_face[face_num].B);
                }
                tempxd=tempxd+delta_;
                temp_EP.zl=temp_EP.zl-(delta_*F_face[face_num].a/F_face[face_num].c);//zi+1=-[a(i+1)+bj+d]/c= zi-a/c
            }
        }
//        for(int i =0 ;i<APT.size();i++)
//        {
//            if(APT.elementAt(i).max_Y==j){
//                APT.remove(i);
//                i--;
//                continue;
//            }
//        }
        //处理AET的边
        for(int i=0 ;i<AET.size();i++)
        {
            EdgePair temp_EP=AET.elementAt(i);
            int face_num=temp_EP.FaceNum;
            Face_Detail temp_face=F_face[face_num];
            if(temp_EP.Yl_max<=j && temp_EP.Yr_max<=j)//如果到了两个边的最上方，删除AET中的边对
            {
                AET.remove(i);
                i--;
                continue;
            }
            else if(temp_EP.Yl_max<=j )//左边的边到头了，且有下家
            {
                if(temp_face.Last_Line!=null)
                {
                    temp_EP.changeLeftEdge(temp_face.Last_Line);
                    temp_face.Last_Line=null;
                    if(temp_EP.line_R.a!=0)
                        temp_EP.xr=temp_EP.xr-delta_*temp_EP.line_R.b/temp_EP.line_R.a;
                }
                else continue;

            }
            else if(temp_EP.Yr_max<=j)//右边的边到头了，且有下家
            {
                if(temp_face.Last_Line!=null)
                {
                   temp_EP.changeRightEdge(temp_face.Last_Line);
                   temp_face.Last_Line=null;
                   if(temp_EP.line_L.a!=0)
                        temp_EP.xl=temp_EP.xl-delta_*temp_EP.line_L.b/temp_EP.line_L.a;//xj+1=-[q(j+1)+r]/p= xj -q/p    px+qy+r=0
                   temp_EP.zl=-( (temp_face.a*temp_EP.xl+temp_face.b*j+temp_face.d)/temp_face.c);//z=-(ax+by+d)/c
                }
                else
                    continue;
                

            }
            else {
                if(temp_EP.line_R.a!=0)
                    temp_EP.xr=temp_EP.xr-delta_*temp_EP.line_R.b/temp_EP.line_R.a;
                if(temp_EP.line_L.a!=0)
                    temp_EP.xl=temp_EP.xl-delta_*temp_EP.line_L.b/temp_EP.line_L.a;//xj+1=-[q(j+1)+r]/p= xj -q/p    px+qy+r=0
                temp_EP.zl=-( (temp_face.a*temp_EP.xl+temp_face.b*(j+delta_)+temp_face.d)/temp_face.c);//z=-(ax+by+d)/c
            }

        }
        bm_p_i++;
        j=j+delta_;
        j=formatDN(j);
        System.out.println("到了："+Double.toString(j));
    }
    return bitmap;
}
private Vector<Double> xPUTA(Vector<Double> v12,Vector<Double> v23)
    {
            Vector<Double> Nv=new Vector<Double>();
//            Nv.add(v12.y*v23.z-v23.y*v23.z);
//            Nv.add(v23.x*v12.z-v12.x*v23.z);
//            Nv.add(v12.x*v23.y-v12.y*v23.x);
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
        private double formatDN(double  num)//保留数的delta_位小数
    {
        double tempD=num/delta_;
        int tempI=(int)Math.rint(tempD);
        double reD=tempI*delta_;
        return reD;
    }
    private Vector<Double> formatVec(Vector<Double>  vd)//保留向量数的delta_位小数
    {
        Vector<Double> re=new Vector<Double>(); ;
        for(int i=0;i<vd.size();i++)
        {
            re.add(formatDN(vd.elementAt(i)));
        }
        return re;
    }
    private V_Point getOld_Point(V_Point NP)
    {
        double ox,oy,oz;
        ox=NP.x*X_pie.elementAt(0)+NP.y*Y_pie.elementAt(0)+NP.z*Z_pie.elementAt(0);
        oy=NP.x*X_pie.elementAt(1)+NP.y*Y_pie.elementAt(1)+NP.z*Z_pie.elementAt(1);
        oz=NP.x*X_pie.elementAt(2)+NP.y*Y_pie.elementAt(2)+NP.z*Z_pie.elementAt(2);
        V_Point OP=new V_Point(ox,oy,oz);
        return OP;
    }
 
}
//多边形Y表中只保存多边形的序号和其顶点的最大y坐标。
class Face_Detail{
    V_Point v1_new,v2_new,v3_new;//当前坐标系
    V_Point v1_old,v2_old,v3_old;//原来坐标系
    V_Line v12,v23,v31;
    double max_Y,min_Y;
    double a,b,c,d;//新面方程的系数ax+bx+cz+d=0;
    V_Line Last_Line;
    float R,G,B;
    boolean isFlatFace=false;
    boolean isBackFace=false;
    int Screenbilv;
    Face_Detail(V_Point new1,V_Point new2,V_Point new3,V_Point old1,V_Point old2,V_Point old3 ,Vector<Double> N,boolean isBackFace,int Screenbilv){
        this.v1_new=new1;
        this.v2_new=new2;
        this.v3_new=new3;
        this.v1_old=old1;
        this.v2_old=old2;
        this.v3_old=old3;
        this.isBackFace=isBackFace;
        this.Screenbilv=Screenbilv;
//        this.R=(float)((v1_new.x*Screenbilv)%1.0f);
//        this.G=(float)((v1_new.y*Screenbilv)%1.0f);
//        this.B=(float)((v1_new.z*Screenbilv)%1.0f);
        this.R=(float)Math.random();
        this.G=(float)Math.random();
        this.B=(float)Math.random();
        if (v1_new.y<=v2_new.y && v1_new.y <=v3_new.y  )
            min_Y=v1_new.y;
        else if(v2_new.y<= v1_new.y && v2_new.y<=v3_new.y)
            min_Y=v2_new.y;
        else if(v3_new.y<= v1_new.y && v3_new.y<=v2_new.y)
            min_Y=v3_new.y;

         if (v1_new.y>=v2_new.y && v1_new.y >=v3_new.y  )
            max_Y=v1_new.y;
        else if(v2_new.y>= v1_new.y && v2_new.y>=v3_new.y)
            max_Y=v2_new.y;
        else if(v3_new.y>= v1_new.y && v3_new.y>=v2_new.y)
            max_Y=v3_new.y;

        //求 a,b,c,d
        Vector<Double> v_temp1=new Vector<Double>();
        Vector<Double> v_temp2=new Vector<Double>();
        v_temp1.add(new2.x-new1.x);
        v_temp1.add(new2.y-new1.y);
        v_temp1.add(new2.z-new1.z);
        v_temp2.add(new3.x-new2.x);
        v_temp2.add(new3.y-new2.y);
        v_temp2.add(new3.z-new2.z);
        Vector<Double> v_tempN=xPUTA(v_temp1, v_temp2);
        a=v_tempN.elementAt(0);
        b=v_tempN.elementAt(1);
        c=v_tempN.elementAt(2);
        d=-(a*new1.x+b*new1.y+c*new1.z);
        Vector<Double> Np=new Vector<Double>(3);
        Np.add(0.0);Np.add(0.0);Np.add(1.0);
        double chacha=pPUTA(v_tempN,Np );
        if((chacha>-0.00001 && chacha<0.00001)  || isSameFlatPoint(new1, new2) || isSameFlatPoint(new2, new3) || isSameFlatPoint(new3, new1))
        {
            isFlatFace=true;
            return;
        }
        //增加边
        v12=new V_Line(new1, new2);
        v23=new V_Line(new2, new3);
        v31=new V_Line(new3, new1);

    }
    public V_Line[] chooserTwoLines()
    {
        V_Line[] re= new V_Line[2];
            if(v12.Y_min==v23.Y_min && v12.Y_max==v23.Y_max  )//上下点y值均相等
            {
                if(v12.v1.x==v23.v1.x && v12.v2.x<v23.v2.x)//倒三角
                {
                    re[0]=v12;
                    re[1]=v23;
                }
                else if(v12.v1.x==v23.v1.x && v12.v2.x>v23.v2.x)//倒三角
                {
                    re[0]=v23;
                    re[1]=v12;
                }
                  else if(v12.v2.x==v23.v2.x && v12.v1.x<v23.v1.x)//正三角
                {
                    re[0]=v12;
                    re[1]=v23;
                }
                else if(v12.v2.x==v23.v2.x && v12.v1.x>v23.v1.x)//正三角
                {
                    re[0]=v23;
                    re[1]=v12;
                }
                Last_Line=null;
            }
            else if(v31.Y_min==v23.Y_min && v31.Y_max==v23.Y_max  )//上下点y值均相等
            {
                if(v31.v1.x==v23.v1.x && v31.v2.x<v23.v2.x)//倒三角
                {
                    re[0]=v31;
                    re[1]=v23;
                }
                else if(v31.v1.x==v23.v1.x && v31.v2.x>v23.v2.x)//倒三角
                {
                    re[0]=v23;
                    re[1]=v31;
                }
                  else if(v31.v2.x==v23.v2.x && v31.v1.x<v23.v1.x)//正三角
                {
                    re[0]=v31;
                    re[1]=v23;
                }
                else if(v31.v2.x==v23.v2.x && v31.v1.x>v23.v1.x)//正三角
                {
                    re[0]=v23;
                    re[1]=v31;
                }
                 Last_Line=null;
            }
             else if(v31.Y_min==v12.Y_min && v31.Y_max==v12.Y_max  )//上下点y值均相等
            {
                if(v31.v1.x==v12.v1.x && v31.v2.x<v12.v2.x)//倒三角
                {
                    re[0]=v31;
                    re[1]=v12;
                }
                else if(v31.v1.x==v12.v1.x && v31.v2.x>v12.v2.x)//倒三角
                {
                    re[0]=v12;
                    re[1]=v31;
                }
                  else if(v31.v2.x==v12.v2.x && v31.v1.x<v12.v1.x)//正三角
                {
                    re[0]=v31;
                    re[1]=v12;
                }
                else if(v31.v2.x==v12.v2.x && v31.v1.x>v12.v1.x)//正三角
                {
                    re[0]=v12;
                    re[1]=v31;
                }
                 Last_Line=null;
            }
             else if (v12.Y_min==v23.Y_min)
             {
                if(isRight(v23.v2, v12.v2 ,v23.v1))
                {
                    re[0]=v12;
                    re[1]=v23;
                }
                else
                {
                    re[0]=v23;
                    re[1]=v12;
                }
                    Last_Line=v31;
             }
             else if(v23.Y_min==v31.Y_min)
             {
                if(isRight(v31.v2 ,v23.v2,v23.v1))
                {
                    re[0]=v23;
                    re[1]=v31;
                }
                else
                {
                    re[0]=v31;
                    re[1]=v23;
                }
                    Last_Line=v12;
             }
             else if(v31.Y_min==v12.Y_min)
             {
                if(isRight(v12.v2, v31.v2,v31.v1))
                {
                    re[0]=v31;
                    re[1]=v12;
                }
                else
                {
                    re[0]=v12;
                    re[1]=v31;
                }
                Last_Line=v23;
             }
        return re;
    }
    private Vector<Double> xPUTA(Vector<Double> v12,Vector<Double> v23)
    {
            Vector<Double> Nv=new Vector<Double>();
//            Nv.add(v12.y*v23.z-v23.y*v23.z);
//            Nv.add(v23.x*v12.z-v12.x*v23.z);
//            Nv.add(v12.x*v23.y-v12.y*v23.x);
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
    private boolean isRight(V_Point A,V_Point B,V_Point N)
    {
        double a;
        double x1,y1,x2,y2;
        x1=A.x-N.x;
        y1=A.y-N.y;
        x2=B.x-N.x;
        y2=B.y-N.y;
        a=x1*y2-x2*y1;
        if(a>=0)
            return true;
        else
            return false;
    }
    private boolean isSameFlatPoint(V_Point A,V_Point B)
    {
        if(A.x==B.x && A.y==B.y )
        {
            return true;
        }
        else
            return false;
    }
//    private boolean isSameInLine(V_Point A,V_Point B,V_Point C)
//    {
//        if(A.x==B.x && B.x==C.x)
//            return true;
//        else if(A.y)
//    }
}
class EdgePair
{
    V_Line line_L,line_R;
    double zl,xl,xr,Yl_max,Yr_max;
    float r,g,b;
    int FaceNum=0;
//    public EdgePair(double xl,double xr,double zl,int FaceNum ,double Yl_max,double Yr_max) {
                        //左侧x      右侧x      左侧z值     第几号面      左侧y max    右侧y max
    public EdgePair(V_Line line_L,V_Line line_R,int FaceNum ) {
        this.line_L=line_L;
        this.line_R=line_R;
        this.xl=line_L.v1.x;
        this.xr=line_R.v1.x;
        this.FaceNum=FaceNum;
        this.zl=line_L.v1.z;
        this.Yl_max=line_L.Y_max;
        this.Yr_max=line_R.Y_max;
    }
    public void changeLeftEdge(V_Line line_L){
         this.line_L=line_L;
         this.xl=line_L.v1.x;
         this.zl=line_L.v1.z;
         this.Yl_max=line_L.Y_max;
    }
      public void changeRightEdge(V_Line line_R){
          this.line_R=line_R;
          this.xr=line_R.v1.x;
          this.Yr_max=line_R.Y_max;
    }


}
//class Y{
//    int ser_Num;
//    double max_Y;
//    Y(int ser_Num,double max_Y){
//        this.ser_Num=ser_Num;
//        this.max_Y=max_Y;
//    }
//}

