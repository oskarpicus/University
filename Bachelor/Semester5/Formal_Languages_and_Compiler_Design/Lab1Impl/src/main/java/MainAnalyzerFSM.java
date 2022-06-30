import analysis.LexicalAnalyzer;
import analysis.LexicalAnalyzerFSM;
import state_machine.StateMachine;

import java.io.File;
import java.net.URL;

public class MainAnalyzerFSM {
    public static void main(String[] args) throws Exception {
        StateMachine stateMachineId = StateMachine.from(new File("src/main/resources/identifiers.json"));
        StateMachine stateMachineIntConst = StateMachine.from(new File("src/main/resources/cppConst.json"));
        StateMachine stateMachineOperator = StateMachine.from(new File("src/main/resources/operators.json"));
        StateMachine stateMachineFloatConst = StateMachine.from(new File("src/main/resources/floats.json"));
        LexicalAnalyzer analyzer = new LexicalAnalyzerFSM(stateMachineId, stateMachineIntConst, stateMachineFloatConst, stateMachineOperator);

        URL url = Main.class.getClassLoader().getResource("a.cpp");
        analyzer.analyse(new File(url.toURI()));
        var fip = analyzer.getFip();
        var ts = analyzer.getTs();
        fip.forEach(System.out::println);
        System.out.println(" ");
        System.out.println(ts);
    }
}
