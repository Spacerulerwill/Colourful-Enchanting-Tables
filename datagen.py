import os
import json

ALL_TABLE_COLOURS = [
    "white",
    "light_gray",
    "gray",
    "black",
    "brown",
    "red",
    "orange",
    "yellow",
    "lime",
    "green",
    "cyan",
    "light_blue",
    "blue",
    "purple",
    "magenta",
    "pink",
]


def get_name_from_colour(colour: str) -> str:
    if colour == "red":
        return "minecraft:enchanting_table"
    else:
        return f"{MOD_ID}:{colour}_enchanting_table"


def get_recipe_string(colour: str, item_name: str) -> str:
    return json.dumps(
        {
            "type": "minecraft:crafting_shaped",
            "category": "misc",
            "group": "enchanting_tables",
            "key": {
                "?": {
                    "item": "minecraft:obsidian"
                },
                "B": {
                    "item": "minecraft:book"
                },
                "D": {
                    "item": "minecraft:diamond"
                },
                "C": {
                    "item": f"minecraft:{colour}_carpet"
                }
            },
            "pattern": ["DBD", "C?C", "???"],
            "result": {"count": 1, "id": item_name},
        },
        indent=4,
    )


def get_enchanting_table_recipe_advancement(colour: str) -> str:
    return json.dumps(
        {
            "parent": "minecraft:recipes/root",
            "criteria": {
                "has_white_enchanting_table_recipe": {
                    "conditions": {
                        "recipe": "colourful_enchanting_tables:white_enchanting_table"
                    },
                    "trigger": "minecraft:recipe_unlocked",
                },
                "has_item": {
                    "trigger": "minecraft:inventory_changed",
                    "conditions": {
                        "items": [
                            {
                                "items": [
                                    f"minecraft:{colour}_wool",
                                    f"minecraft:{colour}_carpet",
                                ]
                            }
                        ]
                    },
                },
            },
            "requirements": [
                ["has_white_enchanting_table_recipe"],
                ["has_item"],
            ],
            "rewards": {"recipes": [get_name_from_colour(colour)]},
        },
        indent=4,
    )


def get_enchanting_table_dye_recipe_advancement(colour: str) -> str:
    return json.dumps(
        {
            "parent": "minecraft:recipes/root",
            "criteria": {
                "has_needed_dye": {
                    "conditions": {"items": [{"items": f"minecraft:{colour}_dye"}]},
                    "trigger": "minecraft:inventory_changed",
                },
            },
            "requirements": [["has_needed_dye"]],
            "rewards": {
                "recipes": [
                    f"colourful_enchanting_tables:{colour}_enchanting_table_from_dye"
                ]
            },
        },
        indent=4,
    )


def get_enchanting_table_loot_table(colour: str) -> str:
    return json.dumps(
        {
            "type": "minecraft:block",
            "pools": [
                {
                    "bonus_rolls": 0,
                    "conditions": [{"condition": "minecraft:survives_explosion"}],
                    "entries": [
                        {
                            "type": "minecraft:item",
                            "functions": [
                                {
                                    "function": "minecraft:copy_components",
                                    "include": ["minecraft:custom_name"],
                                    "source": "block_entity",
                                }
                            ],
                            "name": f"colourful_enchanting_tables:{colour}_enchanting_table",
                        }
                    ],
                    "rolls": 1,
                }
            ],
            "random_sequence": f"colourful_enchanting_tables:blocks/{colour}_enchanting_table",
        },
        indent=4,
    )


def get_dye_recipe_string(colour: str) -> str:
    colours = ALL_TABLE_COLOURS.copy()
    colours.remove(colour)
    this_table = get_name_from_colour(colour)
    ingredients = [{"item": get_name_from_colour(colour)} for colour in colours]
    return json.dumps(
        {
            "type": "minecraft:crafting_shapeless",
            "category": "misc",
            "group": "enchanting_tables_from_dye",
            "ingredients": [ingredients, {"item": f"minecraft:{colour}_dye"}],
            "result": {"count": 1, "id": this_table},
        },
        indent=4,
    )


MOD_ID = "colourful_enchanting_tables"
OUR_RECIPE_ADVANCEMENTS_PATH = os.path.join(
    "data", MOD_ID, "advancements", "recipes", "decorations"
)
OUR_STORY_ADVANCEMENTS_PATH = os.path.join("data", MOD_ID, "advancements", "story")
MINECRAFT_RECIPE_ADVANCEMENTS_PATH = os.path.join(
    "data", "minecraft", "advancements", "recipes", "decorations"
)
OUR_BLOCK_LOOT_TABLES_PATH = os.path.join("data", MOD_ID, "loot_tables", "blocks")
OUR_RECIPE_PATH = os.path.join("data", MOD_ID, "recipes")
MINECRAFT_RECIPE_PATH = os.path.join("data", "minecraft", "recipes")
MINECRAFT_MINEABLE_BLOCK_TAGS = os.path.join(
    "data", "minecraft", "tags", "blocks", "mineable"
)
ALL_PATHS = [
    OUR_RECIPE_ADVANCEMENTS_PATH,
    OUR_STORY_ADVANCEMENTS_PATH,
    MINECRAFT_RECIPE_ADVANCEMENTS_PATH,
    OUR_BLOCK_LOOT_TABLES_PATH,
    OUR_RECIPE_PATH,
    MINECRAFT_RECIPE_PATH,
    MINECRAFT_MINEABLE_BLOCK_TAGS,
]


def create_folder_structure() -> None:
    for path in ALL_PATHS:
        os.makedirs(path, exist_ok=True)


def create_recipes() -> None:
    for colour in ALL_TABLE_COLOURS:
        file_path: str
        if colour == "red":
            file_path = os.path.join(MINECRAFT_RECIPE_PATH, "enchanting_table.json")
        else:
            file_path = os.path.join(OUR_RECIPE_PATH, f"{colour}_enchanting_table.json")
        item_name = get_name_from_colour(colour)
        file_content = get_recipe_string(colour, item_name)
        with open(file_path, "w+") as f:
            f.write(file_content)


def create_dye_recipes() -> None:
    for colour in ALL_TABLE_COLOURS:
        with open(
            os.path.join(OUR_RECIPE_PATH, f"{colour}_enchanting_table_from_dye.json"),
            "w+",
        ) as f:
            f.write(get_dye_recipe_string(colour))


def replace_enchanting_table_recipe_advancement() -> None:
    """Special case: replacing the vanilla enchanting table recipe advancement to give you a white enchanting table instead of a red one"""
    with open(
        os.path.join(MINECRAFT_RECIPE_ADVANCEMENTS_PATH, "enchanting_table.json"), "w+"
    ) as f:
        json.dump(
            {
                "parent": "minecraft:recipes/root",
                "criteria": {
                    "has_obsidian": {
                        "conditions": {"items": [{"items": "minecraft:obsidian"}]},
                        "trigger": "minecraft:inventory_changed",
                    },
                },
                "requirements": [["has_obsidian"]],
                "rewards": {
                    "recipes": ["colourful_enchanting_tables:white_enchanting_table"]
                },
            },
            f,
            indent=4,
        )


def create_enchanting_table_recipe_advancements() -> None:
    colours = ALL_TABLE_COLOURS.copy()
    colours.remove("white")
    for colour in colours:
        with open(
            os.path.join(
                OUR_RECIPE_ADVANCEMENTS_PATH, f"{colour}_enchanting_table.json"
            ),
            "w+",
        ) as f:
            f.write(get_enchanting_table_recipe_advancement(colour))


def create_enchanting_table_dye_recipe_advancements() -> None:
    for colour in ALL_TABLE_COLOURS:
        with open(
            os.path.join(
                OUR_RECIPE_ADVANCEMENTS_PATH, f"{colour}_enchanting_table_from_dye.json"
            ),
            "w+",
        ) as f:
            f.write(get_enchanting_table_dye_recipe_advancement(colour))


def create_block_loot_tables() -> None:
    colours = ALL_TABLE_COLOURS.copy()
    colours.remove("red")
    for colour in colours:
        with open(
            os.path.join(OUR_BLOCK_LOOT_TABLES_PATH, f"{colour}_enchanting_table.json"),
            "w+",
        ) as f:
            f.write(get_enchanting_table_loot_table(colour))


def create_pickaxe_mineable_json() -> None:
    colours = ALL_TABLE_COLOURS.copy()
    colours.remove("red")
    all_table_names = [
        f"colourful_enchanting_tables:{colour}_enchanting_table" for colour in colours
    ]
    with open(os.path.join(MINECRAFT_MINEABLE_BLOCK_TAGS, "pickaxe.json"), "w+") as f:
        json.dump({"replace": False, "values": all_table_names}, f, indent=4)


def create_rainbow_tables_advancement() -> None:
    criteria = {
        f"{colour}_enchanting_table": {
            "conditions": {"items": [{"items": get_name_from_colour(colour)}]},
            "trigger": "minecraft:inventory_changed",
        }
        for colour in ALL_TABLE_COLOURS
    }
    requirements = [[f"{colour}_enchanting_table"] for colour in ALL_TABLE_COLOURS]
    with open(
        os.path.join(OUR_STORY_ADVANCEMENTS_PATH, "obtain_all_enchanting_tables.json"),
        "w+",
    ) as f:
        json.dump(
            {
                "parent": "minecraft:story/mine_diamond",
                "criteria": criteria,
                "display": {
                    "description": {
                        "translate": "advancements.story.obtain_all_enchanting_tables.description"
                    },
                    "icon": {
                        "count": 1,
                        "id": "colourful_enchanting_tables:purple_enchanting_table",
                    },
                    "title": {
                        "translate": "advancements.story.obtain_all_enchanting_tables.title"
                    },
                },
                "requirements": requirements,
                "sends_telemetry_event": True,
            },
            f,
            indent=4,
        )


def main() -> None:
    create_folder_structure()
    create_recipes()
    create_dye_recipes()
    replace_enchanting_table_recipe_advancement()
    create_enchanting_table_recipe_advancements()
    create_enchanting_table_dye_recipe_advancements()
    create_block_loot_tables()
    create_pickaxe_mineable_json()
    create_rainbow_tables_advancement()


if __name__ == "__main__":
    main()
