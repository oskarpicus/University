using System;

namespace ProiectCShMPP.domain.validator
{
    public class ValidationException : ApplicationException
    {
        public ValidationException(string message) : base(message)
        {
            
        }
    }
}