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
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * 参考资源：使用 Stanford NLP 进行中文分词 https://blog.sectong.com/blog/corenlp_segment.html
 */
public class CoreNLPChinese {

    public static String chinesePkuProp  = "CoreNLP-chinese-pku.properties";
    public static String chineseCtbProp = "CoreNLP-chinese-ctb.properties";

    public static void main(String[] args) {

        // 载入自定义的Properties文件
        StanfordCoreNLP pipeline = new StanfordCoreNLP(chinesePkuProp);
        
        // 用一些文本来初始化一个注释。文本是构造函数的参数。
        Annotation annotation;
        annotation = new Annotation("我爱北京天安门，天安门上太阳升。杭州西湖附近的景点!");

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
            System.out.println("*******************************************");
        }
    }
}
