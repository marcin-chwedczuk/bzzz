package pl.marcinchwedczuk.bzzz;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.List;

public class mcSevenSegmentDigit extends Parent {

    private final List<Shape> segA = createSegmentA();
    private final List<Shape> segB = createSegmentB();
    private final List<Shape> segC = createSegmentC();
    private final List<Shape> segD = createSegmentD();
    private final List<Shape> segE = createSegmentE();
    private final List<Shape> segF = createSegmentF();
    private final List<Shape> segG = createSegmentG();

    private static List<Shape> createSegmentA() {
        double arcCenterX = 130;
        double arcCenterY = 74;

        double arcStartX = 109;
        double arcStartY = 2;

        double arcStopX = 60;
        double arcStopY = 47;

        var arc = arc(arcCenterX, arcCenterY, arcStartX, arcStartY, arcStopX, arcStopY);

        var bloc = polygon(arcCenterX, arcCenterY,
                //arcStopX, arcStopY,
                arcStartX, arcStartY,
                388, 1,
                353, 74);
        bloc.setFill(Color.GREEN);

        return List.of(arc, bloc);
    }

    private static List<Shape> createSegmentB() {
        double arcCenterX = 373, arcCenterY = 74;
        double startX = 449, startY = 51;
        double stopX = 407, stopY = 1;

        return List.of(
                arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY),
                polygon(arcCenterX, arcCenterY,
                        startX, startY,
                        423, 307,
                        403, 326,
                        353, 270)
        );
    }

    private static List<Shape> createSegmentC() {
        double arcCenterX = 320, arcCenterY = 596;
        double startX = 341, startY = 669;
        double stopX = 392, stopY = 622;

        return List.of(
                arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY),
                polygon(arcCenterX, arcCenterY,
                        stopX, stopY,
                        417, 364,
                        401, 345,
                        340, 402
                        )
        );
    }

    private static List<Shape> createSegmentD() {
        double arcCenterX = 77, arcCenterY = 596;
        double startX = 1, startY = 624;
        double stopX = 42, stopY = 669;

        return List.of(
                arc(arcCenterX, arcCenterY, startX, startY, stopX, stopY),
                polygon(arcCenterX, arcCenterY,
                        stopX, stopY,
                        321, 669,
                        301, 596
                )
        );
    }

    private static List<Shape> createSegmentE() {
        return List.of(
                polygon(4, 604,
                        27, 364,
                        47, 345,
                        98, 401,
                        79, 577)
        );
    }

    private static List<Shape> createSegmentF() {
        return List.of(
                polygon(33, 307,
                        57, 67,
                        128, 93,
                        111, 270,
                        49, 326)
        );
    }

    private static List<Shape> createSegmentG() {
        return List.of(
                polygon(68, 335,
                        108, 298,
                        350, 299,
                        383, 336,
                        343, 372,
                        100, 372)
        );
    }

    public mcSevenSegmentDigit() {
        this.getChildren().addAll(segA);
        this.getChildren().addAll(segB);
        this.getChildren().addAll(segC);
        this.getChildren().addAll(segD);
        this.getChildren().addAll(segE);
        this.getChildren().addAll(segF);
        this.getChildren().addAll(segG);
    }

    // Center + 2 points counter-clock wise
    private static Arc arc(double arcCenterX, double arcCenterY,
                           double arcStartX, double arcStartY,
                           double arcStopX, double arcStopY) {
        double arcR = dist(arcCenterX, arcCenterY, arcStartX, arcStartY);

        double startAngle = Math.toDegrees(Math.atan2(-(arcStartY - arcCenterY), arcStartX - arcCenterX));
        double stopAngle = Math.toDegrees(Math.atan2(-(arcStopY - arcCenterY), arcStopX - arcCenterX));

        var arc = new Arc(arcCenterX, arcCenterY, arcR, arcR, startAngle, stopAngle - startAngle);
        arc.setType(ArcType.ROUND);
        arc.setFill(Color.RED);
        return arc;
    }

    private static Polygon polygon(double... points) {
        var bloc = new Polygon(points);
        bloc.setFill(Color.GREEN);
        return bloc;
    }

    private static double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((ax-bx)*(ax-bx) + (ay-by)*(ay-by));
    }
}
