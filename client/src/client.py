
if __name__ == '__main__':
    print("Client starting up")

    HOST = "127.0.0.1"  # The server's hostname or IP address
    PORT = 8080  # The port used by the server

    import socket
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))
        print("Connection established")
        s.sendall("Hi there\n".encode())
        data = s.recv(1024).decode()
        print(f"Server response: {data}")
        s.sendall("close\n".encode())
