package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeRepository recipeRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository);
	}

	@Test
	public void getRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeSet = new HashSet<>();
		recipeSet.add(recipe);

		Mockito.when(recipeRepository.findAll()).thenReturn(recipeSet);

		Set<Recipe> recipes = recipeService.getRecipes();

		Assert.assertEquals(1, recipes.size());
		Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void getRecipeById() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		Mockito.when(recipeRepository.findById(Mockito.any(Long.class))).thenReturn(recipeOptional);

		Recipe result = recipeService.findById(1L);

		Assert.assertNotNull(result);
		Assert.assertEquals(recipe.getId(), result.getId());
	}

	@Test(expected = RuntimeException.class)
	public void getRecipeByIdNotFound() {
		Optional<Recipe> recipeOptional = Optional.empty();
		Mockito.when(recipeRepository.findById(Mockito.any(Long.class))).thenReturn(recipeOptional);

		recipeService.findById(1L);
	}
}