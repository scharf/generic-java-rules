package gr.scharf.rules.actions;

public class SetValueAction extends AbstractStoreAction {
    String name;
    Object value;

    public SetValueAction(String name, Object value) {
        super();
        this.name = name;
        this.value = value;
    }

    @Override
    public void run() {
        store.setValue(name, value);
    }
}
