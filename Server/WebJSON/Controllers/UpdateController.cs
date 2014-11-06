using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class UpdateController : Controller
    {
        //
        // GET: /Update/

        public ActionResult Index()
        {

            var path = "D:\\ftp\\ApkFile\\usta-debug.apk";
            var name = Path.GetFileName(path);
            return File(path, "application/zip-x-compressed", name);
            //var path = "D:\\ftp\\ApkFile\\usta.apk";
            //var name = Path.GetFileName(path);
            //FileStream s = new FileStream(path, FileMode.Open);
            //return File(s, "application/x-zip-compressed", Url.Encode(name));

        }

    }
}
