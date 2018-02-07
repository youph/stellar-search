package au.com.d2dcrc.ia.search.ingestor.error;

/**
 * Indicates that an EPG element data file does not conform to the agreed
 * syntax.
 */
@SuppressWarnings("serial")
public class EpgSyntaxException extends EpgIngestionException {

    public EpgSyntaxException(String message) {
        super(message);
    }

}
