//------------------------------------------------------------------------------
// <auto-generated>
//     此代码已从模板生成。
//
//     手动更改此文件可能导致应用程序出现意外的行为。
//     如果重新生成代码，将覆盖对此文件的手动更改。
// </auto-generated>
//------------------------------------------------------------------------------

namespace Admin
{
    using System;
    using System.Collections.Generic;
    
    public partial class weather
    {
        public int id { get; set; }
        public Nullable<System.DateTime> datetime { get; set; }
        public string h12 { get; set; }
        public string h12temp { get; set; }
        public string h12img1 { get; set; }
        public string h12img2 { get; set; }
        public string h24 { get; set; }
        public string h24temp { get; set; }
        public string h24img1 { get; set; }
        public string h24img2 { get; set; }
        public string aqi { get; set; }
        public Nullable<int> pm25 { get; set; }
    }
}