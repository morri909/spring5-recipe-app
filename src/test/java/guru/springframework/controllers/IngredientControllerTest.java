package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
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
	RecipeService recipeService;

	IngredientController sut;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		sut = new IngredientController(recipeService);
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
}