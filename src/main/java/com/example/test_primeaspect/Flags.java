package com.example.test_primeaspect;


public class Flags {

    private String name;

    private FlagsUrl flags;

    public String getName() {
        return name;
    }

    public FlagsUrl getFlags() {
        return flags;
    }

    public static class FlagsUrl {
        private String svg;
        private String png;

        public String getSvg() {
            return svg;
        }

        public String getPng() {
            return png;
        }
    }


}
