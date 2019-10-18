package snakegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class SnakeView implements SnakeModelView {

	private SnakeGameModel snakeModel;
	private JFrame gameFrame;
	private DrawPanel drawPanel;
	private Timer gameLoopTimer;

	public SnakeView()
	{
		init();
	}

	private void init()
	{
		snakeModel = new SnakeGameModel();
		snakeModel.registerObserver(this);

		gameFrame = new JFrame("Snake");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SnakeController snakeController = new SnakeController(snakeModel);

		drawPanel = new DrawPanel(snakeModel);
		gameFrame.getContentPane().add(drawPanel);
		gameFrame.addKeyListener(snakeController);

		gameFrame.pack();
		gameFrame.setFocusable(true);
		gameFrame.setVisible(true);

		gameLoopTimer = new Timer(70, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(snakeModel.isGameOver())
				{
					gameLoopTimer.stop();
				}
				else
				{
					snakeModel.step();
				}
			}
		});

		gameLoopTimer.start();
	}

	@Override
	public void update() {
		drawPanel.repaint();
	}
}
