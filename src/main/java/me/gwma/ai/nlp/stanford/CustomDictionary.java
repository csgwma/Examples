package me.gwma.ai.nlp.stanford;

import java.io.PrintWriter;
import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.wordseg.ChineseDictionary;

public class CustomDictionary {

    public static String chineseCtbCustomProp = "CoreNLP-cn-ctb-custom-dict.properties";

    public static void main(String[] args) {
        String[] myArgs = new String[4];
        myArgs[0] = "-inputDicts";
        myArgs[1] = "src/main/resources/dict/name.txt,src/main/resources/dict/place.txt";
        // use our existing dictionary as a starting point
        myArgs[1] += ",edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz";
        myArgs[2] = "-output";
        myArgs[3] = "src/main/resources/custom-dict.ser.gz";
        generateChineseDict(myArgs);
        main2(args);
    }

    public static void main2(String[] args) {

        // 载入自定义的Properties文件
        StanfordCoreNLP pipeline = new StanfordCoreNLP(chineseCtbCustomProp);

        // 用一些文本来初始化一个注释。文本是构造函数的参数。
        Annotation annotation;
        annotation = new Annotation("中国人民共和国成立典礼将于10月1日下午3点在北京天安门举行。杭州西湖附近的景点!");

        // 运行所有选定的代码在本文
        pipeline.annotate(annotation);

        // 从注释中获取CoreMap List，并依次处理CoreMap
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (int i = 0; i < sentences.size(); ++i) {
            CoreMap sentence = sentences.get(i);
            System.out.println("*******************************************");
            System.out.println("Sentence " + i + "  " + sentence.toString());
            // 从CoreMap中取出CoreLabel List，逐一打印出来
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
            System.out.println("-----------------------------");
            System.out.println("字/词" + "\t " + "词性" + "\t " + "实体标记");
            for (CoreLabel token : tokens) {
                String word = token.getString(TextAnnotation.class);
                String pos = token.getString(PartOfSpeechAnnotation.class);
                String ner = token.getString(NamedEntityTagAnnotation.class);
                System.out.println(word + "\t " + pos + "\t " + ner);
            }
            System.out.println("---------- Parse Tree ---------");
            // 输出Sentence对应的Parsed Tree信息
            Tree tree = sentence.get(TreeAnnotation.class);
            System.out.println("Parsed Tree=" + tree.toString());
            PrintWriter out = new PrintWriter(System.out);
            out.println("The sentence parsed is:");
            tree.pennPrint(out);

            // 输出Sentence对应的Dependencies信息
            // SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            CoreUtils.outputDependencies(dependencies, System.out);

            System.out.println("*******************************************");
        }
    }

    /**
     * 生成自定义的词典
     * <p>
     * 手动调用ChineseDictionary.main()函数，不用重新训练模型了
     * 
     * @param args
     */
    public static void generateChineseDict(String[] args) {
        ChineseDictionary.main(args);
    }
}
