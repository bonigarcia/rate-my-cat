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
package io.github.bonigarcia;

import static io.github.bonigarcia.CookiesService.COOKIE_NAME;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WebController {

    private CatService catService;
    private CookiesService cookiesService;

    public WebController(CatService catService, CookiesService cookiesService) {
        this.catService = catService;
        this.cookiesService = cookiesService;
    }

    final Logger log = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/")
    public ModelAndView index(
            @CookieValue(value = COOKIE_NAME, defaultValue = "") String cookiesValue) {
        log.trace("Cookies: {}", cookiesValue);
        ModelAndView model = new ModelAndView("index");
        List<Cat> allCats = catService.getAllCats();
        model.addObject("cats",
                cookiesService.filterCatListWithCookies(allCats, cookiesValue));
        return model;
    }

    @PostMapping("/")
    public ModelAndView rate(@RequestParam Long catId,
            @RequestParam(required = false) Double stars,
            @RequestParam String comment,
            @CookieValue(value = COOKIE_NAME, defaultValue = "") String cookiesValue,
            HttpServletResponse response) {
        log.info("Received vote for cat {}: stars={} comment={}", catId, stars,
                comment);

        ModelAndView model = new ModelAndView("index");
        String newCookiesValue = cookiesValue;
        try {
            if (stars == null) {
                model.addObject("errorMessage",
                        "You need to select some stars for rating each cat");

            } else {
                Cat ratedCat = catService.rateCat(stars, comment, catId);
                String sucessMessage = String.format(Locale.US,
                        "Your vote for %s with %.1f stars and comment '%s' has been stored",
                        ratedCat.getName(), stars, comment);
                model.addObject("sucessMessage", sucessMessage);

                newCookiesValue = cookiesService.updateCookies(cookiesValue,
                        catId, stars, comment, response);
            }
        } catch (Exception e) {
            log.error("Exception rating cat: {}", e.getMessage());
            model.addObject("errorMessage", e.getMessage());
        } finally {
            List<Cat> allCats = catService.getAllCats();
            model.addObject("cats", cookiesService
                    .filterCatListWithCookies(allCats, newCookiesValue));
        }

        return model;
    }

}
