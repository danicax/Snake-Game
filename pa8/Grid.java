package pa8;


import objectdraw.*;
import java.awt.Color;
import java.util.*;

public class Grid {
	private DrawingCanvas canvas;
	private GridCell[][] gridCellArray;
	private int rows;
	private int columns;
	private Random rand;
	private int HALF_DIVISOR=2;

	public Grid(DrawingCanvas canvas, int rows, int columns){
		rand = new Random();
		this.canvas = canvas;
		this.rows = rows;
		this.columns=columns;
		draw();
		reset();
	}
	
	public void makeNewFruit(Color color) {
		new FruitLoop(getRandomCell(), color,canvas);
	}
	
	public void reset(){
		gridCellArray = new GridCell[rows][columns];
		for (int i=0;i<gridCellArray.length;i++){
			for (int j=0;j<gridCellArray[i].length;j++){
		gridCellArray[i][j]=new GridCell(i,j,canvas);
	}
	}
		
	}
	
	public int getWidth(){
		return columns*PA8Constants.GRID_CELL_SIZE;
	}
	public int getHeight(){
		return rows*PA8Constants.GRID_CELL_SIZE;
	}
	public Location getCenter(){

		return (new Location (rows/HALF_DIVISOR,columns/HALF_DIVISOR));
	}
	
	public GridCell getGridCell(int rows, int columns){
		if (rows>this.rows||columns>this.columns||rows<0||columns<0){
			return null;
		}
		return gridCellArray[rows][columns];
	}
	
	public GridCell getCellNeighbour(GridCell cell, Direction dir ){
		int row = cell.getRow()+dir.getY();
		int col = cell.getCol()+dir.getX();
		if (row >=rows||row<0||col>=columns||col<0){
			return null;
		}
	 return gridCellArray[row][col];
	}
	
	public GridCell getRandomCell(){
		GridCell temp;
		do{
		temp = gridCellArray[rand.nextInt(rows)][rand.nextInt(columns)];
		}
		while (temp.isEmpty());
			return temp;
	}
	
	public void draw(){
		FilledRect background = new FilledRect(0,0,columns*PA8Constants.GRID_CELL_SIZE,rows*PA8Constants.GRID_CELL_SIZE,canvas);
		background.setColor(PA8Constants.BACKGROUND_COLOR);
		
		for (int i = 0; i<=columns;i++){
		Line line = new Line(i*PA8Constants.GRID_CELL_SIZE,0,i*PA8Constants.GRID_CELL_SIZE,rows*PA8Constants.GRID_CELL_SIZE,canvas);	
		line.setColor(PA8Constants.GRID_LINE_COLOR);
		}
		for (int i = 0; i<=rows; i++){
			Line line = new Line(0,i*PA8Constants.GRID_CELL_SIZE,columns*PA8Constants.GRID_CELL_SIZE,i*PA8Constants.GRID_CELL_SIZE,canvas);
			line.setColor(PA8Constants.GRID_LINE_COLOR);
		}
	}

}






