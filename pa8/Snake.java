package pa8;

import java.util.*;
import objectdraw.*;

public class Snake extends ActiveObject{

	private int delay;
	private Grid grid;
	private SnakeGame snakeGame;
	private DrawingCanvas canvas;
	private boolean paused = false;
	private boolean alive = true;
	private Direction dir = Direction.NONE;
	private ArrayList <SnakeSegment> snakeArray;
	private SnakeSegment head;
	private boolean stopped = false;
	
	public Snake(Grid grid, SnakeGame snakeGame, DrawingCanvas canvas, int delay){
		this.grid = grid;
		this.snakeGame = snakeGame;
		this.canvas = canvas;
		this.delay = delay;
		snakeArray = new ArrayList <SnakeSegment>();
		
		head = new SnakeSegment(grid.getRandomCell(),canvas);
		//snakeArray.add(head);
		this.start();
	}
	public void run(){
		while(alive){
			
			while(paused||stopped){
				pause(delay);
			}
			if (!move()) {
				die();
			}
			pause(delay);
		}
	}
	public void setStopped() {
		stopped=true;
	}
	public void setPaused(boolean paused){
		this.paused = paused;
		
	}
	public boolean getPaused() {
		return paused;
	}
	public void setDirection(Direction dir){
		if (!paused){
			if (snakeArray.size()==0||!dir.isOpposite(this.dir)){
				this.dir = dir;
			}
		}
	}
	
	public boolean move(){
		if (!paused) {
		GridCell lastCell=head.getCell();
		if (dir == Direction.NONE){
			return true;
		}
		
		else if (grid.getCellNeighbour(head.getCell(), dir)==null||grid.getCellNeighbour(head.getCell(),dir).getSnakeSegment()){
	
			return false;
		}
		head.moveCell(grid.getCellNeighbour(head.getCell(), dir));
		SnakeSegment tempSnake=null;
		for (SnakeSegment sg:snakeArray){
			GridCell temp = lastCell;
			lastCell = sg.getCell();
			sg.moveCell(temp);
			
		}
		if (head.getCell().getFruitLoop()){
			tempSnake = new SnakeSegment(lastCell,canvas);
			if (snakeGame.hasWon()) {
				tempSnake.setColor(head.getCell().getFruitColor());
			}
			snakeArray.add(tempSnake);
			head.getCell().getFruit().eat();
			snakeGame.ateFruitLoop();
		}
		}
		return true;
	}
	public void die(){
		alive = false;
		grid.reset();
		snakeGame.death();
		snakeArray = new ArrayList <SnakeSegment>();
		
	}
}




