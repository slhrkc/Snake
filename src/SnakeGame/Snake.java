package SnakeGame;

import processing.core.*;
import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;

/**
 * This is classic Snake game written in Java by using the Processing
 * Application.
 * 
 * @version 1.0
 * @see processing.org
 * @author Salih ER�KC�
 * 
 * 
 */
enum Way {
	UP, DOWN, RIGHT, LEFT
}

public class Snake extends PApplet {

	private static final long serialVersionUID = 1L;

	public Cell lastCell;
	public PFont font;
	public int score = 0;
	public Coordinates food;
	private int level = 1;
	private int counter = 0;
	private final int LEVEL_LIMIT = 5;

	public Head head;
	public ArrayList<Cell> cells;

	/**
	 * Creates a Collection that keeps all the Cells.<br>
	 * Setting the size of the Applet <br>
	 * Initialize the Head Cell in the Applet
	 * 
	 */
	public void setup() {
		// frameRate(10);
		size(1280, 640);

		// Create the Collection
		// Add the head to the top of the Collection
		cells = new ArrayList<Cell>();

		// This is used for the Coordinates
		Random random = new Random();

		// This is used for the Way
		Random random2 = new Random();

		// Creates a random coordinate for the head,it has an offset 75 for both
		// X and Y dimensions.

		Coordinates c = new Coordinates((random.nextInt(30) + 5) * 30 + 10,
				(random.nextInt(8) + 5) * 30 + 30);

		// Creating a Head cell.
		head = new Head(c);
		cells.add(head);
		food = placeFood();

		// The new cell that will be added to snake will be added to the
		// lastCell.Whenever we add a new Cell,we set the lastCell as the latest
		// added cell.
		lastCell = head;
		addCell();
		// Setting the initial way for the head.
		int t = random2.nextInt(3);
		Way startingWay;
		switch (t) {
		case 0: {
			startingWay = Way.UP;
			break;
		}
		case 1: {
			startingWay = Way.DOWN;
			break;
		}
		case 2: {
			startingWay = Way.LEFT;
			break;
		}
		case 3: {
			startingWay = Way.RIGHT;
			break;
		}
		default: {
			startingWay = Way.RIGHT;
			break;
		}
		}

		head.setWay(startingWay);

	}

	/**
	 * Deciding the way for the Head with respect to Key pressed.
	 */
	public void keyPressed() {
		// 37 Left Arrow
		if (keyCode == 37) {
			head.setWay(Way.LEFT);
		}
		// 38 Up Arrow
		if (keyCode == 38) {
			head.setWay(Way.UP);
		}
		// 39 Right Arrow
		if (keyCode == 39) {
			head.setWay(Way.RIGHT);
		}
		// 40 Down Arrow
		if (keyCode == 40) {
			head.setWay(Way.DOWN);
		}
	}

	public void draw() {

		try {
			Thread.currentThread().sleep(50-level);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stroke(100);
		background(50);
		fill(70);
		rect(10, 30, 1260, 600);
		fill(255);
		font = createFont("Arial", 20);
		textFont(font, 15);
		String s = "Score : " + score;

		text(s, 10, 10, 100, 20); // Text wraps within text box
		String t = "Level :"+level;
		text(t, 110, 10, 100, 20); // Text wraps within text box

		

		drawFood();

		for (Cell c : cells) {
			c.move();
		}

		if (!isCollision()) {
			for (Cell c : cells) {
				c.draw();
			}
			if (isHunt()) {
				score++;
				counter++;
				if(counter == LEVEL_LIMIT){
					level++;
					counter = 0;
				}
				food = placeFood();
				addCell();
			}

		} else {

			fill(255);
			try {
				Thread.currentThread().sleep(7500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			exit();
		}

	}

	class Cell {
		private Coordinates coordinate;
		protected Coordinates toSon;
		private Cell parent;

		// This array used as RGB for each Cell.
		private final int[] colors = new int[3];

		private Random random = new Random();

		public Cell getParent() {
			return parent;
		}

		/**
		 * Only the Head cell will use this Constructor
		 * 
		 * @param c
		 *            is the Coordinate to start with
		 */
		public Cell(Coordinates c) {
			this.setCoordinate(c);
			this.colors[0] = random.nextInt(255);
			this.colors[1] = random.nextInt(255);
			this.colors[2] = random.nextInt(255);
		}

		public Cell(Cell parent) {
			this.parent = parent;
			this.colors[0] = random.nextInt(155);
			this.colors[1] = random.nextInt(155);
			this.colors[2] = random.nextInt(155);

		}

		public Cell(Coordinates c, Cell parent) {
			this(c);
			this.parent = parent;
		}

		public Coordinates getCoordinate() {
			return coordinate;
		}

		public void setCoordinate(Coordinates coordinate) {
			this.coordinate = coordinate;
		}

		public void draw() {
			fill(colors[0], colors[1], colors[2]);
			rect(coordinate.getX(), coordinate.getY(), 30, 30, 8);
		}

		public void move() {
			this.toSon = this.coordinate;
			this.coordinate = parent.toSon;

		}

	}

	class Head extends Cell {
		private int speed = 30;
		private Way w;

		public Head(Coordinates c) {
			super(c);
		}

		public void setWay(Way w) {
			if (this.w == Way.UP && w == Way.DOWN) {

			} else if (this.w == Way.DOWN && w == Way.UP) {

			} else if (this.w == Way.RIGHT && w == Way.LEFT) {

			} else if (this.w == Way.LEFT && w == Way.RIGHT) {

			} else {
				this.w = w;
			}
		}

		public void move() {
			int addToX = 0, addToY = 0;
			switch (w) {
			case UP: {
				addToX = 0 * speed;
				addToY = -1 * speed;
				break;

			}
			case DOWN: {
				addToX = 0 * speed;
				addToY = 1 * speed;
				break;
			}
			case RIGHT: {
				addToX = 1 * speed;
				addToY = 0 * speed;
				break;
			}
			case LEFT: {
				addToX = -1 * speed;
				addToY = 0 * speed;
				break;
			}
			}
			Coordinates c = new Coordinates(this.getCoordinate().getX()
					+ addToX, this.getCoordinate().getY() + addToY);
			this.toSon = this.getCoordinate();
			this.setCoordinate(c);

		}
	}

	/**
	 * 
	 * @return true if there is a collision<br>
	 *         else it returns false
	 */
	public boolean isCollision() {
		for (Cell c : cells) {
			if (head.getCoordinate().equals(c.getCoordinate()) && head != c) {
				return true;
			} else if (head.getCoordinate().getX() > 1270) {
				head.getCoordinate().setCoordinates(10,head.getCoordinate().getY());
			}else if (head.getCoordinate().getX() < -20){
				head.getCoordinate().setCoordinates(1240,head.getCoordinate().getY());
			}else if (head.getCoordinate().getY() > 600){
				head.getCoordinate().setCoordinates(head.getCoordinate().getX(),30);
			}else if (head.getCoordinate().getY() < 30){
				head.getCoordinate().setCoordinates(head.getCoordinate().getX(),600);
			}
		}
		return false;

	}

	public void addCell() {
		lastCell = new Cell(lastCell);
		cells.add(lastCell);

	}


	public Coordinates placeFood() {
		Random random = new Random();

		Coordinates c = new Coordinates((random.nextInt(30) + 5) * 30 + 10,
				(random.nextInt(8) + 5) * 30 + 30);
		return c;

	}

	public void drawFood() {
		rect(food.getX(), food.getY(), 30, 30, 15);

	}

	public boolean isHunt() {
		if (head.getCoordinate().equals(food)) {
			return true;
		}
		return false;
	}
}
