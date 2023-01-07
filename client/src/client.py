import json
import socket

from action import try_parse_action
from game import Game, Card


def pretty_print_game(game: Game):
    """
    Example:
    /////////////////////////////////////////////////
    Bob
    Hand: 4 cards

          ♠ ( 4) | ♠Q(2) ♠9(2)
    (35)  ♣ (16) | ♣6 ♣K
          ♦ (15) | ♦4 ♦3 ♦8
    Weather:   ♠ +------------------------ Round: 2
          ♦ (10) | ♦K(10)
    (23)  ♣ (11) | ♦5 ♣7(9) ♣10
          ♠ ( 2) | ♠J(2)
    *
    Alice <<
    Hand: ♠3 ♦7 ♦8 ♥Q ♥6
    /////////////////////////////////////////////////
    """

    def letter_to_suit_icon(letter: str) -> str:
        return {'S': '♠', 'D': '♦', 'C': '♣', 'H': '♥'}[letter]

    def pretty_unit(unit: Card) -> str:
        base = letter_to_suit_icon(unit.name[0]) + unit.name[1:]
        # TODO: if unit.current_power != unit.base_power: add (XX) with current power
        return base

    def pretty_weather(g: Game) -> str:
        return '---'

    print('/////////////////////////////////////////////////')
    # Print enemy. Assume enemy is index 1
    print(game.players[1].name, '<<' if game.current_player == 1 else '')
    print(f'Hand: {len(game.players[1].hand)} cards')
    if game.players[1].rounds_won > game.players[0].rounds_won:
        print('*')
    else:
        print('')

    # Print player boards
    print(f'      ♠ ({game.players[1].board.spades.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[1].board.spades.units))
    print(f'({game.players[1].board.current_power:>2})  ♣ ({game.players[1].board.clubs.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[1].board.clubs.units))
    print(f'      ♦ ({game.players[1].board.diamonds.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[1].board.diamonds.units))
    print(f'Weather: {pretty_weather(game)} +------------------------ Round: {game.round}')
    print(f'      ♦ ({game.players[0].board.diamonds.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[0].board.diamonds.units))
    print(f'({game.players[1].board.current_power:>2})  ♣ ({game.players[0].board.clubs.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[0].board.clubs.units))
    print(f'      ♠ ({game.players[0].board.spades.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[0].board.spades.units))

    # Print local player. Assume local player is index 0
    if game.players[1].rounds_won < game.players[0].rounds_won:
        print('*')
    else:
        print('')
    print(game.players[0].name, '<<' if game.current_player == 0 else '')
    print(f'Hand: ' + ' '.join(pretty_unit(u) for u in game.players[0].hand))
    print('/////////////////////////////////////////////////')


class Message:
    """
    Communication between server and client is done through messages.
    Each message is a json object and the 'type' field indicates which kind of message it is.
    """
    GET_GAME_STATE = 'get-game-state'
    GAME_STATE = 'game-state'
    RESTART_GAME = 'restart-game'


class GwentClient:

    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.reader = self.socket.makefile('r')
        self.writer = self.socket.makefile('w')

        self.player_index = -1

    def run(self):
        """
        The main loop of the Gwent client.
        """
        # Manually determine player index
        while self.player_index not in [0, 1]:
            try:
                self.player_index = int(input('Your player index (0/1): '))
            except ValueError:
                pass

        print("Client starting up")
        with self.socket:
            self.socket.connect((self.host, self.port))
            print('Connection established')
            game = None
            while True:
                msg_raw = self.reader.readline()
                js = json.loads(msg_raw)

                # Branch based on message type
                if js['type'] == Message.GAME_STATE:
                    if js['game'] is None:
                        game = None
                    else:
                        game = Game.from_json_dict(js['game'])
                        pretty_print_game(game)
                        if game.current_player == self.player_index:
                            self.prompt_for_action()
                else:
                    print(f'ERROR: Unhandled message of type \'{js["type"]}\'')

    def prompt_for_action(self):
        while True:
            action_raw = input('Your move: ')
            try:
                action = try_parse_action(self.player_index, action_raw)
                action_json = json.dumps(action.to_dict())
                self.writer.writelines([action_json, '\n'])
                return   # TODO Consider what happens if server does not like the given action
            except ValueError as err:
                print(f'Error: {err}')
                pass

    def close(self):
        self.reader.close()
        self.writer.close()
        self.socket.close()


if __name__ == '__main__':
    HOST = input('Host IP [default: 127.0.0.1]: ') or '127.0.0.1'
    PORT = int(input('Port [default: 8080]: ') or 8080)

    client = GwentClient(HOST, PORT)
    try:
        client.run()
    finally:
        client.close()
