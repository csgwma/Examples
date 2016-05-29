/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package me.gwma.java.basic.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 类AutoConfig.java的实现描述：TODO 类实现描述
 * 
 * @author guowei.magw 2015年8月14日 下午3:49:40
 */
public class XmlOperation {

    public void replaceValue(HashMap<String, String> scriptMap, HashMap<String, String> groupMap,
                             String destfileFolder, String templateFolder) {
        for (Map.Entry<String, String> e : scriptMap.entrySet()) {
            String templatePath = templateFolder + "/" + e.getKey();
            String destfilePath = destfileFolder + "/" + e.getValue();
            process(destfilePath, templatePath, groupMap);

        }
    }

    private void process(String destfilePath, String templatePath, HashMap<String, String> groupMap) {
        System.out.println("destfilePath=" + destfilePath);
        System.out.println("templatePath= " + templatePath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(destfilePath)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(templatePath)));
            String line;
            while ((line = reader.readLine()) != null) {
                for (Map.Entry<String, String> e : groupMap.entrySet()) {
                    String regex = "\"" + e.getKey() + "\"";
                    String replaceStr = "\"${" + e.getValue() + "}\"";
                    if (line.contains(regex)) {
                        // System.out.println("line=" + line);
                        line = line.replaceAll(Matcher.quoteReplacement(regex), Matcher.quoteReplacement(replaceStr));
                        // System.out.println("line+=" + line);
                        break;
                    }
                }

                writer.write(line + "\n");
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parserXml(String fileName, HashMap<String, String> groupMap, HashMap<String, String> scriptMap) {
        try {
            System.out.println("filename = " + fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileName);
            NodeList node1 = document.getChildNodes();
            for (int i = 0; i < node1.getLength(); i++) {
                Node node1i = node1.item(i);
                System.out.println(node1i.getNodeName());
                NodeList nodes2 = node1i.getChildNodes();
                for (int j = 0; j < nodes2.getLength(); ++j) {
                    Node node2j = nodes2.item(j);
                    if (node2j instanceof Element) {
                        System.out.println("  " + node2j.getNodeName());
                        if (node2j.getNodeName().equals("group")) {
                            for (Node childNode = node2j.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
                                if (childNode instanceof Element) {
                                    System.out.println("    " + childNode.getNodeName());
                                    String name = ((Element) childNode).getAttribute("name");
                                    String defaultValue = ((Element) childNode).getAttribute("defaultValue");
                                    System.out.println("        " + name + " : " + defaultValue);
                                    groupMap.put(defaultValue, name);
                                }
                            }
                        } else if (node2j.getNodeName().equals("script")) {
                            for (Node childNode = node2j.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
                                if (childNode instanceof Element) {
                                    System.out.println("    " + childNode.getNodeName());
                                    String template = ((Element) childNode).getAttribute("template");
                                    String destfile = ((Element) childNode).getAttribute("destfile");
                                    System.out.println("        " + template + " : " + destfile);
                                    scriptMap.put(template, destfile);
                                }
                            }
                        } else {
                            System.err.println("error");
                        }
                    }
                }
            }
            System.out.println("解析完毕");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        XmlOperation dd = new XmlOperation();
        String fileName = "files/autoconf/auto-config.xml";
        String destfileFolder = "files/";
        String templateFolder = "files/autoconf";

        HashMap<String, String> groupMap = new HashMap<String, String>();
        HashMap<String, String> scriptMap = new HashMap<String, String>();
        dd.parserXml(fileName, groupMap, scriptMap);
        dd.replaceValue(scriptMap, groupMap, destfileFolder, templateFolder);

    }

}
