package SnakeGame;

public class Coordinates {
	private int x;
	private int y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Coordinates coordinates) {
		if (coordinates.x == this.x && coordinates.y == this.y) {
			return true;
		} else {
			return false;
		}

	}


}
