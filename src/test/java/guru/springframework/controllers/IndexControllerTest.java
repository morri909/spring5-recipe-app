package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

public class IndexControllerTest {

	@Mock
	private RecipeService recipeService;
	@Mock
	private Model model;

	private IndexController indexController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}

	@Test
	public void getIndexPage() {
		Set<Recipe> recipes = new HashSet<>();
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		recipes.add(recipe1);
		Recipe recipe2 = new Recipe();
		recipe2.setId(2L);
		recipes.add(recipe2);
		Mockito.when(recipeService.getRecipes()).thenReturn(recipes);

		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		String view = indexController.getIndexPage(model);

		Assert.assertEquals("index", view);
		Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
		Mockito.verify(model, Mockito.times(1))
				.addAttribute(ArgumentMatchers.eq("recipes"), argumentCaptor.capture());

		Set<Recipe> results = argumentCaptor.getValue();
		Assert.assertEquals(2, results.size());
	}
}