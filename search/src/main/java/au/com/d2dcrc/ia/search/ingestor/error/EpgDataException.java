package au.com.d2dcrc.ia.search.ingestor.error;

/**
 * Indicates either that an EPG element data file or data path could not be
 * found, or that the data could not be read properly (e.g. due to IO problems).
 */
@SuppressWarnings("serial")
public class EpgDataException extends EpgIngestionException {

    public EpgDataException(String message) {
        super(message);
    }

}
