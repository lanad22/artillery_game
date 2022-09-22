
public class Ball {
	private double x;
	private double y;
	private double diameter;

	private double vx;
	private double vy;

	public Ball(int x, int y, int diameter, int vx, int vy) {
		this.x = (double) x;
		this.y = (double) y;
		this.diameter = (double) diameter;
		this.vx = (double) vx * getTimeFraction();
		this.vy = (double) vy * getTimeFraction();
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getDiameter() {
		return (int) diameter;
	}

	public void update() {
		x += vx;
		vy += 0.4;
		y += vy;

		if (x > Game.WIDTH - diameter || x < 0) {
			vx = 0;
			vy = 0;
			x = Game.WIDTH - diameter;
		}

		if (y + diameter >= Game.HEIGHT - 50) {
			vx = 0;
			vy = 0;
			y = Game.HEIGHT - 50 - diameter;
		}
	}

	private double getTimeFraction() {
		return 1 / 6.5;
	}

}
