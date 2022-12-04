package de.hsflensburg.recipe_backend.shared.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.hibernate.annotations.Formula;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CalculateTotalCalories {
    @AliasFor(annotation = Formula.class, attribute = "value")
    String value() default RecipeConstants.PRE_SQL + RecipeConstants.COLUMN_CALORIES + RecipeConstants.POST_SQL;
}

class RecipeConstants {
    public static final String COLUMN_CALORIES = "calories";
    public static final String PRE_SQL = "(SELECT SUM(ingredient_info.amount / 100 * ingredient.";
    public static final String POST_SQL = ") FROM recipe INNER JOIN recipe_step ON recipe.id = recipe_step.recipe_id INNER JOIN ingredient_info ON recipe_step.id = ingredient_info.recipe_step_id INNER JOIN ingredient ON ingredient_info.ingredient_id = ingredient.id WHERE recipe.id = id)";
}

/*
@Retention(RetentionPolicy.RUNTIME)
public @interface CalculateTotalCalories {
    Formula value() default @Formula("hasAnyAuthority('HELLO', 'HEALTH_ENDPOINT')");
}

 */