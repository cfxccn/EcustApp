using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class NewsDetailsController : Controller
    {
        DataInfo storedb = new DataInfo();
        //
        // GET: /NewsDetails/
        public JArray Index(int id)
        {
            IEnumerable<news> News;
            News = storedb.news.Where(u => u.id ==id).Take(1);
            string json = JsonConvert.SerializeObject(News);
            return JArray.Parse(json);
        }
    }
}
