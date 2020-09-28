using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Examify.Models;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;


namespace Examify.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        [HttpPost]
        public IActionResult _DisplayTableAjax(string needle)
        {
            ExamModel.needle = needle;
            return PartialView();
        }
        
        public IActionResult Index()
        {
            ViewBag.Message = "foo";
            return View();
        }

        public IActionResult AddExam()
        {
            return View();
        }

        public IActionResult ListExams()
        {
            ExamModel.needle = "";
            return View();
        }

        public IActionResult Ajax()
        {
            ExamModel.needle = "";
            return View();
        }

        [HttpPost]
        public IActionResult Ajax(int examId)
        {
            if (ExamModel.deleteExam(examId))
            {
                return Json(new { status = "success", message = "exam deleted" });
            }
            return Json(new { status = "failed", message = "exam not deleted" });
        }

        public IActionResult CompleteExam(int examId, int status)
        {
            if (ExamModel.completeExam(examId, status))
            {
                return Json(new { status = "success", message = "exam updated" });
            }
            return Json(new { status = "failed", message = "exam not updated" });
        }

        [HttpPost]
        public IActionResult DeleteExam(int examId)
        {
            if (ExamModel.deleteExam(examId))
            {
                return RedirectToAction("ListExams");
            }
            return RedirectToAction("Index");
        }

        [HttpPost]
        public IActionResult AddExam(ExamModel model)
        {
            if (ModelState.IsValid)
            {
                if (ExamModel.insertNewExam(model)) {
                    return RedirectToAction("ListExams");
                }
            }
            return View();
        }

        [HttpPost]
        public IActionResult _ExamForm(ExamModel model)
        {
            if (ModelState.IsValid)
            {
                if (ExamModel.insertNewExam(model))
                {
                    return Json(new { status = "success", message = "exam saved" });
                }
            }
            return PartialView();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
