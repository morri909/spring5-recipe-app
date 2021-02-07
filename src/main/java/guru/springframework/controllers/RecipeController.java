package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping("/{id}/show")
	public String showById(@PathVariable Long id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe/show";
	}

	@RequestMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}

	@RequestMapping("/{id}/update")
	public String updateRecipe(@PathVariable Long id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/recipeform";
	}

	@PostMapping
	@RequestMapping("")
	public String save(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
	}
}
