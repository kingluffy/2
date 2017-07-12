import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends JFrame implements KeyListener{

    //window vars
    private final int MAX_FPS;
    private final int WIDTH;
    private final int HEIGHT;

    //double buffer
    private BufferStrategy strategy;

    //loop variables
    private boolean isRunning = true;
    private long rest = 0;

    //timing variables
    private float dt;
    private long lastFrame;
    private long startFrame;
    private int fps;
    public boolean goup = false;
    public boolean godown = false;
    public boolean goleft = false;
    public boolean goright = false;
    public int move = 1;
    public int domove = 0;
    public int b_over = 20;
    public int b_up = 500;
    public int hp = 100;
    public int select = 0;
    public enum game_state{
        menu,
        play_game,
        end,
        encounter

    }
    game_state current_state = game_state.menu;

    //current_state = game_state(menu);

    //sprite1 variables
    private float x = 50;
    private float v = 10;

    //sprite2 variables
    private float x2 = 50.0f;
    private float v2 = 100.0f;


    private float wi = 20;
    private float wo = 40;

    private ArrayList<Integer> keys = new ArrayList<>();

    private void handlekeys(){
        for(int key : keys){
            switch(key){

            }
        }
    }

    public Game(int width, int height, int fps){
        super("JFrame Demo");
        this.MAX_FPS = fps;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    void init(){
        //initialize JFrame
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(0, 0, WIDTH, HEIGHT);

        setIgnoreRepaint(true);

        setResizable(false);
        setVisible(true);

        //create double buffer strategy
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        lastFrame = System.currentTimeMillis();

        addKeyListener(this);
        setFocusable(true);
    }

    private void update() {
        //update current fps
        fps = (int) (1f / dt);
        handlekeys();

        //update sprite
        x += v;
        if (x < 50 || x > (WIDTH - 50)) v *= -1;

        x2 += v2 * dt;
        if (x2 < 50 || x2 > (WIDTH - 50)) v2 *= -1.0f;

        if (move == 1 && domove == 1){
            wi = wi-5;
        }
        if (move == 2 && domove == 1){
            wi = wi+5;
        }
        if (move == 3 && domove == 1){
            wo = wo-5;
        }
        if (move == 4 && domove == 1){
            wo = wo+5;
        }
        if(hp == 0){
            current_state = game_state.end;
        }

    }




    @Override
    public void keyTyped(KeyEvent keyEvent) {


    }

    public void keyPressed(KeyEvent w) {
        if(!keys.contains(w.getKeyCode()))
     keys.add(w.getKeyCode());

        switch (current_state) {

            case menu:
                switch (w.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                       current_state = game_state.play_game;
                       break;

                }

                break;
            case play_game:
                switch (w.getKeyCode()) {

                case KeyEvent.VK_W:
                    move = 1;
                    domove = 1;
                    break;
                case KeyEvent.VK_S:
                    move = 2;
                    domove = 1;
                    break;
                case KeyEvent.VK_A:
                    move = 3;
                    domove = 1;
                    break;
                case KeyEvent.VK_D:
                    move = 4;
                    domove = 1;
                    break;
                    case KeyEvent.VK_MINUS:
                    current_state = game_state.encounter;
                    break;
                }
                break;
            case encounter:
                switch (w.getKeyCode()) {
                    case KeyEvent.VK_W:
                       b_up = 500;
                        break;
                    case KeyEvent.VK_S:
                        b_up = 550;
                        break;
                    case KeyEvent.VK_A:
                        b_over = 20;
                        break;
                    case KeyEvent.VK_D:
                        b_over = 230;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        current_state = game_state.play_game;
                        break;
                    case KeyEvent.VK_MINUS:
                        hp = hp-1;
                        break;
                    case KeyEvent.VK_EQUALS:
                        if(hp != 100) {
                            hp = hp + 1; }
                        break;
                    case KeyEvent.VK_ENTER:
                        select = 10;
                }
                break;
            case end:
                switch (w.getKeyCode()){
                    case KeyEvent.VK_ENTER:
                    hp = 100;
                    current_state = game_state.menu;
                }
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        domove = 0;
    }



    private void draw(){

//get canvas
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        switch(current_state){

            case menu:


            //clear screen
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH, HEIGHT);

            //draw fps
            g.setColor(Color.GREEN);
            g.drawString(Long.toString(fps), 10, 40);

            g.setColor(Color.GRAY);
            g.fillRect(200, 500, 400, 100 );

            g.setColor(Color.GRAY);
            g.fillRect(200, 397, 400, 103 );

            g.setColor(Color.black);
            g.fillRect(200, 497, 400, 3 );
            break;
            case play_game:

                //clear screen
                g.setColor(Color.black);
                g.fillRect(0,0,WIDTH, HEIGHT);

                //draw fps
                g.setColor(Color.GREEN);
                g.drawString(Long.toString(fps), 10, 40);

                g.setColor(Color.GRAY);
                g.fillRect( (int)wo, (int)wi, 40,40);
                break;
            case encounter:
                g.setColor(Color.black);
                g.fillRect(0,0,WIDTH, HEIGHT);

                //draw fps
                g.setColor(Color.GREEN);
                g.drawString(Long.toString(fps), 10, 40);

                g.setColor(Color.gray);
                g.fillRect(20,500,200, 40);

                g.setColor(Color.gray);
                g.fillRect(20,550,200, 40);

                g.setColor(Color.gray);
                g.fillRect(230,500,200, 40);

                g.setColor(Color.gray);
                g.fillRect(230,550,200, 40);

                g.setColor(Color.red);
                g.fillRect((int)b_over,(int)b_up,20, 40);
                if(select > 0){
                    g.setColor(Color.white);
                    g.fillRect((int)b_over,(int)b_up,200, 40);
                    select = select-1;
                }

                g.setColor(Color.white);
                g.fillRect(490,500,280, 30);

                if(hp >= 70) {
                    g.setColor(Color.green);
                }
                if(hp < 70 && hp >= 30){
                    g.setColor(Color.yellow);
                }
                if(hp < 30){
                    g.setColor(Color.red);
                }

                g.fillRect(560,504,(int)hp*2, 22);
                break;
            case end:
                g.setColor(Color.red);
                g.fillRect(0,0,WIDTH, HEIGHT);
                g.setColor(Color.black);
                g.drawString("DEATH", 400, 300);

        }

        //release resources, show the buffer
        g.dispose();
        strategy.show();
    }


    public void run(){
        init();

        while(isRunning){
            //new loop, clock the start
            startFrame = System.currentTimeMillis();
            //calculate delta time
            dt = (float)(startFrame - lastFrame)/1000;
            //log the current time
            lastFrame = startFrame;

            //call update and draw methods
            update();
            draw();

            //dynamic thread sleep, only sleep the time we need to cap the framerate
            rest = (1000/MAX_FPS) - (System.currentTimeMillis() - startFrame);
            if(rest >0){
                try{ Thread.sleep(rest); }
                catch (InterruptedException e){ e.printStackTrace(); }
            }
        }

    }


    public static void main(String[] args){
        Game game = new Game(800, 600, 60);
        game.run();
    }

}
