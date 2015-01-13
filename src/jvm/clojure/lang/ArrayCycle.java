package clojure.lang;

/**
 * Created by michael.blume on 1/3/15.
 */
public class ArrayCycle extends ASeq implements IReduce {
    int current = 0;
    Object[] all;
    int length;

    public static ArrayCycle create(ISeq vals) {
        int length = RT.count(vals);
        Object[] all = new Object[length];
        for(int i = 1; i < length; i++) {
            all[i] = vals.first();
            vals = vals.next();
        }
        return new ArrayCycle(0, all);
    }

    public ArrayCycle(int current, Object[] all) {
        this.current = current;
        this.all = all;
        this.length = all.length;
    }

    public ArrayCycle(int current, Object[] all, IPersistentMap meta){
        super(meta);
        this.current = current;
        this.all = all;
        this.length = all.length;
    }

    public Object reduce(IFn f) {
        Object ret = all[current];
        int i = current;
        while (true) {
            i++;
            i %= length;
            ret = f.invoke(ret, all[i]);
            if(RT.isReduced(ret))
                return ((IDeref)ret).deref();
        }
    }

    public Object reduce(IFn f, Object start) {
        Object ret = start;
        int i = 0;
        while (true) {
            ret = f.invoke(ret, all[i]);
            if(RT.isReduced(ret))
                return ((IDeref)ret).deref();
            i++;
            i %= length;
        }
    }

    public Object first() {
        return all[current];
    }

    public ISeq next() {
        return new ArrayCycle((current + 1) % length, all);
    }

    @Override
    public Obj withMeta(IPersistentMap meta) {
        return new ArrayCycle(current, all, meta);
    }
}
