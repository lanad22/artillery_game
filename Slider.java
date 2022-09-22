import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Slider {

	private double min;
	private double max;
	private int x;
	private int y;
	
	private int width = 180;
	private int height = 8;
	
	private String label;
	
	private int dragX;
	private int dragWidth = 9;
	private int dragHeight = 22;
	private boolean grabbed = false;
	
	public Slider(int x, int y, double min, double max, String label) {
		this.min = min;
		this.max = max;
		this.x = x;
		this.y = y;
		this.label = label;
		
		this.dragX = (width / 2) - (dragWidth / 2);
	}
	
	public Graphics2D draw(Graphics2D g) {
		
		if (isGrabbed()) {
			grabbed = true;
		}
		else {
			grabbed = false;
		}
		if (grabbed && Game.mouseX > x + (dragWidth / 2) && Game.mouseX < x + width - 1) {
			dragX = Game.mouseX - x - (dragWidth / 2);
		}

		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.WHITE);
		g.fillRect(dragX + x, y - (dragHeight / 3), dragWidth, dragHeight);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD, 15));
		g.drawString(label, 5, y + (height / 2) + 3);
		return g;
	}
	
	public double getValue() {
		int xPos = dragX + dragWidth / 2;
		double ratio = (double) xPos / width; 
		
		return ratio * (max - min);
			
	}

	private boolean isGrabbed(){
		int gameX = Game.mouseX;
		int gameY  = Game.mouseY;
		if (Game.drag && gameX > (dragX - 10) + x && gameX < dragX + (dragWidth + 10) + x && gameY > y && gameY < y + height){
			return true;
		}
		return false;
	}

}
