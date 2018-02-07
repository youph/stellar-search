package au.com.d2dcrc.ia.search.ingestor.error;

/**
 * Indicates that an EPG element data file does not conform to the agreed syntax
 * by missing a required field.
 */
@SuppressWarnings("serial")
public class EpgMissingFieldException extends EpgSyntaxException {

    public EpgMissingFieldException(String message) {
        super(message);
    }

}
