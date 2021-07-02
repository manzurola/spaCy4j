# SpaCy4J 🚀

Java client for spaCy and more.

![maven](https://github.com/manzurola/spacy-java/actions/workflows/maven.yml/badge.svg)

## About

SpaCyJ enables the use of spaCy in Java via [spaCy Server](https://github.com/neelkamath/spacy-server). It provides an API similar to spaCy, mimicking most of the functionality of Doc, Span and Token. As it was originally used as a wrapper around [CoreNLP](https://github.com/stanfordnlp/CoreNLP), an adapter that translates CoreNLP objects to SpaCyJ containers is provided as well.

## Installation

Available via [github packages](https://github.com/manzurola?tab=packages&repo_name=spacy-java).

## Usage

### SpaCy Server Adapter

This adapter intergrates with [spaCy Server](https://github.com/neelkamath/spacy-server).
An instance of spacy-server must be running in the background.

Include the `spacy4j-adapters-spacy-server` dependency in your `pom.xml`.

Now, create a new SpaCy with the relevant adapter as argument, and parse a document.

```java
SpaCy spacy = SpaCy.create(SpaCyServerAdapter.create("localhost", 8080));

Doc doc = spacy.nlp("My head feels like a frisbee, twice its normal size.");

// inspect tokens
for (Token token : doc.tokens()) {
    System.out.printf("Token: %s, Tag: %s, Pos: %s, Dependency: %s%n", 
            token.text(), token.tag(), token.pos(), token.dependency());
}
```

### CoreNLP Adapter

This adapter loads and intergrates with [CoreNLP](https://github.com/stanfordnlp/CoreNLP).

Include the `spacy4j-adapters-corenlp` dependency in your `pom.xml`.

Load a new SpaCy with a CoreNlpAdapter.

```java
SpaCy spacy = SpaCy.create(CoreNlpAdapter.create();

// same as above example
```

## Contributions

[Contributions](https://github.com/manzurola/aligner/blob/a39d2719394fa258d3193e8258231950a3647920/CONTRIBUTING.md) are welcome.
