// jshell run-in-jshell.jsh
/env --class-path ./build/libs/bzzz-all.jar

import pl.marcinchwedczuk.bzzz.simulator.*;
import pl.marcinchwedczuk.bzzz.ics.flipflops.*;
import pl.marcinchwedczuk.bzzz.primitives.*;
import pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered.*;

Simulator simulator = new EventLoopSimulator();
CircuitBuilder builder = new CircuitBuilder(simulator);

