//------------------------------------------------------------------------------
// <auto-generated>
//    此代码是根据模板生成的。
//
//    手动更改此文件可能会导致应用程序中发生异常行为。
//    如果重新生成代码，则将覆盖对此文件的手动更改。
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebJSON
{
    using System;
    using System.Collections.Generic;
    
    public partial class post
    {
        public int postid { get; set; }
        public string posttitle { get; set; }
        public Nullable<System.DateTime> date { get; set; }
        public string text { get; set; }
        public Nullable<int> userid { get; set; }
        public Nullable<int> anonymity { get; set; }
    
        public virtual user user { get; set; }
    }
}
