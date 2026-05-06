# Baking App

An Android application that displays baking recipes with ingredients, step-by-step instructions, and video tutorials. Includes a home screen widget for quick access to recipe ingredients.

## Tech Stack

- **Java** / **Android SDK**
- **ExoPlayer** for video playback
- **Espresso** for UI testing
- **Gradle**

## Features

- Browse a list of baking recipes
- View recipe details including ingredients and steps
- Watch step-by-step video instructions
- Tablet-optimized master-detail layout (`sw600dp`)
- Home screen widget displaying recipe ingredients

## Project Structure

```
app/src/main/java/com/e/baking/
├── MainActivity.java                      # Recipe list screen
├── RecipeDetailsActivity.java             # Recipe detail (ingredients + steps)
├── VideoAndInstructionsActivity.java      # Step video + instructions
├── VideoFragment.java                     # ExoPlayer video fragment
├── InstructionFragment.java               # Step instruction fragment
├── MasterListFragment.java                # Master list for tablet layout
├── RecipeWidgetProvider.java              # Home screen widget
├── RecipeWidgetService.java               # Widget data service
├── model/
│   ├── Recipe.java
│   ├── Ingredient.java
│   └── Step.java
└── *Adapter.java                          # RecyclerView adapters
```

## Setup

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on an emulator or connected device (API 21+)

## Testing

Espresso UI tests are located in `app/src/androidTest/`:

```bash
./gradlew connectedAndroidTest
```
