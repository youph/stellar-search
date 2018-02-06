package au.com.d2dcrc.ia.search.ingestor;

/**
 * Indicates that an EPG element could not be ingested from a data source, for
 * reasons including: IO errors; syntactic errors; and semantic errors.
 */
@SuppressWarnings("serial")
public class EpgIngestionException extends RuntimeException {

    /*package-private*/ EpgIngestionException(String message) {
        super(message);
    }

}
