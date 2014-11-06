using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    
    public class JobsTitlesController : Controller
    {
        DataInfo storedb = new DataInfo();
        //
        // GET: /Jobs/

        public JArray Index()
        {
            IEnumerable<job> Jobs;
            Jobs = storedb.job.OrderByDescending(u => u.id).Take(8);
            string json = JsonConvert.SerializeObject(Jobs);
            return JArray.Parse(json);
        }
    }
}
