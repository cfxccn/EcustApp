using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class JobDetailsController : Controller
    {
        DataInfo storedb = new DataInfo();
        //
        // GET: /JobDetails/

        public JArray Index(int id)
        {

            IEnumerable<job> Jobs;
            Jobs = storedb.job.OrderByDescending(u => u.id==id).Take(1);
            string json = JsonConvert.SerializeObject(Jobs);
            return JArray.Parse(json);
        }

    }
}
