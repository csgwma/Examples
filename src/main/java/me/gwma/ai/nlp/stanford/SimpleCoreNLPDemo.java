package me.gwma.ai.nlp.stanford;

import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.StringUtils;

public class SimpleCoreNLPDemo {

    public static void main(String[] args) {
        testSentence(true);

//        testDocSentenceTokens();
    }

    /**
     * 测试一句话的分词
     */
    public static void testSentence(boolean chinese) {
        // go ahead! 属于第二句话不会添加上
        Sentence sent = null;
        if(chinese){
            Properties props = new Properties();
            props = StringUtils.propFileToProperties("src/main/resources/StanfordCoreNLP-chinese.properties");
            props.setProperty("annotators", "segment,ssplit,pos,lemma,ner");
            sent = new Sentence("杭州西湖附近的景点!", props);
        }else{
            sent = new Sentence("Lucy is in the sky with diamonds. go ahead!");
        }
        System.out.println("--- print the info of each parsed word");
        String format = "%5d %20s %20s %20s %20s";
        System.out.println(String.format("%5s %20s %20s %20s %20s", "NO", "word", "lemma", "posTag", "nerTag"));
        for (int i = 0; i < sent.words().size(); ++i) {
            System.out.println(String.format(format, i, sent.word(i), sent.lemma(i), sent.posTag(i), sent.nerTag(i)));
        }
        System.out.println("The parse of the sentence '" + sent + "' is " + sent.parse());
    }
    
    

    /**
     * 
     */
    public static void testDocSentenceTokens() {
        // Create a document. No computation is done yet.
        Document doc = new Document("add your text here! It contains multiple sentences.");
        // Will iterate over two sentences
        for (Sentence sent : doc.sentences()) {
            System.out.println("The sentence = " + sent.text());
            // We're only asking for words -- no need to load any models yet
            System.out.println("The second word of the sentence '" + sent + "' is " + sent.word(1));
            // When we ask for the lemma, it will load and run the part of speech tagger
            System.out.println("The third lemma of the sentence '" + sent + "' is " + sent.lemma(2));
            // When we ask for the parse, it will load and run the parser
            System.out.println("The parse of the sentence '" + sent + "' is " + sent.parse());
            // ...
            String format = "%5d %20s %20s %20s %20s";
            System.out.println(String.format("%5s %20s %20s %20s %20s", "NO", "word", "lemma", "posTag", "nerTag"));
            for (int i = 0; i < sent.words().size(); ++i) {
                System.out.println(String.format(format, i, sent.word(i), sent.lemma(i), sent.posTag(i),
                                                 sent.nerTag(i)));
            }
        }
    }

    // public static void test(){
    // Annotator pipeline = new StanfordCoreNLP();
    // Annotation annotation = new Annotation(
    // "Can you parse my sentence?");
    // pipeline.annotate(annotation);
    // }
}
