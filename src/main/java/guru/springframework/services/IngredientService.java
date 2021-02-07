package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByRecipeIdAndId(Long recipeId, Long id);
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
