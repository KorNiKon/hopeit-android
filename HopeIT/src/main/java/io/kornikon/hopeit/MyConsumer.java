package io.kornikon.hopeit;

import java.util.function.Consumer;

@FunctionalInterface
public interface MyConsumer<T> {

    public void accept(T t);
}
