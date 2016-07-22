package me.gwma.ai.nlp;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;

public class SegDemo {

    // public static String getProperty(String key, String def)
    // Gets the system property indicated by the specified key.
    private static final String basedir = System.getProperty("SegDemo", "data");

    public static void main(String[] args) throws Exception {
        testChinese();
    }

    public static void test2(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "utf-8"));

        Properties props = new Properties();
        props.setProperty("sighanCorporaDict", basedir);
        // props.setProperty("NormalizationTable", "data/norm.simp.utf8");
        // props.setProperty("normTableEncoding", "UTF-8");
        // below is needed because CTBSegDocumentIteratorFactory accesses it
        props.setProperty("serDictionary", basedir + "/dict-chris6.ser.gz");
        if (args.length > 0) {
            props.setProperty("testFile", args[0]);
        }
        props.setProperty("inputEncoding", "UTF-8");
        props.setProperty("sighanPostProcessing", "true");

        CRFClassifier<CoreLabel> segmenter = new CRFClassifier<CoreLabel>(props);
        segmenter.loadClassifierNoExceptions(basedir + "/ctb.gz", props);
        for (String filename : args) {
            segmenter.classifyAndWriteAnswers(filename);
        }

        String sample = "杭州西湖附近的景点！";
        List<String> segmented = segmenter.segmentString(sample);
        System.out.println(segmented);
    }

    public static void testChinese() {
        Properties props = new Properties();
        props = StringUtils.propFileToProperties("src/main/resources/StanfordCoreNLP-chinese.properties");
        // that properties file will run the entire pipeline
        // if you uncomment the following line it will just go up to ner
        props.setProperty("annotators", "segment,ssplit,pos,lemma,ner");
        System.out.println("---begin to build pipeline");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation("杭州西湖附近的景点！");
        pipeline.annotate(annotation);
        
        System.out.println("---");
        // An Annotation is a Map and you can get and use the 
        // various analyses individually. For instance, this 
        // gets the parse tree of the 1st sentence in the text.
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        if (sentences != null && sentences.size() > 0) {
            CoreMap sentence = sentences.get(0);
            Tree tree = sentence.get(TreeAnnotation.class);
            System.out.println("---Tree="+tree.toString());
            PrintWriter out = new PrintWriter(System.out);
            out.println("The first sentence parsed is:");
            tree.pennPrint(out);
        }
    }
}
