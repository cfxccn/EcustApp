using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class RoomController : Controller
    {
        //
        // GET: /Room/
        DataInfo storedb = new DataInfo();

        public JArray Index(string day, string time ,string roomLocation)
        {
            IEnumerable<ROOM> room;
            room = storedb.ROOM.SqlQuery(

           "SELECT * FROM  [dbo].[ROOM] WHERE ROOM.ROOMLOCATION ='" + roomLocation + "' AND ROOM.ROOMID NOT IN ("
            +"SELECT ROOMID  FROM [dbo].[ROOMUSE_TEACHER]" +
  "WHERE [ROOMUSE_TEACHER].WEEKDAYTIME like '" + day + time + "%' )").Take(10);


            // roomUseTeachers = storedb.ROOMUSE_TEACHER.Where(u => u.WEEKDAYTIME.Substring(0, 1) != day)or().Take(10);

            string json = JsonConvert.SerializeObject(room);
            return JArray.Parse(json);

        }

    }
}
