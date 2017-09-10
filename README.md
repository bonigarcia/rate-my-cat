# Rate my cat! [![][Logo]][GitHub Repository]

[![Build Status](https://travis-ci.org/bonigarcia/rate-my-cat.svg?branch=master)](https://travis-ci.org/bonigarcia/rate-my-cat)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=io.github.bonigarcia:rate-my-cat)](https://sonarcloud.io/dashboard/index/io.github.bonigarcia:rate-my-cat)
[![codecov](https://codecov.io/gh/bonigarcia/rate-my-cat/branch/master/graph/badge.svg)](https://codecov.io/gh/bonigarcia/rate-my-cat)

This project contains a complete sample application for the book *Mastering JUnit 5*. It consists on a web application in which end uses can rate a list of cats by watching its name and picture. The rate shall be done once per end user using a star mechanism. Optionally, comments can be made per cat. This application has been built using the following technologies:

* Spring Framework, as application framework: Spring Boot, Spring MVC + Thymeleaf, Spring Data JPA, and Spring Test (for integration tests).
* JUnit 5, as testing framework.
* Hamcrest, for improving the readability of assertions.
* Mockito, for unit testing.
* Selenium WebDriver, for end-to-end testing.

The screenshots below show the application GUI in action.

![][Screeshot 1]
![][Screeshot 2]

You can find an online demo of *Rate my cat!* deployed on [Amazon EC2].

# About

This is a project made by [Boni Garcia], Assistant Professor at [U-tad] and Researcher at [Universidad Rey Juan Carlos], Spain. Copyright &copy; 2017.

[Boni Garcia]: http://bonigarcia.github.io/
[U-tad]: http://www.u-tad.com/
[Universidad Rey Juan Carlos]: https://www.urjc.es/
[GitHub Repository]: https://github.com/bonigarcia/rate-my-cat
[Logo]: https://raw.githubusercontent.com/bonigarcia/rate-my-cat/master/src/main/resources/static/img/rate-my-cat.png
[Amazon EC2]: http://ec2-34-211-5-120.us-west-2.compute.amazonaws.com/
[Screeshot 1]: https://raw.githubusercontent.com/bonigarcia/rate-my-cat/master/doc/rate-my-cat-screeshot-1.png
[Screeshot 2]: https://raw.githubusercontent.com/bonigarcia/rate-my-cat/master/doc/rate-my-cat-screeshot-2.png
