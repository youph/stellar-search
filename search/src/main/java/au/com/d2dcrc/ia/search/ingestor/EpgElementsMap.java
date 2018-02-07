package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgElement;
import au.com.d2dcrc.ia.search.ingestor.error.EpgElementExistsException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EpgElementsMap<T extends EpgElement> implements EpgElementContainer<T> {

    private final Map<String, T> map = new HashMap<>();

    @Override
    public void addElement(T elem) throws EpgElementExistsException {
        T oldElem = map.put(elem.getId(), elem);
        if (oldElem != null) {
            throw new EpgElementExistsException(
                    "Duplicate " + oldElem.getClass().getSimpleName() + " with identifier " + elem.getId()
            );
        }
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public boolean hasElement(String id) {
        return map.containsKey(id);
    }

    @Override
    public T getElement(String id) {
        return map.get(id);
    }

}
