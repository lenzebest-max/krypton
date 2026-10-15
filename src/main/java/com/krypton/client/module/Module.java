package com.krypton.client.module;

public final class Module {
    private final String name, description, category;
    private boolean enabled;
    public Module(String category, String name, String description) {
        this.category = category; this.name = name; this.description = description;
    }
    public String name() { return name; }
    public String description() { return description; }
    public String category() { return category; }
    public boolean enabled() { return enabled; }
    public void toggle() { enabled = !enabled; }
    public void setEnabled(boolean value) { enabled = value; }
}
