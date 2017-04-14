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
- Use H2 (in memory database) for unit tests
- Document REST API (_Swagger_ and _REST Docs_)

# Part 1 - Create empty project

## Spring Initializr

You can use [Spring Initializr service](https://start.spring.io/) to select what components you will need in your Spring Boot application.

This service will generate **zip** archive that contains basic file/folder structure and Maven `pom.xml` file that will contain dependencies that were selected in the web interface.

So, in essence, you don't need to use this once you learn what dependencies you need for your project, you can just create simple empty maven project and add appropriate dependencies

### What to select for this part

1. Open [Spring Initializr service](https://start.spring.io/)
2. Enter **Group** and **Artifact** information
3. Search for dependency **Web**
4. Click on **Generate Project**

Alternatively

1. Open [Spring Initializr service](https://start.spring.io/)
2. Click on link **Switch to the full version**
3. Enter **Group** and **Artifact** information (this will automatically populate **Name** field, you can change this)
4. Select **Web** dependency
5. Click on **Generate Project**

## Maven configuration

If you did steps above where you used Spring Initializr to generate the project, then you should see generated `pom.xml` configuration.

If you want to do it manually, you can just create empty maven project and place this configuration in your `pom.xml` file: 

### Parent

`pom.xml` contains one basic `parent` definition.

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>1.5.2.RELEASE</version>
	<relativePath/>
</parent>
```

This parent definition will relate proper **jar** dependency versions when we place appropriate dependencies. This means that we can place Spring Boot dependency without specifying version of the dependency and Maven will use parent version to determine appropriate dependency version.

### Dependencies

Effectively there is one main dependency, but you will see another one that will be used later on when we create unit tests:

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>
``` 

### Other properties

There are other properties that are forcing Java version, UTF-8:

```xml
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
	<java.version>1.8</java.version>
</properties>
```

Additionally there is build plugin `spring-boot-maven-plugin`. This plugin is added when you generate project from the web service (Spring Initializr), it is giving some additional maven build goals that are useful for Spring Boot applications, but for this tutorial, this plugin is optional.

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```

## Main method

If you generated your project using Spring Initializr, you will receive one default `java` class that contains `main` method.

```java
// start of the file
package nl.infodation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringtutorialApplication {
    public static void main(String[] args) {
    	SpringApplication.run(SpringtutorialApplication.class, args);
    }
}
```

That is all! - This is everything that you need in order to start Spring Boot application!

There are 2 important parts here:

### Annotation "@SpringBootApplication"

When adding this annotation you also have to add (Eclipse should offer automatically) 2 dependancies (`import` section).

This annotation is marking this class as a class that contains `main` method that shuold be used by Spring Boot

### Method - `main`

This is standard way of starting JAVA application. In case of Spring Boot - `man` method only contains one command:

```java
SpringApplication.run(SpringtutorialApplication.class, args);
```

This will start up Spring Boot application and pass any (optional) arguments used when starting java application.

## Run application

You can already run this application. Open java class that contains `main` method and start project as (standard) Java Application.

You should see in your console output something like this:

```
 :: Spring Boot ::        (v1.5.2.RELEASE)

2017-04-14 13:24:10.859  INFO 16340 --- [           main] nl.infodation.SpringtutorialApplication  : Starting SpringtutorialApplication on LT07-Luka with PID 16340 (D:\STS\workspace\springtutorial\target\classes started by lstosic in D:\STS\workspace\springtutorial)
2017-04-14 13:24:10.864  INFO 16340 --- [           main] nl.infodation.SpringtutorialApplication  : No active profile set, falling back to default profiles: default
2017-04-14 13:24:10.986  INFO 16340 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6366ebe0: startup date [Fri Apr 14 13:24:10 CEST 2017]; root of context hierarchy
2017-04-14 13:24:15.274  INFO 16340 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat initialized with port(s): 8080 (http)
2017-04-14 13:24:15.309  INFO 16340 --- [           main] o.apache.catalina.core.StandardService   : Starting service Tomcat
2017-04-14 13:24:15.311  INFO 16340 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.11
2017-04-14 13:24:15.525  INFO 16340 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2017-04-14 13:24:15.526  INFO 16340 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 4548 ms
2017-04-14 13:24:15.873  INFO 16340 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Mapping servlet: 'dispatcherServlet' to [/]
2017-04-14 13:24:15.880  INFO 16340 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2017-04-14 13:24:15.880  INFO 16340 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2017-04-14 13:24:15.881  INFO 16340 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2017-04-14 13:24:15.881  INFO 16340 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2017-04-14 13:24:16.573  INFO 16340 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6366ebe0: startup date [Fri Apr 14 13:24:10 CEST 2017]; root of context hierarchy
2017-04-14 13:24:16.786  INFO 16340 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2017-04-14 13:24:16.787  INFO 16340 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2017-04-14 13:24:16.912  INFO 16340 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-04-14 13:24:16.913  INFO 16340 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-04-14 13:24:17.112  INFO 16340 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-04-14 13:24:17.495  INFO 16340 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-04-14 13:24:17.782  INFO 16340 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-04-14 13:24:17.789  INFO 16340 --- [           main] nl.infodation.SpringtutorialApplication  : Started SpringtutorialApplication in 8.013 seconds (JVM running for 8.74)
```

You can see that lots of things are happening and Spring is doing lots of work in the background.

Close to the end - you should see line that contains:

```
Tomcat started on port(s): 8080 (http)
```

This means that Spring Boot used embedded Tomcat server and started it up on port 8080. Try to open any page (for example http://localhost:8080/movies) and you should see "Whitelabel error page".

This error page simply means that we didn't develop any actual business logic, but we can see that our Tomcat (Spring Boot) application is running.

## How did this happen?

As mentioned in introduction - Spring framework is dealing with lots of defaults. 

Some of the defaults:

- Application will run on embedded Tomcat server
- Tomcat server will be started up on port 8080
- There is default "/error" page that will be shown on any problem that is not handled in any specific way

Changing these defaults will be covered in later tutorial parts, but for now we have our Spring Boot Application up and running with no coding (if you used Initializr) or with just one `main` method with one line of code if you tried to create it manually as standard Maven project.

Spring Boot did all the _hard work_ for us, so we can only focus on developing business logic.

## Now what ?

This is the end of Part 1.

We learned how to:

- Create empty Spring Boot application
- Run our empty application using Spring Boot defaults