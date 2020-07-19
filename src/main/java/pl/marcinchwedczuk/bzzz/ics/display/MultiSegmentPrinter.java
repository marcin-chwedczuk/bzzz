package pl.marcinchwedczuk.bzzz.ics.display;

public class MultiSegmentPrinter {
    private final StringBuilder[] lines = new StringBuilder[] {
            new StringBuilder(),
            new StringBuilder(),
            new StringBuilder()
    };

    private final SevenSegmentDisplay[] displays;

    public MultiSegmentPrinter(SevenSegmentDisplay... displays) {
        this.displays = displays;
    }

    public void printToStdOut() {
        for (StringBuilder l: lines) {
            l.replace(0, l.length(), "");
        }

        for (SevenSegmentDisplay display: displays) {
            int i = 0;
            for (String tmp: display.print3Lines()) {
                lines[i].append(' ').append(tmp);
                i++;
            }
        }

        System.out.println("--- 7-SEGMENT DISPLAY ---");
        for(StringBuilder l: lines) {
            System.out.println(l.toString());
        }
        System.out.println();
    }
}
