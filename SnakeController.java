package snakegame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeController implements KeyListener {

	private SnakeGameModel snakeModel;

	public SnakeController(SnakeGameModel snakeModel)
	{
		this.snakeModel = snakeModel;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();

		//initial position of the head is on the right side of the body,
        	//moving left from starting position would reverse the direction which is not allowed
        	if(key == KeyEvent.VK_LEFT && !(snakeModel.getDirection() == SnakeGameModel.Direction.Right) && !(snakeModel.getDirection() == SnakeGameModel.Direction.None))
        	{
        		snakeModel.setDirection(SnakeGameModel.Direction.Left);
        	}
        	else if(key == KeyEvent.VK_RIGHT && !(snakeModel.getDirection() == SnakeGameModel.Direction.Left))
        	{
        		snakeModel.setDirection(SnakeGameModel.Direction.Right);
        	}
        	else if(key == KeyEvent.VK_UP && !(snakeModel.getDirection() == SnakeGameModel.Direction.Down))
        	{
        		snakeModel.setDirection(SnakeGameModel.Direction.Up);
        	}
        	else if(key == KeyEvent.VK_DOWN && !(snakeModel.getDirection() == SnakeGameModel.Direction.Up))
        	{
        		snakeModel.setDirection(SnakeGameModel.Direction.Down);
        	}
	}

	@Override
	public void keyPressed(KeyEvent arg0) { }
	@Override
	public void keyTyped(KeyEvent arg0) { }
}
