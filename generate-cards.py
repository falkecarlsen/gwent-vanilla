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


@dataclass
class Card:
    suit: Suit
    power: int
    ability: Optional[Ability]
    file_name: str


# ===================== Constants =====================
WIDTH = 24
HEIGHT = 32
SIZE = (WIDTH, HEIGHT)

FOLDER = Path(__file__).absolute().parent
CARDS_FOLDER = FOLDER / "src" / "public" / "cards"
ASSETS_FOLDER = FOLDER / "assets"

BASE_IMG_PATH = ASSETS_FOLDER / "base.png"

SUIT_DECORATIONS = {
    Suit.MELEE: Image.open(ASSETS_FOLDER / "melee_decorations.png"),
    Suit.RANGED: Image.open(ASSETS_FOLDER / "ranged_decorations.png"),
    Suit.CAVALRY: Image.open(ASSETS_FOLDER / "cavalry_decorations.png"),
    Suit.WILD: Image.open(ASSETS_FOLDER / "wild_decorations.png"),
}

SUIT_ICONS = {
    Suit.MELEE: Image.open(ASSETS_FOLDER / "melee_icon.png"),
    Suit.RANGED: Image.open(ASSETS_FOLDER / "ranged_icon.png"),
    Suit.CAVALRY: Image.open(ASSETS_FOLDER / "cavalry_icon.png"),
    Suit.WILD: Image.open(ASSETS_FOLDER / "wild_icon.png"),
}

ABILITY_ICONS = {
    Ability.HORDE: Image.open(ASSETS_FOLDER / "horde_symbol.png"),
    Ability.SEER: Image.open(ASSETS_FOLDER / "seer_symbol.png"),
    Ability.UNIQUE: Image.open(ASSETS_FOLDER / "unique_symbol.png"),
    Ability.WIZARD: Image.open(ASSETS_FOLDER / "wizard_symbol.png"),
    Ability.SPY: Image.open(ASSETS_FOLDER / "spy_symbol.png"),
    Ability.COMMANDER: Image.open(ASSETS_FOLDER / "commander_symbol.png"),
    Ability.IMMUNE: Image.open(ASSETS_FOLDER / "immune_symbol.png"),
}

# ===================== Card data =====================
cards = [
    Card(Suit.MELEE, 3, Ability.HORDE, "melee_3.png"),
    Card(Suit.MELEE, 4, Ability.SEER, "melee_4.png"),
    Card(Suit.MELEE, 5, None, "melee_5.png"),
    Card(Suit.MELEE, 6, None, "melee_6.png"),
    Card(Suit.MELEE, 7, None, "melee_7.png"),
    Card(Suit.MELEE, 8, None, "melee_8.png"),
    Card(Suit.MELEE, 9, None, "melee_9.png"),
    Card(Suit.MELEE, 10, Ability.SPY, "melee_10.png"),
    Card(Suit.MELEE, 10, Ability.COMMANDER, "melee_jack.png"),
    Card(Suit.MELEE, 10, Ability.UNIQUE, "melee_queen.png"),
    Card(Suit.MELEE, 10, Ability.IMMUNE, "melee_king.png"),
    Card(Suit.RANGED, 3, Ability.HORDE, "ranged_3.png"),
    Card(Suit.RANGED, 4, Ability.SEER, "ranged_4.png"),
    Card(Suit.RANGED, 5, None, "ranged_5.png"),
    Card(Suit.RANGED, 6, None, "ranged_6.png"),
    Card(Suit.RANGED, 7, None, "ranged_7.png"),
    Card(Suit.RANGED, 8, None, "ranged_8.png"),
    Card(Suit.RANGED, 9, None, "ranged_9.png"),
    Card(Suit.RANGED, 10, Ability.SPY, "ranged_10.png"),
    Card(Suit.RANGED, 10, Ability.COMMANDER, "ranged_jack.png"),
    Card(Suit.RANGED, 10, Ability.UNIQUE, "ranged_queen.png"),
    Card(Suit.RANGED, 10, Ability.IMMUNE, "ranged_king.png"),
    Card(Suit.CAVALRY, 3, Ability.HORDE, "cavalry_3.png"),
    Card(Suit.CAVALRY, 4, Ability.SEER, "cavalry_4.png"),
    Card(Suit.CAVALRY, 5, None, "cavalry_5.png"),
    Card(Suit.CAVALRY, 6, None, "cavalry_6.png"),
    Card(Suit.CAVALRY, 7, None, "cavalry_7.png"),
    Card(Suit.CAVALRY, 8, None, "cavalry_8.png"),
    Card(Suit.CAVALRY, 9, None, "cavalry_9.png"),
    Card(Suit.CAVALRY, 10, Ability.SPY, "cavalry_10.png"),
    Card(Suit.CAVALRY, 10, Ability.COMMANDER, "cavalry_jack.png"),
    Card(Suit.CAVALRY, 10, Ability.UNIQUE, "cavalry_queen.png"),
    Card(Suit.CAVALRY, 10, Ability.IMMUNE, "cavalry_king.png"),
    Card(Suit.WILD, 3, Ability.HORDE, "wild_3.png"),
    Card(Suit.WILD, 4, Ability.SEER, "wild_4.png"),
    Card(Suit.WILD, 5, Ability.WIZARD, "wild_5.png"),
    Card(Suit.WILD, 6, None, "wild_6.png"),
    Card(Suit.WILD, 7, None, "wild_7.png"),
    Card(Suit.WILD, 8, None, "wild_8.png"),
    Card(Suit.WILD, 9, None, "wild_9.png"),
    Card(Suit.WILD, 10, Ability.SPY, "wild_10.png"),
    Card(Suit.WILD, 10, Ability.COMMANDER, "wild_jack.png"),
    Card(Suit.WILD, 10, Ability.UNIQUE, "wild_queen.png"),
    Card(Suit.WILD, 10, Ability.IMMUNE, "wild_king.png"),
]

# ===================== Image generation =====================
for card in cards:
    #img = Image.new("RGBA", SIZE, (0, 0, 0, 0))
    img = Image.open(BASE_IMG_PATH)
    draw = ImageDraw.Draw(img)

    suit_decoration, suit_icon = SUIT_DECORATIONS[card.suit], SUIT_ICONS[card.suit]
    img.paste(suit_decoration, (0, 0), suit_decoration)
    img.paste(suit_icon, (0, 0), suit_icon)

    if card.ability is not None:
        symbol_pos = (39, 8)
        ability_symbol = ABILITY_ICONS[card.ability]
        img.paste(ability_symbol, symbol_pos, ability_symbol)

    img.save(CARDS_FOLDER / card.file_name, "PNG")

print(f"Generated {len(cards)} cards in {CARDS_FOLDER}")
