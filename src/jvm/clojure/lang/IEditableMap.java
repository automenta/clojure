package clojure.lang;

/**
 * Created by michael.blume on 12/13/14.
 */
public interface IEditableMap extends IPersistentMap, IEditableCollection {
    public ITransientMap asTransient();
}
