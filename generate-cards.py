from enum import Enum
from dataclasses import dataclass

from pathlib import Path
from typing import Optional

from PIL import Image, ImageDraw


# ===================== Classes =====================
class Suit(Enum):
    MELEE = 0
    RANGED = 1
    CAVALRY = 2
    WILD = 4
    JOKER = 5


class Ability(Enum):
    HORDE = 0
    SEER = 1
    WIZARD = 2
    SPY = 3
    COMMANDER = 4  # Jack
    UNIQUE = 5  # Queen
    IMMUNE = 6  # King


class Effect(Enum):
    WEATHER = 0
    SCORCH = 1
    EMPOWER = 2


@dataclass
class Card:
    file_name: str


@dataclass
class Unit(Card):
    suit: Suit
    power: int
    ability: Optional[Ability]


@dataclass
class Special(Card):
    suit: Suit
    effect: Effect


# ===================== Constants =====================
WIDTH = 24
HEIGHT = 32
SIZE = (WIDTH, HEIGHT)

FOLDER = Path(__file__).absolute().parent
CARDS_FOLDER = FOLDER / "assets" / "cards"
TEMPLATES_FOLDER = FOLDER / "templates"

BASE_UNIT_IMG_PATH = TEMPLATES_FOLDER / "base.png"
BASE_SPECIAL_IMG_PATH = TEMPLATES_FOLDER / "base_special.png"

SUIT_DECORATIONS = {
    Suit.MELEE: Image.open(TEMPLATES_FOLDER / "melee_decorations.png"),
    Suit.RANGED: Image.open(TEMPLATES_FOLDER / "ranged_decorations.png"),
    Suit.CAVALRY: Image.open(TEMPLATES_FOLDER / "cavalry_decorations.png"),
    Suit.WILD: Image.open(TEMPLATES_FOLDER / "wild_decorations.png"),
}

SUIT_DECORATIONS_SPECIAL = {
    Suit.MELEE: Image.open(TEMPLATES_FOLDER / "melee_decorations_special.png"),
    Suit.RANGED: Image.open(TEMPLATES_FOLDER / "ranged_decorations_special.png"),
    Suit.CAVALRY: Image.open(TEMPLATES_FOLDER / "cavalry_decorations_special.png"),
    Suit.WILD: Image.open(TEMPLATES_FOLDER / "wild_decorations_special.png"),
    Suit.JOKER: Image.open(TEMPLATES_FOLDER / "joker_decorations_special.png"),
}

SUIT_ICONS = {
    Suit.MELEE: Image.open(TEMPLATES_FOLDER / "melee_icon.png"),
    Suit.RANGED: Image.open(TEMPLATES_FOLDER / "ranged_icon.png"),
    Suit.CAVALRY: Image.open(TEMPLATES_FOLDER / "cavalry_icon.png"),
    Suit.WILD: Image.open(TEMPLATES_FOLDER / "wild_icon.png"),
}

ABILITY_ICONS = {
    Ability.HORDE: Image.open(TEMPLATES_FOLDER / "horde_symbol.png"),
    Ability.SEER: Image.open(TEMPLATES_FOLDER / "seer_symbol.png"),
    Ability.UNIQUE: Image.open(TEMPLATES_FOLDER / "unique_symbol.png"),
    Ability.WIZARD: Image.open(TEMPLATES_FOLDER / "wizard_symbol.png"),
    Ability.SPY: Image.open(TEMPLATES_FOLDER / "spy_symbol.png"),
    Ability.COMMANDER: Image.open(TEMPLATES_FOLDER / "commander_symbol.png"),
    Ability.IMMUNE: Image.open(TEMPLATES_FOLDER / "immune_symbol.png"),
}

EFFECT_ICONS = {
    Effect.WEATHER: Image.open(TEMPLATES_FOLDER / "weather_symbol.png"),
    Effect.SCORCH: Image.open(TEMPLATES_FOLDER / "scorch_symbol.png"),
    Effect.EMPOWER: Image.open(TEMPLATES_FOLDER / "empower_symbol.png"),
}

# ===================== Card data =====================
cards = [
    Unit("melee_3.png", Suit.MELEE, 3, Ability.HORDE),
    Unit("melee_4.png", Suit.MELEE, 4, Ability.SEER),
    Unit("melee_5.png", Suit.MELEE, 5, None),
    Unit("melee_6.png", Suit.MELEE, 6, None),
    Unit("melee_7.png", Suit.MELEE, 7, None),
    Unit("melee_8.png", Suit.MELEE, 8, None),
    Unit("melee_9.png", Suit.MELEE, 9, None),
    Unit("melee_10.png", Suit.MELEE, 10, Ability.SPY),
    Unit("melee_jack.png", Suit.MELEE, 10, Ability.COMMANDER),
    Unit("melee_queen.png", Suit.MELEE, 10, Ability.UNIQUE),
    Unit("melee_king.png", Suit.MELEE, 10, Ability.IMMUNE),
    Unit("ranged_3.png", Suit.RANGED, 3, Ability.HORDE),
    Unit("ranged_4.png", Suit.RANGED, 4, Ability.SEER),
    Unit("ranged_5.png", Suit.RANGED, 5, None),
    Unit("ranged_6.png", Suit.RANGED, 6, None),
    Unit("ranged_7.png", Suit.RANGED, 7, None),
    Unit("ranged_8.png", Suit.RANGED, 8, None),
    Unit("ranged_9.png", Suit.RANGED, 9, None),
    Unit("ranged_10.png", Suit.RANGED, 10, Ability.SPY),
    Unit("ranged_jack.png", Suit.RANGED, 10, Ability.COMMANDER),
    Unit("ranged_queen.png", Suit.RANGED, 10, Ability.UNIQUE),
    Unit("ranged_king.png", Suit.RANGED, 10, Ability.IMMUNE),
    Unit("cavalry_3.png", Suit.CAVALRY, 3, Ability.HORDE),
    Unit("cavalry_4.png", Suit.CAVALRY, 4, Ability.SEER),
    Unit("cavalry_5.png", Suit.CAVALRY, 5, None),
    Unit("cavalry_6.png", Suit.CAVALRY, 6, None),
    Unit("cavalry_7.png", Suit.CAVALRY, 7, None),
    Unit("cavalry_8.png", Suit.CAVALRY, 8, None),
    Unit("cavalry_9.png", Suit.CAVALRY, 9, None),
    Unit("cavalry_10.png", Suit.CAVALRY, 10, Ability.SPY),
    Unit("cavalry_jack.png", Suit.CAVALRY, 10, Ability.COMMANDER),
    Unit("cavalry_queen.png", Suit.CAVALRY, 10, Ability.UNIQUE),
    Unit("cavalry_king.png", Suit.CAVALRY, 10, Ability.IMMUNE),
    Unit("wild_3.png", Suit.WILD, 3, Ability.HORDE),
    Unit("wild_4.png", Suit.WILD, 4, Ability.SEER),
    Unit("wild_5.png", Suit.WILD, 5, Ability.WIZARD),
    Unit("wild_6.png", Suit.WILD, 6, None),
    Unit("wild_7.png", Suit.WILD, 7, None),
    Unit("wild_8.png", Suit.WILD, 8, None),
    Unit("wild_9.png", Suit.WILD, 9, None),
    Unit("wild_10.png", Suit.WILD, 10, Ability.SPY),
    Unit("wild_jack.png", Suit.WILD, 10, Ability.COMMANDER),
    Unit("wild_queen.png", Suit.WILD, 10, Ability.UNIQUE),
    Unit("wild_king.png", Suit.WILD, 10, Ability.IMMUNE),
    Special("melee_scorch.png", Suit.MELEE, Effect.SCORCH),
    Special("ranged_scorch.png", Suit.RANGED, Effect.SCORCH),
    Special("cavalry_scorch.png", Suit.CAVALRY, Effect.SCORCH),
    Special("wild_scorch.png", Suit.WILD, Effect.SCORCH),
    Special("melee_weather.png", Suit.MELEE, Effect.WEATHER),
    Special("ranged_weather.png", Suit.RANGED, Effect.WEATHER),
    Special("cavalry_weather.png", Suit.CAVALRY, Effect.WEATHER),
    Special("wild_weather.png", Suit.WILD, Effect.WEATHER),
    Special("joker.png", Suit.JOKER, Effect.EMPOWER),
]

# ===================== Image generation =====================
for card in cards:

    if isinstance(card, Unit):
        # Generate unit card
        img = Image.open(BASE_UNIT_IMG_PATH)
        draw = ImageDraw.Draw(img)

        suit_decoration, suit_icon = SUIT_DECORATIONS[card.suit], SUIT_ICONS[card.suit]
        img.paste(suit_decoration, (0, 0), suit_decoration)
        img.paste(suit_icon, (0, 0), suit_icon)

        if card.ability is not None:
            symbol_pos = (39, 8)
            ability_symbol = ABILITY_ICONS[card.ability]
            img.paste(ability_symbol, symbol_pos, ability_symbol)

        img.save(CARDS_FOLDER / card.file_name, "PNG")

    elif isinstance(card, Special):
        # Generate special card
        img = Image.open(BASE_SPECIAL_IMG_PATH)
        draw = ImageDraw.Draw(img)

        suit_decoration = SUIT_DECORATIONS_SPECIAL[card.suit]
        img.paste(suit_decoration, (0, 0), suit_decoration)

        effect_symbol = EFFECT_ICONS[card.effect]
        img.paste(effect_symbol, (5, 5), effect_symbol)

        img.save(CARDS_FOLDER / card.file_name, "PNG")


print(f"Generated {len(cards)} cards in {CARDS_FOLDER}")
