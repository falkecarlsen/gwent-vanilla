import json
import socket

from action import try_parse_action
from game import Game, Card


def pretty_print_game(game: Game, pov: int):
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

    opponent = 1 - pov

    def letter_to_suit_icon(letter: str) -> str:
        return {'S': '♠', 'D': '♦', 'C': '♣', 'H': '♥'}[letter]

    def pretty_unit(unit: Card) -> str:
        base = letter_to_suit_icon(unit.name[0]) + unit.name[1:]
        # TODO: if unit.current_power != unit.base_power: add (XX) with current power
        return base

    def pretty_weather(g: Game) -> str:
        return '---'

    print('/////////////////////////////////////////////////')
    # Print opponent
    print(game.players[opponent].name, '<<' if game.current_player == opponent else '')
    print(f'Hand: {len(game.players[opponent].hand)} cards')
    if game.players[opponent].rounds_won > game.players[pov].rounds_won:
        print('*')
    else:
        print('')

    # Print player boards
    print(f'      ♠ ({game.players[opponent].board.spades.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[opponent].board.spades.units))
    print(f'({game.players[opponent].board.current_power:>2})  ♣ ({game.players[opponent].board.clubs.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[opponent].board.clubs.units))
    print(f'      ♦ ({game.players[opponent].board.diamonds.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[opponent].board.diamonds.units))
    print(f'Weather: {pretty_weather(game)} +------------------------ Round: {game.round}')
    print(f'      ♦ ({game.players[pov].board.diamonds.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[pov].board.diamonds.units))
    print(f'({game.players[pov].board.current_power:>2})  ♣ ({game.players[pov].board.clubs.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[pov].board.clubs.units))
    print(f'      ♠ ({game.players[pov].board.spades.current_power:>2}) | ' + ' '.join(pretty_unit(u) for u in game.players[pov].board.spades.units))

    # Print local player
    if game.players[opponent].rounds_won < game.players[pov].rounds_won:
        print('*')
    else:
        print('')
    print(game.players[pov].name, '<<' if game.current_player == pov else '')
    print(f'Hand: ' + ' '.join(pretty_unit(u) for u in game.players[pov].hand))
    print('/////////////////////////////////////////////////')


class Message:
    """
    Communication between server and client is done through messages.
    Each message is a json object and the 'type' field indicates which kind of message it is.
    """
    COMMUNICATION_ERROR = 'communication-error'
    GET_GAME_STATE = 'get-game-state'
    GAME_STATE = 'game-state'
    RESTART_GAME = 'restart-game'
    ACTION = 'action'
    INVALID_ACTION = 'invalid-action'


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
                        pretty_print_game(game, self.player_index)
                        if game.round < 3:
                            if game.current_player == self.player_index:
                                self.prompt_for_action()
                            else:
                                print('Waiting for opponents move...')
                        else:
                            print('Game over')
                            return
                elif js['type'] == Message.INVALID_ACTION:
                    # The player's action was invalid
                    # Print why and then ensure we have the correct game state
                    print(f'Invalid action: ' + js['details'])
                    self.writer.writelines([json.dumps({'type': Message.GET_GAME_STATE}), '\n'])
                    self.writer.flush()
                elif js['type'] == Message.COMMUNICATION_ERROR:
                    print('Communication error: ' + js['details'])
                    if game is not None:
                        if game.current_player == self.player_index:
                            self.prompt_for_action()
                        else:
                            print('Waiting for your opponent\'s move...')
                else:
                    print(f'ERROR: Unhandled message of type \'{js["type"]}\'')

    def prompt_for_action(self):
        while True:
            action_raw = input('Your move: ')
            try:
                action = try_parse_action(self.player_index, action_raw)
                action_msg = {'type': 'action', 'action': action.to_dict()}
                action_json = json.dumps(action_msg)
                self.writer.writelines([action_json, '\n'])
                self.writer.flush()
                return
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
    except ConnectionResetError:
        print('Connection lost')
    finally:
        client.close()
