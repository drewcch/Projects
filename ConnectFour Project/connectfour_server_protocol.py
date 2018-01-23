#Andrew Chien

from collections import namedtuple
import socket

connectfourConnection = namedtuple(
    'connectfourConnection',
    ['socket', 'input', 'output'])



_SHOW_DEBUG_TRACE = False


class ConnectfourProtocolError(Exception):
    pass





def connectfourserver(connectfour_host: str, connectfour_port: int) -> connectfourConnection: 
    '''Connects to the connectfour server, which is assumed to be running on the
    given host and listening on the given port.  If successful, a
    connection object is returned; if unsuccessful, an exception is
    raised'''
    connectfour_socket = socket.socket()
    
 
    connectfour_socket.connect((connectfour_host, connectfour_port))

    connectfour_socket_input = connectfour_socket.makefile('r')
    connectfour_socket_output = connectfour_socket.makefile('w')


    return connectfourConnection(
        socket = connectfour_socket,
        input = connectfour_socket_input,
        output = connectfour_socket_output)




def welcome(connection: connectfourConnection, username: str) -> str:
    '''
    Logs a user into connectfour server over a previously-made connection and
    returns the server's message.
    If the attempt to send text to the connectfour server or receive a response
    fails (or if the server sends back a response that does not conform to
    the connectfour protocol), an exception is raised.
    '''
    
    _write_line(connection, 'I32CFSP_HELLO ' + username)

    response = _read_line(connection)

    if response.startswith('WELCOME'):
        return response
    
    else:
        raise ConnectfourProtocolError()

    


def send_and_receive(connection: connectfourConnection, line: str) -> str:
    '''Handles the process of sending and receiving messages between the
    client and the AI and returns the received message by the AI.
    If the message sent to the server is blank, the AI will still return
    its message received.'''
    if line != '':
        _write_line(connection, line)

    received = _read_line(connection)
    
    return received




def close(connection: connectfourConnection) -> None:
    '''Closes a connection'''

    connection.input.close()
    connection.output.close()
    connection.socket.close()



def _read_line(connection: connectfourConnection) -> str:
    '''
    Reads a line of text sent from the server and returns it without
    a newline on the end of it
    '''

    line = connection.input.readline()[:-1]

    if _SHOW_DEBUG_TRACE:
        print('RCVD: ' + line)

    return line



def _expect_line(connection: connectfourConnection, expected: str) -> None:
    '''
    Reads a line of text sent from the server, expecting it to contain
    a particular text.  If the line of text received is different, this
    function raises an exception; otherwise, the function has no effect.
    '''

    line = _read_line(connection)

    if line != expected:
        raise ConnectfourProtocolError()



def _write_line(connection: connectfourConnection, line: str) -> None:
    '''
    Writes a line of text to the server, including the appropriate
    newline sequence, and ensures that it is sent immediately.
    '''
    connection.output.write(line + '\r\n')
    connection.output.flush()

    if _SHOW_DEBUG_TRACE:
        print('SENT: ' + line)

           

        
            
