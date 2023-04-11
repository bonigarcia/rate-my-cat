/*
 * (C) Copyright 2017 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.test.e2e;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.bonigarcia.seljup.Arguments;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith({ SpringExtension.class, SeleniumJupiter.class })
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("E2E tests: user interface")
@Tag("e2e")
class UserInferfaceTest {

    @LocalServerPort
    int serverPort;

    @Test
    @DisplayName("List cats in the GUI")
    @Tag("functional-requirement-1")
    void testListCats(
            @Arguments("--remote-allow-origins=*") ChromeDriver driver) {
        driver.get("http://localhost:" + serverPort);
        List<WebElement> catLinks = driver
                .findElements(By.className("lightbox"));
        assertThat(catLinks.size(), equalTo(9));
    }

    @Test
    @DisplayName("Rate a cat using the GUI")
    @Tag("functional-requirement-2")
    void testRateCat(FirefoxDriver driver) {
        driver.get("http://localhost:" + serverPort);
        driver.findElement(By.id("Baby")).click();

        String fourStarsSelector = "#form1 span:nth-child(4)";
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(elementToBeClickable(By.cssSelector(fourStarsSelector)));
        driver.findElement(By.cssSelector(fourStarsSelector)).click();

        driver.findElement(By.xpath("//*[@id=\"comment\"]"))
                .sendKeys("Very nice cat");
        driver.findElement(By.cssSelector("#form1 > button")).click();

        WebElement sucessDiv = driver
                .findElement(By.cssSelector("#success > div"));
        assertThat(sucessDiv.getText(), containsString("Your vote for Baby"));
    }

    @Test
    @DisplayName("Rate a cat using the GUI with error")
    @Tag("functional-requirement-2")
    void testRateCatWithError(@Arguments({ "--headless",
            "--remote-allow-origins=*" }) ChromeDriver driver) {
        driver.get("http://localhost:" + serverPort);
        driver.findElement(By.id("Baby")).click();

        String sendButtonSelector = "#form1 > button";
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                elementToBeClickable(By.cssSelector(sendButtonSelector)));
        driver.findElement(By.cssSelector(sendButtonSelector)).click();

        WebElement sucessDiv = driver
                .findElement(By.cssSelector("#error > div"));
        assertThat(sucessDiv.getText(), containsString(
                "You need to select some stars for rating each cat"));
    }

}
