import json

from data import Game, Card


def pretty_print_game(game: Game):
    """
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




if __name__ == '__main__':
    print("Client starting up")

    HOST = "127.0.0.1"  # The server's hostname or IP address
    PORT = 8080  # The port used by the server

    import socket
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))
        print("Connection established")
        with s.makefile('r') as incoming:
            with s.makefile('w') as outgoing:

                # Server immediately sends us correct game state
                response = incoming.readline().strip()
                game = Game.from_json_dict(json.loads(response))

                pretty_print_game(game)

                outgoing.write("Hi there\n")
                outgoing.flush()

                response = incoming.readline().strip()
                print(f"Server response: {response}")
