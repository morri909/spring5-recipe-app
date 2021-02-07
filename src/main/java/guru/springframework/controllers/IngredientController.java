package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipe")
public class IngredientController {

	private final IngredientService ingredientService;
	private final RecipeService recipeService;
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(IngredientService ingredientService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
		this.ingredientService = ingredientService;
		this.recipeService = recipeService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping("/{recipeId}/ingredients")
	public String list(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		return "recipe/ingredient/list";
	}

	@GetMapping("/{recipeId}/ingredient/{id}/show")
	public String show(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
		return "recipe/ingredient/show";
	}

	@GetMapping("/{recipeId}/ingredient/{id}/update")
	public String update(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
		model.addAttribute("uomList", unitOfMeasureService.listAll());
		return "recipe/ingredient/ingredientform";
	}

	@PostMapping("/{recipeId}/ingredient")
	public String save(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
	}
}
