from enum import Enum
from dataclasses import dataclass

from pathlib import Path
from typing import Optional

from PIL import Image, ImageDraw


# ===================== Classes =====================
class Suit(Enum):
    SPADES = 0
    DIAMONDS = 1
    CLUBS = 2
    HEARTS = 4
    JOKER = 5


class Ability(Enum):
    MILITIA = 0  # Get stronger with other militia
    SEER = 1  # See card from enemy hand
    HERO = 2  # Destroy big enemy
    SPY = 3  # Go on enemy board to draw a card
    CHARGER = 4  # Stronger when recently played
    WIZARD = 5  # Move an enemy
    COMMANDER = 6  # Jack, get stronger with more units in row
    UNIQUE = 7  # Queen, only one on entire board
    IMMUNE = 8  # King, ignored to some spells


class Effect(Enum):
    WEATHER = 0  # Make row weak
    SCORCH = 1  # Destroy the strongest unit
    EMPOWER = 2  # Double power of unit


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
CARDS_FOLDER = FOLDER / "cards"
ASSETS_FOLDER = FOLDER / "assets"

BASE_UNIT_IMG_PATH = ASSETS_FOLDER / "base.png"
BASE_SPECIAL_IMG_PATH = ASSETS_FOLDER / "base_special.png"

SUIT_DECORATIONS = {
    Suit.SPADES: Image.open(ASSETS_FOLDER / "spades_decorations.png").convert('RGBA'),
    Suit.DIAMONDS: Image.open(ASSETS_FOLDER / "diamonds_decorations.png").convert('RGBA'),
    Suit.CLUBS: Image.open(ASSETS_FOLDER / "clubs_decorations.png").convert('RGBA'),
    Suit.HEARTS: Image.open(ASSETS_FOLDER / "hearts_decorations.png").convert('RGBA'),
}

SUIT_DECORATIONS_SPECIAL = {
    Suit.SPADES: Image.open(ASSETS_FOLDER / "spades_decorations_special.png").convert('RGBA'),
    Suit.DIAMONDS: Image.open(ASSETS_FOLDER / "diamonds_decorations_special.png").convert('RGBA'),
    Suit.CLUBS: Image.open(ASSETS_FOLDER / "clubs_decorations_special.png").convert('RGBA'),
    Suit.HEARTS: Image.open(ASSETS_FOLDER / "hearts_decorations_special.png").convert('RGBA'),
    Suit.JOKER: Image.open(ASSETS_FOLDER / "joker_decorations_special.png").convert('RGBA'),
}

SUIT_ICONS = {
    Suit.SPADES: Image.open(ASSETS_FOLDER / "spades_icon.png").convert('RGBA'),
    Suit.DIAMONDS: Image.open(ASSETS_FOLDER / "diamonds_icon.png").convert('RGBA'),
    Suit.CLUBS: Image.open(ASSETS_FOLDER / "clubs_icon.png").convert('RGBA'),
    Suit.HEARTS: Image.open(ASSETS_FOLDER / "hearts_icon.png").convert('RGBA'),
}

ABILITY_ICONS = {
    Ability.MILITIA: Image.open(ASSETS_FOLDER / "militia_symbol.png").convert('RGBA'),
    Ability.SEER: Image.open(ASSETS_FOLDER / "seer_symbol.png").convert('RGBA'),
    Ability.HERO: Image.open(ASSETS_FOLDER / "hero_symbol.png").convert('RGBA'),
    Ability.SPY: Image.open(ASSETS_FOLDER / "spy_symbol.png").convert('RGBA'),
    Ability.CHARGER: Image.open(ASSETS_FOLDER / "charger_symbol.png").convert('RGBA'),
    Ability.WIZARD: Image.open(ASSETS_FOLDER / "wizard_symbol.png").convert('RGBA'),
    Ability.COMMANDER: Image.open(ASSETS_FOLDER / "commander_symbol.png").convert('RGBA'),
    Ability.UNIQUE: Image.open(ASSETS_FOLDER / "unique_symbol.png").convert('RGBA'),
    Ability.IMMUNE: Image.open(ASSETS_FOLDER / "immune_symbol.png").convert('RGBA'),
}

EFFECT_ICONS = {
    Effect.WEATHER: Image.open(ASSETS_FOLDER / "weather_symbol.png").convert('RGBA'),
    Effect.SCORCH: Image.open(ASSETS_FOLDER / "scorch_symbol.png").convert('RGBA'),
    Effect.EMPOWER: Image.open(ASSETS_FOLDER / "empower_symbol.png").convert('RGBA'),
}

# ===================== Card data =====================
cards = [
    Unit("spades_3.png", Suit.SPADES, 3, Ability.SPY),
    Unit("spades_4.png", Suit.SPADES, 4, Ability.MILITIA),
    Unit("spades_5.png", Suit.SPADES, 5, None),
    Unit("spades_6.png", Suit.SPADES, 6, None),
    Unit("spades_7.png", Suit.SPADES, 7, Ability.CHARGER),
    Unit("spades_8.png", Suit.SPADES, 8, Ability.WIZARD),
    Unit("spades_9.png", Suit.SPADES, 9, None),
    Unit("spades_10.png", Suit.SPADES, 10, None),
    Unit("spades_jack.png", Suit.SPADES, 10, Ability.COMMANDER),
    Unit("spades_queen.png", Suit.SPADES, 10, Ability.UNIQUE),
    Unit("spades_king.png", Suit.SPADES, 10, Ability.IMMUNE),
    Unit("diamonds_3.png", Suit.DIAMONDS, 3, Ability.SPY),
    Unit("diamonds_4.png", Suit.DIAMONDS, 4, Ability.MILITIA),
    Unit("diamonds_5.png", Suit.DIAMONDS, 5, None),
    Unit("diamonds_6.png", Suit.DIAMONDS, 6, None),
    Unit("diamonds_7.png", Suit.DIAMONDS, 7, Ability.CHARGER),
    Unit("diamonds_8.png", Suit.DIAMONDS, 8, Ability.WIZARD),
    Unit("diamonds_9.png", Suit.DIAMONDS, 9, None),
    Unit("diamonds_10.png", Suit.DIAMONDS, 10, None),
    Unit("diamonds_jack.png", Suit.DIAMONDS, 10, Ability.COMMANDER),
    Unit("diamonds_queen.png", Suit.DIAMONDS, 10, Ability.UNIQUE),
    Unit("diamonds_king.png", Suit.DIAMONDS, 10, Ability.IMMUNE),
    Unit("clubs_3.png", Suit.CLUBS, 3, Ability.SPY),
    Unit("clubs_4.png", Suit.CLUBS, 4, Ability.MILITIA),
    Unit("clubs_5.png", Suit.CLUBS, 5, None),
    Unit("clubs_6.png", Suit.CLUBS, 6, None),
    Unit("clubs_7.png", Suit.CLUBS, 7, Ability.CHARGER),
    Unit("clubs_8.png", Suit.CLUBS, 8, Ability.WIZARD),
    Unit("clubs_9.png", Suit.CLUBS, 9, None),
    Unit("clubs_10.png", Suit.CLUBS, 10, None),
    Unit("clubs_jack.png", Suit.CLUBS, 10, Ability.COMMANDER),
    Unit("clubs_queen.png", Suit.CLUBS, 10, Ability.UNIQUE),
    Unit("clubs_king.png", Suit.CLUBS, 10, Ability.IMMUNE),
    Unit("hearts_3.png", Suit.HEARTS, 3, Ability.SPY),
    Unit("hearts_4.png", Suit.HEARTS, 4, Ability.MILITIA),
    Unit("hearts_5.png", Suit.HEARTS, 5, Ability.HERO),
    Unit("hearts_6.png", Suit.HEARTS, 6, None),
    Unit("hearts_7.png", Suit.HEARTS, 7, Ability.CHARGER),
    Unit("hearts_8.png", Suit.HEARTS, 8, Ability.WIZARD),
    Unit("hearts_9.png", Suit.HEARTS, 9, None),
    Unit("hearts_10.png", Suit.HEARTS, 10, None),
    Unit("hearts_jack.png", Suit.HEARTS, 10, Ability.COMMANDER),
    Unit("hearts_queen.png", Suit.HEARTS, 10, Ability.UNIQUE),
    Unit("hearts_king.png", Suit.HEARTS, 10, Ability.IMMUNE),
    Special("spades_scorch.png", Suit.SPADES, Effect.SCORCH),
    Special("diamonds_scorch.png", Suit.DIAMONDS, Effect.SCORCH),
    Special("clubs_scorch.png", Suit.CLUBS, Effect.SCORCH),
    Special("hearts_scorch.png", Suit.HEARTS, Effect.SCORCH),
    Special("spades_weather.png", Suit.SPADES, Effect.WEATHER),
    Special("diamonds_weather.png", Suit.DIAMONDS, Effect.WEATHER),
    Special("clubs_weather.png", Suit.CLUBS, Effect.WEATHER),
    Special("hearts_weather.png", Suit.HEARTS, Effect.WEATHER),
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
