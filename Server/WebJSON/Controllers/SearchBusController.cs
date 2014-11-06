using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class SearchBusController : Controller
    {

        DataInfo storedb = new DataInfo();
        //
        // GET: /SearchBus/

        public JArray Index(string day,string type,string route)
        {


            IEnumerable<busInfo> businfos;
            businfos = storedb.busInfo.Where(u => u.busType == type).Where(i => i.day == day).Where(n=>n.route==route);
            string json = JsonConvert.SerializeObject(businfos);
            return JArray.Parse(json);
        }

    }
}
