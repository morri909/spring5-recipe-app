package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
	public void showById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Mockito.when(recipeService.findById(Mockito.any(Long.class))).thenReturn(recipe);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

		mockMvc.perform(get("/recipe/" + recipe.getId() + "/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void newRecipe() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

		mockMvc.perform(get("/recipe/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void updateRecipe() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(recipeCommand);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

		mockMvc.perform(get("/recipe/" + recipeCommand.getId() + "/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void save() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		Mockito.when(recipeService.saveRecipeCommand(Mockito.any(RecipeCommand.class)))
				.thenReturn(recipeCommand);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

		mockMvc.perform(post("/recipe", recipeCommand))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/recipe/show/"  + recipeCommand.getId()));

		Mockito.verify(recipeService).saveRecipeCommand(Mockito.any(RecipeCommand.class));
	}
}