from dataclasses import dataclass
from typing import List


@dataclass
class Game:
    deck: List['Card']
    players: List['Player']
    current_player: int
    round: int

    @staticmethod
    def from_json_dict(json: dict) -> 'Game':
        return Game(
            deck=[Card.from_json_dict(j) for j in json['deck']],
            players=[Player.from_json_dict(j) for j in json['players']],
            current_player=json['current_player'],
            round=json['round'],
        )


@dataclass
class Card:
    name: str
    base_power: int
    current_power: int

    @staticmethod
    def from_json_dict(json: dict) -> 'Card':
        def base_power_from_name(name: str) -> int:
            if name[1] == 'K':
                return 10
            if name[1] == 'Q':
                return 10
            if name[1] == 'J':
                return 0
            return int(name[1:])
        return Card(
            name=json['name'],
            base_power=base_power_from_name(json['name']),
            current_power=json['current_power'],
        )


@dataclass
class Player:
    index: int
    name: str
    rounds_won: int
    hand: List[Card]
    board: 'PlayerBoard'
    has_passed: bool

    @staticmethod
    def from_json_dict(json: dict) -> 'Player':
        return Player(
            index=json['index'],
            name=json['name'],
            rounds_won=json['rounds_won'],
            hand=[Card.from_json_dict(j) for j in json['hand']],
            board=PlayerBoard.from_json_dict(json['board']),
            has_passed=json['has_passed'],
        )


@dataclass
class PlayerBoard:
    current_power: int
    spades: 'Row'
    clubs: 'Row'
    diamonds: 'Row'

    def __getitem__(self, suit: str) -> 'Row':
        if suit == 'spades':
            return self.spades
        if suit == 'clubs':
            return self.clubs
        if suit == 'diamonds':
            return self.diamonds
        raise ValueError

    @staticmethod
    def from_json_dict(json: dict) -> 'PlayerBoard':
        return PlayerBoard(
            current_power=json['current_power'],
            spades=Row.from_json_dict(json['spades']),
            clubs=Row.from_json_dict(json['clubs']),
            diamonds=Row.from_json_dict(json['diamonds']),
        )


@dataclass
class Row:
    current_power: int
    units: List['Card']

    @staticmethod
    def from_json_dict(json: dict) -> 'Row':
        return Row(
            current_power=json['current_power'],
            units=[Card.from_json_dict(j) for j in json['units']],
        )
