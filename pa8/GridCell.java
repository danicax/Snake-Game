package pa8;

import objectdraw.*;
import java.awt.Color;

public class GridCell {
	
	private FruitLoop fruitLoop;
	private SnakeSegment snakeSegment;
	private int row;
	private int column;

	public GridCell(int row, int column, DrawingCanvas canvas){
	//	new FramedRect(row*PA8Constants.GRID_CELL_SIZE,column*PA8Constants.GRID_CELL_SIZE,PA8Constants.GRID_CELL_SIZE,PA8Constants.GRID_CELL_SIZE,canvas);
		this.row = row;
		this.column = column;

	}
	public void setFruitLoop(FruitLoop fruit){
		this.fruitLoop = fruit;
	}
	
public void setSnakeSegment(SnakeSegment snake){
		this.snakeSegment = snake;
	}

public FruitLoop getFruit() {
	return fruitLoop;
}

public boolean getSnakeSegment(){
	return (snakeSegment!=null);
}
public boolean getFruitLoop(){
	return (fruitLoop!=null);
}
public boolean isEmpty(){
	return (this.getSnakeSegment()||this.getFruitLoop());
}
public int getRow(){
	return row;
}
public int getCol(){
	return column;
}
public Color getFruitColor() {
	return fruitLoop.getColor();
}

}

