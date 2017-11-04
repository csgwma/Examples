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
 * 类AutoConfGenerator.java的实现描述：自动生成auto-conf文件的类实现描述
 * 
 * @author guowei.magw 2015年8月14日 下午4:49:40
 */
public class AutoConfGenerator {

    /**
     * 根据autoconf文件自动将destfileFolder文件夹下面的文件生成相应的模板，放在templateFolder文件夹下。
     * 
     * @param filename auto-conf.xml文件路径
     * @param destfileFolder 目标文件所在文件夹
     * @param templateFolder 生成的模板文件所在文件夹
     */
    public void generateAutoTemplate(String filename, String destfileFolder, String templateFolder) {
        HashMap<String, String> groupMap = new HashMap<String, String>();
        HashMap<String, String> scriptMap = new HashMap<String, String>();
        parserXml(filename, groupMap, scriptMap);
        replaceValue(scriptMap, groupMap, destfileFolder, templateFolder);
        return;
    }

    /**
     * 根据autoconf文件的auto-config.xml文件，生成相应的文件保存到map中
     * 
     * @param fileName autoconf文件夹下的auto-config.xml文件路径，如....
     * @param groupMap 根据group标签生成相应的<defaultValue, name>键值对，供后面查找替换
     * @param scriptMap 根据script标签生成相应的<destfile, template>键值对，表示需要对哪些目标文件destfile进行处理，并生成相应的template
     */
    private void parserXml(String fileName, HashMap<String, String> groupMap, HashMap<String, String> scriptMap) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileName);
            NodeList node1 = document.getChildNodes();
            for (int i = 0; i < node1.getLength(); i++) {
                Node node1i = node1.item(i);
                NodeList nodes2 = node1i.getChildNodes();
                for (int j = 0; j < nodes2.getLength(); ++j) {
                    Node node2j = nodes2.item(j);
                    if (node2j instanceof Element) {
                        if (node2j.getNodeName().equals("group")) {
                            for (Node childNode = node2j.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
                                if (childNode instanceof Element) {
                                    String name = ((Element) childNode).getAttribute("name");
                                    String defaultValue = ((Element) childNode).getAttribute("defaultValue");
                                    groupMap.put(defaultValue, name);
                                }
                            }
                        } else if (node2j.getNodeName().equals("script")) {
                            for (Node childNode = node2j.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
                                if (childNode instanceof Element) {
                                    String template = ((Element) childNode).getAttribute("template");
                                    String destfile = ((Element) childNode).getAttribute("destfile");
                                    scriptMap.put(template, destfile);
                                }
                            }
                        } else {
                            System.err.println("error");
                        }
                    }
                }
            }
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
     * 针对scriptMap中的文件，逐个进行处理
     * 
     * @param scriptMap 根据script标签生成相应的<destfile, template>键值对
     * @param groupMap 根据group标签生成相应的<defaultValue, name>键值对
     * @param destfileFolder destfile所在的文件夹
     * @param templateFolder 生成的template文件所在的文件夹
     */
    private void replaceValue(HashMap<String, String> scriptMap, HashMap<String, String> groupMap,
                              String destfileFolder, String templateFolder) {
        for (Map.Entry<String, String> e : scriptMap.entrySet()) {
            String templatePath = templateFolder + "/" + e.getKey();
            String destfilePath = destfileFolder + "/" + e.getValue();
            process(destfilePath, templatePath, groupMap);

        }
    }

    /**
     * 根据groupMap中保存的键值对信息，对destfilePath文件中的key进行替换，生成相应的template
     * 
     * @param destfilePath 目标文件路径
     * @param templatePath 生成的模板文件路径
     * @param groupMap 需要替换的键值对数据
     */
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
                    if (line.contains(regex)) { // 默认一行只有一个key匹配
                        line = line.replaceAll(Matcher.quoteReplacement(regex), Matcher.quoteReplacement(replaceStr));
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

    /**
     * @param args
     */
    public static void main(String[] args) {
        AutoConfGenerator acf = new AutoConfGenerator();
        String filename = "files/autoconf/auto-config.xml";
        String destfileFolder = "files/";
        String templateFolder = "files/autoconf";
        acf.generateAutoTemplate(filename, destfileFolder, templateFolder);
    }

}
