import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Button {

	private int x;
	private int y;
	private int width;
	private int height;
	private String label;
	private Color c;

	public Button(int x, int y, int width, int height, String label, Color c) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		this.c = c;
	}

	public Graphics2D draw(Graphics2D g) {

		g.setColor(c);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString(label, x + 6, y + height - 9);

		return g;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean button() {
		int x = Game.mouseX;
		int y = Game.mouseY;
		if (Game.mouse == true && x > getX() && x < getX() + getWidth() && y > getY() && y < getY() + getHeight()) {
			return true;
		}
		return false;

	}
}
