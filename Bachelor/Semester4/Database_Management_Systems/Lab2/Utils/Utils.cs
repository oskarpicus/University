using System;
using System.Configuration;
using System.Linq;

namespace Lab1
{
    public class Utils
    {
        public static string GetResource(Resources key)
        {
            return ConfigurationManager.AppSettings[key.ToString()];
        }

        public static string GetChildIdColumn()
        {
            return GetResource(Resources.ChildColumns)
                .Split(',')[0];
        }

        public static string GetParentIdColumn()
        {
            return GetResource(Resources.ParentColumns)
                .Split(',')[0];
        }
    }
}