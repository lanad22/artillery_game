import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Game extends JPanel implements Runnable, MouseListener, MouseMotionListener {

	public static boolean drag = false;
	public static boolean mouse = false;
	public static int mouseX = 0;
	public static int mouseY = 0;

	public static int WIDTH = 1025;
	public static int HEIGHT = 700;

	private Thread thread;
	private boolean run;
	private BufferedImage image;
	private Graphics2D g;
	private int framePerSecond = 60;

	private Artillery artillery = new Artillery();
	private Slider angle = new Slider(60, 80, Math.toRadians(90), 0, "Angle");
	private Slider power = new Slider(60, 120, 1, 200, "Power");

	public Game() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		run = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		long start;
		long timeMillis;
		long wait;
		long target = 1000 / framePerSecond;

		while (run) {
			start = System.nanoTime();
			setup();
			draw();
			timeMillis = (System.nanoTime() - start) / 1000000;
			wait = target - timeMillis;
			try {
				Thread.sleep(wait);
			} catch (Exception e) {
			}
		}
	}

	public String message() {
		String summary = "";
		summary += "\nRounds played: " + Integer.toString(artillery.getRound()) +
				"\nShots used: " + Integer.toString(artillery.getNumShots()) +
				"\nTargets hit: " + Integer.toString(artillery.getScore() / 100) +
				"\nYour score: " + Integer.toString(artillery.getScore());
		return summary + "\nIf you choose to exit, your results will be erased.\nAre you sure you want to exit?";
	}

	private void setup() {
		g = artillery.draw(g, angle.getValue(), power.getValue());
		g = angle.draw(g);
		g = power.draw(g);
	}

	private void draw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouse = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		drag = true;
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		drag = false;
		mouse = false;
		mouseX = e.getX();
		mouseY = e.getY();
	}

	// empty function
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
}
