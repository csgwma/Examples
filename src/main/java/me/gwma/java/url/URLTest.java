package me.gwma.java.url;


import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 类URLTest.java的实现描述：HTTP 编码解码
 * @author Ace Nov 16, 2015 11:12:50 AM
 */
public class URLTest {

    public static void main(String[] args) throws IOException{
        // http 编码解码
        String sql = "http://10.101.93.212:3030/AliTravel/query?query=PREFIX++rdf%3A++%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX++alitravel%3A+%3Chttp%3A%2F%2Falibaba.com%2Fdt%2Fontology%2Ftravel%23%3E%0A%0ASELECT++%3Fs+%3Fname+%3Fp+%3Fo%0AWHERE%0A++%7B+%3Fs++alitravel%3Aname++%3Fname+%3B%0A++++++++%3Fp++++++++++++++%3Fo%0A++++FILTER+%28+%3Fname+IN+%28%22%E8%8A%B1%E6%B8%AF%E8%A7%82%E9%B1%BC%22%29+%29%0A++%7D%0A";
        String keyWord = URLDecoder.decode(sql);
        System.out.println(keyWord);
        
        sql = URLEncoder.encode(keyWord);
        System.out.println(sql);
    }
}
