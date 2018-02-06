package au.com.d2dcrc.ia.search.ingestor;

/**
 * Indicates that an EPG element with the same identifier already exists.
 */
@SuppressWarnings("serial")
public class EpgElementExistsException extends EpgSemanticsException {

    /*package-private*/ EpgElementExistsException(String message) {
        super(message);
    }

}
