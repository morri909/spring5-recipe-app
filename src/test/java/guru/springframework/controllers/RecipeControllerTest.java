package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	RecipeController sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sut = new RecipeController(recipeService);
	}

	@Test
	public void getRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Mockito.when(recipeService.findById(Mockito.any(Long.class))).thenReturn(recipe);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

		mockMvc.perform(get("/recipe/show/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}
}