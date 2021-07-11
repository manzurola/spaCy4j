# spaCy4J 🚀

spaCy4J is a library that allows developers to use [spaCy](https://github.com/explosion/spaCy) in Java via [spaCy Server](https://github.com/neelkamath/spacy-server).
It provides an API similar to spaCy, mimicking most of the functionality of Doc, Span and Token.  

As it was originally developed for [CoreNLP](https://github.com/stanfordnlp/CoreNLP), an adapter that translates CoreNLP objects to spaCy4J
containers is provided as well.

![maven](https://github.com/manzurola/spacy-java/actions/workflows/maven.yml/badge.svg)

## Prerequisits

Before you begin, ensure you have met the following requirements:

* You have Java 11 installed.

To use the spacy-server adapter, ensure you have met the following requirements:

* You have [Docker](https://docs.docker.com/get-docker/) installed.
* You have a running instance of spaCy Server as described in the [official Docker Hub docs](https://hub.docker.com/r/neelkamath/spacy-server).

## Installing spaCy4J

Available as Maven dependencies via [GitHub Packages](https://github.com/manzurola?tab=packages&repo_name=spacy-java) as `spacy4j-adapters-spacy-server` or `spacy4j-adapters-corenlp`.

See GitHub documentation on [installing a package](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#installing-a-package).

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
