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
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CatService {

    private static final double MIN_STARS = 0.5;
    private static final double MAX_STARS = 5;

    final Logger log = LoggerFactory.getLogger(CatService.class);

    private CatRepository catRepository;
    private CookiesService cookiesService;

    public CatService(CatRepository catRepository,
            CookiesService cookiesService) {
        this.catRepository = catRepository;
        this.cookiesService = cookiesService;
    }

    public Cat saveCat(Cat cat) {
        return catRepository.save(cat);
    }

    public Cat rateCat(double stars, String comment, long catId) {
        Optional<Cat> optionalCat = catRepository.findById(catId);
        if (!optionalCat.isPresent()) {
            throw new CatException("Cat with id " + catId + "not available");
        }
        rateCat(stars, comment, optionalCat.get());
        return optionalCat.get();
    }

    public Cat rateCat(double stars, String comment, Cat cat) {
        log.debug("Rating cat with {} stars and comment: \"{}\"", stars,
                comment);

        if (stars < MIN_STARS) {
            throw new CatException(
                    "The minimum number of possible stars is " + MIN_STARS);
        }
        if (stars > MAX_STARS) {
            throw new CatException(
                    "The maximum number of possible stars is " + MAX_STARS);
        }

        cat.rate(stars, comment);
        saveCat(cat);
        log.debug("Cat info after rating: {}", cat);

        return cat;
    }

    public long getCatCount() {
        return catRepository.findAll().spliterator().getExactSizeIfKnown();
    }

    public List<Cat> getAllCats(String cookieValue) {
        Iterable<Cat> allCats = catRepository.findAll();
        List<Cat> filteredCats = new ArrayList<>();
        allCats.forEach(cat -> {
            cat.setInCookies(cookiesService.isCatInCookies(cat, cookieValue));
            filteredCats.add(cat);
            log.debug("{} isCatInCookies {}", cat.getName(),
                    cookiesService.isCatInCookies(cat, cookieValue));
        });
        return filteredCats;
    }

    public List<Opinion> getOpinions(Cat cat) {
        Optional<Cat> optionalCat = catRepository.findById(cat.getId());
        if (!optionalCat.isPresent()) {
            throw new CatException("Cat not available: " + cat);
        }
        return optionalCat.get().getOpinions();
    }

}
