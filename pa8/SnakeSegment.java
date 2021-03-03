package pa8;

import objectdraw.*;
import java.awt.Color;
import java.awt.color.*;

public class SnakeSegment{

	private GridCell cell;
	private FilledOval body;
	private DrawingCanvas canvas;
	private Color color = Color.white;
	
	public SnakeSegment(GridCell cell, DrawingCanvas canvas){
		this.cell = cell;
		this.canvas = canvas;
		cell.setSnakeSegment(this);
		body = new FilledOval(cell.getCol()*PA8Constants.GRID_CELL_SIZE, cell.getRow()*PA8Constants.GRID_CELL_SIZE,PA8Constants.GRID_CELL_SIZE,PA8Constants.GRID_CELL_SIZE,canvas);
body.setColor(Color.white);
	}
	
	public GridCell getCell(){
		return cell;
	}
	public void setCell (GridCell cell) {
		this.cell = cell;
	}
	
	public void moveCell(GridCell cell){
		this.cell.setSnakeSegment(null);
		this.cell = cell;
		cell.setSnakeSegment(this);
		body.moveTo(cell.getCol()*PA8Constants.GRID_CELL_SIZE,cell.getRow()*PA8Constants.GRID_CELL_SIZE);
		//body = new FilledOval(cell.getCol()*PA8Constants.GRID_CELL_SIZE, cell.getRow()*PA8Constants.GRID_CELL_SIZE,PA8Constants.GRID_CELL_SIZE,PA8Constants.GRID_CELL_SIZE,canvas);
		//body.setColor(Color.white);
	}
	public void setColor(Color color) {
		this.color = color;
		body.setColor(color);
	}
	public Color getColor() {
		return color;
	}
}

