package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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
		String view = indexController.getIndexPage(model);

		Assert.assertEquals("index", view);
		Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
		Mockito.verify(model, Mockito.times(1))
				.addAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.anySet());
	}
}