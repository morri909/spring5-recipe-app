package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientController {

	private final IngredientService ingredientService;
	private final RecipeService recipeService;

	public IngredientController(IngredientService ingredientService, RecipeService recipeService) {
		this.ingredientService = ingredientService;
		this.recipeService = recipeService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String list(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		return "recipe/ingredient/list";
	}

	@GetMapping("/recipe/{recipeId}/ingredients/{id}/show")
	public String show(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
		return "recipe/ingredient/show";
	}
}
