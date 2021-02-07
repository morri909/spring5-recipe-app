package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class IngredientServiceImplTest {

	@Mock
	IngredientCommandToIngredient ingredientCommandToIngredient;
	@Mock
	IngredientToIngredientCommand ingredientToIngredientCommand;
	@Mock
	RecipeRepository recipeRepository;
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	IngredientService sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		sut = new IngredientServiceImpl(
				ingredientCommandToIngredient,
				ingredientToIngredientCommand,
				recipeRepository,
				unitOfMeasureRepository
			);
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

	@Test
	public void saveIngredientCommand() {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(2L);
		ingredientCommand.setRecipeId(1L);
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(3L);
		ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

		Recipe recipe = new Recipe();
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		recipe.getIngredients().add(ingredient1);;
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		recipe.getIngredients().add(ingredient2);
		Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));
		Mockito.when(unitOfMeasureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new UnitOfMeasure()));
		Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);
		Mockito.when(ingredientToIngredientCommand.convert(Mockito.any(Ingredient.class))).thenReturn(new IngredientCommand());

		// when
		IngredientCommand result = sut.saveIngredientCommand(ingredientCommand);

		// then
		Assert.assertNotNull(result);
		Mockito.verify(recipeRepository).save(Mockito.any(Recipe.class));
		Mockito.verify(ingredientToIngredientCommand).convert(Mockito.any(Ingredient.class));
	}
}