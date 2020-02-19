from enum import Enum
from dataclasses import dataclass

from pathlib import Path
from PIL import Image, ImageDraw


# ===================== Classes =====================
class Suit(Enum):
    MELEE = 0
    RANGED = 1
    CAVALRY = 2
    WILD = 4
    JOKER = 5


@dataclass
class Card:
    suit: Suit
    power: int
    file_name: str


# ===================== Constants =====================
WIDTH = 24
HEIGHT = 32
SIZE = (WIDTH, HEIGHT)

FOLDER = Path(__file__).absolute().parent
CARDS_FOLDER = FOLDER / "src" / "public" / "cards"
ASSETS_FOLDER = FOLDER / "assets"

BASE_IMGS = {
    Suit.MELEE: Image.open(ASSETS_FOLDER / "base_melee.png"),
    Suit.RANGED: Image.open(ASSETS_FOLDER / "base_ranged.png"),
    Suit.CAVALRY: Image.open(ASSETS_FOLDER / "base_cavalry.png"),
}

SUIT_ICONS = {
    Suit.MELEE: Image.open(ASSETS_FOLDER / "icon_suit_melee.png"),
    Suit.RANGED: Image.open(ASSETS_FOLDER / "icon_suit_ranged.png"),
    Suit.CAVALRY: Image.open(ASSETS_FOLDER / "icon_suit_cavalry.png"),
}

# ===================== Card data =====================
cards = [
    Card(Suit.MELEE, 8, "melee 8.png"),
    Card(Suit.MELEE, 4, "melee 4.png"),
    Card(Suit.RANGED, 5, "ranged 5.png"),
    Card(Suit.CAVALRY, 6, "cavalry 6.png"),
]

# ===================== Image generation =====================
for card in cards:
    img = Image.new("RGBA", SIZE, (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    suit_base, suit_icon = BASE_IMGS[card.suit], SUIT_ICONS[card.suit]
    img.paste(suit_base, (0, 0), suit_base)
    img.paste(suit_icon, (16, 2), suit_icon)

    img.save(CARDS_FOLDER / card.file_name, "PNG")
