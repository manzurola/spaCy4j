package edu.guym.spacyj.api.features;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum UdPos {

    ADJ("ADJ"),
    ADP("ADP"),
    ADV("ADV"),
    AUX("AUX"),
    CCONJ("CCONJ"),
    DET("DET"),
    INTJ("INTJ"),
    NOUN("NOUN"),
    NUM("NUM"),
    PART("PART"),
    PRON("PRON"),
    PROPN("PROPN"),
    PUNCT("PUNCT"),
    SCONJ("SCONJ"),
    SYM("SYM"),
    VERB("VERB"),
    X("X"),
    SPACE("SPACE");

    private final String tag;

    UdPos(String tag) {
        this.tag = tag;
    }

    /**
     * Returns true if this PTB pos equals the supplied tag, false otherwise.
     * This method can be overridden/changed to accommodate different representation of a PTB tag name
     * @param tag a PTB part-of-speech tag name, such as CC or VBZ.
     */
    public boolean matches(String tag) {
        return this.tag.equalsIgnoreCase(tag.trim());
    }

    public static UdPos ofTag(String tag) {
        Objects.requireNonNull(tag);
        return Arrays.stream(UdPos.values()).filter(pos -> pos.matches(tag))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown pt pos tag " + tag));
    }

    public static Set<PtbPos> all() {
        return Arrays.stream(PtbPos.values()).collect(Collectors.toSet());
    }

    public final String getTag() {
        return tag;
    }

}
