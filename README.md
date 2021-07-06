# SpaCy4J 🚀

Java client for spaCy and more.

![maven](https://github.com/manzurola/spacy-java/actions/workflows/maven.yml/badge.svg)

## About

SpaCyJ enables the use of spaCy in Java via [spaCy Server](https://github.com/neelkamath/spacy-server). It provides an
API similar to spaCy, mimicking most of the functionality of Doc, Span and Token. As it was originally used as a wrapper
around [CoreNLP](https://github.com/stanfordnlp/CoreNLP), an adapter that translates CoreNLP objects to SpaCyJ
containers is provided as well.

## Installation

First thing we need to get started is to add the chosen adapter to our maven dependencies. Available choicese
are `spacy4j-adapters-spacy-server` or `spacy4j-adapters-corenlp`, for spaCy server and CoreNLP, respectively. Refer to
the [github packages](https://github.com/manzurola?tab=packages&repo_name=spacy-java) page for the full maven
dependency.

## Quick Start

Let's assume we chose the spacy-server adapter. The following snippet shows how to create a new `SpaCy` with a matching
adapter, parse a document and inspect the tokens:

```java

// Create a new spacy-server adapter with host and port matching a running instance of spacy-server.
SpaCyAdapter adapter = SpaCyServerAdapter.create("localhost", 8080);

// Create a new SpaCy object. It is thread safe and should be reused across our app
SpaCy spacy = SpaCy.create(adapter);

// Parse a doc
Doc doc = spacy.nlp("My head feels like a frisbee, twice its normal size.");

// Inspect tokens
for (Token token : doc.tokens()) {
    System.out.printf("Token: %s, Tag: %s, Pos: %s, Dependency: %s%n", 
            token.text(), token.tag(), token.pos(), token.dependency());
}
```

## Contributions

[Contributions](https://github.com/manzurola/aligner/blob/a39d2719394fa258d3193e8258231950a3647920/CONTRIBUTING.md) are
welcome.
