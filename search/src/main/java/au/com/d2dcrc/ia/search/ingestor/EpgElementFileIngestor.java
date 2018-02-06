package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Loads EPG element data from one or more files. Each file contains zero, one
 * or more lines, and each non-blank line contains a textual JSON representation
 * of an EPG element of the specified type.
 * 
 * @param <T> The type of EPG element to ingest.
 */
public class EpgElementFileIngestor<T extends EpgElement> {

    private final EpgElementStore<T> storer;
    private final EpgElementExtractor<T> extractor;

    /**
     * Constructs an EPG element ingestor linked to the specified storage set.
     * 
     * @param storer - A storage of EPG elements of common type.
     * @param extractor - The JSON string to EPG element extractor.
     */
    public EpgElementFileIngestor(EpgElementStore<T> storer, EpgElementExtractor<T> extractor) {
        this.storer = storer;
        this.extractor = extractor;
    }

    /**
     * Ingests EPG element data from the file or directory path into the
     * pre-defined storage set.
     * 
     * @param path - The source of the EPG element data.
     * @throws EpgDataException if the EPG element data are not obtainable from the data source.
     * @throws EpgSyntaxException if an EPG element representation does not obey the agreed syntax.
     */
    public void ingest(Path path) throws EpgDataException, EpgSyntaxException {
        if (Files.isDirectory(path)) {
            ingestElementFromDirectory(path);
        } else if (Files.isRegularFile(path)) {
            ingestElementFromFile(path);
        } else {
            throw new EpgDataException("Invalid file path: " + path);
        }
    }

    private void ingestElementFromDirectory(Path path) {
        try {
            Files.walk(path, FileVisitOption.FOLLOW_LINKS).forEach(this::ingestElementFromFile);
        } catch (IOException e) {
            throw new EpgDataException("Error reading from directory path: " + path);
        }
    }

    private void ingestElementFromFile(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.lines().forEach(this::ingestElementFromLine);
        } catch (IOException e) {
            throw new EpgDataException("Error reading from file path: " + path);
        }
    }

    private void ingestElementFromLine(String line) {
        if (!line.trim().isEmpty()) {
            storer.addElement(extractor.extract(line));
        }
    }

}
