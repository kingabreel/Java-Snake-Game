package view;

import Util.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    public boolean verified = false;
    int SCREEN_WIDTH = 600;
    int SCREEN_HEIGHT = 600;
    static int UNIT_SIZE = 25;
    final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static int DELAY = 100;
    final int[] x = new int [GAME_UNITS];
    final int[] y = new int [GAME_UNITS];
    int body = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    public GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(850, 850));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setAlignmentY(CENTER_ALIGNMENT);
        this.addKeyListener(new MyKeyAdapter());
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void menu(){
        Style style = new Style();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel labelDificulty = new JLabel(getDifficultyLabel());
        JLabel labelVel = new JLabel(getVelocityLabel());
        JLabel labelWidth = new JLabel(getWidthLabel());

        JPanel buttonPanelDifficulty = new JPanel(new FlowLayout());
        JPanel buttonPanelVelocity = new JPanel(new FlowLayout());
        JPanel buttonPanelSize = new JPanel(new FlowLayout());

        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(e -> {
            UNIT_SIZE = 50;
            labelDificulty.setText(getDifficultyLabel());
        });
        easyButton = style.styledButton(easyButton);
        JButton mediumButton = new JButton("Medium");
        mediumButton = style.styledButton(mediumButton);
        mediumButton.addActionListener(e -> {
            UNIT_SIZE = 25;
            labelDificulty.setText(getDifficultyLabel());
        });
        JButton hardButton = new JButton("Hard");
        hardButton = style.styledButton(hardButton);
        hardButton.addActionListener(e -> {
            UNIT_SIZE = 10;
            labelDificulty.setText(getDifficultyLabel());
        });

        JButton slowGameButton = new JButton("Slow");
        slowGameButton = style.styledButton(slowGameButton);
        slowGameButton.addActionListener(e -> {
            DELAY = 150;
            labelVel.setText(getVelocityLabel());
        });
        JButton normalGameButton = new JButton("Normal");
        normalGameButton = style.styledButton(normalGameButton);
        normalGameButton.addActionListener(e -> {
            DELAY = 125;
            labelVel.setText(getVelocityLabel());
        });
        JButton fastGameButton = new JButton("Fast");
        fastGameButton = style.styledButton(fastGameButton);
        fastGameButton.addActionListener(e -> {
            DELAY = 100;
            labelVel.setText(getVelocityLabel());
        });

        JButton smallSizeButton = new JButton("Small");
        smallSizeButton = style.styledButton(smallSizeButton);
        smallSizeButton.addActionListener(e -> {
            SCREEN_WIDTH = 300;
            SCREEN_HEIGHT = 300;
            labelWidth.setText(getWidthLabel());
        });
        JButton mediumSizeButton = new JButton("Medium");
        mediumSizeButton = style.styledButton(mediumSizeButton);
        mediumSizeButton.addActionListener(e -> {
            SCREEN_HEIGHT = 600;
            SCREEN_WIDTH = 600;
            labelWidth.setText(getWidthLabel());
        });
        JButton largeSizeButton = new JButton("Large");
        largeSizeButton = style.styledButton(largeSizeButton);
        largeSizeButton.addActionListener(e -> {
            SCREEN_WIDTH = 750;
            SCREEN_HEIGHT = 750;
            labelWidth.setText(getWidthLabel());
        });

        buttonPanelDifficulty.add(easyButton);
        buttonPanelDifficulty.add(mediumButton);
        buttonPanelDifficulty.add(hardButton);

        buttonPanelVelocity.add(slowGameButton);
        buttonPanelVelocity.add(normalGameButton);
        buttonPanelVelocity.add(fastGameButton);

        buttonPanelSize.add(smallSizeButton);
        buttonPanelSize.add(mediumSizeButton);
        buttonPanelSize.add(largeSizeButton);

        JButton startGameButton = new JButton("Start Game");
        startGameButton = style.styledButton(startGameButton);
        startGameButton.addActionListener(e -> {
            verified = true;
            this.removeAll();
            this.repaint();
        });

        JPanel startButtonPanel = new JPanel();
        startButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        startButtonPanel.add(startGameButton);

        this.add(buttonPanelDifficulty);
        this.add(buttonPanelSize);
        this.add(buttonPanelVelocity);
        this.add(startButtonPanel);
    }
    public void draw (Graphics g){
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.ORANGE);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < body; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void move(){
        for (int i= body;i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }

    }

    public void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH /UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT /UNIT_SIZE)*UNIT_SIZE;

    }

    public void checkApple(){
        if ((x[0] == appleX) && (y[0] == appleY)){
            body++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision(){
        for (int i = body; i>0;i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }

        if ((x[0] < 0) || (x[0] > SCREEN_WIDTH) || (y[0] < 0) || (y[0] > SCREEN_HEIGHT)){
            running = false;
        }

        if (!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    private String getDifficultyLabel() {
        return (UNIT_SIZE == 50) ? "Baby" : (UNIT_SIZE == 25) ? "Good" : "God";
    }

    private String getVelocityLabel() {
        return (DELAY == 150) ? "Worm" : (DELAY == 125) ? "Snake" : "Anaconda";
    }

    private String getWidthLabel() {
        return (SCREEN_WIDTH == 300) ? "Beginner" : (SCREEN_WIDTH == 600) ? "Player" : "Insane";
    }
}
