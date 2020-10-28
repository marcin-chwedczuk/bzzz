package pl.marcinchwedczuk.bzzz;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class SevenSegmentDigit extends Region {

    private final static double ORIGINAL_WIDTH = 450;
    private final static double ORIGINAL_HEIGHT = 670;

    private ObjectProperty<Color> lightColor = new SimpleObjectProperty<>(
            this, "lightColor", Color.web("#ed3237")
    );

    private ObjectProperty<Color> darkColor = new SimpleObjectProperty<>(
            this, "darkColor", Color.web("#441f11")
    );

    private Canvas canvas;
    private GraphicsContext ctx;

    private void drawSegmentA() {
        double arcCenterX = 130;
        double arcCenterY = 74;

        double arcStartX = 109;
        double arcStartY = 2;

        double arcStopX = 60;
        double arcStopY = 47;

        arc(arcCenterX, arcCenterY, arcStartX, arcStartY, arcStopX, arcStopY);

        // -0.5 to remove artifacts
        polygon(arcCenterX-0.5, arcCenterY,
                //arcStopX, arcStopY,
                arcStartX-0.5, arcStartY,
                388, 1,
                353, 74);
    }

    private void drawSegmentB() {
        double arcCenterX = 373, arcCenterY = 74;
        double startX = 449, startY = 51;
        double stopX = 407, stopY = 1;

        arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY);
        polygon(arcCenterX, arcCenterY - 0.5,
                startX, startY - 0.5,
                423, 307,
                403, 326,
                353, 270);
    }

    private void drawSegmentC() {
        double arcCenterX = 320, arcCenterY = 596;
        double startX = 341, startY = 669;
        double stopX = 392, stopY = 622;

        arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY);
        polygon(arcCenterX, arcCenterY + 0.5,
                stopX, stopY + 0.5,
                417, 364,
                401, 345,
                340, 402
                );
    }

    private void drawSegmentD() {
        double arcCenterX = 77, arcCenterY = 596;
        double startX = 1, startY = 624;
        double stopX = 42, stopY = 669;

        arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY);
        polygon(arcCenterX - 0.5, arcCenterY,
                stopX - 0.5, stopY,
                321, 669,
                301, 596
        );
    }

    private void drawSegmentE() {
        polygon(4, 604,
                27, 364,
                47, 345,
                98, 401,
                79, 577);
    }

    private void drawSegmentF() {
        polygon(33, 307,
                57, 67,
                128, 93,
                111, 270,
                49, 326);
    }

    private void drawSegmentG() {
        polygon(68, 335,
                108, 298,
                350, 299,
                383, 336,
                343, 372,
                100, 372);
    }

    public SevenSegmentDigit() {
        canvas = new Canvas();
        ctx = canvas.getGraphicsContext2D();

        getChildren().add(canvas);

        init();

        widthProperty().addListener(observable -> resize());
        heightProperty().addListener(observable -> resize());
    }

    private void init() {
        if (Double.compare(getWidth(), 0) <= 0 || Double.compare(getHeight(), 0) <= 0 ||
                Double.compare(getPrefWidth(), 0) <= 0 || Double.compare(getPrefHeight(), 0) <= 0) {
            setPrefSize(ORIGINAL_WIDTH, ORIGINAL_HEIGHT);
        }
        if (Double.compare(getMinWidth(), 0) <= 0 || Double.compare(getMinHeight(), 0) <= 0) {
            setMinSize(10, 10);
        }
        if (Double.compare(getMaxWidth(), 0) <= 0 || Double.compare(getMaxHeight(), 0) <= 0) {
            setMaxSize(1000, 1000);
        }
    }

    private void resize() {
        draw();
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        if (width <= 0 || height <= 0) return;

        // Clean control area
        canvas.setWidth(width);
        canvas.setHeight(height);
        //canvas.setScaleY(1.0); canvas.setScaleX(1.0);
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, width, height);
        //ctx.clearRect(0, 0, width, height);

        // Setup aspect ration
        double originalAspect = ORIGINAL_WIDTH / ORIGINAL_HEIGHT;
        double canvasAspect = width / height;

        double finalWidth, finalHeight;
        if (originalAspect >= canvasAspect) {
            finalWidth = width;
            finalHeight = finalWidth / originalAspect;
        } else {
            finalHeight = height;
            finalWidth = finalHeight * originalAspect;
        }

        // canvas.setScaleX(finalWidth / (2*ORIGINAL_WIDTH));
        //canvas.setScaleY(finalHeight / (2*ORIGINAL_HEIGHT));

        ctx.save();
        ctx.scale(finalWidth / ORIGINAL_WIDTH, finalHeight / ORIGINAL_HEIGHT);
        ctx.setFill(lightColor.get());
        ctx.setStroke(lightColor.get());

        // Draw digit
        drawSegmentA();
        drawSegmentB();
        drawSegmentC();
        drawSegmentD();
        drawSegmentE();
        drawSegmentF();

        ctx.setFill(darkColor.get());
        ctx.setStroke(darkColor.get());
        drawSegmentG();

        ctx.restore();
    }

    // Center + 2 points counter-clock wise
    private void arc(double arcCenterX, double arcCenterY,
                    double arcStartX, double arcStartY,
                    double arcStopX, double arcStopY) {
        double arcR = dist(arcCenterX, arcCenterY, arcStartX, arcStartY);

        double startAngle = Math.toDegrees(Math.atan2(-(arcStartY - arcCenterY), arcStartX - arcCenterX));
        double stopAngle = Math.toDegrees(Math.atan2(-(arcStopY - arcCenterY), arcStopX - arcCenterX));

        ctx.fillArc(arcCenterX-arcR, arcCenterY-arcR,
                arcR*2, arcR*2,
                startAngle, stopAngle - startAngle,
                ArcType.ROUND);
    }

    private void polygon(double... points) {
        double[] xs = new double[points.length / 2];
        double[] ys = new double[points.length / 2];

        for (int i = 0; i < points.length / 2; i++) {
            xs[i] = points[2*i];
            ys[i] = points[2*i+1];
        }

        ctx.fillPolygon(xs, ys, xs.length);
    }

    private static double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((ax-bx)*(ax-bx) + (ay-by)*(ay-by));
    }
}
