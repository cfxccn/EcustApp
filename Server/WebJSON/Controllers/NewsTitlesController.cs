using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class NewsTitlesController : Controller
    {
        DataInfo storedb = new DataInfo();
        //
        // GET: /NewsTitles/

        public JArray Index()
        {

            IEnumerable<news> News;
            News = storedb.news.OrderByDescending(u=>u.id).Take(7);
            
            string json = JsonConvert.SerializeObject(News);
            return JArray.Parse(json);
        }

    }
}
