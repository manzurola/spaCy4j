# spaCy4J 🚀

![maven](https://github.com/manzurola/spacy-java/actions/workflows/maven.yml/badge.svg)

A Java library for processing natural language text using [spaCy Server](https://github.com/neelkamath/spacy-server) or [CoreNLP](https://github.com/stanfordnlp/CoreNLP).

## Prerequisits

Before you begin, ensure you have met the following requirements:

* You have Java 11 installed.
* You have access to Github Packages Maven registry as described [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages).

Additionaly, to use the spacy-server adapter, ensure you have met the following requirements:

* You have [Docker](https://docs.docker.com/get-docker/) installed.
* You have a running instance of spaCy Server as described in the [official Docker Hub docs](https://hub.docker.com/r/neelkamath/spacy-server).

## Installing spaCy4J

To use the spaCy Server adapter, add this to the dependencies section in your `pom.xml`:
```xml
<dependency>
  <groupId>io.github.manzurola</groupId>
  <artifactId>spacy4j-adapters-spacy-server</artifactId>
  <version>0.3.0</version>
</dependency>
```

To use the CoreNLP adapter, add this to the dependencies section in your `pom.xml`:
```xml
<dependency>
  <groupId>io.github.manzurola</groupId>
  <artifactId>spacy4j-adapters-corenlp</artifactId>
  <version>0.3.0</version>
</dependency>
```

## Using spaCy4J

To use spaCy4J in code, follow these steps:

```java

// Create a new spacy-server adapter with host and port matching a running instance of spacy-server
SpaCyAdapter adapter = SpaCyServerAdapter.create("localhost", 8000);

// Create a new SpaCy object. It is thread safe and should be reused across your app
SpaCy spacy = SpaCy.create(adapter);

// Parse a doc
Doc doc = spacy.nlp("My head feels like a frisbee, twice its normal size.");

// Inspect tokens
for (Token token : doc.tokens()) {
    System.out.printf("Token: %s, Tag: %s, Pos: %s, Dependency: %s%n", 
            token.text(), token.tag(), token.pos(), token.dependency());
}
```

If you wish to use the CoreNLP adapter, replace the first line above with the following:

```
SpaCyAdapter adapter = CoreNLPAdapter.create();
```

## Contributions

To contribute to spaCy4J, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin <project_name>/<location>`
5. Create the pull request.

Alternatively see the GitHub documentation on [creating a pull request](https://docs.github.com/en/github/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request).

        
## Contributors
        
Thanks to the following people who have contributed to this project:
        
* [@manzurola](https://github.com/manzurola) 🐈        

## Contact

If you want to contact me you can reach me at [guy.manzurola@gmail.com](guy.manzurola@gmail.com).

## License
        
This project uses the following license: [MIT](https://github.com/LanguageToys/aligner/blob/555fd35e842feb8d899d7197a1965ea01bc74c95/LICENSE).
