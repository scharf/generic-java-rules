package gr.scharf.rules;

public class State {
    Object value;

    public State(Object value) {
        this.value = value;
    }

    public Object get() {
        return value;
    }

    public void set(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
