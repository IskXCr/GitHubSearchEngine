# GitHubSearchEngine

A GitHub search engine for CS209A project backend application

## What is GitHubSearchEngine?
This engine provides the basic functionality for querying GitHub `repositories`, `topics`, `codes` and `users` with specific ```keywords```,
```qualifiers``` and ```options```.

The Engine interacts with GitHub REST api to provide the service, and it relies on the OAuthToken provided by the end user.

## To register the search engine

You need the ```OAuthToken``` to register the engine.

```java
    GitHubAPI gitHubAPI = GitHubAPI.registerAPI(OAuthtoken); 
```

## To search codes in the GitHub

For example, suppose you want to search all ```pom.xml``` files containing keyword ```log4j``` in GitHub,
with ```language=Maven POM```, and creation date before Jan 3, 2022:

```java
        //Build a request
        CodeSearchRequest request = CodeSearchRequest
                .newBuilder()
                .addSearchKeyWord("log4j")
                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml")
                .addLanguageOption("Maven POM")
                .addDateOption(CodeSearchRequest.DateOption.Created, "2022-01-03", "<=")
                .build();

        //Get the result
        CodeResult results = gitHubAPI.searchAPI.searchCode(request);
        for (CodeItem item : results.getItems()) {
            System.out.println(item.getRepository().getFullName());
        }

```
