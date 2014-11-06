using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class LectureDetailsController : Controller
    {
        //
        // GET: /LectureDetail/
        DataInfo storedb = new DataInfo();

        public JArray Index(int id)
        {
            IEnumerable<lecture> lectures;
            lectures = storedb.lecture.OrderByDescending(u => u.id == id).Take(1);
            string json = JsonConvert.SerializeObject(lectures);
            return JArray.Parse(json);
        }

    }
}
