package net.frankheijden.serverutils.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ListBuilder<T> {

    private final List<T> list;
    private ListFormat<T> formatter;
    private String seperator;
    private String lastSeperator;

    private ListBuilder(List<T> list) {
        this.list = list;
    }

    public static <T> ListBuilder<T> create(List<T> list) {
        return new ListBuilder<>(list);
    }

    public static <T> ListBuilder<T> create(Collection<T> list) {
        return new ListBuilder<>(new ArrayList<>(list));
    }

    @SafeVarargs
    public static <T> ListBuilder<T> create(T... elements) {
        return new ListBuilder<>(Arrays.asList(elements));
    }

    /**
     * Creates a pre-defined ListBuilder with type String.
     * @param list The collection to be used.
     * @return The ListBuilder.
     */
    @SuppressWarnings("unchecked")
    public static ListBuilder<String> createStrings(List<? extends String> list) {
        ListBuilder<String> builder = create((List<String>) list);
        builder.format(ListFormat.stringFormat);
        return builder;
    }

    public ListBuilder<T> format(ListFormat<T> formatter) {
        this.formatter = formatter;
        return this;
    }

    public ListBuilder<T> seperator(String seperator) {
        this.seperator = seperator;
        return this;
    }

    public ListBuilder<T> lastSeperator(String lastSeperator) {
        this.lastSeperator = lastSeperator;
        return this;
    }

    @Override
    public String toString() {
        if (list.size() == 1) {
            return formatter.format(list.iterator().next());
        } else {
            StringBuilder sb = new StringBuilder();

            int i = 1;
            for (T t : list) {
                sb.append(formatter.format(t));
                if (i == list.size() - 1 && lastSeperator != null) {
                    sb.append(lastSeperator);
                } else if (i != list.size() && seperator != null) {
                    sb.append(seperator);
                }
                i++;
            }
            return sb.toString();
        }
    }
}
