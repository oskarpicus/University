using System.IO;
using log4net;
using log4net.Config;

namespace ProiectCShMPP.domain.validator
{
    public class UserValidator : IValidator<long,User>
    {
        private static readonly ILog _logger = LogManager.GetLogger(typeof(UserValidator));

        static UserValidator()
        {
            XmlConfigurator.Configure(new FileInfo("log4net.xml"));
        }
        
        public void Validate(User entity)
        {
            string errors = "";
            if (!ValidateUsername(entity))
                errors += "Invalid username\n";
            if (!ValidatePassword(entity))
                errors += "Invalid password\n";
            if (errors != "")
            {
                _logger.Error("User Validation: "+errors);
                throw new ValidationException(errors);
            }
        }

        /// <summary>
        /// Method for verifying the username of a user
        /// </summary>
        /// <param name="user"> user to check </param>
        /// <returns>
        /// - true, if user's username is not empty
        /// - false, otherwise
        /// </returns>
        private bool ValidateUsername(User user)
        {
            return user.Username != "";
        }

        /// <summary>
        /// Method for verifying the password of a user
        /// </summary>
        /// <param name="user"> user to check </param>
        /// <returns>
        /// - true, if user's password is not empty and has at least 5 characters
        /// - false, otherwise
        /// </returns>
        private bool ValidatePassword(User user)
        {
            return user.Password != "" && user.Password.Length>=5;
        }
    }
}