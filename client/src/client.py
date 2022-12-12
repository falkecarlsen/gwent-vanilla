
if __name__ == '__main__':
    print("Client starting up")

    HOST = "127.0.0.1"  # The server's hostname or IP address
    PORT = 8080  # The port used by the server

    # import socket
    # with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    #     s.connect((HOST, PORT))
    #     print("Connection established")
    #     s.sendall("GET /gwent HTTP/1.0\r\nContent-Type: text\r\nContent-Length: 1024\r\nAccept-Language: en-us\r\nConnection: Keep-Alive\r\n\r\nTest\r\n".encode())
    #     data = s.recv(1024).decode()
    #     print(f"Server response: {data}")

    # import http.client
    # connection = http.client.HTTPConnection(HOST, PORT, timeout=15)
    # connection.request("GET", "/gwent", body="{\"msg\": \"hi\"}", headers={'Content-type': 'application/json'})
    # response = connection.getresponse()
    # print("Status: {} and reason: {}".format(response.status, response.reason))

    import requests
    with requests.Session() as session:
        response = session.get(f"http://{HOST}:{PORT}/gwent", data="hi")
        print("Status: {} and reason: {}".format(response.status_code, response.reason))
