# GitHubSearchEngine

A GitHub search engine for CS209A project backend application

## What is GitHubSearchEngine?
This engine provides basic functionalities for querying GitHub `repositories`, `topics`, `codes` and `users` with specific ```keywords```,
```qualifiers``` and ```options```, and supports java abstractions for `repos`, `users`, `topic`, `code segments`, `content files`, `content directories`, etc.

The engine interacts with GitHub REST API to provide the service, and it relies on the `OAuthToken` provided by the end user.

## Documentations
You can find the related javadoc under /docs inside the repository.

Or you can check [GitHub Search Engine API doc](http://iskxcr.github.io/GitHubSearchEngine/), which is hosted on GitHub.io

## To register the search engine

You need the ```OAuthToken``` to register the engine.

```java
    GitHubAPI gitHubAPI = GitHubAPI.registerAPI(OAuthtoken); 
```

## To search codes in GitHub

For example, suppose you want to search all ```pom.xml``` files containing keyword ```log4j``` in GitHub,
with ```language=Maven POM```, and creation date before Jan 3, 2022:

###Create a `SearchRequest`
```java
        //Build a request
        SearchRequest request = CodeSearchRequest
                .newBuilder()
                .addSearchKeyWord("log4j")
                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml")
                .addLanguageOption("Maven POM")
                .addDateOption(CodeSearchRequest.DateOption.Created, "2022-01-03", "<=")
                .build();

        SearchRequest req1 = RepoSearchRequest.newBuilder()
                .addSearchKeyword("Data")
                .addSearchField(RepoSearchRequest.SearchBy.Topic, "java")
                .addDateOption(RepoSearchRequest.DateOption.Created, "2012-01-01", ">=")
                .build();

        SearchRequest req2 = TopicSearchRequest.newBuilder()
                .addSearchKeyword("Java")
                .addSearchKeyword("Data")
                .addSearchKeyword("Visualization")
                .build();
```
...etc.

###Search using a `SearchRequest`
```java
        TopicResult result = gitHubAPI.searchAPI.searchTopic(req2, 100);
```
