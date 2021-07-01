# spaCyJ

Java client for spaCy and more.

![maven](https://github.com/manzurola/spacy-java/actions/workflows/maven.yml/badge.svg)

## About

This library consists of two core modules; API and Adapter. The API module defines the token container classes, mimicking most of the functionality of Doc, Token etc from spaCy. The adapter module contains implemetations of integrations with NLP engines. There are currently two available adapters: [spaCy Server](https://github.com/neelkamath/spacy-server) and [CoreNLP](https://github.com/stanfordnlp/CoreNLP).

## Usage

### spaCy Server Adapter

This adapter intergrates with [spaCy Server](https://github.com/neelkamath/spacy-server).
An instance of spacy-server must be running in the background.

Include the following dependency in you pom.xml (available via github packages only):

```xml
<dependency>
  <groupId>edu.guym</groupId>
  <artifactId>spacyj-adapters-spacy-server</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```


Create a new SpaCy with the relevant adapter as argument, and parse a document.

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
Include the following dependency in you pom.xml (available via github packages only):

```xml
<dependency>
  <groupId>edu.guym</groupId>
  <artifactId>spacyj-adapters-corenlp</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
Create a new Spacy with a new CoreNlpAdapter.

```java
SpaCy spacy = SpaCy.create(CoreNlpAdapter.create();

// same as above example
```

## Contributions

[Contributions](https://github.com/manzurola/aligner/blob/a39d2719394fa258d3193e8258231950a3647920/CONTRIBUTING.md) are welcome.
