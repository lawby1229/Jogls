package keg.thu.heatmap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.Raster;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.swingx.graphics.BlendComposite;
import org.jdesktop.swingx.graphics.BlendComposite.BlendingMode;

/**
 * An example of a "heat map".
 * 
 */
public class Heatmap extends JPanel implements MouseListener {
	/** */
	private static final long serialVersionUID = -2105845119293049049L;

	/** Image drawn below the heat map */
	private final BufferedImage backgroundImage;

	/**  */
	private final BufferedImage dotImage = createFadedCircleImage(192);

	private BufferedImage monochromeImage;

	/** Cached heat map image */
	private BufferedImage heatmapImage;

	/** Lookup operation used to color the monochrome image according to "heat" */
	private LookupOp colorOp;

	private double baseLatitude_up = 39.7416;
	private double baseLatitude_dwon = 40.1778;
	private double baseY = Math.abs(baseLatitude_up - baseLatitude_dwon);

	private double baseLogitude_left = 116.1778;
	private double baseLogitude_right = 116.619;
	private double baseX = baseLogitude_right - baseLogitude_left;

	public Heatmap(BufferedImage backgroundImage) {
		this.backgroundImage = backgroundImage;
		int width = backgroundImage.getWidth();
		int height = backgroundImage.getHeight();

		// Create lookup operation for colorizing the monochrome image
		final BufferedImage colorImage = createGradientImage(new Dimension(64,
				1), Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN.darker(),
				Color.CYAN, Color.BLUE, new Color(0, 0, 0x33));
		final LookupTable colorTable = createColorLookupTable(colorImage, 0.5f);
		colorOp = new LookupOp(colorTable, null);

		// Create monochrome image and fill with with
		monochromeImage = createCompatibleTranslucentImage(width, height);
		Graphics g = monochromeImage.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
	}

	public BufferedImage colorize(LookupOp colorOp) {
		return colorOp.filter(monochromeImage, null);
	}

	public BufferedImage colorize(LookupTable colorTable) {
		return colorize(new LookupOp(colorTable, null));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		heatmapImage = colorize(colorOp);
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(heatmapImage, 0, 0, this);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			addDotImage(e.getPoint(), 0.5f);
		else if (e.getButton() == MouseEvent.BUTTON3)
			minusDotImage(e.getPoint(), 0.5f);
		repaint();
	}

	public void drawCell(double longi, double lat) {
		int circleRadius = dotImage.getWidth() / 2;
		Graphics2D g = (Graphics2D) monochromeImage.getGraphics();
		g.setComposite(BlendComposite.Multiply);
		g.drawImage(
				dotImage,
				null,
				(int) Math.rint(((longi - baseLogitude_left) / baseX)
						* backgroundImage.getWidth() - circleRadius),
				(int) Math.rint(((lat - baseLatitude_up) / baseY)
						* backgroundImage.getHeight() - circleRadius));
	}

	/**
	 * 
	 * @param p
	 * @param alpha
	 */
	private void addDotImage(Point p, float alpha) {
		int circleRadius = dotImage.getWidth() / 2;
		Graphics2D g = (Graphics2D) monochromeImage.getGraphics();
		g.setComposite(BlendComposite.Multiply.derive(alpha));
		g.drawImage(dotImage, null, p.x - circleRadius, p.y - circleRadius);
	}

	private void minusDotImage(Point p, float alpha) {
		int circleRadius = dotImage.getWidth() / 2;
		Graphics2D g = (Graphics2D) monochromeImage.getGraphics();
		g.setComposite(BlendComposite.getInstance(BlendingMode.MULTIPLY));
		g.drawImage(dotImage, null, p.x - circleRadius, p.y - circleRadius);
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Creates the color lookup table from an image.
	 * 
	 * @param im
	 * @param alpha
	 *            alpha channel (between 0.0 and 1.0)
	 * @return
	 */
	public static LookupTable createColorLookupTable(BufferedImage im,
			float alpha) {
		int tableSize = 256;
		Raster imageRaster = im.getData();
		double sampleStep = 1d * im.getWidth() / tableSize; // Sample pixels
															// evenly
		byte[][] colorTable = new byte[4][tableSize];
		int[] pixel = new int[1]; // Sample pixel
		Color c;

		for (int i = 0; i < tableSize; ++i) {
			imageRaster.getDataElements((int) (i * sampleStep), 0, pixel);
			c = new Color(pixel[0]);
			colorTable[0][i] = (byte) c.getRed();
			colorTable[1][i] = (byte) c.getGreen();
			colorTable[2][i] = (byte) c.getBlue();
			colorTable[3][i] = (byte) (Math.max(0, Math.min(1, alpha)) * 0xff);
		}

		LookupTable lookupTable = new ByteLookupTable(0, colorTable);
		return lookupTable;
	}

	/**
	 * Creates an image filled with a linear gradient
	 * 
	 * @param size
	 *            size of the image
	 * @param colors
	 *            gradient colors
	 * @return
	 */
	public static BufferedImage createGradientImage(Dimension size,
			Color... colors) {
		float[] fractions = new float[colors.length];
		float step = 1f / colors.length;

		for (int i = 0; i < colors.length; i++) {
			fractions[i] = i * step;
		}

		LinearGradientPaint gradient = new LinearGradientPaint(0, 0,
				size.width, 1, fractions, colors,
				MultipleGradientPaint.CycleMethod.NO_CYCLE);
		BufferedImage im = createCompatibleTranslucentImage(size.width,
				size.height);

		Graphics2D g = im.createGraphics();
		g.setPaint(gradient);
		g.fillRect(0, 0, size.width, size.height);
		g.dispose();

		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
		//
		// encoder.encode(img);
		//
		// out.close();
		return im;
	}

	/**
	 * Creates a translucent image.
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage createCompatibleTranslucentImage(int width,
			int height) {
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice dev = env.getDefaultScreenDevice();
		GraphicsConfiguration conf = dev.getDefaultConfiguration();
		return conf.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
	}

	/**
	 * Creates an image with a monochrome circle (fading from center to edges,
	 * black to white).
	 * 
	 * @param size
	 *            diameter of the circle and width and height of the image
	 * @return
	 */
	public static BufferedImage createFadedCircleImage(int size) {
		float radius = size / 2f;
		RadialGradientPaint gradient = new RadialGradientPaint(radius, radius,
				radius, new float[] { 0f, 1f }, new Color[] { Color.black,
						Color.white
				// new Color(0xffffffff, true)
				});
		BufferedImage im = createCompatibleTranslucentImage(size, size);
		Graphics2D g = (Graphics2D) im.getGraphics();
		g.setPaint(gradient);
		g.fillRect(0, 0, size, size);
		g.dispose();
		return im;
	}

	public static void main(String... args) throws IOException {
		BufferedImage backgroundImage = ImageIO.read(new FileInputStream(
				"data/map2.png"));
		Heatmap comp = new Heatmap(backgroundImage);
		JFrame frame = new JFrame("Heatmap");
		frame.add(comp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

		ReadData rd = new ReadData("BejingData");
		List<Double> cell0 = rd.tMap.get(0);
		double max = -1, min = Double.MAX_VALUE;
		for (int i = 0; i < cell0.size(); i++) {
			if (cell0.get(i) > max)
				max = cell0.get(i);
			if (cell0.get(i) < min)
				min = cell0.get(i);
		}
		for (int i = 0; i < cell0.size(); i++) {
			double longi = Double.valueOf(rd.cellMap.get(i).split(",")[0]);
			double lat = Double.valueOf(rd.cellMap.get(i).split(",")[1]);
			for (int j = 0; j < cell0.get(i) * 10 / max; j++) {
				comp.drawCell(longi, lat);
			}
		}
		frame.repaint();

	}
}
