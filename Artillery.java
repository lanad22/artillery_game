import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Artillery {
	private int diameter = 17;
	private int width = 70;
	private int x = 60;
	private int y = Game.HEIGHT - 130;

	private ArrayList<Ball> balls = new ArrayList<>();
	private int xBall;
	private int yBall;

	private Button shootButton = new Button(50, 170, 51, 25, "SHOOT", new Color(220, 20, 60));;
	private Button resetButton = new Button(50, 210, 51, 25, "RESET", new Color(50, 205, 50));;

	private Target target = new Target(800, Game.HEIGHT - 125, "./files/skeleton.png");

	public BufferedImage[] images = new BufferedImage[10];
	private int rand = 4;

	private double angle;
	private double power;

	private boolean reset = false;
	private boolean hit = false;
	private int round = 1;
	private int numShots = 0;
	private int numBalls = 3;
	private int score = 0;

	public Artillery() {
		try {
			images[0] = ImageIO.read(getClass().getResource("./files/artillery.png"));
			images[1] = ImageIO.read(getClass().getResource("./files/bomb.png"));
			images[2] = ImageIO.read(getClass().getResource("./files/star.png"));
			images[3] = ImageIO.read(getClass().getResource("./files/mountain1.png"));
			images[4] = ImageIO.read(getClass().getResource("./files/mountain2.png"));
			images[5] = ImageIO.read(getClass().getResource("./files/forest.png"));
			images[6] = ImageIO.read(getClass().getResource("./files/city.png"));
			images[7] = ImageIO.read(getClass().getResource("./files/desert.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Graphics2D draw(Graphics2D g, double angle, double power) {

		this.angle = angle;
		this.power = power;
		g = drawSky(g);
		g = drawGround(g, rand);
		g = drawText(g);
		g = drawArtillery(g);
		g = drawBalls(g);
		g = drawButtons(g);
		g = drawTarget(g);
		return g;

	}

	public int getRound() {
		return this.round;
	}

	public int getNumBalls() {
		return this.numBalls;
	}

	public int getScore() {
		return this.score;
	}

	public int getNumShots() {
		return this.numShots;
	}

	private Graphics2D drawArtillery(Graphics2D g) {
		int xCoor[] = { x, x + width, x + width, x };
		int yCoor[] = { y, y, y + diameter, y + diameter };

		for (int i = 0; i < xCoor.length; i++) {
			int newXY[] = update(xCoor[i], yCoor[i], angle, x, y + diameter);
			xCoor[i] = newXY[0];
			yCoor[i] = newXY[1];
		}
		for (int i = 0; i < xCoor.length; i++) {
			yCoor[i] = yCoor[i] + y + width - yCoor[3];
		}
		g.setColor(new Color(60, 179, 113));
		Polygon poly = new Polygon(xCoor, yCoor, xCoor.length);
		g.fillPolygon(poly);

		AffineTransform at = new AffineTransform();
		at.translate(7, y + 10);
		at.scale(0.2, 0.2);
		g.drawImage(images[0], at, null);

		// Set up ball
		xBall = xCoor[1];
		yBall = yCoor[1];

		return g;
	}

	private Graphics2D drawBalls(Graphics2D g) {
		int vx = (int) ((power) * Math.cos(angle));
		int vy = (int) (power * Math.sin(angle));
		Ball ball = new Ball(xBall, yBall, diameter, vx, vy);

		if (shootButton.button()) {
			balls.add(ball);
			int add = (balls.size() > 3 && balls.size() % 3 == 1) ? 0 : 1;
			numShots += add;
			numBalls -= 1;
			Game.mouse = false;
		}

		if (balls.size() > 3 || hit) {
			reset = true;
			numBalls = 3;
			hit = false;
			balls.removeAll(balls);

		}

		for (Ball b : balls) {
			boolean hitX = b.getX() >= target.getX() && (b.getX() + diameter) <= (target.getX() + target.getSize());
			boolean hitY = b.getY() >= target.getY() && (b.getY() + diameter) <= (target.getY() + target.getSize());
			if (hitX && hitY) {
				hit = true;
				score += 100;
				b.setDiameter(0);
				new Sound();
				target = new Target(target.getX(), target.getY(), "./files/explosion.png");
				drawTarget(g);
			} else {
				g.setColor(new Color(50, 205, 50));
				g.fillOval(b.getX(), b.getY(), b.getDiameter(), b.getDiameter());
				b.update();
			}
		}
		return g;
	}

	private Graphics2D drawTarget(Graphics2D g) {
		if (reset) {
			try {
				Thread.sleep(150);
			} catch (Exception e) {
			}
		}
		if (reset || resetButton.button()) {
			target = new Target(700, Game.HEIGHT - 125, "./files/skeleton.png");
			rand = getRandomNumber(3, 8);
			balls.removeAll(balls);
			numBalls = 3;
			target.setX(target.updateX());
			target.setY(target.updateY());
			round += 1;
			reset = false;
			Game.mouse = false;
		}

		AffineTransform at = new AffineTransform();
		at.translate(target.getX(), target.getY());
		at.scale(0.17, 0.17);
		g.drawImage(target.getImage(), at, null);
		return g;
	}

	private Graphics2D drawButtons(Graphics2D g) {
		shootButton.draw(g);
		resetButton.draw(g);
		return g;
	}

	private Graphics2D drawSky(Graphics2D g) {
		g.setColor(new Color(4, 7, 32));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		AffineTransform[] ats = new AffineTransform[4];
		for (int i = 0; i < 4; i++) {
			ats[i] = new AffineTransform();
		}

		ats[0].translate(0, 0);
		ats[1].translate(350, 0);
		ats[2].translate(0, 170);
		ats[3].translate(350, 170);

		for (int i = 0; i < 4; i++) {
			g.drawImage(images[2], ats[i], null);
		}
		return g;
	}

	private Graphics2D drawGround(Graphics2D g, int rand) {
		g.setColor(new Color(102, 51, 0));
		g.fillRect(0, Game.HEIGHT - 50, Game.WIDTH, 50);

		int x = 0;
		int y = 0;
		AffineTransform at = new AffineTransform();
		if (rand == 3) {
			y = 80;
		}
		if (rand == 4 || rand == 5 || rand == 6) {
			y = Game.HEIGHT - 50 - images[rand].getHeight();
		}
		at.translate(x, y);
		g.drawImage(images[rand], at, null);
		return g;
	}

	private Graphics2D drawText(Graphics2D g) {
		g.setColor(Color.WHITE);

		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("Round", 800, 50);
		g.drawString("Score", 800, 90);
		g.drawString("Shots", 5, 40);

		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(Integer.toString(round), 860, 50);
		String scoreStr = "";
		if (score == 0) {
			scoreStr = "0";
		} else {
			scoreStr = "+" + Integer.toString(score);
		}
		g.drawString(scoreStr, 860, 90);

		for (int i = 0; i < numBalls; i++) {
			AffineTransform at = new AffineTransform();
			at.translate(60 + i * 40, 0);
			at.scale(0.09, 0.09);
			g.drawImage(images[1], at, null);
		}
		return g;
	}

	private int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	private int[] update(int x, int y, double angle, int cx, int cy) {
		double rotatedX = (x - cx) * Math.cos(angle) - (y - cy) * Math.sin(angle);
		double rotatedY = (x - cx) * Math.sin(angle) + (y - cy) * Math.cos(angle);
		x = (int) (rotatedX + cx);
		y = (int) (rotatedY + cy);
		return new int[] { x, y };

	}
}
