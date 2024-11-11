# Progetto di Ricerca e Indicizzazione di Paper Scientifici con Apache Lucene
Questo progetto implementa un sistema di indicizzazione e ricerca di articoli scientifici basato su Apache Lucene. 
L’obiettivo è creare un indice di articoli scientifici per facilitare ricerche efficienti e mirate su vari campi 
(come titolo, autori, contenuto) con possibilità di eseguire query complesse e multi-campo.

**URL del Progetto**
[https://github.com/gabrulele/progetto_index]

## Descrizione del Progetto
Il sistema permette di:

Indicizzare articoli scientifici con campi specifici come titolo, autori e contenuto.
Utilizzare Analyzer personalizzati per ciascun campo, con gestione di stopword e opzioni di tokenizzazione.
Eseguire ricerche su uno o più campi contemporaneamente tramite query composte.
Visualizzare i termini indicizzati, il numero di documenti e altre statistiche sull’indice.

## Configurazione del Progetto
Analyzer Utilizzati:

Abbiamo configurato gli Analyzer come segue, per ottenere i risultati migliori su ciascun campo:

-Titolo: Utilizziamo un StandardAnalyzer senza rimozione di maiuscole/minuscole, dato che i titoli contengono spesso acronimi importanti. Lo scopo è mantenere l’integrità delle parole chiave, evitando tokenizzazioni indesiderate. Vengono rimossi caratteri non significativi e stopword comuni (es. “the”, “in”).

-Autori: Utilizziamo un WhitespaceAnalyzer per preservare i nomi completi degli autori, separando i singoli autori e rimuovendo stopword specifiche per nomi (es. “and”, “et al.”) se presenti. Questo aiuta a ottenere risultati di ricerca precisi per autore.

-Contenuto: È stato impiegato un CustomAnalyzer basato su StandardAnalyzer e ShingleAnalyzer, combinando tokenizzazione e gestione di bigrammi/trigrammi per catturare contesti e frasi ricorrenti all'interno dei paper. Questo consente ricerche di frasi più precise.

## Stopword Utilizzate
Un insieme personalizzato di stopword è stato definito per migliorare la qualità dei risultati, 
soprattutto per i campi titolo e contenuto. Le stopword includono parole generiche, preposizioni e congiunzioni come:

"a", "an", "the", "in", "on", "and", "or", "of", "with", "to", ecc.

## Statistiche dell'indice
*Tempo di indicizzazione totale*: Circa 17 min

*Numero di documenti indicizzati*: 9372 documenti

## Query di Test
Per verificare il funzionamento del sistema di ricerca, sono state eseguite query su diversi campi, inclusi esempi come:

**Query per Titolo**: Ricerca di frasi specifiche come "Data Cleaning".

**Query per Autore**: Ricerca per autori specifici, ad esempio "John Smith" o solo nomi o cognomi.

**Query per Contenuto**: Ricerca per contenuto o porzioni di contenuto dei documenti HTML.

**Query Combinata**: Ricerca multi-campo, come "titolo:Machine Learning" AND "autori:Smith", per ottenere risultati mirati.

## Risultati e Conclusioni
L’utilizzo di Analyzer dedicati per ciascun campo ha migliorato significativamente la precisione dei risultati. 
Inoltre, l’impiego di un CustomAnalyzer per il campo contenuto ha permesso di ottenere query di frase più affidabili, 
specialmente per concetti comuni nei paper scientifici.