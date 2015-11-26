package gr.scharf.rules;

import java.util.HashMap;
import java.util.Map;

public class StateStore {
    private Map<String, State> states     = new HashMap<String, State>();
    private boolean            dirtyState = false;

    public void define(String name, State state) {
        System.out.println("define " + name + " = " + state);
        states.put(name, state);
    }

    public void setValue(String name, Object value) {
        State state = getState(name);

        if (!value.equals(state.get())) {
            state.set(value);
            System.out.println("    " + name + " = " + value);
            dirtyState = true;
        }
    }

    public Object getValue(String name) {
        return getState(name).get();
    }

    private State getState(String name) {
        State state = states.get(name);

        if (state == null) {
            throw new RuntimeException("unknown variable: " + name);
        }

        return state;
    }

    @Override
    public String toString() {
        return states.toString();
    }

    public void clearDirtyState() {
        this.dirtyState = false;
    }

    public boolean isDirtyState() {
        return dirtyState;
    }
}
