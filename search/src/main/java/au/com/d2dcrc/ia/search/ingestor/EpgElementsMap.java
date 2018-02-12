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
        if (map.containsKey(elem.getId())) {
            throw new EpgElementExistsException(
                "Duplicate " + elem.getClass().getSimpleName() + " with identifier " + elem.getId()
            );
        }
        map.put(elem.getId(), elem);
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
