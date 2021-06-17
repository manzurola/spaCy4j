# spaCyJ

Harness the simplicity and convenience of spaCy containers in Java.

## spacyj-api

The spacyj-api module contains the basic token container interfaces [Doc](https://github.com/manzurola/spacy-java/blob/6a6995ab56039823b00e2fa5dc01159f503f608d/spacyj-api/src/main/java/edu/guym/spacyj/api/containers/Doc.java), Span, Token and linguisitc enum features Tag, Pos, Dependency.

## spacyj-adapters

The spacyj-adapters multi-module contains two adapters. An adapter produces a list of TokenData objects used to create containers.

### About
This package was originally developed as a wrapper for the powerfull [CoreNLP](https://github.com/stanfordnlp/CoreNLP) for projects relating to language learning. Later when I discovered spaCy I was impressed with the convenient and simple token container API, and so decided to bring it to Java. So now we can use CoreNLP in Java with a spaCy interface.

Ready-to-use adapters for [spaCy Server](https://github.com/neelkamath/spacy-server) and [CoreNLP](https://github.com/stanfordnlp/CoreNLP).

