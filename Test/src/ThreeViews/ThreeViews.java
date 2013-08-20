/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ThreeViews.java
 *
 * Created on 2011-4-16, 13:30:14
 */

package ThreeViews;

import com.sun.opengl.util.Animator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import org.yourorghere.GLRenderer;
import org.yourorghere.V_Face;
import org.yourorghere.V_Point;

/**
 *
 * @author Law
 */
public class ThreeViews extends javax.swing.JFrame {

    /** Creates new form ThreeViews */
     private Animator animator1,animator2,animator3,animator4;
     private GLRender2 render1,render2,render3;
     private GLRender4 render4;
    public ThreeViews(V_Point vp[] ,V_Face vf[],Vector<Double> N) {

        super("SMF����ͼ");
           initComponents();
        render1=new GLRender2();
        render1.setDirection(0, 0, 4);
        render1.vf=vf;
        render1.vp=vp;
        render2=new GLRender2();
        render2.setDirection(-4, 0, 0);
        render2.vf=vf;
        render2.vp=vp;
        render3=new GLRender2();
        render3.setDirection(0, 4, 0.001);
        render3.vf=vf;
        render3.vp=vp;
          render4=new GLRender4();
        render4.setDirection(0,0,4);
        render4.vf=vf;
        render4.vp=vp;
        render4.N=N;
        gLJPanel1.addGLEventListener(render1);
        gLJPanel2.addGLEventListener(render2);
        gLJPanel3.addGLEventListener(render3);
        gLJPanel4.addGLEventListener(render4);
        animator1 = new Animator(gLJPanel1);
    animator2 = new Animator(gLJPanel2);
    animator3 = new Animator(gLJPanel3);
     animator4 = new Animator(gLJPanel4);
//    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.addWindowListener(new WindowAdapter() {
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//                // Run this on another thread than the AWT event queue to
//                // make sure the call to Animator.stop() completes before
//                // exiting
////                new Thread(new Runnable() {
////
////                    public void run() {
////                        animator1.stop();
////                         animator2.stop();
////                          animator3.stop();
//////                        System.exit(0);
////
////                    }
////                }).start();
//            }
//        });
    }
        public void setVisible(boolean show){
        if(!show){
             animator1.stop();
             animator2.stop();
              animator3.stop();
               animator4.stop();
        }
        super.setVisible(show);
        if(!show){
            animator1.start();
            animator2.start();
            animator3.start();
             animator4.stop();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gLJPanel1 = new javax.media.opengl.GLJPanel();
        gLJPanel2 = new javax.media.opengl.GLJPanel();
        gLJPanel3 = new javax.media.opengl.GLJPanel();
        gLJPanel4 = new javax.media.opengl.GLJPanel();

        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });

        gLJPanel1.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout gLJPanel1Layout = new javax.swing.GroupLayout(gLJPanel1);
        gLJPanel1.setLayout(gLJPanel1Layout);
        gLJPanel1Layout.setHorizontalGroup(
            gLJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        gLJPanel1Layout.setVerticalGroup(
            gLJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        gLJPanel2.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout gLJPanel2Layout = new javax.swing.GroupLayout(gLJPanel2);
        gLJPanel2.setLayout(gLJPanel2Layout);
        gLJPanel2Layout.setHorizontalGroup(
            gLJPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        gLJPanel2Layout.setVerticalGroup(
            gLJPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        gLJPanel3.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout gLJPanel3Layout = new javax.swing.GroupLayout(gLJPanel3);
        gLJPanel3.setLayout(gLJPanel3Layout);
        gLJPanel3Layout.setHorizontalGroup(
            gLJPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        gLJPanel3Layout.setVerticalGroup(
            gLJPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        gLJPanel4.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout gLJPanel4Layout = new javax.swing.GroupLayout(gLJPanel4);
        gLJPanel4.setLayout(gLJPanel4Layout);
        gLJPanel4Layout.setHorizontalGroup(
            gLJPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        gLJPanel4Layout.setVerticalGroup(
            gLJPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gLJPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gLJPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gLJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gLJPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(gLJPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gLJPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gLJPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gLJPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        int dir = evt.getWheelRotation();
  // down
  if (dir == 1) {

      GLRender2.Sizen=GLRender2.Sizen-30;
       GLRender4.Sizen=GLRender4.Sizen-30;

  }
    // up
    else if (dir == -1) {
      GLRender2.Sizen=GLRender2.Sizen+30;
       GLRender4.Sizen=GLRender4.Sizen+30;

  }
         repaint();
    }//GEN-LAST:event_formMouseWheelMoved

    /**
    * @param args the command line arguments
    */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.media.opengl.GLJPanel gLJPanel1;
    private javax.media.opengl.GLJPanel gLJPanel2;
    private javax.media.opengl.GLJPanel gLJPanel3;
    private javax.media.opengl.GLJPanel gLJPanel4;
    // End of variables declaration//GEN-END:variables

}
