import socket

from action import try_parse_action
from game import Game, Card
from message import MessageType, Messenger


def pretty_print_game(game: Game, pov: int):
    """
    Example:
    /////////////////////////////////////////////////
    Bob
    Hand: 4 cards

          ♠ ( 4) │ ♠Q(2) ♠9(2)
    (35)  ♣ (16) │ ♣6 ♣K
          ♦ (15) │ ♦4 ♦3 ♦8
    Weather:   ♠ ├───────────────────────── Round: 2
          ♦ (10) │ ♦K(10)
    (23)  ♣ (11) │ ♦5 ♣7(9) ♣10
          ♠ ( 2) │ ♠J(2)
    *
    Alice <<
    Hand: ♠3 ♦7 ♦8 ♥Q ♥6
    /////////////////////////////////////////////////
    """

    opponent = 1 - pov

    def letter_to_suit_icon(letter: str) -> str:
        return {'S': '♠', 'D': '♦', 'C': '♣', 'H': '♥'}[letter]

    def pretty_unit(unit: Card, hide_power: bool=False) -> str:
        base = letter_to_suit_icon(unit.name[0]) + unit.name[1:]
        if not hide_power and unit.numeric != unit.current_power:
            base += f'({unit.current_power})'
        return base

    def pretty_weather(g: Game) -> str:
        return '---'

    print('/////////////////////////////////////////////////')
    # Print opponent
    print(game.players[opponent].name, '<<' if game.current_player == opponent else '')
    print(f'Hand: {len(game.players[opponent].hand)} cards')
    print('*' * game.players[opponent].rounds_won)

    # Print player boards
    print(f'      ♠ ({game.players[opponent].board.spades.current_power:>2}) │ ' + ' '.join(pretty_unit(u) for u in game.players[opponent].board.spades.units))
    print(f'({game.players[opponent].board.current_power:>2})  ♣ ({game.players[opponent].board.clubs.current_power:>2}) │ ' + ' '.join(pretty_unit(u) for u in game.players[opponent].board.clubs.units))
    print(f'      ♦ ({game.players[opponent].board.diamonds.current_power:>2}) │ ' + ' '.join(pretty_unit(u) for u in game.players[opponent].board.diamonds.units))
    print(f'Weather: {pretty_weather(game)} ├───────────────────────── Round: {game.round}')
    print(f'      ♦ ({game.players[pov].board.diamonds.current_power:>2}) │ ' + ' '.join(pretty_unit(u) for u in game.players[pov].board.diamonds.units))
    print(f'({game.players[pov].board.current_power:>2})  ♣ ({game.players[pov].board.clubs.current_power:>2}) │ ' + ' '.join(pretty_unit(u) for u in game.players[pov].board.clubs.units))
    print(f'      ♠ ({game.players[pov].board.spades.current_power:>2}) │ ' + ' '.join(pretty_unit(u) for u in game.players[pov].board.spades.units))

    # Print local player
    print('*' * game.players[pov].rounds_won)
    print(game.players[pov].name, '<<' if game.current_player == pov else '')
    print(f'Hand: ' + ' '.join(pretty_unit(u, hide_power=True) for u in game.players[pov].hand))
    print('/////////////////////////////////////////////////')


class GwentClient:

    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.messenger = Messenger(self.socket)

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
                msg = self.messenger.receive()

                # Branch based on message type
                if msg['type'] == MessageType.GAME_STATE:
                    if msg['game'] is None:
                        game = None
                    else:
                        game = Game.from_json_dict(msg['game'])
                        pretty_print_game(game, self.player_index)
                        if game.round < 3:
                            if game.current_player == self.player_index:
                                self.prompt_for_action()
                            else:
                                print('Waiting for opponents move...')
                        else:
                            winner = 0 if game.players[0].rounds_won > game.players[1].rounds_won else 1   # TODO Ties
                            print(f'Game over, {game.players[winner].name} wins!')
                            return
                elif msg['type'] == MessageType.INVALID_ACTION:
                    # The player's action was invalid
                    # Print why and then ensure we have the correct game state
                    print(f'Invalid action: ' + msg['details'])
                    self.messenger.sendRequestGameState()
                elif msg['type'] == MessageType.COMMUNICATION_ERROR:
                    print('Communication error: ' + msg['details'])
                    if game is not None:
                        if game.current_player == self.player_index:
                            self.prompt_for_action()
                        else:
                            print('Waiting for your opponent\'s move...')
                else:
                    print(f'ERROR: Unhandled message of type \'{msg["type"]}\'')

    def prompt_for_action(self):
        while True:
            try:
                action_raw = input('Your move: ')
                action = try_parse_action(self.player_index, action_raw)
                self.messenger.sendAction(action)
                return
            except ValueError as err:
                print(f'Error: {err}')
                pass

    def close(self):
        self.messenger.close()
        self.socket.close()


if __name__ == '__main__':
    LOCAL_HOST = '127.0.0.1'
    DEFAULT_PORT = 8080
    host = input(f'Host IP [default: {LOCAL_HOST}]: ') or LOCAL_HOST
    port = int(input(f'Port [default: {DEFAULT_PORT}]: ') or DEFAULT_PORT)

    try:
        client = GwentClient(host, port)
        client.run()
    except ConnectionRefusedError:
        print('Connection could not be established because the destination did not respond or refused')
    except ConnectionResetError:
        print('Connection lost')
    finally:
        client.close()
