package snakegame;

//Observer Interface implemented by classes to get notified of changes to the observed class (in this case SnakeGameModel)
public interface SnakeModelView {
	void update();
}