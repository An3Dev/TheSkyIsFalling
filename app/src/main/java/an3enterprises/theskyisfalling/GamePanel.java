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

    private Rect r = new Rect();

    private Paint score, gameOverText;

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

    private Point originalPlayerPoint, originalBlockOne, originalBlockTwo, originalBlockThree,
            originalBlockFour, originalBlockFive, originalBlockSix, originalBlockSeven;

    public static int blocksDodged = 0;

    private Grass grass;
    private Point grassPoint;

    private float touchPoint;

    private float movedPoint;

    private int totalMoved;

    boolean gameOver = false;

    int tapThreeToRestart = 0;


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

        score = new Paint();
        gameOverText = new Paint();

        score.setColor(Color.argb(255, 0, 0, 0));
        score.setTextSize(50);

        gameOverText.setColor(Color.argb(255, 0, 0, 0));
        gameOverText.setTextSize(70);

        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 400);

        skyBlockOne = new SkyBlockOne(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        originalBlockOne = skyBlockOnePoint = new Point(100, -100);
        skyBlockOne.update(skyBlockOnePoint);

        skyBlockTwo = new SkyBlockTwo(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        originalBlockTwo = skyBlockTwoPoint = new Point(250, -100);
        skyBlockTwo.update(skyBlockTwoPoint);

        skyBlockThree = new SkyBlockThree(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        originalBlockThree = skyBlockThreePoint = new Point(400, -100);
        skyBlockThree.update(skyBlockThreePoint);

        skyBlockFour = new SkyBlockFour(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        originalBlockFour = skyBlockFourPoint = new Point(550, -100);
        skyBlockFour.update(skyBlockFourPoint);

        skyBlockFive = new SkyBlockFive(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        originalBlockFive = skyBlockFivePoint = new Point(700, -100);
        skyBlockFive.update(skyBlockFivePoint);

        skyBlockSix = new SkyBlockSix(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        originalBlockSix = skyBlockSixPoint = new Point(850, -100);
        skyBlockSix.update(skyBlockSixPoint);

        skyBlockSeven = new SkyBlockSeven(new Rect(100, 100, 200, 200), Color.rgb(0, 160, 160));
        originalBlockSeven = skyBlockSevenPoint = new Point(1000, -100);
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

        canvas.drawText(blocksDodged + " SkyBlocks Dodged", 100, Constants.SCREEN_HEIGHT - 150, score);
        if (gameOver) {
            gameOverText.setColor(Color.argb(255, 0, 0, 0));
        }
        else {
            gameOverText.setColor(Color.argb(0, 0, 0, 0));
        }

        drawCenterText(canvas, gameOverText, "The Sky has killed you!");

    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
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

    public void reset() {
        blocksDodged = 0;

        skyBlockOnePoint = originalBlockOne;
        skyBlockTwoPoint = originalBlockTwo;
        skyBlockThreePoint = originalBlockThree;
        skyBlockFourPoint = originalBlockFour;
        skyBlockFivePoint = originalBlockFive;
        skyBlockSixPoint = originalBlockSix;
        skyBlockSevenPoint = originalBlockSeven;

        skyBlockOne.update(skyBlockOnePoint);
        skyBlockTwo.update(skyBlockTwoPoint);
        skyBlockThree.update(skyBlockThreePoint);
        skyBlockFour.update(skyBlockFourPoint);
        skyBlockFive.update(skyBlockFivePoint);
        skyBlockSix.update(skyBlockSixPoint);
        skyBlockSeven.update(skyBlockSevenPoint);

        movingPlayer = false;
        gameOver = false;

        startTime = System.currentTimeMillis();
    }

    public boolean playerAndSkyCollide() {
        return Rect.intersects(player.getRectangle(), skyBlockOne.getRectangle())
                || Rect.intersects(player.getRectangle(), skyBlockTwo.getRectangle())
                || Rect.intersects(player.getRectangle(), skyBlockThree.getRectangle())
                || Rect.intersects(player.getRectangle(), skyBlockFour.getRectangle())
                || Rect.intersects(player.getRectangle(), skyBlockFive.getRectangle())
                || Rect.intersects(player.getRectangle(), skyBlockSix.getRectangle())
                || Rect.intersects(player.getRectangle(), skyBlockSeven.getRectangle());
    }

    public void update() {

        System.out.println("gameOver:" + gameOver);
        System.out.println("movingPlayer: " + movingPlayer);

        if (!gameOver) {
            player.update(playerPoint);

            System.out.println("Blocks dodged: " + blocksDodged);

//        if (player.getRectangle().top == skyBlockOne.getRectangle().bottom
//                || player.getRectangle().top == skyBlockTwo.getRectangle().bottom
//                || player.getRectangle().top == skyBlockThree.getRectangle().bottom
//                || player.getRectangle().top == skyBlockFour.getRectangle().bottom
//                || player.getRectangle().top == skyBlockFive.getRectangle().bottom
//                || player.getRectangle().top == skyBlockSeven.getRectangle().bottom) {
//
//            System.out.println("Game Over");
//        }

            if (playerAndSkyCollide()) {
                gameOver = true;



            }

            if (moveSkyBlockOne) {
                skyBlockOne.incrementY(10);
                if (skyBlockOne.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockOne.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                    blocksDodged += 1;
                    skyBlockOne.update(skyBlockOnePoint);
                    moveSkyBlockOne = false;
                }
            }
            if (moveSkyBlockTwo) {
                skyBlockTwo.incrementY(10);
                if (skyBlockTwo.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockTwo.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                    blocksDodged += 1;
                    skyBlockTwo.update(skyBlockTwoPoint);
                    moveSkyBlockTwo = false;
                }
            }
            if (moveSkyBlockThree) {
                skyBlockThree.incrementY(10);
                if (skyBlockThree.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockThree.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                    blocksDodged += 1;
                    skyBlockThree.update(skyBlockThreePoint);
                    moveSkyBlockThree = false;
                }
            }
            if (moveSkyBlockFour) {
                skyBlockFour.incrementY(10);
                if (skyBlockFour.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockFour.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                    blocksDodged += 1;
                    skyBlockFour.update(skyBlockFourPoint);
                    moveSkyBlockFour = false;
                }
            }
            if (moveSkyBlockFive) {
                skyBlockFive.incrementY(10);
                if (skyBlockFive.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockFive.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                    blocksDodged += 1;
                    skyBlockFive.update(skyBlockFivePoint);
                    moveSkyBlockFive = false;
                }
            }
            if (moveSkyBlockSix) {
                skyBlockSix.incrementY(10);
                if (skyBlockSix.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockSix.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                    blocksDodged += 1;
                    skyBlockSix.update(skyBlockSixPoint);
                    moveSkyBlockSix = false;
                }
            }
            if (moveSkyBlockSeven) {
                skyBlockSeven.incrementY(10);
                if (skyBlockSeven.getRectangle().bottom == Constants.SCREEN_HEIGHT - 300 || skyBlockSeven.getRectangle().bottom == Constants.SCREEN_HEIGHT - 280) {
                    blocksDodged += 1;
                    skyBlockSeven.update(skyBlockSevenPoint);
                    moveSkyBlockSeven = false;
                }
            }
            currentTime = System.currentTimeMillis();

            elapsedTime = (currentTime - startTime) / 1000;
            int randomBlocks = (int) (Math.random() * 766 + 1);
            System.out.println("Elapsed time: " + elapsedTime + "  randomBlock: " + randomBlocks);


            if (elapsedTime == 1) {
                System.out.println("Is elapsed time 1 ");
                startTime = System.currentTimeMillis();

                if (randomBlocks == 1) {
                    moveSkyBlockOne = true;
                }
                int num = randomBlocks;
                String strNum = "" + num;
                int strLength = strNum.length();
                int sum = 0;

                for (int i = 0; i < strLength; ++i) {
                    int digit = Integer.parseInt(strNum.charAt(i) + "");
                    sum += (digit * digit);
                    System.out.println("result: " + digit);
                    switch (digit) {
                        case 1:
                            moveSkyBlockOne = true;
                            System.out.println("moveSkyBlockOne");
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
            }
//
        }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
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

                if (gameOver) {
                    tapThreeToRestart += 1;
                }


                break;

        }

        if (tapThreeToRestart == 3) {
            tapThreeToRestart = 0;
            reset();
            movingPlayer = false;

        }

        return true;
    }

    public static Point subtract(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }
}