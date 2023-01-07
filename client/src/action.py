from dataclasses import dataclass
from typing import Dict


class Action:
    def to_dict(self) -> Dict:
        """
        Returns a json-like dict of the action intended for serialization.
        Notably, the dict includes the type attribute which distinguishes action subtypes.
        """
        raise NotImplementedError


@dataclass
class PlayCard(Action):
    player: int
    card_name: str

    def to_dict(self) -> Dict:
        return dict(self.__dict__, type='play-card')


@dataclass
class Pass(Action):
    player: int

    def to_dict(self) -> Dict:
        return dict(self.__dict__, type='pass')


def try_parse_action(player: int, txt: str) -> Action:
    """
    Attempts to parse a string of text as an action.
    Raises a `ValueError` if unsuccessful.
    """
    pieces = txt.strip().split()
    if len(pieces) <= 0:
        raise ValueError('Empty action string')

    if pieces[0] == 'play' and len(pieces) == 2:
        # Server will verify if this card name make sense, so we do not need to check
        card = pieces[1]
        return PlayCard(player, card)

    if pieces[0] == 'pass':
        return Pass(player)

    raise ValueError(f'Unknown action: {txt}')
