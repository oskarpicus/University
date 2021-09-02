using log4net;
using ProiectCShMPP.domain;
using ProiectCShMPP.repository;

namespace ProiectCShMPP.service
{
    public class UserService
    {
        private readonly IUserRepository _repository;
        private static readonly ILog Logger = LogManager.GetLogger(typeof(UserService));

        public UserService(IUserRepository repository)
        {
            _repository = repository;
        }

        /// <summary>
        /// Method for finding a user based on its username or password (e.g. login)
        /// </summary>
        /// <param name="username">the desired username</param>
        /// <param name="password">the desired password</param>
        /// <returns>
        /// - null, if there is no User with username and password
        /// - the user, otherwise
        /// </returns>
        public User Find(string username, string password)
        {
            Logger.InfoFormat("Entry find user {0} {1}", username, password);
            User result = _repository.FindByUsernamePassword(username, password);
            Logger.InfoFormat("Exit find user {0}", result);
            return result;
        }
        
    }
}