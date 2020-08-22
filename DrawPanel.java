package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

    private SnakeGameModel snakeModel;

    private static final long serialVersionUID = -2506407454898288039L;

    public DrawPanel(SnakeGameModel snakeModel) {
	this.setPreferredSize(new Dimension(SnakeGameModel.SCREEN_WIDTH, SnakeGameModel.SCREEN_HEIGHT));
	this.snakeModel = snakeModel;
    }

    public void paintComponent(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;

	// clear the screen
	g2d.setColor(Color.WHITE);
	g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

	BodyPos head = snakeModel.getSnakeBody().getFirst();

	// print the head
	g2d.setColor(Color.RED);
	g2d.fill(new Rectangle(head.getX(), head.getY(), SnakeGameModel.SNAKE_SIZE_X, SnakeGameModel.SNAKE_SIZE_Y));

	// print the rest of the snake
	g2d.setColor(Color.ORANGE);
	for (int i = 1; i < snakeModel.getSnakeBody().size(); i++) {
	    g2d.fill(new Rectangle(snakeModel.getSnakeBody().get(i).getX(), snakeModel.getSnakeBody().get(i).getY(),
		    SnakeGameModel.SNAKE_SIZE_X, SnakeGameModel.SNAKE_SIZE_Y));
	}

	// print the fruit
	g2d.setColor(Color.GREEN);
	Rectangle fruit = new Rectangle(snakeModel.getFruitX(), snakeModel.getFruitY(), SnakeGameModel.SNAKE_SIZE_X,
		SnakeGameModel.SNAKE_SIZE_Y);
	g2d.fill(fruit);

	g2d.dispose();
    }
}