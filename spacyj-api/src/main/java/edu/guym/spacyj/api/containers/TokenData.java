package edu.guym.spacyj.api.containers;

import java.io.Serializable;
import java.util.Objects;

public final class TokenData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String text;
    private final String whitespaceBefore;
    private final String whitespaceAfter;
    private final int index;
    private final int beginOffset;
    private final int endOffset;

    private final String lemma;
    private final String tag;
    private final String pos;
    private final int head;
    private final String dependency;

    private final boolean sentenceStart;

    private final boolean isPunct;
    private final boolean likeNum;

    private TokenData(String text,
                      String whitespaceBefore,
                      String whitespaceAfter,
                      int index,
                      int beginOffset,
                      int endOffset,
                      String lemma,
                      String tag,
                      String pos,
                      int head,
                      String dependency,
                      boolean sentenceStart,
                      boolean isPunct,
                      boolean likeNum) {
        this.text = text;
        this.whitespaceBefore = whitespaceBefore;
        this.whitespaceAfter = whitespaceAfter;
        this.index = index;
        this.beginOffset = beginOffset;
        this.endOffset = endOffset;
        this.sentenceStart = sentenceStart;
        this.tag = tag;
        this.pos = pos;
        this.lemma = lemma;
        this.head = head;
        this.dependency = dependency;
        this.isPunct = isPunct;
        this.likeNum = likeNum;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final String text() {
        return text;
    }

    public final String whitespaceBefore() {
        return whitespaceBefore;
    }

    public final String whitespaceAfter() {
        return whitespaceAfter;
    }

    public final int index() {
        return index;
    }

    public final int beginOffset() {
        return beginOffset;
    }

    public final int endOffset() {
        return endOffset;
    }

    public final String lemma() {
        return lemma;
    }

    public final String tag() {
        return tag;
    }

    public final String pos() {
        return pos;
    }

    public final int head() {
        return head;
    }

    public final String dependency() {
        return dependency;
    }

    public final boolean isSentenceStart() {
        return sentenceStart;
    }

    public final boolean isPunct() {
        return isPunct;
    }

    public final boolean likeNum() {
        return likeNum;
    }

    public static final class Builder {
        private String text = "";
        private String before = "";
        private String after = "";
        private int index = 0;
        private int beginOffset = 0;
        private int endOffset = 0;
        private String lemma = "";
        private String tag = "";
        private String pos = "";
        private int head = -1;
        private String dependency = "";
        private boolean sentenceStart = false;
        private boolean isPunct = false;
        private boolean likeNum = false;

        public final Builder setText(String text) {
            this.text = text;
            return this;
        }

        public final Builder setBefore(String before) {
            this.before = before;
            return this;
        }

        public final Builder setAfter(String after) {
            this.after = after;
            return this;
        }

        public final Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        public final Builder setBeginOffset(int beginOffset) {
            this.beginOffset = beginOffset;
            return this;
        }

        public final Builder setEndOffset(int endOffset) {
            this.endOffset = endOffset;
            return this;
        }

        public final Builder setLemma(String lemma) {
            this.lemma = lemma;
            return this;
        }

        public final Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public final Builder setPos(String pos) {
            this.pos = pos;
            return this;
        }

        public final Builder setHead(int head) {
            this.head = head;
            return this;
        }

        public final Builder setDependency(String dependency) {
            this.dependency = dependency;
            return this;
        }

        public final Builder setSentenceStart(boolean sentenceStart) {
            this.sentenceStart = sentenceStart;
            return this;
        }

        public final Builder setIsPunct(boolean isPunct) {
            this.isPunct = isPunct;
            return this;
        }

        public final Builder setLikeNum(boolean likeNum) {
            this.likeNum = likeNum;
            return this;
        }

        public final TokenData build() {
            return new TokenData(
                    text,
                    before,
                    after,
                    index,
                    beginOffset,
                    endOffset,
                    lemma,
                    tag,
                    pos,
                    head,
                    dependency,
                    sentenceStart,
                    isPunct,
                    likeNum);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenData tokenData = (TokenData) o;
        return index == tokenData.index &&
                beginOffset == tokenData.beginOffset &&
                endOffset == tokenData.endOffset &&
                head == tokenData.head &&
                sentenceStart == tokenData.sentenceStart &&
                isPunct == tokenData.isPunct &&
                likeNum == tokenData.likeNum &&
                text.equals(tokenData.text) &&
                whitespaceBefore.equals(tokenData.whitespaceBefore) &&
                whitespaceAfter.equals(tokenData.whitespaceAfter) &&
                lemma.equals(tokenData.lemma) &&
                tag.equals(tokenData.tag) &&
                pos.equals(tokenData.pos) &&
                dependency.equals(tokenData.dependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                text,
                whitespaceBefore,
                whitespaceAfter,
                index,
                beginOffset,
                endOffset,
                lemma,
                tag,
                pos,
                head,
                dependency,
                sentenceStart,
                isPunct,
                likeNum);
    }

    @Override
    public String toString() {
        return "TokenData{" +
                "text='" + text + '\'' +
                ", whitespaceBefore='" + whitespaceBefore + '\'' +
                ", whitespaceAfter='" + whitespaceAfter + '\'' +
                ", index=" + index +
                ", beginOffset=" + beginOffset +
                ", endOffset=" + endOffset +
                ", lemma='" + lemma + '\'' +
                ", tag='" + tag + '\'' +
                ", pos='" + pos + '\'' +
                ", head=" + head +
                ", dependency='" + dependency + '\'' +
                ", sentenceStart=" + sentenceStart +
                ", isPunct=" + isPunct +
                ", likeNum=" + likeNum +
                '}';
    }


}
