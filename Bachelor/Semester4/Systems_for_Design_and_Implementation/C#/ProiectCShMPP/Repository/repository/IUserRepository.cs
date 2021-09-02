using System;
using ProiectCShMPP.domain;

namespace ProiectCShMPP.repository
{
    public interface IUserRepository : IRepository<long, User>
    {
        /// <summary>
        /// Method for finding a user based on its username or password (e.g. login)
        /// </summary>
        /// <param name="username">the desired username</param>
        /// <param name="password">the desired password</param>
        /// <returns>
        /// - null, if there is no User with username and password
        /// - the user, otherwise
        /// </returns>
        User FindByUsernamePassword(String username, String password);
    }
}