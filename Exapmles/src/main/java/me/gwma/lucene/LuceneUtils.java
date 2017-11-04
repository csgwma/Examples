package me.gwma.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneUtils {

    // Lucene默认的分词器不适应中文，一般用到都需要指定自己的分类器
    // public static Analyzer analyzer2 = new StandardAnalyzer(Version.LUCENE_43);
    public static Analyzer analyzer = new IKAnalyzer(true);

    public static void buildIndex(String dataFilePath, String indexFileDir) {
        BufferedReader br = null;
        IndexWriter iw = null;
        try {
            iw = getIndexWriter(indexFileDir, false);
            br = new BufferedReader(new FileReader(new File(dataFilePath)));
            String line = "";
            while ((line = br.readLine()) != null) {
                CityInfo obj = CityInfo.line2CityInfo(line);
                if (obj != null) {
                    addCityInfo2Doc(iw, obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (iw != null) iw.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * 获取指定索引文件的IndexSearcher
     * 
     * @param indexFilePath
     * @return
     */
    public static IndexSearcher getIndexSearcher(String indexFilePath) {
        try {
            Directory dire = FSDirectory.open(new File(indexFilePath));
            IndexReader ir = DirectoryReader.open(dire);
            IndexSearcher res = new IndexSearcher(ir);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取索引写入器
     * 
     * @param indexPath 索引文件目录
     * @param append 是否追加模式创建索引
     * @return
     * @throws IOException
     */
    private static IndexWriter getIndexWriter(String indexPath, boolean append) throws IOException {
        Directory dir = FSDirectory.open(new File(indexPath)); // 已经默认使用MMapDirectory
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer);
        if (append) {
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        } else {
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        }
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 写入城市对象
     * 
     * @param iw
     * @param obj
     * @throws IOException
     */
    private static void addCityInfo2Doc(IndexWriter iw, CityInfo obj) throws IOException {
        // build document
        Document doc = new Document();
        doc.add(new LongField(FiledName.CITY_ID, obj.getId(), FieldTypes.INDEX_UNTOKEN_STORE_LONG.getType()));
        doc.add(new Field(FiledName.CITY_NAME_CN, obj.getNameCn(), FieldTypes.INDEX_UNTOKEN_STORE_COMMON.getType()));
        doc.add(new Field(FiledName.CITY_NAME_EN, obj.getNameEn(), FieldTypes.INDEX_UNTOKEN_STORE_COMMON.getType()));
        doc.add(new Field(FiledName.CITY_DESC, obj.getDescption(), FieldTypes.INDEX_TOKEN_STORE_COMMON.getType()));
        doc.add(new IntField(FiledName.CITY_VISIT_NUM, obj.getVisitNum(),
                             FieldTypes.INDEX_UNTOKEN_STORE_INT.getType()));
        doc.add(new IntField(FiledName.CITY_COUNTRY_ID, obj.getCountryId(),
                             FieldTypes.UNINDEX_UNTOKEN_STORE_INT.getType()));

        // 也可以前面的索引都不store，最后把对象整体信息序列化存储到起来（不索引）
        // doc.add(new Field(FiledName.CITY_OBJ, Serialization.serialize(obj),
        // FieldTypes.UNINDEX_UNTOKEN_STORE_COMMON.getType()));
        // 通过docment获取代码示例为：
        // CityInfo ci =(CityInfo) Serialization.deserialize(d.getBinaryValue(LuceneFiledName.KG_ENTRY_OBJ).bytes);
        iw.addDocument(doc);
    }

}
