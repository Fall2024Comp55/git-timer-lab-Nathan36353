import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
    private ArrayList<GOval> balls;
    private ArrayList<GRect> enemies;
    private GLabel text;
    private GLabel scoreLabel;
    private Timer movement;
    private RandomGenerator rgen;

    public static final int SIZE = 25;
    public static final int SPEED = 2;
    public static final int MS = 50;
    public static final int MAX_ENEMIES = 10;
    public static final int WINDOW_HEIGHT = 600;
    public static final int WINDOW_WIDTH = 300;

    private int numTimes;
    private int score;

    public void run() {
        rgen = RandomGenerator.getInstance();
        balls = new ArrayList<GOval>();
        enemies = new ArrayList<GRect>();

        text = new GLabel("" + enemies.size(), 0, WINDOW_HEIGHT);
        add(text);

        scoreLabel = new GLabel("Score: 0", 100, WINDOW_HEIGHT);
        add(scoreLabel);

        movement = new Timer(MS, this);
        movement.start();
        addMouseListeners();
    }

    public void actionPerformed(ActionEvent e) {
        if (enemies.size() >= MAX_ENEMIES) {
            endGame();
            return;
        }

        numTimes++;
        moveAllBallsOnce();
        moveAllEnemiesOnce();

        if (numTimes % 40 == 0) {
            addAnEnemy();
        }
    }

    public void mousePressed(MouseEvent e) {
        for (GOval b : balls) {
            if (b.getX() < SIZE * 2.5) {
                return;
            }
        }
        addABall(e.getY());
    }

    private void addABall(double y) {
        GOval ball = makeBall(SIZE / 2, y);
        add(ball);
        balls.add(ball);
    }

    public GOval makeBall(double x, double y) {
        GOval temp = new GOval(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
        temp.setColor(Color.RED);
        temp.setFilled(true);
        return temp;
    }

    private void addAnEnemy() {
        GRect e = makeEnemy(rgen.nextInt(0, WINDOW_HEIGHT - SIZE / 2));
        enemies.add(e);
        text.setLabel("" + enemies.size());
        add(e);
    }

    public GRect makeEnemy(double y) {
        GRect temp = new GRect(WINDOW_WIDTH - SIZE, y - SIZE / 2, SIZE, SIZE);
        temp.setColor(Color.GREEN);
        temp.setFilled(true);
        return temp;
    }

    private void moveAllBallsOnce() {
        for (int i = 0; i < balls.size(); i++) {
            GOval ball = balls.get(i);
            ball.move(SPEED, 0);

            GObject obj = getElementAt(ball.getX() + SIZE, ball.getY() + SIZE / 2);
            if (obj instanceof GRect) {
                remove(obj);
                enemies.remove(obj);
                text.setLabel("" + enemies.size());
                score++;
                scoreLabel.setLabel("Score: " + score);
            }
        }
    }

    private void moveAllEnemiesOnce() {
        for (GRect enemy : enemies) {
            int randomYMovement = rgen.nextInt(-SPEED, SPEED);
            enemy.move(0, randomYMovement);
        }
    }

    private void endGame() {
        movement.stop();
        GLabel gameOverLabel = new GLabel("Game Over! Your score: " + score, 50, WINDOW_HEIGHT / 2);
        gameOverLabel.setFont("Arial-Bold-24");
        gameOverLabel.setColor(Color.RED);
        add(gameOverLabel);
    }

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String args[]) {
        new DodgeBall().start();
    }
}

