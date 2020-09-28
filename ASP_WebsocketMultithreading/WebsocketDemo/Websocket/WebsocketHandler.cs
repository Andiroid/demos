using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.WebSockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace WebsocketDemo.Websocket
{

    public class WebsocketHandler
    {
        public List<SocketConnection> websocketConnections = new List<SocketConnection>();

        public WebsocketHandler()
        {
            // TODO CleanUpTask();
        }


        public async Task Handle(Guid id,WebSocket webSocket, String queryId)
        {
            // prevent race condition -> SendMessageToSockets()
            lock (websocketConnections) { 
                websocketConnections.Add(new SocketConnection { 
                    Id = id,
                    WebSocket = webSocket
                });
            }

            await SendMessageToSockets($"<span class='join'><b>{queryId}</b> has joined the chat</span>");

            while (webSocket.State == WebSocketState.Open)
            {
                var message = await ReceiveMessage(id.ToString(), queryId.ToString(), webSocket);
                if (message != null)
                {
                    await SendMessageToSockets(message);
                }
            }
        }

        private async Task<string> ReceiveMessage(String id, String queryId, WebSocket webSocket)
        {
            var buffer = new ArraySegment<byte>(new byte[4096]);
            var receivedMessage = await webSocket.ReceiveAsync(buffer, CancellationToken.None);
            if (receivedMessage.MessageType == WebSocketMessageType.Text) {
                // encoding default -> utf-8 -> trim null terminator
                var message = Encoding.Default.GetString(buffer).TrimEnd('\0');
                if (!string.IsNullOrWhiteSpace(message)) {
                    return $"<b style='color:rgba(255, 255, 255, 0.3); cursor:pointer;' alt='{id}' title='{id}'>{queryId}</b>: {message}";
                }
            }  else if (receivedMessage.MessageType == WebSocketMessageType.Binary) { 
                // TODO
            }
            return null;
        }

        private async Task SendMessageToSockets(string message)
        {
            List<SocketConnection> targetConnections;
            // prevent race condition -> Handle()
            lock (websocketConnections) {
                targetConnections = websocketConnections;
            }
            // parallel tasks
            var tasks = targetConnections.Select(async websocketConnection => { 
                await websocketConnection.WebSocket.SendAsync(new ArraySegment<byte>(Encoding.Default.GetBytes(message)), WebSocketMessageType.Text, true, CancellationToken.None); 
            });
            await Task.WhenAll(tasks);
        }

    }

    public class SocketConnection
    {
        public Guid Id { get; set; }
        public WebSocket WebSocket { get; set; }
    }
}
