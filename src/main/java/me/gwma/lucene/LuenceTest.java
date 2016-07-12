package me.gwma.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.Version;

public class LuenceTest {
	private static final int topN = 100;

	public static List<Document> rangeQuery(IndexSearcher searcher, String filedName, long beg, long end,
			boolean minInclusive, boolean maxInclusive) {
		NumericRangeQuery<Long> numericRangeQuery = NumericRangeQuery.newLongRange(filedName,
				NumericUtils.PRECISION_STEP_DEFAULT, beg, end, minInclusive, maxInclusive);
		// NumericUtils.PRECISION_STEP_DEFAULT 优化用的，不影响效果可以不关注

		// 过滤掉厦门和西安城市
		TermRangeQuery termRangeQuery = TermRangeQuery.newStringRange(FiledName.CITY_NAME_EN, "B", "X", true, false);

		BooleanQuery query = new BooleanQuery();
		if (numericRangeQuery != null) {
			query.add(numericRangeQuery, BooleanClause.Occur.MUST);
		}

		if (termRangeQuery != null) {
			query.add(termRangeQuery, BooleanClause.Occur.MUST);
		}
		List<Document> doclist = query(searcher, query, topN);
		return doclist;
	}

	public static List<Document> termQuery(IndexSearcher searcher, String filedName, String value) {
		TermQuery query = new TermQuery(new Term(filedName, value));
		List<Document> doclist = query(searcher, query, topN);
		return doclist;
	}

	public static List<Document> fuzzyQuery(IndexSearcher searcher, String filedName, String value) {
		Query query = new FuzzyQuery(new Term(filedName, value)); // 类似like
																	// 正则表达式查询，效率不高
		List<Document> doclist = query(searcher, query, topN);
		return doclist;
	}

	private static List<Document> query(IndexSearcher searcher, Query query, int topN) {
		List<Document> retlist = new ArrayList<Document>();
		try {
			TopDocs td = searcher.search(query, topN);
			ScoreDoc[] sds = td.scoreDocs;
			for (ScoreDoc sd : sds) {
				Document d = searcher.doc(sd.doc);
				CityInfo city = doc2CityInfo(d);
				// CityInfo city = (CityInfo)
				// Serialization.deserialize(d.getBinaryValue(FiledName.CITY_OBJ).bytes);
				System.out.println(String.format("%10.4f  -- %s", sd.score, city.toString()));
				retlist.add(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (retlist == null || retlist.isEmpty()) {
			System.out.println("The result is empty!!!");
		}
		return retlist;
	}

	private static CityInfo doc2CityInfo(Document d) {
		CityInfo city = new CityInfo();
		// city.setId(d.getField(FiledName.CITY_ID));
		city.setId(Long.parseLong(d.get(FiledName.CITY_ID)));
		city.setNameCn(d.get(FiledName.CITY_NAME_CN));
		city.setNameEn(d.get(FiledName.CITY_NAME_EN));
		city.setDescption(d.get(FiledName.CITY_DESC));
		city.setVisitNum(Integer.parseInt(d.get(FiledName.CITY_VISIT_NUM)));
		city.setCountryId(Integer.parseInt(d.get(FiledName.CITY_COUNTRY_ID)));
		return city;
	}

	public static void testRangeQuery(IndexSearcher searcher) {
		String filedName = FiledName.CITY_ID;
		long min = 10004;
		long max = 10010;
		boolean minInclusive = true;
		boolean maxInclusive = true;
		rangeQuery(searcher, filedName, min, max, minInclusive, maxInclusive);
	}

	public static void testTermQuery(IndexSearcher searcher) {
		String filedName = FiledName.CITY_DESC;
		String filedValue = "北";
		System.out.println(String.format("\t filedName = %s, filedValue = %s", filedName, filedValue));
		termQuery(searcher, filedName, filedValue);

		filedValue = "南京好"; // 这个相当于分词后的结果了，不会对此进行在分了
		System.out.println(String.format("\t filedName = %s, filedValue = %s", filedName, filedValue));
		termQuery(searcher, filedName, filedValue);

		filedName = FiledName.CITY_NAME_EN;
		filedValue = "h";
		System.out.println(String.format("\t filedName = %s, filedValue = %s", filedName, filedValue));
		termQuery(searcher, filedName, filedValue);
	}

	public static void testQueryPaser(IndexSearcher searcher) throws ParseException {
		QueryParser parser = new QueryParser(Version.LUCENE_43, FiledName.CITY_DESC, LuceneUtils.analyzer);
		Query query = parser.parse("南京没有杭州和北京好");

		List<Document> doclist = query(searcher, query, topN);
		System.out.println("查找 " + query.toString() + " 共" + doclist.size() + "个结果");
	}

	public static void testFuzzyQuery(IndexSearcher searcher) {
		String filedName = FiledName.CITY_NAME_EN;
		String filedValue = "S*";// TODO:
		System.out.println(String.format("\t filedName = %s, filedValue = %s", filedName, filedValue));
		fuzzyQuery(searcher, filedName, filedValue);
	}

	public static void printResult(List<Document> result) {
		if (result == null || result.isEmpty()) {
			System.out.println("The result is empty!");
		}
	}

	public static void main(String[] args) throws Exception {
		String dataFilePath = "src/main/resources/data/china_cities.txt";
		String indexFileDir = "src/main/resources/data/index";
		LuceneUtils.buildIndex(dataFilePath, indexFileDir);
		IndexSearcher searcher = LuceneUtils.getIndexSearcher(indexFileDir);

		// System.out.println("----testRangeQuery----");
		// testRangeQuery(searcher);

		// System.out.println("----termQuery----");
		// testTermQuery(searcher);

		System.out.println("----fuzzyQuery----");
		testFuzzyQuery(searcher);

		System.out.println("----testQueryPaser----");
		testQueryPaser(searcher);
	}
}
