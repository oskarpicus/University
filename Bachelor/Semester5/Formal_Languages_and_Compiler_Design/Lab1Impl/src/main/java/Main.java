import analysis.LexicalAnalyzer;

import java.io.File;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer analyzer = new LexicalAnalyzer();
        URL url = Main.class.getClassLoader().getResource("a.cpp");
        analyzer.analyse(new File(url.toURI()));
        var fip = analyzer.getFip();
        var ts = analyzer.getTs();
        fip.forEach(System.out::println);
        System.out.println(" ");
        System.out.println(ts);
    }
}
