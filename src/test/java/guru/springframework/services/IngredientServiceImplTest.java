package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;

public class IngredientServiceImplTest {

	@Mock
	IngredientToIngredientCommand ingredientToIngredientCommand;
	@Mock
	RecipeRepository recipeRepository;

	IngredientService sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		sut = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
	}

	@Test
	public void findByRecipeIdAndId() {
		Recipe recipe = new Recipe();
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		recipe.getIngredients().add(ingredient1);;
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		recipe.getIngredients().add(ingredient2);
		Mockito.when(recipeRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(recipe));

		IngredientCommand ingredientCommand = new IngredientCommand();
		Mockito.when(ingredientToIngredientCommand.convert(Mockito.any(Ingredient.class)))
				.thenReturn(ingredientCommand);

		IngredientCommand result = sut.findByRecipeIdAndId(1L, 2L);

		Assert.assertNotNull(result);
		Assert.assertEquals(ingredientCommand, result);
	}
}