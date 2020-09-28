using System;
using System.Net.WebSockets;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebsocketDemo.Websocket;

namespace WebsocketDemo.Controllers
{
    [Route("api/[controller]")]
    public class StreamController : Controller
    {
        public WebsocketHandler WebsocketHandler = null;

        public StreamController(WebsocketHandler websocketHandler)
        {
            WebsocketHandler = websocketHandler;
        }


        [HttpGet]
        public async Task Get(String queryId)
        {
            Guid id = Guid.NewGuid();
            if (string.IsNullOrWhiteSpace(queryId) || queryId == "null") {
                queryId = $"Anon_{id.ToString().Substring(0, 5)}";
            }
            //Console.WriteLine(queryId);
            var context = ControllerContext.HttpContext;
            var isSocketRequest = context.WebSockets.IsWebSocketRequest;

            if (isSocketRequest) {
                WebSocket websocket = await context.WebSockets.AcceptWebSocketAsync();

                await WebsocketHandler.Handle(id, websocket, queryId);
            } else {
                context.Response.StatusCode = 400;
            }
        }
    }
}
