package an3enterprises.theskyisfalling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Point playerPoint;
    private Player player;

    private Paint text;

    private boolean moveSkyBlockOne = false, moveSkyBlockTwo = false, moveSkyBlockThree = false, moveSkyBlockFour = false, moveSkyBlockFive  = false,
            moveSkyBlockSix = false, moveSkyBlockSeven = false;


    private Point skyBlockOnePoint;
    private SkyBlockOne skyBlockOne;

    private Point skyBlockTwoPoint;
    private SkyBlockTwo skyBlockTwo;

    private Point skyBlockThreePoint;
    private SkyBlockThree skyBlockThree;

    private Point skyBlockFourPoint;
    private SkyBlockFour skyBlockFour;

    private Point skyBlockFivePoint;
    private SkyBlockFive skyBlockFive;

    private Point skyBlockSixPoint;
    private SkyBlockSix skyBlockSix;

    private Point skyBlockSevenPoint;
    private SkyBlockSeven skyBlockSeven;

    public static int blocksDodged = 0;

    private Grass grass;
    private Point grassPoint;

    private float touchPoint;

    private float movedPoint;

    private int totalMoved;


    Context context;



    boolean movingPlayer = false;

    long startTime;
    long currentTime;
    long elapsedTime;


    public GamePanel(Context context) {
        super(context);

        this.context = context;

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new Player(new Rect(100, 100, 200, 200), Color.rgb(160, 0, 160));

        text = new Paint();

        text.setColor(Color.argb(255, 0, 0, 0));
        text.setTextSize(40);

        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 400);



        skyBlockOne = new SkyBlockOne(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        skyBlockOnePoint = new Point(100, 0);
        skyBlockOne.update(skyBlockOnePoint);

        skyBlockTwo = new SkyBlockTwo(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        skyBlockTwoPoint = new Point(250, 0);
        skyBlockTwo.update(skyBlockTwoPoint);

        skyBlockThree = new SkyBlockThree(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        skyBlockThreePoint = new Point(400, 0);
        skyBlockThree.update(skyBlockThreePoint);

        skyBlockFour = new SkyBlockFour(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        skyBlockFourPoint = new Point(550, 0);
        skyBlockFour.update(skyBlockFourPoint);

        skyBlockFive = new SkyBlockFive(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        skyBlockFivePoint = new Point(700, 0);
        skyBlockFive.update(skyBlockFivePoint);

        skyBlockSix = new SkyBlockSix(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        skyBlockSixPoint = new Point(850, 0);
        skyBlockSix.update(skyBlockSixPoint);

        skyBlockSeven = new SkyBlockSeven(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        skyBlockSevenPoint = new Point(1000, 0);
        skyBlockSeven.update(skyBlockSevenPoint);



        // Left, Top, Right, Bottom
        grass = new Grass(new Rect(0, 500, Constants.SCREEN_WIDTH, 130), Color.rgb(0, 200, 50));

        grassPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 160);

        grass.update(grassPoint);

//        skyBlockOnePoint = new Point(Constants.SCREEN_WIDTH + 300, Constants.SCREEN_HEIGHT / 2);

//        skyBlockOne = new SkyBlockOne(new Rect(200, 200, 200, 200), Color.rgb(0, 0, 0));

        setFocusable(true);

        startTime = System.currentTimeMillis();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);



        player.draw(canvas);

        skyBlockOne.draw(canvas);
        skyBlockTwo.draw(canvas);
        skyBlockThree.draw(canvas);
        skyBlockFour.draw(canvas);
        skyBlockFive.draw(canvas);
        skyBlockSix.draw(canvas);
        skyBlockSeven.draw(canvas);


        grass.draw(canvas);

        canvas.drawText(blocksDodged + " SkyBlocks Dodged", Constants.SCREEN_WIDTH + 100, Constants.SCREEN_HEIGHT / 4, text);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        while(true) {
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update(playerPoint);

        if (moveSkyBlockOne) {
            skyBlockOne.incrementY(10);
            if (skyBlockOne.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockOne.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                blocksDodged += 1;
                Point subtract = new Point(0, 100);
                skyBlockOne.update(subtract(skyBlockOnePoint, subtract));
                moveSkyBlockOne = false;
            }
        }
        if (moveSkyBlockTwo) {
            skyBlockTwo.incrementY(10);
            if (skyBlockTwo.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockTwo.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                blocksDodged += 1;
                Point subtract = new Point(0, 100);
                skyBlockTwo.update(subtract(skyBlockTwoPoint, subtract));
                moveSkyBlockTwo = false;
            }
        }
        if (moveSkyBlockThree) {
            skyBlockThree.incrementY(10);
            if (skyBlockThree.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockThree.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                blocksDodged += 1;
                Point subtract = new Point(0, 100);
                skyBlockThree.update(subtract(skyBlockThreePoint, subtract));
                moveSkyBlockThree = false;
            }
        }
        if (moveSkyBlockFour) {
            skyBlockFour.incrementY(10);
            if (skyBlockFour.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockFour.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                blocksDodged += 1;
                Point subtract = new Point(0, 100);
                skyBlockFour.update(subtract(skyBlockFourPoint, subtract));
                moveSkyBlockFour = false;
            }
        }
        if (moveSkyBlockFive) {
            skyBlockFive.incrementY(10);
            if (skyBlockFive.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockFive.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                blocksDodged += 1;
                Point subtract = new Point(0, 100);
                skyBlockFive.update(subtract(skyBlockFivePoint, subtract));
                moveSkyBlockFive = false;
            }
        }
        if (moveSkyBlockSix) {
            skyBlockSix.incrementY(10);
            if (skyBlockSix.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockSix.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                blocksDodged += 1;
                Point subtract = new Point(0, 100);
                skyBlockSix.update(subtract(skyBlockSixPoint, subtract));
                moveSkyBlockSix = false;
            }
        }
        if (moveSkyBlockSeven) {
            skyBlockSeven.incrementY(10);
            if (skyBlockSeven.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockSeven.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                blocksDodged += 1;
                Point subtract = new Point(0, 100);
                skyBlockSeven.update(subtract(skyBlockSevenPoint, subtract));
                moveSkyBlockSeven = false;
            }
        }
        currentTime = System.currentTimeMillis();

        elapsedTime = (currentTime - startTime) / 1000;
        int randomBlocks = (int) (Math.random() * 8 + 0);
        System.out.println("Elapsed time: " + elapsedTime + "  randomBlock: " + randomBlocks);


        if (elapsedTime == 1) {
            startTime = System.currentTimeMillis();

            if(randomBlocks == 1) {
                moveSkyBlockOne = true;
            }
            switch (randomBlocks) {
                case 0:

                    break;
                case 1:
                    moveSkyBlockOne = true;

                    break;
                case 2:

                    moveSkyBlockTwo = true;
                    break;
                case 3:

                    moveSkyBlockThree = true;
                    break;
                case 4:

                    moveSkyBlockFour = true;
                    break;
                case 5:

                    moveSkyBlockFive = true;
                    break;
                case 6:

                    moveSkyBlockSix = true;
                    break;
                case 7:

                    moveSkyBlockSeven = true;
                    break;
            }
        }



    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:

//                movedPoint = event.getX();
//                totalMoved = Math.round(movedPoint - touchPoint);
//
//                System.out.println("Touch point: " + touchPoint + "  Moved poin");
//
//                playerPoint.set((playerPoint.x += Math.round(totalMoved)), playerPoint.y);

                if (movingPlayer) {
                    playerPoint.set((int) event.getX(), playerPoint.y);
                }

                break;

            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;

        }

        return true;
    }

    public static Point subtract(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }
}