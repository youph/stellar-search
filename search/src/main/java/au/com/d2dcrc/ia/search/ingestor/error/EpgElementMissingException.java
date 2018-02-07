package au.com.d2dcrc.ia.search.ingestor.error;

/**
 * Indicates that an EPG element with the given identifier does not exist but is
 * expected to already exist.
 */
@SuppressWarnings("serial")
public class EpgElementMissingException extends EpgSemanticsException {

    public EpgElementMissingException(String message) {
        super(message);
    }

}
