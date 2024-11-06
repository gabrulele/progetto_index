package util;

import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.select.Elements;

public class CreaDocumenti {

    public static List<Document> parseHtmlFilesInDirectory(String directoryPath) throws IOException {
        List<Document> documenti = new ArrayList<>();

        // Carica tutti i file HTML dalla directory specificata
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".html"));

        if (files == null) {
            throw new IOException("La directory specificata non contiene file HTML o non è accessibile.");
        }

        for (File file : files) {

            // Parsing del file HTML, jsoupDoc dedicato al parsing
            org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(file, "UTF-8");

            // Estrai il titolo dall'elemento <h1 class="ltx_title ltx_title_document">
            Element titoloElement = jsoupDoc.selectFirst("h1.ltx_title.ltx_title_document");
            String titolo = (titoloElement != null) ? titoloElement.text() : "Titolo non trovato";

            // Estrai il contenuto testuale rimuovendo i tag HTML
            String contenuto = jsoupDoc.text();

            // Estrai gli autori dall'elemento <div class="ltx_authors">
            Element autoriElement = jsoupDoc.selectFirst("div.ltx_authors");
            List<String> autori = estraiAutori(autoriElement);

            // Crea un oggetto Documento di Lucene con l'aggiunta dei relativi campi
            Document documento = new Document();
            documento.add(new TextField("titolo", titolo, Field.Store.YES));
            // Aggiunta di ciascun autore come un valore separato nel campo "authors"
            for (String autore : autori) {
                documento.add(new TextField("autori", autore, Field.Store.YES));
            }
            documento.add(new TextField("contenuto", contenuto, Field.Store.YES));
            documenti.add(documento);
        }

        return documenti;
    }

    /**
     * Metodo per estrarre i nomi degli autori come lista di stringhe.
     */
    private static List<String> estraiAutori(Element autoriElement) {
        List<String> autoriNomiList = new ArrayList<>();

        if (autoriElement == null) {
            return autoriNomiList;  // Restituisce una lista vuota se non ci sono autori
        }

        // Seleziona tutti gli elementi con classe ltx_personname per ottenere i nomi
        Elements autoriNomi = autoriElement.select("span.ltx_personname");

        for (Element autore : autoriNomi) {

            // Rimuovi i tag <br> e sostituiscili con una virgola e spazio per una formattazione leggibile
            String autoreNome = autore.html().replaceAll("<br.*?>", ", ").replaceAll("&amp;", "&").trim();
            autoriNomiList.add(autoreNome);
        }

        return autoriNomiList;
    }

}

