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
package io.github.bonigarcia;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CookiesService {

    static final String COOKIE_NAME = "catList";
    static final String VALUE_SEPARATOR = "#";
    static final String CAT_SEPARATOR = "_";

    final Logger log = LoggerFactory.getLogger(CookiesService.class);

    public String addCookie(String cookieValue, Long catId, Double stars,
            String comment, HttpServletResponse response) {
        String newCookieValue = cookieValue + catId + VALUE_SEPARATOR + stars
                + VALUE_SEPARATOR
                + Base64.getEncoder().encodeToString(comment.getBytes())
                + CAT_SEPARATOR;

        log.debug("Adding cookie {}={}", COOKIE_NAME, newCookieValue);
        response.addCookie(new Cookie("catList", newCookieValue));

        return newCookieValue;
    }

    public boolean isCatInCookies(Cat cat, String cookieValue) {
        String[] cats = cookieValue.split(CAT_SEPARATOR);
        for (String strCat : cats) {
            if (strCat.equals("")) {
                continue;
            }
            if (cat.getId() == Long
                    .parseLong(strCat.split(VALUE_SEPARATOR)[0])) {
                return true;
            }
        }
        return false;
    }

    public List<Opinion> updateOpinionsWithCookiesValue(Cat cat,
            String cookieValue) {
        List<Opinion> outputOpinionList = new ArrayList<>();
        String cookieValueForCat = getValueForCat(cat, cookieValue);

        String[] split = cookieValueForCat.split(VALUE_SEPARATOR);
        double stars = Double.parseDouble(split[1]);
        String comment = split.length > 2
                ? new String(Base64.getDecoder().decode(split[2]))
                : "";

        boolean opinionInCookies = false;
        for (Opinion opinion : cat.getOpinions()) {
            opinionInCookies = isOpinionInCookies(opinion, stars, comment);
            opinion.setInCookies(opinionInCookies);
            outputOpinionList.add(opinion);
        }

        if (!opinionInCookies) {
            Opinion opinion = new Opinion(stars, comment);
            opinion.setInCookies(true);
            outputOpinionList.add(opinion);
        }
        return outputOpinionList;
    }

    public boolean isOpinionInCookies(Opinion opinion, double stars,
            String comment) {
        return opinion.getStars() == stars
                && opinion.getComment().equals(comment);
    }

    public String getValueForCat(Cat cat, String cookieValue) {
        String[] cats = cookieValue.split(CAT_SEPARATOR);
        for (String strCat : cats) {
            if (strCat.equals("")) {
                continue;
            }
            if (cat.getId() == Long
                    .parseLong(strCat.split(VALUE_SEPARATOR)[0])) {
                return strCat;
            }
        }
        return null;
    }

    public List<Cat> filterCatListWithCookies(Iterable<Cat> allCats,
            String cookieValue) {
        List<Cat> filteredCats = new ArrayList<>();
        allCats.forEach(cat -> {
            boolean catInCookies = isCatInCookies(cat, cookieValue);
            cat.setInCookies(catInCookies);

            if (catInCookies) {
                cat.setOpinions(
                        updateOpinionsWithCookiesValue(cat, cookieValue));
            }

            filteredCats.add(cat);
            log.trace("Cat: {}", cat);
        });
        return filteredCats;
    }

}
