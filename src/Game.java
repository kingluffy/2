import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    //sprite1 variables
    private float x = 50;
    private float v = 10;

    //sprite2 variables
    private float x2 = 50.0f;
    private float v2 = 100.0f;


    private float wi = 20;
    private float wo = 40;

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

        //update sprite
        x += v;
        if (x < 50 || x > (WIDTH - 50)) v *= -1;

        x2 += v2 * dt;
        if (x2 < 50 || x2 > (WIDTH - 50)) v2 *= -1.0f;



        if(goup = true){
            wo = wo-10;

        }
        if(godown = true){
            wo = wo+10;

        }
        if(goleft = true){
            wi = wi-10;
        }
        if(goright = true){
            wi = wi+10;
        }


    }

public void keyPressed(KeyEvent w){

        switch(w.getKeyCode()){
            case KeyEvent.VK_W:
                goup = true;
                godown = false;
                goleft = false;
                goright = false;
                break;
            case KeyEvent.VK_S:
                goup = false;
                godown = true;
                goleft = false;
                goright = false;
                break;
            case KeyEvent.VK_A:
                goup = false;
                godown = false;
                goleft = true;
                goright = false;
                break;
            case KeyEvent.VK_D:
                goup = false;
                godown = false;
                goleft = false;
                goright = true;
                break;


        }

}

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {


    }


    private void draw(){
        //get canvas
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        //clear screen
        g.setColor(Color.blue);
        g.fillRect(0,0,WIDTH, HEIGHT);

        //draw fps
        g.setColor(Color.GREEN);
        g.drawString(Long.toString(fps), 10, 40);

        g.setColor(Color.black);
        g.fillRect((int)wi, (int)wo, 40, 40 );

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
        Game game = new Game(400, 300, 60);
        game.run();
    }

}
