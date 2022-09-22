import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Target {
	private int size;
	private int x;
	private int y;
	private BufferedImage iconeNave;	

	public Target(int x, int y, String source) {
		this.x = x; 
		this.y = y;
		try {
			iconeNave = ImageIO.read(getClass().getResource(source));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		this.size = (int) (iconeNave.getWidth() * 0.16);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getImage() {
		return iconeNave;
	}

	public int getSize() {
		return this.size;
	}

	public int updateX() {
		return getRandomNumber(275, Game.WIDTH - 135);	
	}

	public int updateY() {
		return getRandomNumber(200, Game.HEIGHT - 135);
	}

	private int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
