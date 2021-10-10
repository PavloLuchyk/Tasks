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

    public static PageSize getFromSize(int size) {
        switch (size) {
            case 15 :{
                return SIZE15;
            }
            case 30:{
                return SIZE30;
            }
            case 45: {
                return SIZE45;
            }
            case 60: {
                return SIZE60;
            }
            default: {
                throw new IllegalArgumentException("Illegal argument");
            }
        }
    }
}
