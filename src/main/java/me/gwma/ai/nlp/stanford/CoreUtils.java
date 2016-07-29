package me.gwma.ai.nlp.stanford;

import java.io.PrintStream;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.simple.Sentence;

public class CoreUtils {

    public static String format = "%5d %20s %20s %20s %20s";

    public static void outputDependencies(SemanticGraph sg) {
        outputDependencies(sg, System.out);
    }

    public static void outputDependencies(SemanticGraph sg, PrintStream out) {
        out.println(sg.toString(CoreLabel.OutputFormat.VALUE_TAG_INDEX));
        // out.println(sg.toString(CoreLabel.OutputFormat.VALUE_TAG_NER));
        // out.println(sg.toString(CoreLabel.OutputFormat.VALUE_TAG));
        for (SemanticGraphEdge sge : sg.edgeIterable()) {
            out.println(sge.getRelation() + " ( " + sge.getSource().toString(CoreLabel.OutputFormat.VALUE_INDEX) + ", "
                        + sge.getTarget().toString(CoreLabel.OutputFormat.VALUE_INDEX) + " )");
            // out.println(sge.getRelation() + " ( " + sge.getSource().toString(CoreLabel.OutputFormat.VALUE_TAG_INDEX)
            // + ", " + sge.getTarget().toString(CoreLabel.OutputFormat.VALUE_TAG_INDEX) + " )");
        }
    }

    public static void outputSentenceTokens(Sentence sent) {
        for (int i = 0; i < sent.words().size(); ++i) {
            System.out.println(String.format(format, i, sent.word(i), sent.lemma(i), sent.posTag(i), sent.nerTag(i)));
        }
    }
}
