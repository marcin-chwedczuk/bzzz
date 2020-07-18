// ./gradlew fatJar && jshell run-in-jshell.jsh
/env --class-path ./build/libs/bzzz-all.jar

import pl.marcinchwedczuk.bzzz.simulator.*;
import pl.marcinchwedczuk.bzzz.ics.flipflops.*;
import pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered.*;
import pl.marcinchwedczuk.bzzz.primitives.*;
import pl.marcinchwedczuk.bzzz.primitives.other.*;
import pl.marcinchwedczuk.bzzz.primitives.passives.*;
import pl.marcinchwedczuk.bzzz.simulated.*;

Simulator simulator = new EventLoopSimulator();
CircuitBuilder builder = new CircuitBuilder(simulator);

var divider = new FrequencyDivider(builder, ComponentId.of("divider"));
// IC555 ic555 = new IC555(simulator, ComponentId.of("555"));
Probe p = builder.probeFor(divider.output());
p.logStateChanges();

