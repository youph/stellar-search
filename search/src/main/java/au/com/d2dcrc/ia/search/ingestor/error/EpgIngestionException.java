package au.com.d2dcrc.ia.search.ingestor.error;

/**
 * Indicates that an EPG element could not be ingested from a data source, for
 * reasons including: IO errors; syntactic errors; and semantic errors.
 */
@SuppressWarnings("serial")
public class EpgIngestionException extends RuntimeException {

    public EpgIngestionException(String message) {
        super(message);
    }

}
