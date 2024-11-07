package org.example;

import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import util.CreaDocumenti;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            // Definiamo dove salvare il nostro indice Lucene
            Directory directory = FSDirectory.open(Paths.get("C:/Users/hp/progetto_index/src/index"));

            // Define an IndexWriter
            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());

            // Create and add documents
            List<Document> test_documenti = CreaDocumenti.parseHtmlFilesInDirectory("C:/Users/hp/papers/urls_htmls_tables/test_one_doc");
            Document doc1 = test_documenti.get(0);
            Document doc2 = test_documenti.get(1);
            Document doc3 = test_documenti.get(2);
            Document doc4 = test_documenti.get(3);
            Document doc5 = test_documenti.get(4);

            config.setCodec(new SimpleTextCodec());
            IndexWriter writer = new IndexWriter(directory, config);

            writer.addDocument(doc1); // Add documents to be indexed
            writer.addDocument(doc2);
            writer.addDocument(doc3);
            writer.addDocument(doc4);
            writer.addDocument(doc5);

            writer.commit(); // Persist changes to the disk
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}