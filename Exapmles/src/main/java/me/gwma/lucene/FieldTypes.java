package me.gwma.lucene;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.index.FieldInfo.IndexOptions;

public enum FieldTypes {
    
    // 索引，分词，存储
    INDEX_TOKEN_STORE_COMMON(true, true, true, null, true), 
    INDEX_TOKEN_STORE_LONG(true, true, true, NumericType.LONG, true),
    INDEX_TOKEN_STORE_DOUBLE(true, true, true, NumericType.DOUBLE, true),
    INDEX_TOKEN_STORE_INT(true, true, true, NumericType.INT, true),
    INDEX_TOKEN_STORE_FLOAT(true, true, true, NumericType.FLOAT, true),
    
    // 索引，不分词，存储
    INDEX_UNTOKEN_STORE_COMMON(true, false, true, null, true), 
    INDEX_UNTOKEN_STORE_LONG(true, false, true, NumericType.LONG, true),
    INDEX_UNTOKEN_STORE_DOUBLE(true, false, true, NumericType.DOUBLE, true),
    INDEX_UNTOKEN_STORE_INT(true, false, true, NumericType.INT, true),
    INDEX_UNTOKEN_STORE_FLOAT(true, false, true, NumericType.FLOAT, true),
    
    // 索引，不分词，不存储
    INDEX_UNTOKEN_UNSTORE_COMMON(true, false, false, null, true), 
    INDEX_UNTOKEN_UNSTORE_LONG(true, false, false, NumericType.LONG, true),
    INDEX_UNTOKEN_UNSTORE_DOUBLE(true, false, false, NumericType.DOUBLE, true),
    INDEX_UNTOKEN_UNSTORE_INT(true, false, false, NumericType.INT, true),
    INDEX_UNTOKEN_UNSTORE_FLOAT(true, false, false, NumericType.FLOAT, true),

    // 索引，分词，不存储
    INDEX_TOKEN_UNSTORE_COMMON(true, true, false, null, true), 
    INDEX_TOKEN_UNSTORE_LONG(true, true, false, NumericType.LONG, true),
    INDEX_TOKEN_UNSTORE_DOUBLE(true, true, false, NumericType.DOUBLE, true),
    INDEX_TOKEN_UNSTORE_INT(true, true, false, NumericType.INT, true),
    INDEX_TOKEN_UNSTORE_FLOAT(true, true, false, NumericType.FLOAT, true),
    
    // 不索引，只存储
    UNINDEX_UNTOKEN_STORE_COMMON(false, false, true, null, true), 
    UNINDEX_UNTOKEN_STORE_LONG(false, false, true, NumericType.LONG, true),
    UNINDEX_UNTOKEN_STORE_DOUBLE(false, false, true, NumericType.DOUBLE, true),
    UNINDEX_UNTOKEN_STORE_INT(false, false, true, NumericType.INT, true),
    UNINDEX_UNTOKEN_STORE_FLOAT(false, false, true, NumericType.FLOAT, true);

    private FieldType type;

    private FieldTypes(boolean indexed, boolean tokenized, boolean stored, NumericType numtype, boolean freezed){
        this.type = new FieldType();
        type.setIndexed(indexed);
        type.setTokenized(tokenized);
        type.setStored(stored);
        type.setIndexOptions(IndexOptions.DOCS_ONLY);
        if (numtype != null) {
            type.setNumericType(numtype);
        }
        if (freezed) {
            type.freeze();
        }
    }

    public FieldType getType() {
        return type;
    }
}
