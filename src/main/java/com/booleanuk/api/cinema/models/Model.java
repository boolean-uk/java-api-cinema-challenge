package com.booleanuk.api.cinema.models;

public abstract class Model {
    public abstract boolean haveNullFields();
    public abstract void copyOverData(final Model model);
}
