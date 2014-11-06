using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace WebJSON.Controllers
{
    public class WeatherController : Controller
    {
        DataInfo storedb = new DataInfo();


        public JObject Index()
        {
            weather weather;
            weather = storedb.weather.OrderByDescending(u=>u.id).First();
            string json = JsonConvert.SerializeObject(weather);
            JObject result = JObject.Parse(json);

            return JObject.Parse(json);
        }

    }
}
