package io.languagetoys.spacy4j.api.features;

/**
 * Representing Universal Dependencies V2 - https://universaldependencies.org/docs/en/dep/
 */
public enum Dependency {

    PRED("pred"),
    AUX("aux"),
    AUX_PASS("aux:pass"),
    COP("cop"),
    CONJ("conj"),
    CC("cc"),
    PUNCT("punct"),
    ARG("arg"),
    SUBJ("subj"),
    NSUBJ("nsubj"),
    NSUBJ_PASS("nsubj:pass"),
    CSUBJ("csubj"),
    CSUBJ_PASS ("csubj:pass"),
    COMP("comp"),
    OBJ("obj"),
    IOBJ("iobj"),
    CCOMP("ccomp"),
    XCOMP("xcomp"),
    REL("rel"),
    PREP("prep"),
    REF("ref"),
    EXPL ("expl"),
    MOD("mod"),
    NMOD("nmod"),
    OBL("obl"),
    ADVCL("advcl"),
    MARK("mark"),
    AMOD("amod"),
    NUMMOD("nummod"),
    COMPOUND("compound"),
    NAME("name"),
    APPOS("appos"),
    DISCOURSE("discourse"),
    ACL("acl"),
    ACL_RELCL("acl:relcl"),
    ADVMOD("advmod"),
    NEG("neg"),
    OBL_NPMOD("obl:npmod"),
    OBL_TMOD("obl:tmod"),
    FIXED("fixed"),
    DET("det"),
    DET_PREDET("det:predet"),
    CC_PRECONJ("cc:preconj"),
    NMOD_POSS("nmod:poss"),
    CASE("case"),
    COMPOUND_PRT("compound:prt"),
    PARATAXIS("parataxis"),
    GOESWITH("goeswith"),
    LIST("list"),
    DET_QMOD("det:qmod"),
    NSUBJ_XSUBJ("nsubj:xsubj"),
    NSUBJ_PASS_XSUBJ("nsubj:pass:xsubj"),
    CSUBJ_XSUBJ("csubj:xsubj"),
    CSUBJPASS_XSUBJ("csubjpass:xsubj"),
    SDEP("sdep"),
    OBL_AGENT("obl:agent"),
    ORPHAN("orphan"),
    ROOT("root"),
    DEP("dep");

    private final String name;

    Dependency(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final boolean matches(String name) {
        return this.name.startsWith(name);
    }

}
