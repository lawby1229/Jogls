package thu.cs.keg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.opengl.util.FPSAnimator;

public class BeijingFrame extends JFrame {
	GLRender listener=new GLRender();
	static FPSAnimator animator=null;
	public BeijingFrame() throws HeadlessException {
		super("JOGL北京");
		setSize(600,480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GLCapabilities glcaps=new GLCapabilities();
		GLCanvas canvas=new GLCanvas(glcaps);
		canvas.addGLEventListener(listener);
		//canvas.addMouseListener(listener);
		getContentPane().add(canvas, BorderLayout.CENTER);
		animator=new FPSAnimator(canvas,20,true);
		centerWindow(this);		
	}	
	private void centerWindow(Component frame) { // ���д���
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		frame.setLocation((screenSize.width - frameSize.width) >> 1,
				(screenSize.height - frameSize.height) >> 1);

	}
	
	public static void main(String[] args) {
		final BeijingFrame app = new BeijingFrame();
		// ��ʾ����
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				app.setVisible(true);
			}
		});
		// �����߳̿�ʼ
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				animator.start();
			}
		});
	}
}

