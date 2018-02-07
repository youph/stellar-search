package au.com.d2dcrc.ia.search.ingestor.error;

/**
 * Indicates that an EPG element with the same identifier already exists.
 */
@SuppressWarnings("serial")
public class EpgElementExistsException extends EpgSemanticsException {

    public EpgElementExistsException(String message) {
        super(message);
    }

}
