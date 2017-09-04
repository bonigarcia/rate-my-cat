/*
 * (C) Copyright 2017 Boni Garcia (http://bonigarcia.github.io/)
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.bonigarcia.SeleniumExtension;

@DisplayName("Test of the web user interface")
@ExtendWith({ SpringExtension.class, SeleniumExtension.class })
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserInferfaceTest {

    @LocalServerPort
    int serverPort;

    @Test
    @DisplayName("Rate a cat using the GUI")
    public void testRateCat(ChromeDriver chrome) {
        chrome.get("http://localhost:" + serverPort);
        chrome.findElement(By.id("Baby")).click();

        String fourStarsSelector = "#form1 > div > div.rating-stars > span.empty-stars > span:nth-child(4)";
        new WebDriverWait(chrome, 10)
                .until(elementToBeClickable(By.cssSelector(fourStarsSelector)));
        chrome.findElement(By.cssSelector(fourStarsSelector)).click();

        chrome.findElement(By.xpath("//*[@id=\"comment\"]"))
                .sendKeys("Very nice cat");
        chrome.findElement(By.cssSelector("#form1 > button")).click();

        WebElement sucessDiv = chrome
                .findElement(By.cssSelector("#success > div"));
        assertThat(sucessDiv.getText(), containsString("Your vote for Baby"));
    }

}
