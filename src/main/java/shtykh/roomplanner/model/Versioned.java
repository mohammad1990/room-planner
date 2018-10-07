package shtykh.roomplanner.model;

public interface Versioned<T> {
    T getValue();
    int getVersion();
}
