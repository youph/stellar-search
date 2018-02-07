package au.com.d2dcrc.ia.search.ingestor.error;

/**
 * Indicates that an EPG element data file does not conform to the agreed
 * semantics.
 */
@SuppressWarnings("serial")
public class EpgSemanticsException extends EpgIngestionException {

    public EpgSemanticsException(String message) {
        super(message);
    }

}
