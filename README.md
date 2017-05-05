# Spring Boot (REST API) Tutorial

## Intro

This tutorial will cover how to create Spring Boot REST API.

## What is Spring Boot

Spring Boot [https://spring.io](https://spring.io) is JAVA development framework that helps you to automate lots of common tasks.

Spring was evolving over the years and now it is commonly used in building enterprise grade applications, REST APIs, microservices, etc.

### Standalone

End _result_ of Spring Boot application is standalone executable **jar** file (there is `main()` method). This means that single **jar** file contains all dependancies and even embedded web server to be able to run application.

This is effectively removing big part of differences between development and production environment. Any specifics in regard of server configuration is stored inside application itself.

### Lots of defaults (Opinionated)

There are lots of defaults that are _built-in_ inside Spring Boot. For example location of configuration file, port on which embedded server will run, default error paths, etc.

These defaults are product of years of experience of building applications, and in the most cases, you wouldn't need to change anything.

You can easy override these defaults, but if you don't want to, they are just there and they work _out-of-the-box_ which gives you more time to focus on business logic than to configure some basic tasks over and over again. 

## What will we build (in the end) ?

By the end of this tutorial we will build REST API that will handle (very) simplified movies library.

To achieve this we will learn how to use these techniques:

- Spring Boot framework
- REST API - parametrized endpoints
- Use MySQL (MariaDB) database to retrieve data
- Use Flyway to maintain database
- Create unit tests
- Document REST API (_Swagger_ and _REST Docs_)

## How to follow this tutorial

To see all descriptions - follow Wiki pages (link on the left sidebar).

[All wiki pages](https://bitbucket.org/lukastosic/spring-boot-restapi-tutorial/wiki/browse/)

To see how code resembles those description - you can see tags on the master branch (`part1`, `part2` etc.).