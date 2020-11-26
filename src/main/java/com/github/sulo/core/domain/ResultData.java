package com.github.sulo.core.domain;



import com.github.sulo.core.enums.SysCodeEnum;
import com.github.sulo.core.exception.BaseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author sorata 2020-11-25 11:21
 */
public class ResultData<T> {


    private final String message;
    private final Integer code;
    private final T data;


    public ResultData(Integer code, T data, String message) {
        this.message = message;
        this.code = code;
        this.data = data;
    }


    public static <T> ResultData<T> of(Integer code, T data, String message) {
        return new ResultData<>(code, data, message);
    }


    public static <T> ResultData<T> ok(T data, String message) {
        return of(200, data, message);
    }

    public static <T> ResultData<T> ok(T data) {
        return ok(data, "OK");
    }

    public static <T> ResultData<T> okMsg(String message) {
        return ok(null, message);
    }

    public static <T> ResultData<T> err(String message) {
        return of(500, null, message);
    }


    public static <T> ResultData<T> of(Integer code, T data, Consumer<T> consumer) {
        Objects.requireNonNull(consumer).accept(data);
        return of(code, data, "OK");
    }

    public static <T> ResultData<T> of(T data, Consumer<T> consumer) {
        Objects.requireNonNull(consumer).accept(data);
        return ok(data);
    }


    public static <K, V> ResultData<Map<K, V>> ofMap(Consumer<Map<K, V>> consumer) {
        final HashMap<K, V> map = new HashMap<>(8);
        Objects.requireNonNull(consumer).accept(map);
        return ok(map);
    }


    public static <T> ResultData<T> of(Integer code, Supplier<T> supplier) {
        return of(code, Objects.requireNonNull(supplier).get(), "OK");
    }


    public static <T> ResultData<T> of(Supplier<T> supplier) {
        return ok(Objects.requireNonNull(supplier).get());
    }


    public static <T> ResultData<T> of(SysCodeEnum codeEnum) {
        return of(codeEnum.getCode(), null, codeEnum.getMessage());
    }


    public static <T> ResultData<T> of(BaseException e) {
        return of(e.getCode(), null, e.getMessage());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultData<?> that = (ResultData<?>) o;
        return Objects.equals(message, that.message) &&
                code.equals(that.code) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code, data);
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
