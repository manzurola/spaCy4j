package edu.guym.spacyj.api.features;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Penn Treebank Parts of Speech
 *
 */
public enum PtbPos {

    CC("CC"),
    CD("CD"),
    DT("DT"),
    EX("EX"),
    FW("FW"),
    IN("IN"),
    JJ("JJ"),
    JJR("JJR"),
    JJS("JJS"),
    LS("LS"),
    MD("MD"),
    NN("NN"),
    NNS("NNS"),
    NNP("NNP"),
    NNPS("NNPS"),
    PDT("PDT"),
    POS("POS"),
    PRP("PRP"),
    PRP$("PRP$"),
    RB("RB"),
    RBR("RBR"),
    RBS("RBS"),
    RP("RP"),
    SYM("SYM"),
    TO("TO"),
    UH("UH"),
    VB("VB"),
    VBD("VBD"),
    VBG("VBG"),
    VBN("VBN"),
    VBP("VBP"),
    VBZ("VBZ"),
    WDT("WDT"),
    WP("WP"),
    WP$("WP$"),
    WRB("WRB"),
    DOLLAR_SIGN("$"),
    POUND_SIGN("#"),
    L_QUOTE("'"),
    R_QUOTE("\""),
    L_PAREN("("),
    R_PAREN(")"),
    COMMA(","),
    SENT_FIN("."),
    SENT_MID(":"),
    LRB("-LRB-"),
    RRB("-RRB-");

    private final String tag;

    PtbPos(String tag) {
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

    @Deprecated
    public static PtbPos ofTag(String tag) {
        Objects.requireNonNull(tag);
        return Arrays.stream(PtbPos.values()).filter(pos -> pos.matches(tag))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown pt pos tag " + tag));
    }

    public static Set<PtbPos> all() {
        return Arrays.stream(PtbPos.values()).collect(Collectors.toSet());
    }

    public final String tag() {
        return tag;
    }

}
