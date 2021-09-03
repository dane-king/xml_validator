package com.daneking.errors;

import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorMap {
    private final List<Pair<ERROR_TYPE, SAXParseException>> errors = new ArrayList<>();

    public enum ERROR_TYPE {
        ERROR, WARNING, FATAL
    }

    public void add(ERROR_TYPE error_type, SAXParseException exception) {
        errors.add(new Pair<ERROR_TYPE, SAXParseException>(error_type, exception));
    }

    public List<Pair<ERROR_TYPE, SAXParseException>> getParseExceptions() {
        return Collections.unmodifiableList(this.errors);
    }

    public static class Pair<L, R> {
        private final L left;
        private final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return this.left;
        }

        public R getRight() {
            return right;
        }

    }
}
