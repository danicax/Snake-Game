package pa8;


import java.awt.Color;
import objectdraw.*;

public class FruitLoop {
	
	private GridCell cell;
	private DrawingCanvas canvas;
	private FilledOval outer;
	private FilledOval inner;
	private final int HALF_DIVISOR = 2;
	private Color color = Color.white;

	public FruitLoop(GridCell cell, Color color, DrawingCanvas canvas){
		this.cell = cell;
		this.canvas = canvas;
		this.color = color;
		
		int bigOvalStart = (PA8Constants.GRID_CELL_SIZE-PA8Constants.GRID_CELL_SIZE/PA8Constants.OUTER_SIZE_DIVISOR)/HALF_DIVISOR;
		int smallOvalStart = (PA8Constants.GRID_CELL_SIZE-PA8Constants.GRID_CELL_SIZE/PA8Constants.INNER_SIZE_DIVISOR)/HALF_DIVISOR;
		
		cell.setFruitLoop(this);
		outer = new FilledOval(cell.getCol()*PA8Constants.GRID_CELL_SIZE+bigOvalStart,cell.getRow()*PA8Constants.GRID_CELL_SIZE+bigOvalStart,PA8Constants.GRID_CELL_SIZE/PA8Constants.OUTER_SIZE_DIVISOR,PA8Constants.GRID_CELL_SIZE/PA8Constants.OUTER_SIZE_DIVISOR,canvas);
		//FilledOval outer = new FilledOval (cell.getRow()*PA8Constants.GRID_CELL_SIZE+((PA8Constants.GRID_CELL_SIZE-PA8Constants.OUTER_SIZE_DIVISOR)/PA8Constants.INNER_SIZE_DIVISOR),cell.getCol()*PA8Constants.GRID_CELL_SIZE+((PA8Constants.GRID_CELL_SIZE-PA8Constants.OUTER_SIZE_DIVISOR)/PA8Constants.INNER_SIZE_DIVISOR),PA8Constants.GRID_CELL_SIZE/PA8Constants.OUTER_SIZE_DIVISOR,PA8Constants.GRID_CELL_SIZE/PA8Constants.OUTER_SIZE_DIVISOR,canvas);
		inner = new FilledOval (cell.getCol()*PA8Constants.GRID_CELL_SIZE+smallOvalStart,cell.getRow()*PA8Constants.GRID_CELL_SIZE+smallOvalStart,PA8Constants.GRID_CELL_SIZE/PA8Constants.INNER_SIZE_DIVISOR,PA8Constants.GRID_CELL_SIZE/PA8Constants.INNER_SIZE_DIVISOR,canvas);
		outer.setColor(color);
		inner.setColor(PA8Constants.BACKGROUND_COLOR);
	}
	
	public void eat(){
		cell.setFruitLoop(null);
		if (outer!=null)
		outer.removeFromCanvas();
		if (inner !=null)
		inner.removeFromCanvas();
		
	}
	public void setCell (GridCell cell) {
		this.cell = cell;
	}
	public Color getColor() {
		return color;
	}
}

