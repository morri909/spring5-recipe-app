package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
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

public class IngredientControllerTest {

	@Mock
	IngredientService ingredientService;
	@Mock
	RecipeService recipeService;

	IngredientController sut;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		sut = new IngredientController(ingredientService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
	}

	@Test
	public void list() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));

		Mockito.verify(recipeService).findCommandById(Mockito.anyLong());
	}

	@Test
	public void show() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();

		Mockito.when(ingredientService.findByRecipeIdAndId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(ingredientCommand);

		mockMvc.perform(get("/recipe/1/ingredients/2/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show"))
				.andExpect(model().attributeExists("ingredient"));

		Mockito.verify(ingredientService).findByRecipeIdAndId(Mockito.anyLong(), Mockito.anyLong());
	}
}