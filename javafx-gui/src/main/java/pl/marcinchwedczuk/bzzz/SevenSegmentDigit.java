package pl.marcinchwedczuk.bzzz;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SevenSegmentDigit extends Region {

    private final static double ORIGINAL_WIDTH = 450;
    private final static double ORIGINAL_HEIGHT = 670;

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

        polygon(arcCenterX, arcCenterY,
                //arcStopX, arcStopY,
                arcStartX, arcStartY,
                388, 1,
                353, 74);
    }

    private void drawSegmentB() {
        double arcCenterX = 373, arcCenterY = 74;
        double startX = 449, startY = 51;
        double stopX = 407, stopY = 1;

        arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY);
        polygon(arcCenterX, arcCenterY,
                startX, startY,
                423, 307,
                403, 326,
                353, 270);
    }

    private void drawSegmentC() {
        double arcCenterX = 320, arcCenterY = 596;
        double startX = 341, startY = 669;
        double stopX = 392, stopY = 622;

        arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY);
        polygon(arcCenterX, arcCenterY,
                stopX, stopY,
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
        polygon(arcCenterX, arcCenterY,
                stopX, stopY,
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
        // TODO: Preserve aspect ratio
        double w = getWidth();
        double h = getHeight();

        double scaleX = w / ORIGINAL_WIDTH;
        double scaleY = h / ORIGINAL_HEIGHT;

        canvas.setScaleX(scaleX);
        canvas.setScaleY(scaleY);

        draw();
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        if (width <= 0 || height <= 0) return;

        // Clean control area
        canvas.setWidth(width);
        canvas.setHeight(height);
        ctx.setFill(Color.AQUA);
        ctx.fillRect(0, 0, width, height);
        //ctx.clearRect(0, 0, width, height);

        // Setup aspect ration
        double aspect = ORIGINAL_WIDTH / ORIGINAL_HEIGHT;
        double finalWidth = width;
        double finalHeight = 1/aspect * finalWidth;

        // Draw digit
        drawSegmentA();
        drawSegmentB();
        drawSegmentC();
        drawSegmentD();
        drawSegmentE();
        drawSegmentF();
        drawSegmentG();
    }

    // Center + 2 points counter-clock wise
    private void arc(double arcCenterX, double arcCenterY,
                    double arcStartX, double arcStartY,
                    double arcStopX, double arcStopY) {
        double arcR = dist(arcCenterX, arcCenterY, arcStartX, arcStartY);

        double startAngle = Math.toDegrees(Math.atan2(-(arcStartY - arcCenterY), arcStartX - arcCenterX));
        double stopAngle = Math.toDegrees(Math.atan2(-(arcStopY - arcCenterY), arcStopX - arcCenterX));

        ctx.setFill(Color.RED);
        ctx.fillArc(arcCenterX-arcR, arcCenterY-arcR,
                arcR*2, arcR*2,
                startAngle, stopAngle - startAngle,
                ArcType.ROUND);
    }

    private void polygon(double... points) {
        ctx.setFill(Color.GREEN);

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
