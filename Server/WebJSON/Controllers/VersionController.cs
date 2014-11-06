using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class VersionController : Controller
    {
        DataInfo storedb = new DataInfo();

        public int Index()
        {
            return (int)storedb.version.Max(i => i.build);

        }

    }
}
