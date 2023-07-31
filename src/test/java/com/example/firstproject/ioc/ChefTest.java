package com.example.firstproject.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChefTest {

    @Autowired
    IngredientFactory ingredientFactory;
    @Autowired
    Chef chef;

    @Test
    void 돈가스_요리하기() {
        // 준비
        String menu = "돈가스";

        // 수행
        String food = chef.cook(menu);

        // 예상
        String expected = "한돈 등심으로 만든 돈가스";

        // 검증
        assertEquals(food, expected);
        System.out.println(food);
    }

    @Test
    void 스테이크_요리하기() {
        String menu = "스테이크";

        String food = chef.cook(menu);

        String expected = "한우 꽃등심으로 만든 스테이크";
        assertEquals(food, expected);
        System.out.println(food);
    }

    @Test
    void 치킨_요리하기() {
        String menu = "크리스피 치킨";
        String food = chef.cook(menu);

        String expected = "국내산 10호 닭으로 만든 크리스피 치킨";
        assertEquals(food, expected);
        System.out.println(food);
    }
}