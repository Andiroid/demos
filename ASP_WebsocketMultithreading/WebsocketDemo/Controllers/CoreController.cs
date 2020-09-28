using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebsocketDemo.Websocket;

namespace WebsocketDemo.Controllers
{
    public class CoreController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public IActionResult FrameContent()
        {
            return View();
        }
    }
}