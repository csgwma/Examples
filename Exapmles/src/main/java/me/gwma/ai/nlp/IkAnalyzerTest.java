package me.gwma.ai.nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IkAnalyzerTest {

    public static void main(String[] args) throws Exception {
        String filePath = "src/main/resources/data/ik-test.txt";
        String news = "";
        BufferedReader in = new BufferedReader(new FileReader(new File(filePath)));
        String str;
        while ((str = in.readLine()) != null) {
            news += str;
        }
        in.close();
        System.out.println("sentences= " + news);
        IKAnalyzer analyzer = new IKAnalyzer(true);
        StringReader reader = new StringReader(news);
        TokenStream ts = analyzer.tokenStream("", reader);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        System.out.println("----Method1: ---");
        while (ts.incrementToken()) {
            System.out.print(term.toString() + "|");
        }
        analyzer.close();
        reader.close();

        System.out.println();
        System.out.println("----Method2: ---");
        StringReader re = new StringReader(news);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex = null;
        while ((lex = ik.next()) != null) {
            System.out.print(lex.getLexemeText() + "|");
        }
    }

}
