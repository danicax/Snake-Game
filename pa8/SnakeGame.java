package pa8;

import objectdraw.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class SnakeGame extends WindowController implements KeyListener {

	private int rows;
	private int columns;
	private int loopsToWin;
	private int delay;
	private Grid grid;
	private Snake snake;
	private JLabel fruitLoopsEaten;
	private JLabel mostFruitEaten;
	private int collected = 0;
	private final int HALF_DIVISOR = 2;
	private PopupMessage popMsg;
	private Color color;
	private final int NUM_OF_COLORS = 6;
	private int fruitColor = 0;
	private int mostFruitsEaten = 0;
	private PopupMessage restartMsg;
	
	
	public static void main(String[] args) {
	String [] rowColumnInput;
	int rows=PA8Constants.DEFAULT_ROWS;
	int columns=PA8Constants.DEFAULT_COLS;
	int loopsToWin=rows*columns-1;
	int delay=PA8Constants.DEFAULT_ANIMATION_DELAY;
	
	if (args.length>PA8Constants.MAX_NUM_ARGS){
		System.out.println(PA8Constants.NUM_ARGS_ERR);
		usage();
		System.exit(1);
	}
	
	else if (args.length>0){
	args[0].toLowerCase();
	rowColumnInput = args[0].split (PA8Constants.GRID_SIZE_DELIM);
	
	if(rowColumnInput.length!=PA8Constants.NUM_GRID_SIZE_DIMENSIONS){
		System.out.format(PA8Constants.GRID_SIZE_FMT_ERR,args[0]);
		usage();
		System.exit(1);
	}
	
	rows = tryParse(rowColumnInput[0],PA8Constants.ROWS_STR);
	checkRange(rows,PA8Constants.MIN_ROWS,PA8Constants.MAX_ROWS);
	columns = tryParse(rowColumnInput[1],PA8Constants.COLS_STR);
	checkRange(columns,PA8Constants.MIN_COLS,PA8Constants.MAX_COLS);
	loopsToWin = rows*columns-1;
	
	
	if(args.length>1){
		loopsToWin = tryParse(args[1],PA8Constants.LOOPS_STR);
		if (loopsToWin<PA8Constants.MIN_LOOPS_TO_WIN||loopsToWin>rows*columns-1){
			System.out.format(PA8Constants.LOOPS_RANGE_ERR,PA8Constants.MIN_LOOPS_TO_WIN);
			usage();
		}
		
	}
	if (args.length>PA8Constants.NUM_GRID_SIZE_DIMENSIONS){
		delay = tryParse(args[PA8Constants.DELAY_IDX],PA8Constants.DELAY_STR);
checkRange(delay,PA8Constants.MIN_DELAY,PA8Constants.MAX_DELAY);
	}
	
	}
	usage();
	new Acme.MainFrame(new SnakeGame(rows, columns, loopsToWin, delay), args, PA8Constants.GRID_CELL_SIZE*columns+1,PA8Constants.GRID_CELL_SIZE*rows+1+PA8Constants.SCORE_PANEL_HEIGHT);
	
	}
	
	public SnakeGame(int rows, int columns, int loopsToWin, int delay){
		this.rows = rows;
		this.columns = columns;
		this.loopsToWin = loopsToWin;
		this.delay = delay;
		
	}
	
	public static void usage(){
		System.out.format(PA8Constants.USAGE_STR,PA8Constants.MIN_ROWS,PA8Constants.MAX_ROWS,PA8Constants.MIN_COLS,PA8Constants.MAX_COLS,PA8Constants.DEFAULT_ROWS,PA8Constants.DEFAULT_COLS,PA8Constants.MIN_LOOPS_TO_WIN,PA8Constants.MIN_DELAY,PA8Constants.MAX_DELAY,PA8Constants.DEFAULT_ANIMATION_DELAY);
		
	}
	public static int tryParse(String inputString, String errorName){
		int returnNum=0;
		try {
			returnNum=Integer.parseInt(inputString);
		}catch(NumberFormatException e){
			System.out.format(PA8Constants.INT_ERR,errorName, inputString);
			usage();
			System.exit(1);
		}
		
		
		return returnNum;
	}
	
	public static void checkRange(int input, int lower, int higher){
		
		if (input>higher||input<lower){
			System.out.format(PA8Constants.RANGE_ERR, lower,higher);
			usage();
			System.exit(1);
		}
		
	}
	public void begin(){
		canvas.requestFocusInWindow();
		//grid = new Grid(canvas,rows,columns);
		canvas.addKeyListener(this);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,PA8Constants.NUM_LOOP_LABELS));
		fruitLoopsEaten = new JLabel ();
		JLabel fruitLoopsToWin = new JLabel ("Fruit Loops to Win: "+loopsToWin);
		mostFruitEaten = new JLabel("Most Fruit Loops Eaten: 0");
		topPanel.add(fruitLoopsEaten);
		topPanel.add(fruitLoopsToWin);
		topPanel.add(mostFruitEaten);
		this.add(topPanel,BorderLayout.NORTH);
		this.validate();
		this.initGame();
	}
	 
	public void death(){
		
		if (collected >= loopsToWin) {
			this.setText(PA8Constants.WIN_STR, PA8Constants.BIG_FONT_SIZE, true,-PA8Constants.TEXT_Y_OFFSET);
			this.setText(PA8Constants.RESTART_STR, PA8Constants.SMALL_FONT_SIZE, false,PA8Constants.TEXT_Y_OFFSET);
			checkCollected();
			snake.setStopped();
		}
		
		else {
		checkCollected();
		this.setText(PA8Constants.LOSE_STR, PA8Constants.BIG_FONT_SIZE, true,-PA8Constants.TEXT_Y_OFFSET);
		this.setText(PA8Constants.RESTART_STR, PA8Constants.SMALL_FONT_SIZE, false,PA8Constants.TEXT_Y_OFFSET);
		snake.setStopped();
		}
	}
	
	public void initGame() {
		Location point = new Location ((PA8Constants.GRID_CELL_SIZE*columns+1)/HALF_DIVISOR,(PA8Constants.GRID_CELL_SIZE*rows+1)/HALF_DIVISOR);
		popMsg = new PopupMessage(point, PA8Constants.POPUP_COLOR, canvas);
		restartMsg = new PopupMessage(point, PA8Constants.POPUP_COLOR, canvas);
		grid = new Grid(canvas,rows,columns);
		snake = new Snake(grid,this,canvas,delay);
		placeFruitLoop();
		fruitLoopsEaten.setText("Fruit Loops Eaten: 0");
		checkCollected();
		collected = 0;
		this.validate();
		
	}
	
	public void placeFruitLoop() {
		grid.makeNewFruit(PA8Constants.FRUIT_LOOP_COLORS[fruitColor++%5]);
	}
	
	public void gameState() {}
	public void ateFruitLoop() {
		collected ++;
		fruitLoopsEaten.setText("Fruit Loops Eaten: "+collected);
		this.validate();
		/**
		if (collected >= loopsToWin) {
			this.setText(PA8Constants.WIN_STR, PA8Constants.BIG_FONT_SIZE, true,-PA8Constants.TEXT_Y_OFFSET);
			this.setText(PA8Constants.RESTART_STR, PA8Constants.SMALL_FONT_SIZE, false,PA8Constants.TEXT_Y_OFFSET);
			checkCollected();
			snake.setStopped();
		}
		
		else {
		*/
			this.placeFruitLoop();
		//}
	}
	
	public void setPaused() {
		this.setText(PA8Constants.PAUSE_STR, PA8Constants.BIG_FONT_SIZE, true,0);
	}
	
	public void setText(String string, int size,boolean bold, int offset) {
		Text text = new Text (string,0,offset,canvas);
		text.setBold(bold);
		text.setFontSize(size);
		popMsg.display(text,0, offset);
		text.setColor(color.white);
	}
	
	public void checkCollected() {
		if(collected>mostFruitsEaten) {
			mostFruitsEaten = collected;
			mostFruitEaten.setText("Most Fruit Loops Eaten: "+mostFruitsEaten);
			this.validate();
		}
	}
	
	public boolean hasWon() {
		return collected>=loopsToWin;
	}
	
	public void keyPressed(KeyEvent evt) {}
	public void keyReleased(KeyEvent evt) {
		
		
switch (evt.getKeyCode()) {		

		case KeyEvent.VK_LEFT:
			snake.setDirection (Direction.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			snake.setDirection(Direction.RIGHT);
			break;
		case KeyEvent.VK_UP: 
			snake.setDirection(Direction.UP);
			break;
		case KeyEvent.VK_DOWN :
			snake.setDirection(Direction.DOWN);
			break;
		case KeyEvent.VK_SPACE:
			if (!snake.getPaused()) {
				setPaused();
			}
			else {
				popMsg.hide();
			}
			snake.setPaused(!snake.getPaused());
			break;
		case KeyEvent.VK_R:
			snake.die();
			this.initGame();
		}
	canvas.requestFocusInWindow();	
	}
	public void keyTyped (KeyEvent evt) {}
	
}

