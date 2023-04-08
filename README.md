# Rate my cat! [![][Logo]][GitHub Repository]

[![Build Status](https://github.com/bonigarcia/rate-my-cat/workflows/build/badge.svg)](https://github.com/bonigarcia/rate-my-cat/actions)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=io.github.bonigarcia:rate-my-cat&metric=alert_status)](https://sonarcloud.io/project/overview?id=io.github.bonigarcia%3Arate-my-cat)
[![codecov](https://codecov.io/gh/bonigarcia/rate-my-cat/branch/master/graph/badge.svg)](https://codecov.io/gh/bonigarcia/rate-my-cat)

This project contains a complete sample application for the book [Mastering Software Testing with JUnit 5]. It consists on a web application in which end uses can rate a list of cats by watching its name and picture. The rate shall be done once per end user using a star mechanism. Optionally, comments can be made per cat. This application has been built using the following technologies:

* Spring Framework, as application framework: Spring Boot, Spring MVC + Thymeleaf, Spring Data JPA, and Spring Test (for integration tests).
* JUnit 5, as testing framework.
* Hamcrest, for improving the readability of assertions.
* Mockito, for unit testing.
* Selenium WebDriver, for end-to-end testing.

The screenshots below show the application GUI in action.

![][Screeshot 1]
![][Screeshot 2]

# About

This is a project made by [Boni Garcia], Associate Professor at [Universidad Carlos III de Madrid], Spain. Copyright &copy; 2017-2023.

[Boni Garcia]: https://bonigarcia.github.io/
[Universidad Carlos III de Madrid]: https://www.it.uc3m.es/bogarcia/index.html
[GitHub Repository]: https://github.com/bonigarcia/rate-my-cat
[Logo]: https://raw.githubusercontent.com/bonigarcia/rate-my-cat/master/src/main/resources/static/img/rate-my-cat.png
[Screeshot 1]: https://raw.githubusercontent.com/bonigarcia/rate-my-cat/master/doc/rate-my-cat-screeshot-1.png
[Screeshot 2]: https://raw.githubusercontent.com/bonigarcia/rate-my-cat/master/doc/rate-my-cat-screeshot-2.png
[Mastering Software Testing with JUnit 5]: https://www.amazon.com/Mastering-Software-Testing-JUnit-Comprehensive-ebook/dp/B076ZQCK5Q
