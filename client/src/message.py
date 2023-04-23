"""
Communication between server and client is done through messages.
Each message is a json object and the 'type' field indicates which kind of message it is.
"""

import json

from action import Action


class MessageType:
    """
    This class has constants for each message type.
    """
    COMMUNICATION_ERROR = 'communication-error'
    REQUEST_GAME_STATE = 'request-game-state'
    GAME_STATE = 'game-state'
    RESTART_GAME = 'restart-game'
    ACTION = 'action'
    INVALID_ACTION = 'invalid-action'


class Messenger:
    """
    The Messenger is a mediator for communication over a socket.
    It handles the serialization and some deserialization of relevant data and provides methods
    for sending all relevant messages.
    Must be closed.
    """

    def __init__(self, socket):
        self.reader = socket.makefile('r')
        self.writer = socket.makefile('w')

    def close(self):
        self.reader.close()
        self.writer.close()

    def receive(self):
        """
        Receive the next message. This is blocking.
        """
        msg_raw = self.reader.readline()
        return json.loads(msg_raw)

    def _send(self, json_str: str):
        self.writer.writelines([json_str, '\n'])
        self.writer.flush()

    def sendAction(self, action: Action):
        action_msg = {'type': 'action', 'action': action.to_dict()}
        self._send(json.dumps(action_msg))

    def sendRequestGameState(self):
        self._send(json.dumps({'type': MessageType.REQUEST_GAME_STATE}))
