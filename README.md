# spaCyJ

spaCy adapters for the JVM.

![maven](https://github.com/manzurola/spacy-java/actions/workflows/maven.yml/badge.svg)

## Basic Usage

The spaCyJ library is made of two core modules; API and Adapter. The API consists of container interfaces, mimicking most of the functionality of Doc, Token etc as in spaCy. The adapter module provides the underlying NLP engine and acts as a factory for Doc objects.

There are currently two adapters we can use: [spaCy Server](https://github.com/neelkamath/spacy-server) and [CoreNLP](https://github.com/stanfordnlp/CoreNLP).

The spacy-server adapter requires a running docker container as described in the link (very easy to set up). The CoreNLP adapter runs in memory, acting as a translation layer between CoreNLP document and Doc.

### CoreNLP Adapter

This adapter loads and intergrates with [CoreNLP](https://github.com/stanfordnlp/CoreNLP).
Include the following dependency in you pom.xml (available via github packages only):

```
<dependency>
  <groupId>edu.guym.spacyj</groupId>
  <artifactId>spacyj-adapters-corenlp</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

The adapter dependency always includes the API module.

Now create a new Spacy with the relevant adapter as argument, and parse a document.

```java
Spacy spacy = Spacy.create(CoreNlpAdapter.create();

Doc doc = spacy.nlp("My head feels like a frisbee, twice its normal size.");

// inspect tokens
for (Token token : doc.tokens()) {
    System.out.printf("Token: %s, Tag: %s, Pos: %s, Dependency: %s%n", 
            token.text(), token.tag(), token.pos(), token.dependency());
}
```

### spaCy Server Adapter

This adapter intergrates with [spaCy Server](https://github.com/neelkamath/spacy-server).
An instance of spacy-server must be running in the background.

Include the following dependency in you pom.xml (available via github packages only):

```
<dependency>
  <groupId>edu.guym.spacyj</groupId>
  <artifactId>spacyj-adapters-spacy-server</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```


Now create a new Spacy with the relevant adapter as argument, and parse a document.

```java
Spacy spacy = Spacy.create(SpacyServerAdapter.create("localhost", 8080));

Doc doc = spacy.nlp("My head feels like a frisbee, twice its normal size.");

// inspect tokens
for (Token token : doc.tokens()) {
    System.out.printf("Token: %s, Tag: %s, Pos: %s, Dependency: %s%n", 
            token.text(), token.tag(), token.pos(), token.dependency());
}
```

