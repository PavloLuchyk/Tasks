package org.project.util;

public enum PageSize {
    SIZE15(15),
    SIZE30(30),
    SIZE45(45),
    SIZE60(60);

    public final int size;

    PageSize(int size) {
        this.size = size;
    }
}
