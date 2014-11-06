using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class LectureController : Controller
    {
        DataInfo storedb = new DataInfo();


        public JArray Index()
        {

            DateTime starttime = DateTime.Now;

            IEnumerable<lecture> lectures;
            lectures = storedb.lecture.Where(u => u.lecturetime >starttime).OrderBy(u => u.lecturetime).Take(7);
            string json = JsonConvert.SerializeObject(lectures);
            return JArray.Parse(json);


        }

    }
}
